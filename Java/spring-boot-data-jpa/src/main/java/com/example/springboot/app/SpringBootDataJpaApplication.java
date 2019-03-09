package com.example.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springboot.app.services.UploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

  @Autowired
  UploadFileService uploadFileService;
  
  public static void main(String[] args) {
    SpringApplication.run(SpringBootDataJpaApplication.class, args);
  }

  /**
   * When the project starts, the 'uploads/images' directory is automatically deleted and created in order to clear the images
   */
  @Override
  public void run(String... args) throws Exception {
    this.uploadFileService.deleteAll();
    this.uploadFileService.init();
  }

}
