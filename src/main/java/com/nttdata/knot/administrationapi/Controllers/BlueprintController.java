package com.nttdata.knot.administrationapi.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.knot.administrationapi.Interfaces.IBlueprintService;
import com.nttdata.knot.administrationapi.Models.BlueprintPackage.Blueprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/blueprint")
public class BlueprintController {

    private final IBlueprintService blueprintService;
    // private final ArgoCDService argoCDService;
    // private final IAzureService azureService;


    @Autowired
    public BlueprintController(IBlueprintService blueprintService) {
        this.blueprintService = blueprintService;
        // this.argoCDService = argoCDService;
        // this.azureService= azureService;

    }

    @GetMapping("{idOrganization}/{idArea}")
    public ResponseEntity<Mono<List<Blueprint>>> getAreaList(@PathVariable String idOrganization, @PathVariable String idArea) throws JsonProcessingException {
        var blueprintList = blueprintService.getBlueprintList(idOrganization, idArea);
        return ResponseEntity.ok(blueprintList);
    }

    @PostMapping("/{idOrganization}/{idArea}")
    public ResponseEntity<Mono<Boolean>> createBlueprint(@RequestBody Blueprint blueprint, @PathVariable String idOrganization, @PathVariable String idArea) throws JsonProcessingException {
        var canCreateNewBlueprint = blueprintService.createBlueprint(blueprint, idOrganization, idArea);
        return ResponseEntity.ok(canCreateNewBlueprint);
    }

    @PutMapping("/{idOrganization}/{idArea}")
    public ResponseEntity<Mono<Blueprint>> updateBlueprint(@RequestBody Blueprint blueprint, @PathVariable String idOrganization, @PathVariable String idArea) throws JsonProcessingException {
        var updatedBlueprint = blueprintService.updatedBlueprint(blueprint, idOrganization, idArea);
        return ResponseEntity.ok(updatedBlueprint);
    }

    @DeleteMapping("/{idOrganization}/{idArea}/{idBlueprint}")
    public ResponseEntity<Mono<Blueprint>> deleteBlueprint(@PathVariable String idOrganization, @PathVariable String idArea, @PathVariable String idBlueprint) throws JsonProcessingException {
        var newBlueprint = blueprintService.deleteBlueprint(idOrganization, idArea, idBlueprint);
        return ResponseEntity.ok(newBlueprint);
    }

}