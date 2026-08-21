package com.camunda.techchallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.camunda.techchallenge.models.AnimalPicture;

public interface AnimalPictureRepository extends JpaRepository<AnimalPicture, Long> {
    
    AnimalPicture findTopByAnimalTypeOrderByCreatedAtDesc(String animalType);
    
}
