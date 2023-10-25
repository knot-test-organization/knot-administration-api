package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGithubTagResponse {
    @JsonProperty("node_id")
    private String nodeId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("zipball_url")
    private String zipballUrl;
    @JsonProperty("tarball_url")
    private String tarballUrl;
    @JsonProperty("commit")
    private Tree commit;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipballUrl() {
        return zipballUrl;
    }

    public void setZipballUrl(String zipballUrl) {
        this.zipballUrl = zipballUrl;
    }

    public String getTarballUrl() {
        return tarballUrl;
    }

    public void setTarballUrl(String tarballUrl) {
        this.tarballUrl = tarballUrl;
    }

    public Tree getCommit() {
        return commit;
    }

    public void setCommit(Tree commit) {
        this.commit = commit;
    }
}