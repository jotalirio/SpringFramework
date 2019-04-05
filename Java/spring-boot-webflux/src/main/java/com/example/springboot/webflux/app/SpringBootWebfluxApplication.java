package com.example.springboot.webflux.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springboot.webflux.app.dao.ProductDao;
import com.example.springboot.webflux.app.models.documents.Product;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private ProductDao productDao;
  
  
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	// Implementing this method from CommandLineRunner we are going to create our mocked data in MongoDB
  @Override
  public void run(String... args) throws Exception {
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
        .flatMap(product -> this.productDao.save(product))
        .subscribe(product -> LOGGER.info("Insert: " + product.getId() + " " + product.getName()));
    
    
  }

}
