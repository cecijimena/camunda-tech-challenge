package com.camunda.techchallenge.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.camunda.techchallenge.models.AnimalPicture;
import com.camunda.techchallenge.service.AnimalPictureService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/pictures")
public class AnimalController {
    private final AnimalPictureService animalPictureService;

    public AnimalController(AnimalPictureService animalPictureService) {
        this.animalPictureService = animalPictureService;
        
    }

    @Operation(
        summary = "Fetch and save animal pictures",
        description = "Fetches a specified number of animal pictures of the given type and saves them to the database."
    )
    @PostMapping
    public List<AnimalPicture> fetchAndSave(String animalType, int count) {
        List<AnimalPicture> pictures = animalPictureService.fetchAndSave(animalType, count);
        return pictures;
    }

    @Operation(
        summary = "Get the last saved animal picture",
        description = "Retrieves the last saved animal picture of the specified type from the database."
    )   
    @GetMapping(value="/{animalType}/last", produces = "image/jpeg")
    public byte[] getLast(@PathVariable String animalType) {
        AnimalPicture lastPicture = animalPictureService.getLast(animalType);
        return lastPicture.getImageData();
    }
}
