package com.example.springboot.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.example.springboot.app.utils.Constants;


// We store this class like a 'Bean' inside the Spring's context using the '@Component' annotation
// Doing this, we can inject this class in 'SpringSecurityConfig.java' to use it
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    // We have to do the flash messages in this way because we are not allowed to pass the 'RedirectAttributes flash' to the method by parameters
    // but this way to implement it is equivalent
    SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
    FlashMap flashMap = new FlashMap();
    flashMap.put(Constants.ATTRIBUTE_FLASH_SUCCESS_KEY, "Hi " + authentication.getName() + ", you have logged in successfully !!!");
    flashMapManager.saveOutputFlashMap(flashMap, request, response);
    if (authentication != null) {
      // This logger is inherited by 'SimpleUrlAuthenticationSuccessHandler' from the abstract class 'AbstractAuthenticationTargetUrlRequestHandler'
      logger.info("The user " + authentication.getName() + " has logged in successfully !!!");
    }
    super.onAuthenticationSuccess(request, response, authentication);
  }
  
 

}
