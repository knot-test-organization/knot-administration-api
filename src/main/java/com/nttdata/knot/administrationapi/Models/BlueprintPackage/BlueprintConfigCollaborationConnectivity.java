package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BlueprintConfigCollaborationConnectivity {
    private List<BlueprintConfigTool> tool;

    public BlueprintConfigCollaborationConnectivity() {
    }

    public BlueprintConfigCollaborationConnectivity(List<BlueprintConfigTool> tool) {
        this.tool = tool;
    }
}
