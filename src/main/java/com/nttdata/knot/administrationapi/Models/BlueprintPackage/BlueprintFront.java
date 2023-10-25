package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BlueprintFront {
    private String id;
    private String label;

    public BlueprintFront(String id, String label) {
        this.id = id;
        this.label = label;
    }
}
