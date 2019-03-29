package com.example.springboot.app.controllers.impl;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.app.controllers.LoginController;
import com.example.springboot.app.utils.Constants;

@Controller
public class LoginControllerImpl implements LoginController {

  // The Spring Security's interceptor will capture the "/login" then using the username-password and will make the login process
  // If something is wrong (login process fails) will redirect to this URI another time appending the '?error' param in the browser URL: 'http://localhost:8080/login?error'
  // and we can to customize the error message returned, this '?error' param is not required because it is present only when there is an error.
  // On the contrary, if the login process is success then the interceptor will allow to access to the application 
  // The Principal object is used to validate if the user is already logged in
  @GetMapping("/login")
  @Override
  public String login(@RequestParam(value = "error", required = false) String error, Model model, Principal principal, RedirectAttributes flash) {
    
    if (principal != null) {
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_INFO_KEY, "You have already logged in !!!");
      return "redirect:/";
    } 
    if (error != null) {
      model.addAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "ERROR: Username or password incorrect, please try again.");
    }
    return Constants.VIEW_LOGIN;
  }

}
