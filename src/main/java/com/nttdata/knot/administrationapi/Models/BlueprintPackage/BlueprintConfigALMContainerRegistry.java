package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter @Setter
public class BlueprintConfigALMContainerRegistry {
    private List<BlueprintConfigTool> tool;

    public BlueprintConfigALMContainerRegistry() {
    }

    public BlueprintConfigALMContainerRegistry(List<BlueprintConfigTool> tool) {
        this.tool = tool;
    }
}
