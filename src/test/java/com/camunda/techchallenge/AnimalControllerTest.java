package com.camunda.techchallenge;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import com.camunda.techchallenge.controller.AnimalController;
import com.camunda.techchallenge.models.AnimalPicture;
import com.camunda.techchallenge.service.AnimalPictureService;

import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {
  
    @Autowired
    private MockMvcTester mvc;
    
    @MockitoBean
    private AnimalPictureService animalPictureService;

    @Test
    void fetchAndSave_returnsPicturesFromService() {
        AnimalPicture picture = new AnimalPicture("cat", new byte[] {1, 2, 3}, Instant.now());
        when(animalPictureService.fetchAndSave(eq("cat"), anyInt())).thenReturn(List.of(picture));

        assertThat(mvc.post().uri("/api/pictures?animalType=cat&count=1"))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$[0].animalType")
                .isEqualTo("cat");
    }

    @Test
    void getLast_returnsImageBytesWithJpegContentType() {
        byte[] imageData = {9, 8, 7};
        AnimalPicture picture = new AnimalPicture("dog", imageData, Instant.now());

        when(animalPictureService.getLast("dog")).thenReturn(picture);

        assertThat(mvc.get().uri("/api/pictures/dog/last"))
                .hasStatusOk()
                .hasContentType(MediaType.IMAGE_JPEG)
                .body()
                .isEqualTo(imageData);
    }
}
