package com.camunda.techchallenge.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.camunda.techchallenge.providers.CatImageProvider;
import com.camunda.techchallenge.providers.DogImageProvider;
import com.camunda.techchallenge.providers.DuckImageProvider;
import org.springframework.http.MediaType;

@RestController
public class AnimalController {
    private final DogImageProvider dogImageProvider;
    private final CatImageProvider catImageProvider;
    private final DuckImageProvider duckImageProvider;

    public AnimalController(CatImageProvider catImageProvider, DogImageProvider dogImageProvider, DuckImageProvider duckImageProvider) {
        this.catImageProvider = catImageProvider;
        this.dogImageProvider = dogImageProvider;
        this.duckImageProvider = duckImageProvider;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping(value="/cat", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getCat() {
        return catImageProvider.fetchImage();
    }

    @GetMapping(value="/dog", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getDog() {
        return dogImageProvider.fetchImage();
    }

     @GetMapping(value="/duck", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getDuck() {
        return duckImageProvider.fetchImage();
    }
}
