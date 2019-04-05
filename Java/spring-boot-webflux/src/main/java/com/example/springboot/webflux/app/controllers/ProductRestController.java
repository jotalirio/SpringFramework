package com.example.springboot.webflux.app.controllers;

import org.springframework.web.bind.annotation.PathVariable;

import com.example.springboot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRestController {

  public Flux<Product> list();
  public Mono<Product> listById(@PathVariable String id);
  
}
