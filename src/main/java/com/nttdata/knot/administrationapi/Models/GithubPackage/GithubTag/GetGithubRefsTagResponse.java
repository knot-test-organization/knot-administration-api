package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGithubRefsTagResponse {
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("url")
    private String url;
    @JsonProperty("object")
    private ObjectTag object;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ObjectTag getObject() {
        return object;
    }

    public void setObject(ObjectTag object) {
        this.object = object;
    }
}
