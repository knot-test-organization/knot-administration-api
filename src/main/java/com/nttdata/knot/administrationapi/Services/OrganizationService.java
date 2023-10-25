package com.nttdata.knot.administrationapi.Services;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.nttdata.knot.administrationapi.Interfaces.IGithubService;
import com.nttdata.knot.administrationapi.Interfaces.IOrganizationService;
import com.nttdata.knot.administrationapi.Models.BlueprintPackage.*;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.Committer;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.CreateGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest.DeleteGithubFileRequest;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Area;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Organization;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.OrganizationList;

import java.util.*;

import reactor.core.publisher.Mono;

@Service
public class OrganizationService implements IOrganizationService {

        private IGithubService githubService;
        YAMLFactory yamlFactory;
        ObjectMapper objectMapper;

        public OrganizationService(IGithubService githubService) {
                this.githubService = githubService;
                this.yamlFactory = new YAMLFactory();
                this.yamlFactory.configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false);
                this.objectMapper = new ObjectMapper(yamlFactory);
        }

        @Override
        public Mono<List<Object>> getOrganizationList() throws JsonProcessingException {
                List<Object> jsonStringList = new ArrayList<Object>();

                // ObjectMapper jsonMapper = new ObjectMapper();

                var existingOrganizationsList = this.githubService
                                .getGithubFileAsync("knot-blueprints-base", "organization.yaml").block();

                TypeReference<Map<String, List<String>>> typeRef = new TypeReference<Map<String, List<String>>>() {
                };

                Map<String, List<String>> organizationListResponse = objectMapper.readValue(
                                new String(Base64.getDecoder()
                                                .decode(existingOrganizationsList.getContent().replaceAll("\\s", ""))),
                                typeRef);

                List<String> organizationsList = organizationListResponse.get("organizations");

                for (String organization : organizationsList) {
                        var existingDepartement = this.githubService
                                        .getGithubFileAsync("knot-blueprints-base", organization + "/metadata.yaml")
                                        .block();
                        String departementData = new String(
                                        Base64.getDecoder()
                                                        .decode(existingDepartement.getContent().replaceAll("\\s", "")),
                                        StandardCharsets.UTF_8);
                        // Parse the YAML string into a Java object
                        Object objDep = objectMapper.readValue(departementData, Object.class);

                        // Serialize the Java object into a JSON string
                        // String jsonString = jsonMapper.writeValueAsString(objDep);

                        jsonStringList.add(objDep);

                }

                return Mono.just(jsonStringList);
        }

        @Override
        public Mono<Organization> getOrganizationByName(String id) throws JsonProcessingException {
                String repoName = "knot-blueprints-base";
                String metadataFilePath = id + "/metadata.yaml";
                var metadataFile = this.githubService.getGithubFileAsync(repoName, metadataFilePath).block();

                Organization organization = this.objectMapper.readValue(new String(Base64.getDecoder()
                                .decode(metadataFile.getContent()
                                                .replaceAll("\\s", "")),
                                StandardCharsets.UTF_8), Organization.class);

                return Mono.just(organization);
        }

        @Override
        public Mono<Boolean> createOrganization(Organization organization) throws JsonProcessingException {
                String repoName = "knot-blueprints-base";
                String valuesFilePath = "organization.yaml";
                var existingOrganizationsList = this.githubService.getGithubFileAsync(repoName, valuesFilePath).block();

                OrganizationList organizationList = this.objectMapper.readValue(new String(Base64.getDecoder()
                                .decode(existingOrganizationsList.getContent()
                                                .replaceAll("\\s", "")),
                                StandardCharsets.UTF_8), OrganizationList.class);

                // Set the date in the organization
                String pattern = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());
                organization.setCreationDate(date);

                Boolean canCreateNewOrganization = true;

                for (String name : organizationList.getOrganizations()) {
                        if (name.equals(organization.getId())) {
                                canCreateNewOrganization = false;
                        }
                }

                if (canCreateNewOrganization) {
                        Blueprint blueprintBase = new Blueprint(
                                        "netcore",
                                        ".Net Core Rest Service",
                                        "microservices",
                                        date);
                        List<Blueprint> blueprints = new ArrayList<>();
                        blueprints.add(blueprintBase);
                        List<Area> areas = new ArrayList<>();
                        areas.add(new Area("netcore", ".Net Core Rest Service", organization.getCreationDate(),
                                        blueprints));
                        organization.setAreas(areas);

                        organizationList.getOrganizations()
                                        .add(organization.getId());

                        // Add the organization the list of organizations
                        String contentInBase64String = Base64.getEncoder()
                                        .encodeToString(this.objectMapper
                                                        .writeValueAsString(organizationList)
                                                        .getBytes(StandardCharsets.UTF_8));

                        updateGithubFileRequest(
                                        contentInBase64String,
                                        existingOrganizationsList.getSha(),
                                        "Add new Organization, with name " + organization.getName()
                                                        + " to the organization list",
                                        repoName,
                                        valuesFilePath);

                        // Create the organization folder
                        String contentInBase64StringOrganization = Base64.getEncoder()
                                        .encodeToString(this.objectMapper
                                                        .writeValueAsString(organization)
                                                        .getBytes(StandardCharsets.UTF_8));

                        String metadataFilePath = organization.getId() + "/metadata.yaml";

                        createGithubFileRequest(
                                        contentInBase64StringOrganization,
                                        "Add new Organization, with name " + organization.getName(),
                                        repoName,
                                        metadataFilePath);

                        // Create the base area folder
                        BlueprintConfig blueprintConfig = InitializeBlueprint();

                        String contentInBase64StringArea = Base64.getEncoder()
                                        .encodeToString(this.objectMapper
                                                        .writeValueAsString(blueprintConfig)
                                                        .getBytes(StandardCharsets.UTF_8));

                        String filePath = organization.getId() + "/"
                                        + organization.getId() + "_" + organization.getAreas().get(0).getId()
                                        + "/blueprint-" + organization.getAreas().get(0).getBlueprints().get(0).getId()
                                        + ".yaml";

                        createGithubFileRequest(
                                        contentInBase64StringArea,
                                        "Add new Base Area, into the " + organization.getName() + " organization",
                                        repoName,
                                        filePath);
                }

                return Mono.just(canCreateNewOrganization);
        }

        @Override
        public Mono<Organization> updatedOrganization(Organization organization) throws JsonProcessingException {
                String repoName = "knot-blueprints-base";
                String metadataFilePath = organization.getId() + "/metadata.yaml";

                // Update the metadata file
                var metadataFile = this.githubService.getGithubFileAsync(repoName, metadataFilePath).block();

                String contentInBase64String = Base64.getEncoder()
                                .encodeToString(this.objectMapper
                                                .writeValueAsString(organization)
                                                .getBytes(StandardCharsets.UTF_8));

                updateGithubFileRequest(
                                contentInBase64String,
                                metadataFile.getSha(),
                                "Updating Organization, with name " + organization.getName(),
                                repoName,
                                metadataFilePath);

                return Mono.just(organization);
        }

        @Override
        public Mono<Organization> deleteOrganization(String id) throws JsonProcessingException {
                String repoName = "knot-blueprints-base";
                String valuesFilePath = "organization.yaml";

                var organization = getOrganizationByName(id).block();

                var existingOrganizationsList = this.githubService.getGithubFileAsync(repoName, valuesFilePath).block();

                OrganizationList organizationList = this.objectMapper.readValue(new String(Base64.getDecoder()
                                .decode(existingOrganizationsList.getContent()
                                                .replaceAll("\\s", "")),
                                StandardCharsets.UTF_8), OrganizationList.class);

                organizationList.getOrganizations().remove(id);

                String contentInBase64String = Base64.getEncoder()
                                .encodeToString(this.objectMapper
                                                .writeValueAsString(organizationList)
                                                .getBytes(StandardCharsets.UTF_8));

                updateGithubFileRequest(
                                contentInBase64String,
                                existingOrganizationsList.getSha(),
                                "Removing Organization, with name " + organization.getName()
                                                + " to the organization list",
                                repoName,
                                valuesFilePath);

                var metadataFile = this.githubService.getGithubFileAsync(repoName, id + "/metadata.yaml").block();
                var metadataFilePath = id + "/metadata.yaml";

                deleteGithubFileRequest(
                                metadataFile.getSha(),
                                "Removing Organization, with name " + organization.getName(),
                                repoName,
                                metadataFilePath);

                for (Area area : organization.getAreas()) {
                        var fileYaml = id + "/" + id + "_" + area.getId();

                        for (Blueprint blueprint : area.getBlueprints()) {
                                var file = this.githubService
                                                .getGithubFileAsync(repoName,
                                                                fileYaml + "/blueprint-" + blueprint.getId() + ".yaml")
                                                .block();
                                String filePath = fileYaml + "/blueprint-" + blueprint.getId() + ".yaml";

                                deleteGithubFileRequest(
                                                file.getSha(),
                                                "Removing Area, with name " + area.getName(),
                                                repoName,
                                                filePath);
                        }
                }

                return Mono.just(organization);
        }

        @Override
        public Mono<List<Area>> getAreaList(String idOrganization) throws JsonProcessingException {
                Organization organization = getOrganizationByName(idOrganization).block();

                List<Area> areaList = new ArrayList<>();

                if (organization != null) {
                        areaList = organization.getAreas();
                }

                return Mono.just(areaList);
        }

        @Override
        public Mono<Boolean> createArea(Area newArea, String idOrganization) throws JsonProcessingException {
                Organization organization = getOrganizationByName(idOrganization).block();

                boolean canCreateNewArea = true;

                if (organization != null) {
                        for (Area area : organization.getAreas()) {
                                if (area.getId().equals(newArea.getId())) {
                                        canCreateNewArea = false;
                                }
                        }

                        if (canCreateNewArea) {
                                // Set the date in the area
                                String pattern = "dd/MM/yyyy";
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                                String date = simpleDateFormat.format(new Date());

                                newArea.setCreationDate(date);

                                organization.getAreas().add(newArea);

                                updatedOrganization(organization).block();
                        }
                }

                return Mono.just(canCreateNewArea);
        }

        @Override
        public Mono<Area> updatedArea(Area updatedArea, String idOrganization) throws JsonProcessingException {
                Organization organization = getOrganizationByName(idOrganization).block();

                if (organization != null) {
                        Area deletedArea = new Area();
                        for (Area area : organization.getAreas()) {
                                if (area.getId().equals(updatedArea.getId())) {
                                        deletedArea = area;
                                }
                        }
                        organization.getAreas().remove(deletedArea);
                        organization.getAreas().add(updatedArea);

                        updatedOrganization(organization).block();
                }

                return Mono.just(updatedArea);
        }

        @Override
        public Mono<Boolean> deleteArea(String idOrganization, String idArea) throws JsonProcessingException {
                String repoName = "knot-blueprints-base";

                Organization organization = getOrganizationByName(idOrganization).block();

                boolean canDeleteArea = false;

                if (organization != null) {
                        Area deletedArea = new Area();
                        for (Area area : organization.getAreas()) {
                                if (area.getId().equals(idArea)) {
                                        deletedArea = area;
                                        canDeleteArea = true;
                                }
                        }

                        if (canDeleteArea) {
                                organization.getAreas().remove(deletedArea);

                                updatedOrganization(organization).block();

                                var fileYaml = idOrganization + "/" + idOrganization + "_" + idArea;

                                for (Blueprint blueprint : deletedArea.getBlueprints()) {
                                        var file = this.githubService
                                                        .getGithubFileAsync(repoName,
                                                                        fileYaml + "/blueprint-" + blueprint.getId()
                                                                                        + ".yaml")
                                                        .block();

                                        String filePath = fileYaml + "/blueprint-" + blueprint.getId() + ".yaml";

                                        deleteGithubFileRequest(
                                                        file.getSha(),
                                                        "Removing Area, with name " + deletedArea.getName(),
                                                        repoName,
                                                        filePath);
                                }
                        }
                }

                return Mono.just(canDeleteArea);
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
                gitBranching.add(new BlueprintConfigTool("gitflow", "Gitflow"));
                gitBranching.add(new BlueprintConfigTool("trunkbasedevelopment", "Trunk Base Development"));
                BlueprintConfigALMOrchestrator blueprintConfigALMOrchestrator = new BlueprintConfigALMOrchestrator(
                                tools, gitBranching);
                List<BlueprintConfigTool> containerTools = new ArrayList<>();
                containerTools.add(new BlueprintConfigTool("acr", "ACR"));
                BlueprintConfigALMContainerRegistry blueprintConfigALMContainerRegistry = new BlueprintConfigALMContainerRegistry(
                                containerTools);
                List<BlueprintConfigTool> codeAnalysisTools = new ArrayList<>();
                codeAnalysisTools.add(new BlueprintConfigTool("sonarqube", "SonarQube"));
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
