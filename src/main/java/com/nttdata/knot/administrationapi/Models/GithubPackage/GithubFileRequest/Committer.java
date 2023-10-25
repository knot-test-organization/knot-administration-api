package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Committer {
  
    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;
    
}
