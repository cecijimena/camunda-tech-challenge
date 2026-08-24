package com.camunda.techchallenge;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.camunda.techchallenge.models.AnimalPicture;
import com.camunda.techchallenge.providers.CatImageProvider;
import com.camunda.techchallenge.providers.DogImageProvider;
import com.camunda.techchallenge.providers.DuckImageProvider;
import com.camunda.techchallenge.repository.AnimalPictureRepository;
import com.camunda.techchallenge.service.AnimalPictureService;

@ExtendWith(MockitoExtension.class)
public class AnimalPictureServiceTest {

    @Mock
    private AnimalPictureRepository repository;
    @Mock
    private CatImageProvider catProvider;
    @Mock
    private DogImageProvider dogProvider;
    @Mock
    private DuckImageProvider duckProvider;
    
    private AnimalPictureService service;

    @Test
    void fetchAndSave_fetchesAndSavesRequestedNumberOfPictures() {
        service = new AnimalPictureService(repository, catProvider, dogProvider, duckProvider);
        byte[] imageData = {1, 2, 3};
        when(catProvider.fetchImage()).thenReturn(imageData);
        when(repository.save(any(AnimalPicture.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<AnimalPicture> result = service.fetchAndSave("cat", 3);

        assertThat(result).hasSize(3);
        assertThat(result).allSatisfy(picture -> {
            assertThat(picture.getAnimalType()).isEqualTo("cat");
            assertThat(picture.getImageData()).isEqualTo(imageData);
        });
        verify(catProvider, times(3)).fetchImage();
        verify(repository, times(3)).save(any(AnimalPicture.class));
    }
    
}
