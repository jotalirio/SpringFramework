package com.example.springboot.app.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.springboot.app.auth.filter.JWTAuthenticationFilter;
import com.example.springboot.app.auth.filter.JWTAuthorizationFilter;
import com.example.springboot.app.services.impl.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  
  // MySQL queries
//  private static final String QUERY_TO_FIND_USER_BY_USERNAME = "SELECT username, password, enabled FROM users WHERE username=?";
//  private static final String QUERY_TO_FIND_USER_AUTHORITIES_BY_USERNAME = "SELECT u.username, a.authority FROM authorities a INNER JOIN users u ON (a.user_id=u.id) WHERE u.username=?";

  // Disabling the login form and logout features because now we are going to use JWT token for authentication
  /*
  @Autowired
  private LoginSuccessHandler successHandler;
  */
  
  // Using JDBC authentication
  // Injecting the Datasource to access to our 'users' and 'authorities' tables for JDBC authentication purpose
//  @Autowired
//  private DataSource dataSource;
  
  //Using JDBC authentication
  // Injecting the BCryptPasswordEncoder registered Bean in 'MvcConfig.java' class
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;
  
  @Autowired
  //Using JPA authentication
  private JpaUserDetailsService userDetailsService;
  
  
  // Method for the HTTP Authorisation (routes) in order to give security to all our sections inside the Web site
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    // Changing the '/list' to '/list**' we are allowing the '/list-rest' URL path
    http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/list**", "/locale" /*, "/api/clients/**" */).permitAll()  // Public resources can be accessed by all users
                            /* .antMatchers("/details/**").hasAnyRole("USER") */  // -->  Defining rules by role
                            /* .antMatchers("/uploads/**").hasAnyRole("USER") */
                            /* .antMatchers("/create/**").hasAnyRole("ADMIN") */
                            /* .antMatchers("/edit/**").hasAnyRole("ADMIN") */
                            /* .antMatchers("/delete/**").hasAnyRole("ADMIN") */
                            /* .antMatchers("/invoice/**").hasAnyRole("ADMIN") */
                            /* .antMatchers("/products/**").hasAnyRole("ADMIN") */
                            .anyRequest().authenticated()
                            /*  // Disabling the login form and logout features because now we are going to use JWT token for authentication
                             *  // Now, if we try to access to the login page we get a 403 http forbidden error
                             *  // Now, if we try to access to 'http:localhost:8080/api/clients/list' we get a 403 http forbidden error
                            .and()
                              .formLogin()
                                .successHandler(this.successHandler) // We handle the login success event before to show the View. We are showing a login success message
                                .loginPage("/login") // Configure the login page by setting up the '/login/ path from LoginController
                                .permitAll() // Log in page allowed to all users 
                            .and()
                              .logout().permitAll() // Log out page allowed to all users 
                            .and()
                              .exceptionHandling().accessDeniedPage("/error_403")
                            */
                            .and()
                            .addFilter(new JWTAuthenticationFilter(authenticationManager())) // We register the JWT Authentication filter and we retrieve the authentication manager from the 'authenticationManager()' method located in 'WebSecurityConfigurerAdapter.java'
                            .addFilter(new JWTAuthorizationFilter(authenticationManager())) // We register the JWT Authorization filter and we retrieve the authentication manager from the 'authenticationManager()' method located in 'WebSecurityConfigurerAdapter.java'
                            .csrf().disable() // We disable using Sessions because now we are using JWT tokens (STATELESS policy) so we are not going to use any more the 'csrf' session token protection used to send the forms in Spring. We have to be sure that we are not using explicitly this 'csrf' input in our forms
                            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  
  // We are going to inject the 'build' object
  @Autowired
  public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
    
    /*
     *
     
    // For a memory database
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserBuilder users = User.builder().passwordEncoder(encoder::encode);
    
    // Using the 'build' object we can configure our repository where we are going to store our users
    // We creating an memory repository: Authentication system in memory
    build.inMemoryAuthentication()
         .withUser(users.username("admin").password("12345").roles("ADMIN", "USER")) // Creating our users in memory
         .withUser(users.username("lirio").password("12345").roles("USER"));
     
     *    
     */
    
    
    /*
     *

    // Using a real database 'users' and 'authorities' with JDBC authentication
    // Adding and configuring the JDBC authentication to the AuthenticationManagerBuilder. 
    build.jdbcAuthentication().dataSource(this.dataSource)
                              .passwordEncoder(this.passwordEncoder)
                              .usersByUsernameQuery(QUERY_TO_FIND_USER_BY_USERNAME)
                              .authoritiesByUsernameQuery(QUERY_TO_FIND_USER_AUTHORITIES_BY_USERNAME);

     * 
     */
    
      
     // Using JPA authentication
     // Here the QUERY_TO_FIND_USER_BY_USERNAME is implemented inside the 'UserDao' interface
     build.userDetailsService(this.userDetailsService)
          .passwordEncoder(this.passwordEncoder);


  }
  
}
