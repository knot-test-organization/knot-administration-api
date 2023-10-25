package com.nttdata.knot.administrationapi.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.nttdata.knot.administrationapi.Interfaces.IBlueprintService;
import com.nttdata.knot.administrationapi.Interfaces.IGithubService;
import com.nttdata.knot.administrationapi.Interfaces.IOrganizationService;
import com.nttdata.knot.administrationapi.Models.BlueprintPackage.*;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.Committer;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.CreateGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.DeleteGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Area;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Organization;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BlueprintService implements IBlueprintService {

        private final String repoName = "knot-blueprints-base";
        private IGithubService githubService;

        private IOrganizationService organizationService;
        YAMLFactory yamlFactory;
        ObjectMapper objectMapper;

        public BlueprintService(IGithubService githubService, IOrganizationService organizationService) {
                this.organizationService = organizationService;
                this.githubService = githubService;
                this.yamlFactory = new YAMLFactory();
                this.yamlFactory.configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
                this.objectMapper = new ObjectMapper(yamlFactory);
        }

        @Override
        public Mono<List<Blueprint>> getBlueprintList(String idOrganization, String idArea)
                        throws JsonProcessingException {
                Organization organization = this.organizationService.getOrganizationByName(idOrganization).block();

                List<Blueprint> blueprintList = new ArrayList<>();

                if (organization != null) {
                        for (Area area : organization.getAreas()) {
                                if (area.getId().equals(idArea)) {
                                        blueprintList = area.getBlueprints();
                                }
                        }

                }

                return Mono.just(blueprintList);

        }

        @Override
        public Mono<Boolean> createBlueprint(Blueprint blueprint, String idOrganization, String idArea)
                        throws JsonProcessingException {
                Organization organization = this.organizationService.getOrganizationByName(idOrganization).block();

                Boolean canCreateNewBlueprint = true;

                for (Area area : organization.getAreas()) {
                        if (area.getId().equals(idArea)) {
                                for (Blueprint selectedBlueprint : area.getBlueprints()) {
                                        if (selectedBlueprint.getId().equals(blueprint.getId())) {
                                                canCreateNewBlueprint = false;
                                        }
                                }
                                if (canCreateNewBlueprint) {
                                        String pattern = "dd/MM/yyyy";
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                                        String date = simpleDateFormat.format(new Date());

                                        blueprint.setCreationDate(date);

                                        area.getBlueprints().add(blueprint);
                                }
                        }
                }

                if (canCreateNewBlueprint) {
                        // Update metadata yaml file
                        String metadataFilePath = idOrganization + "/metadata.yaml";

                        var metadataFile = this.githubService.getGithubFileAsync(repoName, metadataFilePath).block();

                        String contentInBase64StringOrganization = Base64.getEncoder()
                                        .encodeToString(this.objectMapper
                                                        .writeValueAsString(organization)
                                                        .getBytes(StandardCharsets.UTF_8));

                        updateGithubFileRequest(
                                        contentInBase64StringOrganization,
                                        metadataFile.getSha(),
                                        "Add new Organization, with name " + organization.getName(),
                                        this.repoName,
                                        metadataFilePath);

                        // Create the blueprint yaml
                        BlueprintConfig blueprintConfig = InitializeBlueprint();

                        String contentInBase64StringArea = Base64.getEncoder()
                                        .encodeToString(this.objectMapper
                                                        .writeValueAsString(blueprintConfig)
                                                        .getBytes(StandardCharsets.UTF_8));

                        String filePath = idOrganization + "/"
                                        + idOrganization + "_" + idArea
                                        + "/blueprint-" + blueprint.getId() + ".yaml";

                        createGithubFileRequest(
                                        contentInBase64StringArea,
                                        "Add new Blueprint " + blueprint.getName() + " to " + idArea
                                                        + " Area, into the " + organization.getName() + " Organization",
                                        repoName,
                                        filePath);
                }

                return Mono.just(canCreateNewBlueprint);
        }

        @Override
        public Mono<Blueprint> updatedBlueprint(Blueprint updatedBlueprint, String idOrganization, String idArea)
                        throws JsonProcessingException {
                Organization organization = this.organizationService.getOrganizationByName(idOrganization).block();

                for (Area area : organization.getAreas()) {
                        if (area.getId().equals(idArea)) {
                                Blueprint deletedBlueprint = new Blueprint();
                                for (Blueprint blueprint : area.getBlueprints()) {
                                        if (blueprint.getId().equals(updatedBlueprint.getId())) {
                                                deletedBlueprint = blueprint;
                                        }
                                }
                                area.getBlueprints().remove(deletedBlueprint);
                                area.getBlueprints().add(updatedBlueprint);

                                this.organizationService.updatedOrganization(organization).block();
                        }
                }

                return Mono.just(updatedBlueprint);
        }

        @Override
        public Mono<Blueprint> deleteBlueprint(String idOrganization, String idArea, String idBlueprint)
                        throws JsonProcessingException {
                Organization organization = this.organizationService.getOrganizationByName(idOrganization).block();

                Blueprint deletedBlueprint = new Blueprint();

                for (Area area : organization.getAreas()) {
                        if (area.getId().equals(idArea)) {
                                for (Blueprint blueprint : area.getBlueprints()) {
                                        if (blueprint.getId().equals(idBlueprint)) {
                                                deletedBlueprint = blueprint;
                                        }
                                }
                                area.getBlueprints().remove(deletedBlueprint);

                                this.organizationService.updatedOrganization(organization).block();

                                var fileYaml = idOrganization + "/" + idOrganization + "_" + idArea;

                                var file = this.githubService
                                                .getGithubFileAsync(repoName,
                                                                fileYaml + "/blueprint-" + deletedBlueprint.getId()
                                                                                + ".yaml")
                                                .block();

                                String filePath = fileYaml + "/blueprint-" + deletedBlueprint.getId() + ".yaml";

                                deleteGithubFileRequest(
                                                file.getSha(),
                                                "Removing Blueprint, with name " + deletedBlueprint.getName(),
                                                repoName,
                                                filePath);
                        }
                }

                return Mono.just(deletedBlueprint);
        }

        private Committer setCommitter() {
                Committer committer = new Committer();
                committer.setEmail("41898282+github-actions[bot]@users.noreply.github.com");
                committer.setName("github-actions[bot]");
                return committer;
        }

        private CreateGithubFileRequest createGithubFileRequest(String contentInBase64String, String message,
                        String repoName, String filePath) {
                Committer committer = setCommitter();

                CreateGithubFileRequest createGithubFileRequest = new CreateGithubFileRequest();
                createGithubFileRequest
                                .setMessage(message);
                createGithubFileRequest.setCommitter(committer);
                createGithubFileRequest.setContent(contentInBase64String);

                this.githubService.createGithubFileAsync(createGithubFileRequest,
                                repoName,
                                filePath)
                                .block();

                return createGithubFileRequest;
        }

        private CreateGithubFileRequest updateGithubFileRequest(String contentInBase64String, String sha,
                        String message, String repoName, String filePath) {
                Committer committer = setCommitter();

                CreateGithubFileRequest updateGithubFileRequest = new CreateGithubFileRequest();
                updateGithubFileRequest
                                .setMessage(message);
                updateGithubFileRequest.setCommitter(committer);
                updateGithubFileRequest.setContent(contentInBase64String);
                updateGithubFileRequest.setSha(sha);

                this.githubService.createGithubFileAsync(updateGithubFileRequest,
                                repoName,
                                filePath)
                                .block();

                return updateGithubFileRequest;
        }

        private DeleteGithubFileRequest deleteGithubFileRequest(String sha, String message, String repoName,
                        String filePath) {
                Committer committer = setCommitter();

                DeleteGithubFileRequest deleteGithubFileRequest = new DeleteGithubFileRequest();
                deleteGithubFileRequest
                                .setMessage(message);
                deleteGithubFileRequest.setCommitter(committer);
                deleteGithubFileRequest.setSha(sha);

                this.githubService.deleteGithubFileAsync(deleteGithubFileRequest,
                                repoName,
                                filePath)
                                .block();

                return deleteGithubFileRequest;
        }

        private BlueprintConfig InitializeBlueprint() {
                List<BlueprintConfigCodeRepository> blueprintConfigCodeTechnologies = new ArrayList<>();
                blueprintConfigCodeTechnologies
                                .add(new BlueprintConfigCodeRepository("netcore", ".Net Core Rest Service"));
                BlueprintConfigCodeRepository blueprintConfigCodeRepository = new BlueprintConfigCodeRepository(
                                "github", "Github");
                List<BlueprintConfigCodeRepository> blueprintConfigCodeRepositories = new ArrayList<>();
                List<String> devcontainers = new ArrayList<>();
                blueprintConfigCodeRepositories.add(blueprintConfigCodeRepository);
                devcontainers.add("dotnet-6.0-copilot");
                devcontainers.add("dotnet-6.0");
                devcontainers.add("dotnet-7.0");
                devcontainers.add("dotnet-6.0-postgres");
                devcontainers.add("dotnet-7.0-postgres");
                List<BlueprintConfigTool> pipelineTemplates = new ArrayList<>();
                pipelineTemplates.add(
                                new BlueprintConfigTool("knot-netcore", ".Net Core Rest Service Pipeline"));
                List<BlueprintConfigALMOrchestratorTool> tools = new ArrayList<>();
                tools.add(new BlueprintConfigALMOrchestratorTool("tekton", "Tekton", pipelineTemplates));
                List<BlueprintConfigTool> gitBranching = new ArrayList<>();
                gitBranching.add(new BlueprintConfigTool("trunkbasedevelopment", "Trunk Base Development"));
                BlueprintConfigALMOrchestrator blueprintConfigALMOrchestrator = new BlueprintConfigALMOrchestrator(
                                tools, gitBranching);
                List<BlueprintConfigTool> containerTools = new ArrayList<>();
                containerTools.add(new BlueprintConfigTool("acr", "ACR"));
                BlueprintConfigALMContainerRegistry blueprintConfigALMContainerRegistry = new BlueprintConfigALMContainerRegistry(
                                containerTools);
                List<BlueprintConfigTool> codeAnalysisTools = new ArrayList<>();
                codeAnalysisTools.add(new BlueprintConfigTool("sonarqube", "Sonarqube"));
                BlueprintConfigALMCodeAnalysis blueprintConfigALMCodeAnalysis = new BlueprintConfigALMCodeAnalysis(
                                codeAnalysisTools);
                List<DB_Variable> databases = new ArrayList<>();
                List<Tier> tiers = new ArrayList<>();
                tiers.add(new Tier("Basic", "B_Gen5_1"));
                tiers.add(new Tier("General Purpose", "GP_Gen5_2"));
                tiers.add(new Tier("Memory Optimized", "MO_Gen5_2"));
                List<String> versions = new ArrayList<>();
                versions.add("9.5");
                versions.add("9.6");
                versions.add("10");
                versions.add("10.0");
                versions.add("10.2");
                versions.add("11");
                databases.add(new DB_Variable(
                                "postgresql",
                                "Azure Database for PostgreSQL",
                                tiers,
                                versions));
                BlueprintConfigIaCServerless serverless = new BlueprintConfigIaCServerless(false, null);
                List<BlueprintConfigTool> collaborationTools = new ArrayList<>();
                collaborationTools.add(new BlueprintConfigTool("microsoftTeams", "Microsoft Teams"));
                BlueprintConfigCollaborationConnectivity connectivity = new BlueprintConfigCollaborationConnectivity(
                                collaborationTools);
                List<BlueprintConfigDeploymentHPATool> deploymentTools = new ArrayList<>();
                deploymentTools.add(new BlueprintConfigDeploymentHPATool(
                                "bronze", "BRONZE",
                                "The entry-level deployment typology designed for users who require basic functionality and features for your component.",
                                1, 1, null, null));
                deploymentTools.add(new BlueprintConfigDeploymentHPATool(
                                "silver", "SILVER",
                                "The intermediate-level deployment typology, offering a balance between advanced features and ease of use.",
                                1, 2, 70, null));
                deploymentTools.add(new BlueprintConfigDeploymentHPATool(
                                "gold", "GOLD",
                                "The top-tier deployment typology, tailored for enterprise-level applications and organizations with demanding requirements.",
                                2, 4, 60, 550));
                BlueprintConfigDeploymentHPA hpa = new BlueprintConfigDeploymentHPA(deploymentTools);

                BlueprintConfig blueprintConfig = new BlueprintConfig(
                                new BlueprintConfigCode(
                                                blueprintConfigCodeTechnologies,
                                                blueprintConfigCodeRepositories,
                                                devcontainers),
                                new BlueprintConfigALM(
                                                blueprintConfigALMOrchestrator,
                                                blueprintConfigALMContainerRegistry,
                                                blueprintConfigALMCodeAnalysis),
                                new BlueprintConfigIaC(
                                                databases,
                                                serverless),
                                new BlueprintConfigCollaboration(
                                                connectivity),
                                new BlueprintConfigDeployment(
                                                hpa));

                return blueprintConfig;
        }
}
