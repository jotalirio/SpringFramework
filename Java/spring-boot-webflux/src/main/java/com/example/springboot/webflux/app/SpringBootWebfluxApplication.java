package com.example.springboot.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.example.springboot.webflux.app.dao.ProductDao;
import com.example.springboot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private ProductDao productDao;
  
  @Autowired
  private ReactiveMongoTemplate reactiveMongoTemplate;
  
  
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	// Implementing this method from CommandLineRunner we are going to create our mocked data in MongoDB
  @Override
  public void run(String... args) throws Exception {
    
    // Making create-drop each time we are running up the app. If we do not subscribe to the Mono<void> returned by the 'dropCollection()' method
    // The method does not do nothing
    this.reactiveMongoTemplate.dropCollection("products").subscribe();
    
    // Creating the Flux
    Flux.just(new Product("TV Panasonic", 456.89), 
              new Product("Sony Camara HD Digital", 177.89),
              new Product("Apple iPod", 46.89),
              new Product("Sony Notebook", 846.89),
              new Product("Hewlett Packard Multifunctional", 200.89),
              new Product("Bianchi Bike", 70.89),
              new Product("HP Notebook Omen 17", 2500.89),
              new Product("Micha Table", 150.89),
              new Product("TV Sony Bravia OLED 4k Ultra HD", 2255.89))
        // 'flatMap' operator converts a Flux<Object> or Mono<Object> in a Object. We need it because for instance, 
        // the ProductDao.save() method is returning a Mono<Product> instead of Product object
        .flatMap(product -> { 
          product.setCreationDate(new Date());
          return this.productDao.save(product); 
        })
        // We need to subscribe to the Flux if not there is none executed actions: inserting the products in the MongoDB 'products' collection
        .subscribe(product -> LOGGER.info("Insert: " + product.getId() + " " + product.getName()));
    
    
  }

}
