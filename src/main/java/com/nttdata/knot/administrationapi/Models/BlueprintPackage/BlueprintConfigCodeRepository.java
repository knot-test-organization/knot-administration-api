package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlueprintConfigCodeRepository {
    private String id;
    private String label;

    public BlueprintConfigCodeRepository() {
    }

    public BlueprintConfigCodeRepository(String id, String label) {
        this.id = id;
        this.label = label;
    }
}
