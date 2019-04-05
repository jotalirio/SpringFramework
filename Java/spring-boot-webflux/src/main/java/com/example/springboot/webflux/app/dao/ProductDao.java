package com.example.springboot.webflux.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.springboot.webflux.app.models.documents.Product;

public interface ProductDao extends ReactiveMongoRepository<Product, String> {

}
