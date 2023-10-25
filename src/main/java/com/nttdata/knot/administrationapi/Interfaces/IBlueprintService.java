package com.nttdata.knot.administrationapi.Interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.knot.administrationapi.Models.BlueprintPackage.Blueprint;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IBlueprintService {

    Mono<List<Blueprint>> getBlueprintList(String idOrganization, String idArea) throws JsonProcessingException;
    Mono<Boolean> createBlueprint(Blueprint blueprint, String idOrganization, String idArea) throws JsonProcessingException;

    Mono<Blueprint> updatedBlueprint(Blueprint updatedBlueprint, String idOrganization, String idArea) throws JsonProcessingException;

    Mono<Blueprint> deleteBlueprint(String idOrganization, String idArea, String idBlueprint) throws JsonProcessingException;

}