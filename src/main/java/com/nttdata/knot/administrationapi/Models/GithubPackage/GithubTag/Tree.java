package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tree {
    @JsonProperty("url")
    private String url;
    @JsonProperty("sha")
    private String sha;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
