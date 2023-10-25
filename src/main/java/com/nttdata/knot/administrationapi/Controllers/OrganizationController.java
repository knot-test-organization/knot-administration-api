package com.nttdata.knot.administrationapi.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.knot.administrationapi.Interfaces.IOrganizationService;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Area;
import com.nttdata.knot.administrationapi.Models.OrganizationPackage.Organization;

// import com.nttdata.knot.baseapi.Models.ArgoCdPackage.Application.ArgoCdApplication;
// import com.nttdata.knot.baseapi.Models.ArgoCdPackage.Project.ArgoCdProject;
// import com.nttdata.knot.baseapi.Models.UserPackage.UserFront;
// import com.nttdata.knot.baseapi.Services.ArgoCDService;
import reactor.core.publisher.Mono;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private final IOrganizationService organizationService;
    // private final ArgoCDService argoCDService;
    // private final IAzureService azureService;


    @Autowired
    public OrganizationController(IOrganizationService organizationService) {
        this.organizationService = organizationService;
        // this.argoCDService = argoCDService;
        // this.azureService= azureService;
    }

    @GetMapping
    public ResponseEntity<Mono<List<Object>>> getOrganizationList() throws JsonProcessingException {      
        var organizationList = organizationService.getOrganizationList();
        return ResponseEntity.ok(organizationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Organization>> getOrganizationByName(@PathVariable String id) throws JsonProcessingException {      
        var organization = organizationService.getOrganizationByName(id);
        return ResponseEntity.ok(organization);
    }

    @PostMapping
    public ResponseEntity<Mono<Boolean>> createOrganization(@RequestBody Organization organization) throws JsonProcessingException {      
        var canCreateNewOrganization = organizationService.createOrganization(organization);
        return ResponseEntity.ok(canCreateNewOrganization);
    }

    @PutMapping
    public ResponseEntity<Mono<Organization>> updateOrganization(@RequestBody Organization organization) throws JsonProcessingException {
        var updatedOrganization = organizationService.updatedOrganization(organization);
        return ResponseEntity.ok(updatedOrganization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Organization>> deleteOrganization(@PathVariable String id) throws JsonProcessingException {      
        var newOrganization = organizationService.deleteOrganization(id);
        return ResponseEntity.ok(newOrganization);
    }

    @GetMapping("{idOrganization}/areas")
    public ResponseEntity<Mono<List<Area>>> getAreaList(@PathVariable String idOrganization) throws JsonProcessingException {
        var areaList = organizationService.getAreaList(idOrganization);
        return ResponseEntity.ok(areaList);
    }

    @PostMapping("/{idOrganization}/areas")
    public ResponseEntity<Mono<Boolean>> createArea(@PathVariable String idOrganization, @RequestBody Area area) throws JsonProcessingException {
        var canCreateNewArea = organizationService.createArea(area, idOrganization);
        return ResponseEntity.ok(canCreateNewArea);
    }

    @PutMapping("/{idOrganization}/areas")
    public ResponseEntity<Mono<Area>> updateArea(@PathVariable String idOrganization, @RequestBody Area area) throws JsonProcessingException {
        var updatedArea = organizationService.updatedArea(area, idOrganization);
        return ResponseEntity.ok(updatedArea);
    }

    @DeleteMapping("/{idOrganization}/areas/{idArea}")
    public ResponseEntity<Mono<Boolean>> deleteArea(@PathVariable String idOrganization, @PathVariable String idArea) throws JsonProcessingException {
        var canDeleteArea = organizationService.deleteArea(idOrganization, idArea);
        return ResponseEntity.ok(canDeleteArea);
    }

}
//     @GetMapping("/test")
//     public ResponseEntity<Mono<String>> test() throws SSLException {
//         var userList = argoCDService.getArgoCDApplicationAsync("knot-api");
//         return ResponseEntity.ok(userList);
//     }
   
//     @PostMapping("/test2")
//     public ResponseEntity<Mono<ArgoCdApplication>> test(@RequestBody ArgoCdApplication argoCDApplication) throws SSLException {
//         var userList = argoCDService.createArgoCDApplicationAsync(argoCDApplication);
//         return ResponseEntity.ok(userList);
//     }

//     @PostMapping("/test3")
//     public ResponseEntity<Mono<Void>> test1() throws SSLException {
//         var userList = azureService.createSecretAsync("github-knot-kv", "base-api", "123456");
//         return ResponseEntity.ok(userList);
//     }
// }
