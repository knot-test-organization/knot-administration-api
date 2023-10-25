package com.nttdata.knot.administrationapi.Services;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.nttdata.knot.administrationapi.Interfaces.IGithubService;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubBranch;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubCommits.GetGithubCommitsResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.CreateGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.DeleteGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse.CreateGithubFileResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse.DeleteGithubFileResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse.GetGithubFileResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.CreateGithubTagRequest;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.CreateGithubTagResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.GetGithubRefsTagResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.GetGithubTagResponse;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.Person;
import com.nttdata.knot.administrationapi.Models.UserPackage.GetUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;

import reactor.core.publisher.Mono;

@Service
public class GithubService implements IGithubService {

    private final Logger logger = LoggerFactory.getLogger(GithubService.class);
    public WebClient webClient;

    // github variables
    @Value("${github.reposUrl}")
    private String githubUrl = "";

    // github variables
    @Value("${github.organization}")
    private String organization = "";

    // github variables
    @Value("${github.repositoryName}")
    private String repositoryName = "";

    public GetGithubFileResponse getGithubFileResponse;
    public CreateGithubFileResponse createGithubFileResponse;
    public CreateGithubTagRequest createGithubTagRequest;
    public DeleteGithubFileResponse deleteGithubFileResponse;
    public List<GetGithubCommitsResponse> getGithubCommitsResponse;
    public List<GetGithubTagResponse> getGithubTagResponse;
    public List<GetUser> getGithubUsersResponse;
    public List<String> getDevcontainerListResponse;
    public List<GithubBranch> getGithubBranches;

    @Autowired
    public GithubService(@Qualifier("githubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<GetGithubFileResponse> getGithubFileAsync(String repoName, String name) {
        String uri = this.githubUrl + "/" + this.organization + "/" + repoName + "/contents/" + name;
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(GetGithubFileResponse.class)
                .doOnSuccess(response -> logger.info("Request sent"))
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }

    @Override
    public Mono<String> createGithubFileAsync(CreateGithubFileRequest createGithubFile, String repoName, String name) {
        String uri = this.githubUrl + "/" + this.organization + "/" + repoName + "/contents/" + name;
        return webClient.put()
                .uri(uri)
                .bodyValue(createGithubFile)
                .retrieve()
                .toBodilessEntity()
                .map(response -> response.getStatusCode().toString())
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }

    @Override
    public Mono<String> deleteGithubFileAsync(DeleteGithubFileRequest deleteGithubFile, String repoName, String name) {
        String uri = this.githubUrl + "/" + this.organization + "/" + repoName + "/contents/" + name;
        return webClient.method(HttpMethod.DELETE)
                .uri(uri)
                .body(Mono.just(deleteGithubFile), DeleteGithubFileRequest.class)
                .exchangeToMono(response -> {
                    logger.info("Request sent");
                    return Mono.just(response.statusCode().toString());
                })
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }

    @Override
    public Mono<String> createGithubTagAsync(String repoName, String source, String target, String message) {
        String uri = this.githubUrl + "/" + this.organization + "/" + repoName + "/git/tags";
        String[] sourceContainer = { source };
        if (source.contains("-rc") || source.contains("-RC")) {
            String url2 = this.githubUrl + "/" + this.organization + "/" + repoName + "/git/refs/tags/" + source;
            webClient.get()
                    .uri(url2)
                    .retrieve()
                    .bodyToMono(GetGithubRefsTagResponse.class)
                    .doOnNext(response -> sourceContainer[0] = response.getObject().getSha())
                    .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()))
                    .subscribe();
            // reset the value of source
            source = sourceContainer[0];
        }

        CreateGithubTagRequest createGithubTagRequest = new CreateGithubTagRequest();
        createGithubTagRequest.setTag(target);
        createGithubTagRequest.setMessage(message);
        createGithubTagRequest.setObject(source);
        createGithubTagRequest.setType("commit");
        Person person = new Person();
        person.setName("github-actions[bot]");
        person.setEmail("41898282+github-actions[bot]@users.noreply.github.com");
        person.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").format(ZonedDateTime.now()));
        createGithubTagRequest.setTagger(person);

        return webClient.post()
                .uri(uri)
                .bodyValue(createGithubTagRequest)
                .retrieve()
                .bodyToMono(CreateGithubTagResponse.class)
                .flatMap(response -> {
                    String url = this.githubUrl + "/" + this.organization + "/" + repoName + "/git/refs";
                    return webClient.post()
                            .uri(url)
                            .bodyValue("{\"ref\":\"refs/tags/" + target + "\", \"sha\":\"" + response.getSha() + "\"}")
                            .retrieve()
                            .toBodilessEntity()
                            .map(res -> {
                                logger.info("Request sent");
                                return res.getStatusCode().toString();
                            });
                })
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }

    @Override
    public Mono<List<GetGithubCommitsResponse>> getGithubCommitsAsync(String repoName) {
        String uri = this.githubUrl + "/" + this.organization + "/" + repoName + "/commits";
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GetGithubCommitsResponse.class)
                .collectList()
                .doOnNext(response -> logger.info("Request sent"))
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }

    @Override
    public Mono<List<GetGithubTagResponse>> getGithubTagAsync(String repoName) {
        String uri = this.githubUrl + "/" + this.organization + "/" + repoName + "/tags";
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GetGithubTagResponse.class)
                .collectList()
                .doOnNext(response -> logger.info("Request sent"))
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }

    @Override
    public Mono<List<GetUser>> getGithubUserList() {
        String uri = "https://api.github.com/orgs/" + this.organization + "/members";
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GetUser.class)
                .collectList()
                .doOnNext(response -> logger.info("Request sent"))
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }

    @Override
    public Mono<List<String>> listDevContainers() {
        String repoName = "knot-devcontainers-versions";
        String fileName = "metadata.yaml";

        GetGithubFileResponse getGithubFileResponse = getGithubFileAsync(repoName, fileName).block();

        logger.info("The response from GitHub is " + getGithubFileResponse.getName());
        String base64String = getGithubFileResponse.getContent().replaceAll("\\s", "");
        try {
            byte[] content = Base64.getDecoder().decode(base64String);
            String contentAsString = new String(content, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            List<String> devcontainerListResponse = objectMapper.readValue(contentAsString,
List.class);

            return Mono.just(devcontainerListResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mono<List<GithubBranch>> getGithubBranches(String repoName, String userToken) {
        String uri = this.githubUrl + "/" + this.organization + "/" + repoName + "/branches";
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GithubBranch.class)
                .collectList()
                .doOnNext(response -> {
                    logger.info("Request sent");
                    response.forEach(item -> item.setLabel(item.getBranchName()));
                })
                .doOnError(e -> logger.error("Unable to process request, exception is " + e.getMessage()));
    }
}
