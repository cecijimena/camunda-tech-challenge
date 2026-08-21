package com.camunda.techchallenge.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.camunda.techchallenge.models.AnimalPicture;
import com.camunda.techchallenge.providers.AnimalImageProvider;
import com.camunda.techchallenge.providers.CatImageProvider;
import com.camunda.techchallenge.providers.DogImageProvider;
import com.camunda.techchallenge.providers.DuckImageProvider;
import com.camunda.techchallenge.repository.AnimalPictureRepository;

@Service
public class AnimalPictureService {
    private final Map<String, AnimalImageProvider> providers;
    private final AnimalPictureRepository repository;

    public AnimalPictureService(AnimalPictureRepository repository, 
                                CatImageProvider catProvider,
                                DogImageProvider dogProvider,
                                DuckImageProvider duckProvider) {
        this.providers = Map.of("dog", dogProvider, 
                                "cat", catProvider, 
                                "duck", duckProvider);
        this.repository = repository;       
    }
    

    public List<AnimalPicture> fetchAndSave(String animalType, int count) {
        AnimalImageProvider provider = providers.get(animalType);
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported animal type: " + animalType);
        }
        List<AnimalPicture> saved = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            byte[] imageData = provider.fetchImage();
            AnimalPicture picture = new AnimalPicture(animalType, imageData, Instant.now());
            saved.add(repository.save(picture));
        }
        return saved;
    }

    public AnimalPicture getLast(String animalType) {
        return repository.findTopByAnimalTypeOrderByCreatedAtDesc(animalType);
    }
}