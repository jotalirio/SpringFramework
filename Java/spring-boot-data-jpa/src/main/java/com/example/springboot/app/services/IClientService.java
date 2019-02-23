package com.example.springboot.app.services;

import java.util.List;

import com.example.springboot.app.models.entity.Client;

public interface IClientService {
  
  // abstract method
  public List<Client> getClients();
}
