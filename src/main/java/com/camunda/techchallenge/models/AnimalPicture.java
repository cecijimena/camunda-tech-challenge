package com.camunda.techchallenge.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

import java.time.Instant;

@Entity
public class AnimalPicture {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String animalType;
    private byte[] imageData;
    private Instant createdAt;

    protected AnimalPicture() {
        // Default constructor for JPA
    }
    
    public AnimalPicture(String animalType, byte[] imageData, Instant createdAt) {
        this.animalType = animalType;
        this.imageData = imageData;
        this.createdAt = createdAt;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public String getAnimalType() {
        return animalType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Long getId() {
        return id;
    }
}
