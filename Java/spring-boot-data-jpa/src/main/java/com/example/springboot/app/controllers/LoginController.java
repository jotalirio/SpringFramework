package com.example.springboot.app.controllers;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface LoginController {

  public String login(String error, String logout, Model model, Principal principal, RedirectAttributes flash);
  
}
