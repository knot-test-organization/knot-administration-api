package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.Person;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.Tree;

public class Commit {
    @JsonProperty("sha")
    private String sha;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("author")
    private Person author;

    @JsonProperty("committer")
    private Person committer;

    @JsonProperty("message")
    private String message;

    @JsonProperty("tree")
    private Tree tree;

    @JsonProperty("parents")
    private List<Parent> parents;

    @JsonProperty("verification")
    private Verification verification;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Person getCommitter() {
        return committer;
    }

    public void setCommitter(Person committer) {
        this.committer = committer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Tree getTree() {
        return tree;
    }

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }

    
}
