package com.example.springboot.app.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  /* We comment the code because now we are going to return the client's photo in a HTTP Response using a service in ClientControllerImpl

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

  */

  
  // Adding a View controller implementing the 'addViewControllers' method. We have to use this name as mandatory.
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/error_403").setViewName("error_403");
  }
  
  // Using the 'Bcrypt' encoder to encrypt the password. This is the best recommended encoder to use with Spring Security
  // We need to register the Bean in order to use @Autowired to inject it. For that, the next method is returning an instance of Bcrypt
  // and we have to annotate it with @Bean. In this way we are registering this bean inside the Spring's context
  // REMINDER: 
  //    - We register new class as a Bean using the @Component such as we did with 'LoginSuccessHandler.java'
  //    - Now, we are instantiating a new BCryptPasswordEncoder instance and register it as a Bean
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
