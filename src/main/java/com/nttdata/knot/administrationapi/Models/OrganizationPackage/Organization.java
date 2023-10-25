package com.nttdata.knot.administrationapi.Models.OrganizationPackage;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Organization {
    private String id;
    private String name;
    private String country;
    private String creationDate;
    private List<Area> areas;
}
    