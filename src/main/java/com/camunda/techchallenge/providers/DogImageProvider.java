package com.camunda.techchallenge.providers;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DogImageProvider implements AnimalImageProvider {

    private final RestClient restClient = RestClient.create();

    @Override
    public byte[] fetchImage() {        
        // Implementation to fetch dog image
        return restClient.get()
                    .uri("https://place.dog/300/200")
                    .retrieve()
                    .body(byte[].class);
    }

    
}
