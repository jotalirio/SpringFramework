package com.example.springboot.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.springboot.app.models.entity.Client;

public interface ClientService {
  
  // abstract methods
  public List<Client> getClients();
  public Page<Client> getClients(Pageable pageable);
  public Client findById(Long id);
  public Client fetchByIdWithInvoices(Long id);
  public void save(Client client);
  public void delete(Long id);
  
}
