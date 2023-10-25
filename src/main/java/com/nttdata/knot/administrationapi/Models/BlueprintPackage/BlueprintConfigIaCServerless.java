package com.nttdata.knot.administrationapi.Models.BlueprintPackage;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlueprintConfigIaCServerless {
    @JsonProperty("createFunctionApp")
    private Boolean createFunctionApp;

    @JsonProperty("applicationStack")
    private String applicationStack;

    public BlueprintConfigIaCServerless() {
    }

    public BlueprintConfigIaCServerless(Boolean createFunctionApp, String applicationStack) {
        this.createFunctionApp = createFunctionApp;
        this.applicationStack = applicationStack;
    }
}
