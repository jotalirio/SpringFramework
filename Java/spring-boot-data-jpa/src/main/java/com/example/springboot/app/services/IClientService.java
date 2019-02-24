package com.example.springboot.app.services;

import java.util.List;

import com.example.springboot.app.models.entity.Client;

public interface IClientService {
  
  // abstract methods
  public List<Client> getClients();
  public Client findOne(Long id);
  public void save(Client client);
  public void delete(Long id);
}
