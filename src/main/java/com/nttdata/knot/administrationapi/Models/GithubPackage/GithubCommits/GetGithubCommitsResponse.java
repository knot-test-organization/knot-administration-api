package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubCommits;


public class GetGithubCommitsResponse {
    private String SHA;
    private Commits Commit;

    public String getSHA() {
        return SHA;
    }

    public void setSHA(String SHA) {
        this.SHA = SHA;
    }

    public Commits getCommit() {
        return Commit;
    }

    public void setCommit(Commits commit) {
        Commit = commit;
    }
}

