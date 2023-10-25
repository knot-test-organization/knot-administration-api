package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGithubTagResponse {
    @JsonProperty("sha")
    private String sha;
    @JsonProperty("url")
    private String url;
    @JsonProperty("tagger")
    private Person tagger;
    @JsonProperty("object")
    private ObjectTag object;
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("message")
    private String message;

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

    public Person getTagger() {
        return tagger;
    }

    public void setTagger(Person tagger) {
        this.tagger = tagger;
    }

    public ObjectTag getObject() {
        return object;
    }

    public void setObject(ObjectTag object) {
        this.object = object;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
