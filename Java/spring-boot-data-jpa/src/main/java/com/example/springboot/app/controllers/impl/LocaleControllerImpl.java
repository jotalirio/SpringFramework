package com.example.springboot.app.controllers.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springboot.app.controllers.LocaleController;

@Controller
public class LocaleControllerImpl implements LocaleController {

  // We are calling this path when the language is changed by the user so once the new locale is intercepted and change 
  // then the user is redirect to the last URL
  @GetMapping("/locale")
  @Override
  public String locale(HttpServletRequest request) {
    // Getting the last visited URL
    String lastUrl = request.getHeader("referer");
    // Redirecting to the last visited URL
    return "redirect:".concat(lastUrl);
  }

}
