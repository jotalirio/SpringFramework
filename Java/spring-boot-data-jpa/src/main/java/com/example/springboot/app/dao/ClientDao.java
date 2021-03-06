package com.example.springboot.app.dao;

import java.util.List;

import com.example.springboot.app.models.entity.Client;

public interface ClientDao {

  // abstract methods
  public List<Client> findAll();
  public Client findById(Long id);
  public void save(Client client);
  public void delete(Long id);
}
