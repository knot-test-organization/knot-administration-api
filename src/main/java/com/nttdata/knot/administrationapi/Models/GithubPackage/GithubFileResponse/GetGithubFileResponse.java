package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetGithubFileResponse {
    @JsonProperty("type")
    private String type;

    @JsonProperty("encoding")
    private String encoding;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("name")
    private String name;

    @JsonProperty("path")
    private String path;

    @JsonProperty("content")
    private String content;

    @JsonProperty("sha")
    private String sha;

    @JsonProperty("url")
    private String url;

    @JsonProperty("git_url")
    private String gitUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("download_url")
    private String downloadUrl;

    @JsonProperty("_links")
    private Links _links;


    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public String getGitUrl() {
        return gitUrl;
    }

    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Links get_links() {
        return _links;
    }

    public void set_links(Links _links) {
        this._links = _links;
    }

   
}
