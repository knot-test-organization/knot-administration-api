package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubCommits;

public class VerificationCommit {
    private Boolean Verified;
    private String Reason;
    private String Signature;
    private String Payload;

    public Boolean getVerified() {
        return Verified;
    }

    public void setVerified(Boolean verified) {
        Verified = verified;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getPayload() {
        return Payload;
    }

    public void setPayload(String payload) {
        Payload = payload;
    }
}

