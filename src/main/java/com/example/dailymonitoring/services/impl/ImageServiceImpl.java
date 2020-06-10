package com.example.dailymonitoring.services.impl;

import com.example.dailymonitoring.constants.Constants;
import com.example.dailymonitoring.exceptions.ResourceNotFoundException;
import com.example.dailymonitoring.services.ImageService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Sirghi Mihail
 */
@Service
public class ImageServiceImpl implements ImageService {

  @Override
  public byte[] getImageByName(String name) {
    try {
      File imgPath = new File(Constants.IMAGE_PATH + name + Constants.IMAGE_EXTENSION);

      return Files.readAllBytes(imgPath.toPath());
    } catch (IOException e) {
      e.printStackTrace();
      throw new ResourceNotFoundException();
    }
  }
}
