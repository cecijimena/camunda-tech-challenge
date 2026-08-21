package com.camunda.techchallenge.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.camunda.techchallenge.models.AnimalPicture;
import com.camunda.techchallenge.service.AnimalPictureService;
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

    @PostMapping
    public List<AnimalPicture> fetchAndSave(String animalType, int count) {
        List<AnimalPicture> pictures = animalPictureService.fetchAndSave(animalType, count);
        return pictures;
    }

    @GetMapping(value="/{animalType}/last", produces = "image/jpeg")
    public byte[] getLast(@PathVariable String animalType) {
        AnimalPicture lastPicture = animalPictureService.getLast(animalType);
        return lastPicture.getImageData();
    }
}
