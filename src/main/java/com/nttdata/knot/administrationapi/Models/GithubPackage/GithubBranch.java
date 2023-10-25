package com.nttdata.knot.administrationapi.Models.GithubPackage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GithubBranch {
    @JsonProperty("name")
    private String branchName;

    private String label;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    
}

