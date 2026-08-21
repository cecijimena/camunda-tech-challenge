package com.camunda.techchallenge.providers;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CatImageProvider implements AnimalImageProvider {
   
    private final RestClient restClient = RestClient.create();

    @Override
    public byte[] fetchImage() {
        // Implementation to fetch cat image
        return restClient.get()
                    .uri("https://cataas.com/cat")
                    .retrieve()
                    .body(byte[].class);
    }
    
}
