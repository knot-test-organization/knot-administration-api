package com.nttdata.knot.administrationapi;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class ConfigurationService {
    
    // Github variables
    @Value("${github.token}")
    private  String githubToken = "";

    private HttpClient httpClient;

    @Autowired
    public ConfigurationService(HttpClient httpClient) throws SSLException {

        this.httpClient = httpClient;
    }
    
    // Github WebClient
    @Bean
    public WebClient githubWebClient() throws SSLException {

        return WebClient.builder()
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "HttpRequestsSample")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "token " + this.githubToken)
                .build();
    }

}
