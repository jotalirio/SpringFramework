package com.example.springboot.reactor.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

// To transform this SpringBoot app as a Console application we have to implement the 'CommandLineRunner' interface
@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	// This method is coming from the 'CommandLineRunner' interface
  @Override
  public void run(String... args) throws Exception {
    
    // Creating our first observable flow. Flux is a 'Publisher', an observable and we can subscribe to him
//    Flux<String> names = Flux.just("Jose", "Andres", "Juan", "Pedro")
//                             .doOnNext(element -> { System.out.println(element); });// We are going to do some actions each time that we receive an element: Jose, Andres, Juan...
    
    
    // Another equivalent way using Java 8
    Flux<String> names = Flux.just("Jose", "Andres", "Juan", "Pedro")
                             .doOnNext(System.out::println); // We transforming this lambda expression into a method reference. In this case System:out::println
                                                             // That indicates that each 'name' have to be passed automatically to the 'println()' method
    
    // We have to subscribe to this observable (Publisher)
    names.subscribe();
    
  }

}
