package com.nttdata.knot.administrationapi.Services;

import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.nttdata.knot.administrationapi.Interfaces.IConfigService;
import com.nttdata.knot.administrationapi.Interfaces.IGithubService;

import java.util.*;

import reactor.core.publisher.Mono;

@Service
public class ConfigService implements IConfigService {

        private IGithubService githubService;
        YAMLFactory yamlFactory;
        ObjectMapper objectMapper;
        private String repoName = "knot-administration";

        public ConfigService(IGithubService githubService) {
                this.githubService = githubService;
                this.yamlFactory = new YAMLFactory();
                this.yamlFactory.configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
                this.objectMapper = new ObjectMapper(yamlFactory);
        }

        @Override
        public Mono<Object> getConfig() throws JsonProcessingException {
                var existingConfig = this.githubService
                                .getGithubFileAsync(this.repoName, "config/config.json")
                                .block();
                String config = new String(
                                Base64.getDecoder()
                                                .decode(existingConfig.getContent().replaceAll("\\s", "")),
                                StandardCharsets.UTF_8);

                Object objDep = objectMapper.readValue(config, Object.class);

                return Mono.just(objDep);
        }

        // private Committer setCommitter() {
        // Committer committer = new Committer();
        // committer.setEmail("41898282+github-actions[bot]@users.noreply.github.com");
        // committer.setName("github-actions[bot]");
        // return committer;
        // }

        // private CreateGithubFileRequest createGithubFileRequest(String
        // contentInBase64String, String message, String filePath) {
        // Committer committer = setCommitter();

        // CreateGithubFileRequest createGithubFileRequest = new
        // CreateGithubFileRequest();
        // createGithubFileRequest
        // .setMessage(message);
        // createGithubFileRequest.setCommitter(committer);
        // createGithubFileRequest.setContent(contentInBase64String);

        // this.githubService.createGithubFileAsync(createGithubFileRequest,
        // this.repoName,
        // filePath)
        // .block();

        // return createGithubFileRequest;
        // }

        // private CreateGithubFileRequest updateGithubFileRequest(String
        // contentInBase64String, String sha, String message, String filePath) {
        // Committer committer = setCommitter();

        // CreateGithubFileRequest updateGithubFileRequest = new
        // CreateGithubFileRequest();
        // updateGithubFileRequest
        // .setMessage(message);
        // updateGithubFileRequest.setCommitter(committer);
        // updateGithubFileRequest.setContent(contentInBase64String);
        // updateGithubFileRequest.setSha(sha);

        // this.githubService.createGithubFileAsync(updateGithubFileRequest,
        // this.repoName,
        // filePath)
        // .block();

        // return updateGithubFileRequest;
        // }

        // private DeleteGithubFileRequest deleteGithubFileRequest(String sha, String
        // message, String filePath) {
        // Committer committer = setCommitter();

        // DeleteGithubFileRequest deleteGithubFileRequest = new
        // DeleteGithubFileRequest();
        // deleteGithubFileRequest
        // .setMessage(message);
        // deleteGithubFileRequest.setCommitter(committer);
        // deleteGithubFileRequest.setSha(sha);

        // this.githubService.deleteGithubFileAsync(deleteGithubFileRequest,
        // this.repoName,
        // filePath)
        // .block();

        // return deleteGithubFileRequest;
        // }
}
