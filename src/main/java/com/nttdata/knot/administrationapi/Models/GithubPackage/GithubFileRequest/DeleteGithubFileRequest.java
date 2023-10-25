package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteGithubFileRequest {
    @JsonProperty("message")
    private String message;
    @JsonProperty("committer")
    private Committer committer;
    @JsonProperty("sha")
    private String sha;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Committer getCommitter() {
        return committer;
    }

    public void setCommitter(Committer committer) {
        this.committer = committer;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }
}
