package com.example.springboot.app.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.springboot.app.dao.IClientDao;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.utils.Constants;

@Repository
public class ClientDaoImpl implements IClientDao {

  private static final String FROM_CLIENTS = Constants.FROM + Constants.BLANK + Constants.TABLE_CLIENTS;
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public List<Client> findAll() {
    return entityManager.createQuery(FROM_CLIENTS).getResultList();
  }

}
