package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlueprintConfigALM {
    private BlueprintConfigALMOrchestrator orchestrator;
    private BlueprintConfigALMContainerRegistry containerRegistry;
    private BlueprintConfigALMCodeAnalysis codeAnalysis;

    public BlueprintConfigALM() {
    }

    public BlueprintConfigALM(BlueprintConfigALMOrchestrator orchestrator, BlueprintConfigALMContainerRegistry containerRegistry, BlueprintConfigALMCodeAnalysis codeAnalysis) {
        this.orchestrator = orchestrator;
        this.containerRegistry = containerRegistry;
        this.codeAnalysis = codeAnalysis;
    }
}
