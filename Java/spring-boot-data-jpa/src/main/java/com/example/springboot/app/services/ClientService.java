package com.example.springboot.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.app.dao.IClientDao;
import com.example.springboot.app.models.entity.Client;

@Service
public class ClientService implements IClientService {

  @Autowired
  private IClientDao clientDao;
  
  @Transactional(readOnly = true)
  @Override
  public List<Client> getClients() {
    return this.clientDao.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public Client findOne(Long id) {
    return this.clientDao.findOne(id);
  }
  
  @Transactional
  @Override
  public void save(Client client) {
    this.clientDao.save(client);
  }

  @Transactional
  @Override
  public void delete(Long id) {
    this.clientDao.delete(id);
  }

}
