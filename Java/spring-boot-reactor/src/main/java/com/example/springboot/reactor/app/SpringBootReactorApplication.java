package com.example.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.springboot.reactor.app.models.User;

import reactor.core.publisher.Flux;

// To transform this SpringBoot app as a Console application we have to implement the 'CommandLineRunner' interface
@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
      
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
//    Flux<String> names = Flux.just("Jose", "Andres", "Juan", "Pedro")
//                             .doOnNext(System.out::println); // We transforming this lambda expression into a method reference. In this case System:out::println
                                                             // That indicates that each 'name' have to be passed automatically to the 'println()' method
    
    // We have to subscribe to this observable (Publisher)
//    names.subscribe(LOGGER::info); // We are going to do some actions each time that we receive an element: Jose, Andres, Juan...
   
    
    
    // Managing errors triggered from the observable (Publisher) to the observator that is subscribe to this observable reactive stream
//    Flux<String> names = Flux.just("Jose", "Andres", "", "Juan", "Pedro")
//                             .doOnNext(e -> { 
//                                 if (e.isEmpty()) { // We are going to throw this exception when an element will be empty
//                                   throw new RuntimeException("Name must be not empty.");
//                                 }
//                                 System.out.println(e); 
//                             });
//
//    // We manage the subscribe method like in Angular 7
//    names.subscribe(
//      e -> {
//        LOGGER.info(e);
//      },
//      error -> {
//        // The error will be done when the observable (Publisher) is throwing the Exception. At this moment the stream is disrupted (interrumpido)
//        // out application will be still running as usual. Only the stream will be stopped
//        LOGGER.error(error.getMessage());
//      }
//    ); 
     
    
    // When the subscription is completed (when the flux finish to send until the last element) we can do some tasks
//    Flux<String> names = Flux.just("Jose", "Andres", "Juan", "Pedro")
//                             .doOnNext(e -> { 
//                                if (e.isEmpty()) { // We are going to throw this exception when an element will be empty
//                                  throw new RuntimeException("Name must be not empty.");
//                                }
//                                System.out.println(e); 
//                             });
//
//    names.subscribe(
//      e -> {
//        LOGGER.info(e);
//      },
//      error -> {
//        LOGGER.error(error.getMessage());
//      },
//      new Runnable() { // Instance from anonymous class of type Runnable. This object is instantiated when the subscription is finished      
//        @Override
//        public void run() {
//          System.out.println();
//          LOGGER.info("The observable (Publisher) execution has finished succesfully !!!");
//        }
//      }
//    );
//    
//    
//    
      // 1. Using the operators. Each operator returns a new Flux instance because the original one is immutable
//      Flux<String> names = Flux.just("Jose", "Andres", "Juan", "Pedro")
//                               .map( e -> { // The operator 'map' always returns a value. Returns another Flux instance with the names in UpperCase
//                                 return e.toUpperCase();
//                               })
//                               .doOnNext(e -> { // The doOnNext() method is managing each name but in UpperCase because of the first map
//                                 if (e.isEmpty()) { 
//                                   throw new RuntimeException("Name must be not empty.");
//                                 }
//                                 System.out.println(e); 
//                               })
//                               .map( e -> { // In this case is returning another Flux instance with the names in LowerCase
//                                 return e.toLowerCase();
//                               });
//
//      names.subscribe(
//        e -> {
//          LOGGER.info(e);
//        },
//        error -> {
//          LOGGER.error(error.getMessage());
//        },
//        new Runnable() {      
//          @Override
//          public void run() {
//            System.out.println();
//            LOGGER.info("The observable (Publisher) execution has finished succesfully !!!");
//          }
//        }
//      );
      
      
      
      // 2. Using the operators. Each operator returns a new Flux instance because the original one is immutable
