package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BlueprintConfigALMCodeAnalysis {
    private List<BlueprintConfigTool> tool;

    public BlueprintConfigALMCodeAnalysis() {
    }

    public BlueprintConfigALMCodeAnalysis(List<BlueprintConfigTool> tool) {
        this.tool = tool;
    }
}
