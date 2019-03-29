package com.example.springboot.app.controllers.impl;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.app.controllers.LoginController;
import com.example.springboot.app.utils.Constants;

@Controller
public class LoginControllerImpl implements LoginController {

  // The Spring Security's interceptor will capture the "/login" then using the username-password and will make the login process
  // If something is wrong (login process fails) will redirect to this URI another time and we can to customize the error message returned
  // but if not (login process is success) then the interceptor will allow to access to the application 
  // The Principal object is used to validate if the user is already logged in
  @GetMapping("/login")
  @Override
  public String login(Model model, Principal principal, RedirectAttributes flash) {
    
    if (principal != null) {
      flash.addAttribute(Constants.ATTRIBUTE_FLASH_INFO_KEY, "You have already logged in");
      return "redirect:/";
    }
    return Constants.VIEW_LOGIN;
  }

}
