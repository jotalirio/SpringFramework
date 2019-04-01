package com.example.springboot.app;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.springboot.app.services.UploadFileService;

@SpringBootApplication
public class SpringBootDataJpaApplication implements CommandLineRunner {

  @Autowired
  private UploadFileService uploadFileService;
  
  // Testing the BCryptPasswordEncoder registered as a Bean in 'MvcConfig.java' class
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  
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
    
    // Testing the BCryptPasswordEncoder registered as a Bean in 'MvcConfig.java' class
    String password = "12345";
    // Creating 2 encrypted password for Testing purpose
    IntStream.range(0, 2).forEach(i -> {
      String bcriptPassword = passwordEncoder.encode(password);
      System.out.println(bcriptPassword);
    });
    
  }

}
