package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateGithubFileRequest {
    @JsonProperty("message")
    private String message;

    @JsonProperty("committer")
    private Committer committer;

    @JsonProperty("content")
    private String content;

    @JsonProperty("sha")
    private String sha;

}
