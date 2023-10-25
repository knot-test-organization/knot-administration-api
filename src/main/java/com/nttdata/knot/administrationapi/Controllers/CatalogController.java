package com.nttdata.knot.administrationapi.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.knot.administrationapi.Interfaces.ICatalogService;

import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final ICatalogService catalogService;
    // private final ArgoCDService argoCDService;
    // private final IAzureService azureService;


    @Autowired
    public CatalogController(ICatalogService catalogService) {
        this.catalogService = catalogService;
        // this.argoCDService = argoCDService;
        // this.azureService= azureService;
    }

    @GetMapping
    public ResponseEntity<Mono<List<Object>>> getCatalog() throws JsonProcessingException {      
        var catalog = catalogService.getCatalog();
        return ResponseEntity.ok(catalog);
    }

}
