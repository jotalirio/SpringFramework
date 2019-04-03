package com.example.springboot.app.controllers;

import java.util.List;

import com.example.springboot.app.models.entity.Product;

public interface ProductController {

  public List<Product> load(String term);
  
}
