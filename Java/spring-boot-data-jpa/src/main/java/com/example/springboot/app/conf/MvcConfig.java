package com.example.springboot.app.conf;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.springboot.app.utils.Constants;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  // We are going to use a directory (uploads/images) located inside the root project
  private static final String UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH = Constants.UPLOADS_DIRECTORY + "/" + Constants.UPLOADS_IMAGES_DIRECTORY;
  private static final String RESOURCE_HANDLER = "/" + Constants.UPLOADS_DIRECTORY + "/" + Constants.UPLOADS_IMAGES_DIRECTORY + "/" + Constants.ANY_FILE;

  private final Logger LOGGER = LoggerFactory.getLogger(MvcConfig.class);
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // TODO Auto-generated method stub
    WebMvcConfigurer.super.addResourceHandlers(registry);
    
    // We are going to use an external directory to store the Client´s profile photo
    // registry.addResourceHandler("/uploads/images/**").addResourceLocations("file:/C:/MY-PROJECTS/SpringFramework/Java/uploads/images/");
    
    // We are going to use a directory (uploads/images) located inside the root project
    // toUri() method gets the same schema "file": "file:/C:/MY-PROJECTS/SpringFramework/Java/uploads/images/"
    String resourcePath = Paths.get(UPLOADS_IMAGES_DIRECTORY_PROJECT_PATH).toAbsolutePath().toUri().toString();
    LOGGER.info("resourcePath: " + resourcePath);
    registry.addResourceHandler(RESOURCE_HANDLER).addResourceLocations(resourcePath);
  }


}
