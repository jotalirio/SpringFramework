package com.example.springboot.app.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.springboot.app.dao.IClientDao;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.utils.Constants;

@Repository
public class ClientDaoImpl implements IClientDao {

  private static final String FROM_CLIENT = Constants.FROM + Constants.BLANK + Constants.ENTITY_CLIENT;
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Client> findAll() {
    return entityManager.createQuery(FROM_CLIENT).getResultList();
  }

  @Override
  public Client findOne(Long id) {
    return entityManager.find(Client.class, id);
  }
  
  @Override
  public void save(Client client) {
    if(client != null && client.getId() != null && client.getId() > 0) {
      // Updating a existing Client
      entityManager.merge(client);
    }
    else {
      // Creating a new Client
      entityManager.persist(client);
    }
  }

  @Override
  public void delete(Long id) {
    entityManager.remove(this.findOne(id));
  }

}
