package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlueprintConfigTool {
    private String id;
    private String name;

    public BlueprintConfigTool() {
    }

    public BlueprintConfigTool(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
