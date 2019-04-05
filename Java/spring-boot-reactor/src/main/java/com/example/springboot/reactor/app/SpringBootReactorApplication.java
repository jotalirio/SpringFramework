package com.example.springboot.reactor.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// To transform this SpringBoot app as a Console application we have to implement the 'CommandLineRunner' interface
@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	// This method is coming from the 'CommandLineRunner' interface
  @Override
  public void run(String... args) throws Exception {
    
    
  }

}
