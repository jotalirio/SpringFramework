package com.example.springboot.webflux.app.controllers.impl;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

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
  
  
  // We are going to manage the delay for each element to be returned from the Observable (Publisher) to the observators or subscriptors using Reactive Data-Drivers
  // A Reactive Data-Driver manage internally events with over the Stream data
  @GetMapping("/list-datadriver-delay")
  public String listUsingDataDriverDelay(Model model) {
    Flux<Product> products = this.productDao.findAll().map(product -> {
      product.setName(product.getName().toUpperCase());
      return product;
    }).delayElements(Duration.ofSeconds(1)); // The observable Flux (Publisher) is waiting a second per each element to be returned to the observator. The Web Browser takes 9 seconds to load the content
    
    products.subscribe(product -> LOGGER.info("Product: " + product.getName()));
    
    model.addAttribute("products", products); 
    model.addAttribute("title", "Products list");
    return "list";
  }
  
  // We are going to manage the quantity of elements returning from the Observable (Publisher) to the observators or subscriptors using Reactive Data-Drivers
  // A Reactive Data-Driver manage internally events with over the Stream data
  @GetMapping("/list-datadriver-delaywithbuffer")
  public String listUsingDataDriverDelayWithBuffer(Model model) {
    Flux<Product> products = this.productDao.findAll().map(product -> {
      product.setName(product.getName().toUpperCase());
      return product;
    }).delayElements(Duration.ofSeconds(1)); // The observable Flux (Publisher) is waiting a second per each element to be returned to the observator. The Web Browser takes 9 seconds to load the content
    
    products.subscribe(product -> LOGGER.info("Product: " + product.getName()));
    
    // Va cargando los elementos de dos en dos en el Navegador web. No hace falta que esperemos a que se obtenga todo el flujo hasta el final para mostrar el contenido
    // En esta forma el buffer se basa en la cantidad de elementos
    model.addAttribute("products", new ReactiveDataDriverContextVariable(products, 2));
    model.addAttribute("title", "Products list");
    return "list";
  }
  
  // En este metodo la estrategia a seguir para gestionar la contra presion es 'FULL', es decir, no se establece cantidad de buffer a la hora de devolver los
  // datos del Stream desde el Observable hacia el Observador. Recomendada cuando tenemos poca cantidad de datos
  @GetMapping("/list-full")
  public String listFull(Model model) {
    Flux<Product> products = this.productDao.findAll().map(product -> {
      product.setName(product.getName().toUpperCase());
      return product;
    }).repeat(5000); // Simulando una cantidad muy grande de registros
    
    // Abrir una pestaña nueva en Chrome
    //RESULT: Al ejecutar vemos en el Chrome Tools, pestaña Network -> Timing (opcion deshabilitar cache -> ok) que el campo TTFB (Time To First Byte tarda unos 4.7 segundos en recuperar el primer byte de datos)
    
    model.addAttribute("products", products); 
    model.addAttribute("title", "Products list");
    return "list";
  }
  
  
  // Estrategia 'Chunked' para gestionar la contra presion a la hora de devolver los datos desde el Observable hacia el Observador
  // El siguiente caso se utiliza cuando el Stream es enorme, con miles y miles de elementos. El buffer se basa en bytes y no en cantidad de elementos
  @GetMapping("/list-chunked")
  public String listChunked(Model model) {
    Flux<Product> products = this.productDao.findAll().map(product -> {
      product.setName(product.getName().toUpperCase());
      return product;
    }).repeat(5000); // Simulando una cantidad muy grande de registros

    // Abrir una pestaña nueva en Chrome
    //RESULT:  Si vamos al 'application.properties' y habilitamos la strategia Chunked y luego volvemos ha cargar los datos veremos que el TTFB se ha reducido a 2.9 segundos.
    
    model.addAttribute("products", products); 
    model.addAttribute("title", "Products list");
    return "list-chunked";
  }
}
