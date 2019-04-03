package com.example.springboot.app.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthoritiesMixin {

  // Esta es nuestra marca para que se utilice este constructor a la hora de recuperar los objetos authorities (los roles) desde el JSON en la clase 'JWTAuthoritationFilter.java'
  // Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper().readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
  // Necesitamos hacer esto porque al realizar el parseo de los authorities (los roles) desde JSON a objeto Java, busca un constructor vacio pero la clase 'SimpleGrantedAuthority.java' no lo tiene
  // solo tiene un constructor parametrizado donde se le pasa el tipo de role, y tampoco se llama a este constructor, por lo tanto al hacer el parseo da fallo
  
  // Para hacer uso del constructor parametrizado donde se le pasa el role, tenemos que indicar que atributo del JSON va a ser el que se pase automaticamente
  // a este constructor parametrizado
  
  /*
   * Ejemplo de lo que viene en el JSON. Para obtenerlo vamos a https:///jwt.io y pegamos el token que devuelve la consulta POSTMAN
  {
    "authorities": "[{\"authority\":\"ROLE_ADMIN\"},{\"authority\":\"ROLE_USER\"}]",
    "sub": "admin",
    "iat": 1554297657,
    "exp": 1554311657
  }
  
  */
  @JsonCreator
  public SimpleGrantedAuthoritiesMixin(@JsonProperty("authority") String role) {

    
  }

}
