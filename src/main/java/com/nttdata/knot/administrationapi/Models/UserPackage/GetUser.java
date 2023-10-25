package com.nttdata.knot.administrationapi.Models.UserPackage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUser {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("login")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

