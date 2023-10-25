package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGithubFileResponse {
    @JsonProperty("content")
    private CreateGithubFileResponseContent content;

    @JsonProperty("commit")
    private Commit commit;

    public CreateGithubFileResponseContent getContent() {
        return content;
    }

    public void setContent(CreateGithubFileResponseContent content) {
        this.content = content;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    
}