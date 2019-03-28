package com.example.springboot.app.services;

import java.util.List;
import java.util.Optional;

import com.example.springboot.app.models.entity.Product;

public interface ProductService {

  public Optional<Product> findById(Long id);
  public List<Product> findByName(String term);

}
