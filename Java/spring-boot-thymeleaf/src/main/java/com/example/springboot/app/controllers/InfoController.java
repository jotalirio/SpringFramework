package com.example.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

  @Value("${application.controllers.title}")
  private String title;
  
  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("title", title);
    return "index";
  }
}
