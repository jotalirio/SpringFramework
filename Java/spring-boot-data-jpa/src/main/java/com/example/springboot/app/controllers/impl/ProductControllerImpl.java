package com.example.springboot.app.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.springboot.app.controllers.ProductController;
import com.example.springboot.app.models.entity.Product;
import com.example.springboot.app.services.ProductService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/products")
public class ProductControllerImpl implements ProductController {

  @Autowired
  private ProductService productService;
  
  
  @GetMapping(value = "/load/{term}", produces = {"application/json"})
  @Override
  public @ResponseBody List<Product> load(@PathVariable String term) {
    return this.productService.findByName(term);
  }

}
