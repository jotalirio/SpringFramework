package com.example.springboot.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.springboot.app.models.entity.Client;

public interface ClientDaoCrudRepository extends CrudRepository<Client, Long>{

}
