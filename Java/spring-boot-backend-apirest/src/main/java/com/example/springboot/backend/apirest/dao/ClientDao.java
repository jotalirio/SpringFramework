package com.example.springboot.backend.apirest.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.springboot.backend.apirest.models.entity.Client;

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.core-concepts
// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
public interface ClientDao extends CrudRepository<Client, Long> {

}
