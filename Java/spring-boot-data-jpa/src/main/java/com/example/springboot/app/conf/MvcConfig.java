package com.example.springboot.app.conf;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.example.springboot.app.view.xml.ClientList;

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
  
    
  // Indicates where is stored our Locale: in this case is in the User's Session
  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(new Locale("en", "GB")); // Default language
    return localeResolver;
  }
  
  
  // Interceptor. LocaleChange interceptor responsible for detect locale changes and then select the proper language. 
  // An interceptor is executed in each http request and in this case executes a handler method inside the Controller
  // This interceptor will be excuted each time we makes a HTTP Request with the paramater lang: For instance, lang=es_ES
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    return localeChangeInterceptor;
  }

  // Registering the previous interceptor
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(this.localeChangeInterceptor());
  }
  
  
  // This the conversor for XML Marshalling and Unmarshalling. We are going to use this Bean in our XML view to transform the Entity object into a XML document
  // Marshalling: From object to XML document
  // Unmarshalling: From XML document to object
  @Bean
  public Jaxb2Marshaller jaxb2Marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    // List of object to be marshalled
    marshaller.setClassesToBeBound(new Class[] {ClientList.class});
    return marshaller;
  }

  
  
}
