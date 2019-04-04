package com.example.springboot.backend.apirest.controllers;

import java.util.List;

import com.example.springboot.backend.apirest.models.entity.Client;

public interface ClientRestController {

  public List<Client> findAll();
  public Client findById(Long id);
  public Client create(Client client);
  public Client update(Client client, Long id);
  public void delete(Long id);
  
}
