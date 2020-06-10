package com.example.dailymonitoring.services;

import org.springframework.core.io.InputStreamResource;

/**
 * @author Sirghi Mihail
 */
public interface ImageService {

  byte[]  getImageByName(String name);
}
