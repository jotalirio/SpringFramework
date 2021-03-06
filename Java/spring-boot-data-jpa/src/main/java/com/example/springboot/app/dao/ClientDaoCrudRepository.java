package com.example.springboot.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.springboot.app.models.entity.Client;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.core-concepts
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
public interface ClientDaoCrudRepository extends CrudRepository<Client, Long>{

}
