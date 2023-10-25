package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObjectTag {
    @JsonProperty("type")
    private String type;
    @JsonProperty("sha")
    private String sha;
    @JsonProperty("url")
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}