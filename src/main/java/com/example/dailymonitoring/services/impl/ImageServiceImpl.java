package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.services.ImageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Sirghi Mihail
 */
@Service
public class ImageServiceImpl implements ImageService {

  @Override
  public InputStreamResource getImageByName(String name) {
    ClassPathResource classPathResource = new ClassPathResource("images/" + name + ".jpeg");
    try {
      return new InputStreamResource(classPathResource.getInputStream());
    } catch (IOException e) {
      throw new ResourceNotFoundException();
    }
  }
}
