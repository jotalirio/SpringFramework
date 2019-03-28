package com.example.springboot.app.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.app.dao.ProductDao;
import com.example.springboot.app.models.entity.Product;
import com.example.springboot.app.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductDao productDao;
  
  @Override
  @Transactional(readOnly = true)
  public List<Product> findByName(String term) {
//    return this.productDao.findByName(term);
    return this.productDao.findByNameLikeIgnoreCase("%"+term+"%");
  }

}
