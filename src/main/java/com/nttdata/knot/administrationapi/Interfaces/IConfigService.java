package com.nttdata.knot.administrationapi.Interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;

public interface IConfigService {

    Mono<Object> getConfig() throws JsonProcessingException;

}