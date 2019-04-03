package com.example.springboot.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.springboot.app.models.entity.Product;

public interface ProductDao extends CrudRepository<Product, Long> {

  // 1. First approach for search products:
  // Query at Entity level, not at Table level
  @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
  public List<Product> findByName(String term);
  
  // 2. Second approach for search products:
  // Auto query based on the method name: This will be a query using LIKE over the Product entity's name attribute and applying an ignore case
  // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
  public List<Product> findByNameLikeIgnoreCase(String term);
}
