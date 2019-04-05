package com.example.springboot.webflux.app.controllers.impl;

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

  @Autowired
  private ProductDao productDao;
  
  @GetMapping({"/", "/list"})
  public String list(Model model) {
    Flux<Product> products = this.productDao.findAll();
    model.addAttribute("title", "Products list");
    model.addAttribute("products", products); // The View subscribe automatically to the Flux<Product> when we pass the flux to the View so the Thymeleaf template is the observator.
    return "list";
  }
}
