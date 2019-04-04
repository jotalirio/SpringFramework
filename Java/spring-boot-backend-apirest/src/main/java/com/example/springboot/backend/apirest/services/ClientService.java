package com.example.springboot.backend.apirest.services;

import java.util.List;
import com.example.springboot.backend.apirest.models.entity.Client;

public interface ClientService {

  public List<Client> findAll();
}
