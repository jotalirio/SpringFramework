package com.example.springboot.backend.apirest.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.backend.apirest.dao.ClientDao;
import com.example.springboot.backend.apirest.models.entity.Client;
import com.example.springboot.backend.apirest.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientDao clientDao;
  
  
  @Transactional(readOnly = true)
  @Override
  public List<Client> findAll() {
    return (List<Client>) this.clientDao.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public Client findById(Long id) {
    return this.clientDao.findById(id).orElse(null);
  }

  @Transactional
  @Override
  public Client save(Client client) {
    return this.clientDao.save(client);
  }

  @Transactional
  @Override
  public void delete(Long id) {
    this.clientDao.deleteById(id);
  }

}
