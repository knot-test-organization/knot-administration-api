package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlueprintConfigALMOrchestratorTool {
    private String id;
    private String name;
    private List<BlueprintConfigTool> pipelineTemplates;

    public BlueprintConfigALMOrchestratorTool() {
    }

    public BlueprintConfigALMOrchestratorTool(String id, String name, List<BlueprintConfigTool> pipelineTemplates) {
        this.id = id;
        this.name = name;
        this.pipelineTemplates = pipelineTemplates;
    }
}