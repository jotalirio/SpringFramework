package com.example.springboot.app.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  
  // Method for the HTTP Authorisation (routes) in order to give security to all our sections inside the Web site
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/list").permitAll()  // Public resources can be accessed by all users
                            .antMatchers("/details/**").hasAnyRole("USER")                             // Defining rules by role
                            .antMatchers("/uploads/**").hasAnyRole("USER")
                            .antMatchers("/create/**").hasAnyRole("ADMIN")
                            .antMatchers("/edit/**").hasAnyRole("ADMIN")
                            .antMatchers("/delete/**").hasAnyRole("ADMIN")
                            .antMatchers("/invoice/**").hasAnyRole("ADMIN")
                            .antMatchers("/products/**").hasAnyRole("ADMIN")
                            .anyRequest().authenticated()
                            .and()
                            .formLogin().permitAll() // Log in page allowed to all users 
                            .and()
                            .logout().permitAll(); // Log out page allowed to all users 
  }

  
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
