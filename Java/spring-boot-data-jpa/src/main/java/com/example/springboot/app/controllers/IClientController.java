package com.example.springboot.app.controllers;

import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import com.example.springboot.app.models.entity.Client;

public interface IClientController {

  // abstracts methods
  public String listClients(Model model);
  public String create(Map<String, Object> model);
  public String save(Client client, BindingResult result, Model model, SessionStatus sessionStatus);
  public String edit(Long id, Map<String, Object> model);
}
