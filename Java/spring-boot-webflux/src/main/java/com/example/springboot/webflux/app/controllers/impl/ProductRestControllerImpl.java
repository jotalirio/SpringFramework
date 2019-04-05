package com.example.springboot.webflux.app.controllers.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.webflux.app.controllers.ProductRestController;
import com.example.springboot.webflux.app.dao.ProductDao;
import com.example.springboot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
public class ProductRestControllerImpl implements ProductRestController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ProductDao productDao;
  
  // This method is mapped with the root path "/api/products"
  @GetMapping()
  public Flux<Product> list() {
    Flux<Product> products = this.productDao.findAll().map(product -> {
      product.setName(product.getName().toUpperCase());
      return product;
    })
    .doOnNext(product -> LOGGER.info("Product: " + product.getName()));
    
    return products;
  }

  
  @GetMapping("/{id}")
  public Mono<Product> listById(@PathVariable String id) {
    
    // This way es faster and more efficient
//    Mono<Product> product = this.productDao.findById(id);    
    
    // Another equivalent way
    Flux<Product> products = this.productDao.findAll();
    Mono<Product> product = products.filter(p -> p.getId().equals(id))
                                    .next() // Next returns the Mono from the Flux
                                    .doOnNext( p -> LOGGER.info("Product: " + p.getName()));
        
    return product;
  }
  
  
  
}
