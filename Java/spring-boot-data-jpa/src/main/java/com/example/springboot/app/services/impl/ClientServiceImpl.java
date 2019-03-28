package com.example.springboot.app.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.app.dao.ClientDaoPagingAndSortingRepository;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.services.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

//  @Autowired
//  private IClientDao clientDao;
  
//  @Autowired
//  private IClientDaoCrudRepository clientDao;

  @Autowired
  private ClientDaoPagingAndSortingRepository clientDao;
  
  @Transactional(readOnly = true)
  @Override
  public List<Client> getClients() {
    return (List<Client>) this.clientDao.findAll();
  }

  @Transactional(readOnly = true)
  @Override
  public Page<Client> getClients(Pageable pageable) {
    return (Page<Client>) this.clientDao.findAll(pageable);
  }
  
  // The CRUD repository methods are Transactional by default but it is a good practise to indicate it explicitly
  @Transactional(readOnly = true)
  @Override
  public Optional<Client> findOne(Long id) {
    return this.clientDao.findById(id);
  }
  
  @Transactional
  @Override
  public void save(Client client) {
    this.clientDao.save(client);
  }

  @Transactional
  @Override
  public void delete(Long id) {
    this.clientDao.deleteById(id);
  }

}