//      Flux<User> names = Flux.just("Jose", "Andres", "Juan", "Pedro")
//                               .map( name -> new User(name.toUpperCase(), null)) // With only one action the 'map' operator make the 'return' automatically
//                               .doOnNext( user -> { // The doOnNext() method is managing each 'User' element from the first map
//                                 if (user == null) { 
//                                   throw new RuntimeException("Name must be not empty.");
//                                 }
//                                 System.out.println(user.getName()); 
//                               })
//                               .map( user -> { // In this case is returning another Flux instance containing the 'User' objects with the names in LowerCase
//                                 String name = user.getName().toLowerCase();
//                                 user.setName(name);
//                                 return user;
//                               });
//
//      names.subscribe(
//        user -> {
//          LOGGER.info(user.toString());
//        },
//        error -> {
//          LOGGER.error(error.getMessage());
//        },
//        new Runnable() {      
//          @Override
//          public void run() {
//            System.out.println();
//            LOGGER.info("The observable (Publisher) execution has finished succesfully !!!");
//          }
//        }
//      );
      
      
      // 3. Using the operators. Each operator returns a new Flux instance because the original one is immutable
//      Flux<User> names = Flux.just("Jose Lirio", "Andres Gimenez", "Juan Perez", "Pedro Lopez", "Bruce Lee", "Bruce Willis")
//          .map( name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase())) 
//          .filter( user -> user.getName().toLowerCase().equals("bruce")) // With only one action the 'filter' operator make the 'return' automatically. Returns another Flux instance with the filtered objects
//          .doOnNext( user -> { 
//            if (user == null) { 
//              throw new RuntimeException("Name must be not empty.");
//            }
//            System.out.println(user.getName().concat(" ").concat(user.getSurname())); 
//          })
//          .map( user -> { 
//            String name = user.getName().toLowerCase();
//            user.setName(name);
//            return user;
//          });
//
//      names.subscribe(
//        user -> {
//          LOGGER.info(user.toString());
//        },
//        error -> {
//          LOGGER.error(error.getMessage());
//        },
//        new Runnable() {      
//          @Override
//          public void run() {
//            System.out.println();
//            LOGGER.info("The observable (Publisher) execution has finished succesfully !!!");
//          }
//        }
//      );
      
      
      // 4. Immutability
      Flux<String> names = Flux.just("Jose Lirio", "Andres Gimenez", "Juan Perez", "Pedro Lopez", "Bruce Lee", "Bruce Willis");
      names.map( name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase())) 
           .filter( user -> user.getName().toLowerCase().equals("bruce")) // With only one action the 'filter' operator make the 'return' automatically. Returns another Flux instance with the filtered objects
           .doOnNext( user -> { 
             if (user == null) { 
               throw new RuntimeException("Name must be not empty.");
             }
             System.out.println(user.getName().concat(" ").concat(user.getSurname())); 
           })
           .map( user -> { 
             String name = user.getName().toLowerCase();
             user.setName(name);
             return user;
           });
      
      names.subscribe( // Is making the subscribe over the Flux<String>
        user -> {
          LOGGER.info(user.toString());
        },
        error -> {
          LOGGER.error(error.getMessage());
        },
        new Runnable() {      
          @Override
          public void run() {
            System.out.println();
            LOGGER.info("The observable (Publisher) execution has finished succesfully !!!");
          }
        }
      );
      
      Flux<User> users = names.map( name -> new User(name.split(" ")[0].toUpperCase(), name.split(" ")[1].toUpperCase())) 
          .filter( user -> user.getName().toLowerCase().equals("bruce")) // With only one action the 'filter' operator make the 'return' automatically. Returns another Flux instance with the filtered objects
          .doOnNext( user -> { 
            if (user == null) { 
              throw new RuntimeException("Name must be not empty.");
            }
            System.out.println(user.getName().concat(" ").concat(user.getSurname())); 
          })
          .map( user -> { 
            String name = user.getName().toLowerCase();
            user.setName(name);
            return user;
          });
  
      users.subscribe(
        user -> {
          LOGGER.info(user.toString());
        },
        error -> {
          LOGGER.error(error.getMessage());
        },
        new Runnable() {      
          @Override
          public void run() {
            System.out.println();
            LOGGER.info("The observable (Publisher) execution has finished succesfully !!!");
          }
        }
      );
  }

}
