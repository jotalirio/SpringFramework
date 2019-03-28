package com.example.springboot.app.controllers;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.app.models.entity.Invoice;

public interface InvoiceController {

  public String create(Long clientId, Map<String, Object> model, RedirectAttributes flash);
  public String save(Invoice invoice, BindingResult result, Model model, Long[] invoiceLinesId, Integer[] quantities, RedirectAttributes flash, SessionStatus sessionStatus);
}
