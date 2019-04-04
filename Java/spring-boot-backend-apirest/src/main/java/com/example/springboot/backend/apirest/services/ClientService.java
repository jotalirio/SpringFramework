package com.example.springboot.backend.apirest.services;

import java.util.List;
import com.example.springboot.backend.apirest.models.entity.Client;

public interface ClientService {

  public List<Client> findAll();
  public Client findById(Long id);
  public Client save(Client client);
  public void delete(Long id);
  
}
