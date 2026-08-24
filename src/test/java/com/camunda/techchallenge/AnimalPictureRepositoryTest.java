package com.camunda.techchallenge;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import com.camunda.techchallenge.models.AnimalPicture;
import com.camunda.techchallenge.repository.AnimalPictureRepository;

@DataJpaTest
public class AnimalPictureRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private AnimalPictureRepository repository;

    @Test
    void findTopByAnimalTypeOrderByCreatedAtDesc_returnsMostRecentPictureOfType() {
        
        AnimalPicture firstCat =entityManager.persist(new AnimalPicture("cat", new byte[] {1}, Instant.parse("2026-08-20T10:00:00Z")));
        AnimalPicture secondCat = entityManager.persist(new AnimalPicture("cat", new byte[] {2}, Instant.parse("2026-08-22T10:00:00Z")));
        AnimalPicture firstDog = entityManager.persist(new AnimalPicture("dog", new byte[] {3}, Instant.parse("2026-08-23T10:00:00Z")));

        AnimalPicture result = repository.findTopByAnimalTypeOrderByCreatedAtDesc("cat");

        assertThat(result.getId()).isEqualTo(secondCat.getId());
        assertThat(result.getImageData()).isEqualTo(new byte[] {2});
    }

    @Test
    void findTopByAnimalTypeOrderByCreatedAtDesc_returnsNullWhenNoneSaved() {
        AnimalPicture result = repository.findTopByAnimalTypeOrderByCreatedAtDesc("dog");

        assertThat(result).isNull();
    }
}
