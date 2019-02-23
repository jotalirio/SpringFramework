package com.example.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("title", "Hello Spring Boot Thymeleaf!");
    return "index";
  }
}
