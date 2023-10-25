package com.nttdata.knot.administrationapi.Services;

import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.nttdata.knot.administrationapi.Interfaces.ICatalogService;
import com.nttdata.knot.administrationapi.Interfaces.IGithubService;

import java.util.*;

import reactor.core.publisher.Mono;

@Service
public class CatalogService implements ICatalogService {

        private IGithubService githubService;
        YAMLFactory yamlFactory;
        ObjectMapper objectMapper;
        private String repoName = "knot-administration";
        private List<String> verticalList = new ArrayList<>();

        public CatalogService(IGithubService githubService) {
                this.githubService = githubService;
                this.yamlFactory = new YAMLFactory();
                this.yamlFactory.configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
                this.objectMapper = new ObjectMapper(yamlFactory);
                this.verticalList.add("alm");
                this.verticalList.add("code");
                this.verticalList.add("iac");
                this.verticalList.add("collaboration");
                this.verticalList.add("deployment");
        }

        @Override
        public Mono<List<Object>> getCatalog() throws JsonProcessingException {
                List<Object> catalog = new ArrayList<>();

                // ObjectMapper jsonMapper = new ObjectMapper();

                for (String vertical : this.verticalList) {
                        var existingData = this.githubService
                                        .getGithubFileAsync(this.repoName, "resources/" + vertical + ".json")
                                        .block();
                                        
                        String data = new String(
                                        Base64.getDecoder()
                                                        .decode(existingData.getContent().replaceAll("\\s", "")),
                                        StandardCharsets.UTF_8);

                        Object objDep = objectMapper.readValue(data, Object.class);

                        catalog.add(objDep);
                }

                return Mono.just(catalog);
        }
}
