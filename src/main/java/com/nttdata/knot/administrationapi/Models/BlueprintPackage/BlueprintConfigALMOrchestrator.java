package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter @Setter
public class BlueprintConfigALMOrchestrator {
    private List<BlueprintConfigALMOrchestratorTool> tool;
    private List<BlueprintConfigTool> gitBranching;

    public BlueprintConfigALMOrchestrator() {
    }

    public BlueprintConfigALMOrchestrator(List<BlueprintConfigALMOrchestratorTool> tool, List<BlueprintConfigTool> gitBranching) {
        this.tool = tool;
        this.gitBranching = gitBranching;
    }
}
