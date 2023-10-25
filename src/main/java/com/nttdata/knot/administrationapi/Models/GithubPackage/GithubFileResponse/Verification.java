package com.nttdata.knot.administrationapi.Models.GithubPackage.GithubFileResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Verification {
    @JsonProperty("verified")
    private String verified;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("signature")
    private String signature;

    @JsonProperty("payload")
    private String payload;

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    
}

