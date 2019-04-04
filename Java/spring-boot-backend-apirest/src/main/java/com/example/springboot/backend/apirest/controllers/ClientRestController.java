package com.example.springboot.backend.apirest.controllers;

import java.util.List;

import com.example.springboot.backend.apirest.models.entity.Client;

public interface ClientRestController {

  public List<Client> findAll();
  
}
