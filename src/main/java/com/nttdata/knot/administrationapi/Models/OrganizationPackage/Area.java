package com.nttdata.knot.administrationapi.Models.OrganizationPackage;
import java.util.List;

import com.nttdata.knot.administrationapi.Models.BlueprintPackage.Blueprint;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Area {
    private String id;
    private String name;
    private String creationDate;
    private List<Blueprint> blueprints;

    public Area() {
    }

    public Area(String id, String name, String creationDate, List<Blueprint> blueprints) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.blueprints = blueprints;
    }
}
