package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlueprintConfig {
    private BlueprintConfigCode code;
    private BlueprintConfigALM alm;
    private BlueprintConfigIaC iac;
    private BlueprintConfigCollaboration collaboration;
    private BlueprintConfigDeployment deployment;

    public BlueprintConfig() {
    }

    public BlueprintConfig(BlueprintConfigCode code, BlueprintConfigALM alm, BlueprintConfigIaC iac, BlueprintConfigCollaboration collaboration, BlueprintConfigDeployment deployment) {
        this.code = code;
        this.alm = alm;
        this.iac = iac;
        this.collaboration = collaboration;
        this.deployment = deployment;
    }
}
