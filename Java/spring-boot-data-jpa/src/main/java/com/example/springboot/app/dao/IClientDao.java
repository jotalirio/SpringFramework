package com.example.springboot.app.dao;

import java.util.List;

import com.example.springboot.app.models.entity.Client;

public interface IClientDao {

  // abstract method
  public List<Client> findAll();
  public void save(Client client);
}
