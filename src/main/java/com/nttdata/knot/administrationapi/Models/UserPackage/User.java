package com.nttdata.knot.administrationapi.Models.UserPackage;

public class User {
    private String id;
    private String name;
    private String role;
    private String azureADUser;
    private String githubRole;
    private String externalSecretName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAzureADUser() {
        return azureADUser;
    }

    public void setAzureADUser(String azureADUser) {
        this.azureADUser = azureADUser;
    }

    public String getGithubRole() {
        return githubRole;
    }

    public void setGithubRole(String githubRole) {
        this.githubRole = githubRole;
    }

    public String getExternalSecretName() {
        return externalSecretName;
    }

    public void setExternalSecretName(String externalSecretName) {
        this.externalSecretName = externalSecretName;
    }
}

