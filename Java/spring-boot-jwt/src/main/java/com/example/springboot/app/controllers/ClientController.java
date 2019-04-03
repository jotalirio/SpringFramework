package com.example.springboot.app.controllers;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.view.xml.ClientList;

public interface ClientController {

  // abstracts methods
  // Use this method with IClientDao or IClientDaoCrudRepository
  // public String listClients(Model model);
  // We use this method with IClientDaoPagindAndSortingRepository
  public String listClients(int page, Model model, Authentication authentication, HttpServletRequest request, Locale locale);
  public ClientList listRest();
  public String create(Map<String, Object> model);
  public String save(Client client, BindingResult result, Model model, MultipartFile photo, RedirectAttributes flash, SessionStatus sessionStatus);
  public String edit(Long id, Map<String, Object> model, RedirectAttributes flash);
  public String delete(Long id, RedirectAttributes flash);
  public String details(Long id, Map<String, Object> model, RedirectAttributes flash);
}
