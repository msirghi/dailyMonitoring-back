package com.example.dailymonitoring.controllers;

import com.example.dailymonitoring.controllers.api.ImageApi;
import com.example.dailymonitoring.services.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sirghi Mihail
 */
@RestController
public class ImageController implements ImageApi {

  private final ImageService imageService;

  public ImageController(ImageService imageService) {
    this.imageService = imageService;
  }

  @Override
  public ResponseEntity<byte[]> getImageByName(String name) {
    return ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(imageService.getImageByName(name));
  }
}
