package com.nttdata.knot.administrationapi.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nttdata.knot.administrationapi.Interfaces.IConfigService;

import reactor.core.publisher.Mono;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigController {

    private final IConfigService configService;

    @Autowired
    public ConfigController(IConfigService configService) {
        this.configService = configService;
    }

    @GetMapping
    public ResponseEntity<Mono<Object>> getConfig() throws JsonProcessingException {      
        var config = this.configService.getConfig();
        return ResponseEntity.ok(config);
    }

}
