package com.example.springboot.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.app.dao.IClientDao;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.services.IClientService;

@Service
public class ClientService implements IClientService {

  @Autowired
  private IClientDao clientDao;
  
  @Transactional(readOnly = true)
  @Override
  public List<Client> getClients() {
    return clientDao.findAll();
  }

  @Transactional
  @Override
  public void save(Client client) {
    clientDao.save(client);
  }

  @Override
  public Client findOne(Long id) {
    return clientDao.findOne(id);
  }

}
