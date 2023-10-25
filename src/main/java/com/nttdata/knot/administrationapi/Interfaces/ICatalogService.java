package com.nttdata.knot.administrationapi.Interfaces;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;

public interface ICatalogService {

    Mono<List<Object>> getCatalog() throws JsonProcessingException;

}