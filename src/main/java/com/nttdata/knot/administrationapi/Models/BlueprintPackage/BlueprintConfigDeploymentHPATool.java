package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlueprintConfigDeploymentHPATool {
    private String id;
    private String name;
    private String description;
    private Integer minReplicas;
    private Integer maxReplicas;
    private Integer cpu;
    private Integer memory;

    public BlueprintConfigDeploymentHPATool() {
    }

    public BlueprintConfigDeploymentHPATool(String id, String name, String description, Integer minReplicas, Integer maxReplicas, Integer cpu,
            Integer memory) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minReplicas = minReplicas;
        this.maxReplicas = maxReplicas;
        this.cpu = cpu;
        this.memory = memory;
    }
    
}