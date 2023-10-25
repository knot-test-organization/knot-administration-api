package com.nttdata.knot.administrationapi.Models.BlueprintPackage;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DB_Variable {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tiers")
    private List<Tier> tiers;

    @JsonProperty("versions")
    private List<String> versions;

    public DB_Variable() {
    }

    public DB_Variable(String id, String name, List<Tier> tiers, List<String> versions) {
        this.id = id;
        this.name = name;
        this.tiers = tiers;
        this.versions = versions;
    }
}
