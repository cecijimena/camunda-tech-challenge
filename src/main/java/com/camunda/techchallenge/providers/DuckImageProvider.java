package com.camunda.techchallenge.providers;

import org.springframework.web.client.RestClient;
import org.springframework.stereotype.Component;

@Component
public class DuckImageProvider implements AnimalImageProvider {
    
    private final RestClient restClient = RestClient.create();

    @Override
    public byte[] fetchImage() {
        // Implementation to fetch cat image
        return restClient.get()
                    .uri("https://random-d.uk/api/randomimg")
                    .retrieve()
                    .body(byte[].class);
    

    }
}