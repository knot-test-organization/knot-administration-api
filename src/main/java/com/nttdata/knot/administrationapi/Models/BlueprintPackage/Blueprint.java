package com.nttdata.knot.administrationapi.Models.BlueprintPackage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Blueprint {
    private String id;
    private String name;
    private String type;

    private String creationDate;

    public Blueprint() {
    }

    public Blueprint(String id, String name, String type, String creationDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.creationDate = creationDate;
    }
}
