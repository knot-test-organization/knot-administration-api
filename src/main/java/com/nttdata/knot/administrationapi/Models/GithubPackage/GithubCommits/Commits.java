package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubCommits;

import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.Person;
import com.nttdata.knot.administrationapi.Models.GithubPackage.GithubTag.Tree;

public class Commits {
    private Person Author;
    private Person Commiter;
    private String Message;
    private Tree Tree;
    private String Url;
    private int Comment_Count;
    private VerificationCommit Verification;

    public Person getAuthor() {
        return Author;
    }

    public void setAuthor(Person author) {
        Author = author;
    }

    public Person getCommiter() {
        return Commiter;
    }

    public void setCommiter(Person commiter) {
        Commiter = commiter;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Tree getTree() {
        return Tree;
    }

    public void setTree(Tree tree) {
        Tree = tree;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getComment_Count() {
        return Comment_Count;
    }

    public void setComment_Count(int comment_Count) {
        Comment_Count = comment_Count;
    }

    public VerificationCommit getVerification() {
        return Verification;
    }

    public void setVerification(VerificationCommit verification) {
        Verification = verification;
    }
}
