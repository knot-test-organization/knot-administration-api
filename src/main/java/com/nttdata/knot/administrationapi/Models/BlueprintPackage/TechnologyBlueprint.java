package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TechnologyBlueprint {
    private String id;
    private String name;
    private String label;
    private String blueprint;

    public TechnologyBlueprint() {
    }

    public TechnologyBlueprint(String id, String name, String label, String blueprint) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.blueprint = blueprint;
    }
}
