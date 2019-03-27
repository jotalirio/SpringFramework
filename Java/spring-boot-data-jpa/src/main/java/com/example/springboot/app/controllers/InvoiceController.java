package com.example.springboot.app.controllers;

import java.util.Map;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface InvoiceController {

  public String create(Long clientId, Map<String, Object> model, RedirectAttributes flash);
}
