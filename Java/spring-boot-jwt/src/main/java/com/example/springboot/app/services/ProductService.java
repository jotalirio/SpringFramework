package com.example.springboot.app.services;

import java.util.List;

import com.example.springboot.app.models.entity.Product;

public interface ProductService {

  public Product findById(Long id);
  public List<Product> findByName(String term);

}
