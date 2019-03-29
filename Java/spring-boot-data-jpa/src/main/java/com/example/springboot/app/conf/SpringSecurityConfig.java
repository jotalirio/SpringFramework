package com.example.springboot.app.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configurable
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  
  // We are going to inject the 'build' object
  @Autowired
  public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
    
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserBuilder users = User.builder().passwordEncoder(encoder::encode);
    
    // Using the 'build' object we can configure our repository where we are going to store our users
    // We creating an memory repository: Authentication system in memory
    build.inMemoryAuthentication()
         .withUser(users.username("admin").password("12345").roles("ADMIN", "USER")) // Creating our users in memory
         .withUser(users.username("lirio").password("12345").roles("USER"));
  }
  
}
