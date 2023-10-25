package com.nttdata.knot.administrationapi.Interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Area;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Organization;

import reactor.core.publisher.Mono;

public interface IOrganizationService {

    Mono<List<Object>> getOrganizationList() throws JsonProcessingException;

    Mono<Organization> getOrganizationByName(String id) throws JsonProcessingException;

    Mono<Boolean> createOrganization(Organization organization) throws JsonProcessingException;

    Mono<Organization> updatedOrganization(Organization organization) throws JsonProcessingException;

    Mono<Organization> deleteOrganization(String id) throws JsonProcessingException;

    Mono<List<Area>> getAreaList(String idOrganization) throws JsonProcessingException;

    Mono<Boolean> createArea(Area area, String idOrganization) throws JsonProcessingException;

    Mono<Area> updatedArea(Area area, String idOrganization) throws JsonProcessingException;

    Mono<Boolean> deleteArea(String idOrganization, String idArea) throws JsonProcessingException;

}