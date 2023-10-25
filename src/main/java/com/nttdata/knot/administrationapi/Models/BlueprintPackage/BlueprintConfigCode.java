package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BlueprintConfigCode {

    private List<BlueprintConfigCodeRepository> technology;
    private List<BlueprintConfigCodeRepository> repository;
    private List<String> devcontainers;

    public BlueprintConfigCode() {
    }

    public BlueprintConfigCode(List<BlueprintConfigCodeRepository> technology, List<BlueprintConfigCodeRepository> repository, List<String> devcontainers) {
        this.technology = technology;
        this.repository = repository;
        this.devcontainers = devcontainers;
    }
}
