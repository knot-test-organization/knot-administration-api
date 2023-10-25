package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGithubTagRequest {
    @JsonProperty("tag")
    private String tag;
    @JsonProperty("message")
    private String message;
    @JsonProperty("object")
    private String object;
    @JsonProperty("type")
    private String type;
    @JsonProperty("tagger")
    private Person tagger;

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

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Person getTagger() {
        return tagger;
    }

    public void setTagger(Person tagger) {
        this.tagger = tagger;
    }
}