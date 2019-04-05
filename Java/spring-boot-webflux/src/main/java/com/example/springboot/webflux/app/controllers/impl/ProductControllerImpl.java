package com.example.springboot.webflux.app.controllers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.springboot.webflux.app.controllers.ProductController;
import com.example.springboot.webflux.app.dao.ProductDao;
import com.example.springboot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;

@Controller
public class ProductControllerImpl implements ProductController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
      
  @Autowired
  private ProductDao productDao;
  
  @GetMapping({"/", "/list"})
  public String list(Model model) {
    Flux<Product> products = this.productDao.findAll().map(product -> {
      product.setName(product.getName().toUpperCase());
      return product;
    });
    
    // Adding another observator or subscriptor to the Flux<Product> returned by the '.map()' operator. Remember that another subscriptor is the View
    products.subscribe(product -> LOGGER.info("Product: " + product.getName()));
    
    // The View subscribe automatically to the Flux<Product> when we pass the flux to the View so the Thymeleaf template is the observator.
    model.addAttribute("products", products); 
    model.addAttribute("title", "Products list");
    return "list";
  }
}
