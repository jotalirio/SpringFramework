package com.example.springboot.app.services;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

  public Resource loadImage(String fileName);
  public String createImage(MultipartFile image);
  public boolean deleteImage(String fileName);
  public Resource getImageNotFound();
  public void deleteAll();
  public void init() throws IOException;
  
}
