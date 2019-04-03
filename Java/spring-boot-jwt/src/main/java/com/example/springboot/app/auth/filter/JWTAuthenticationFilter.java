package com.example.springboot.app.auth.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// This filter is going to be called when a POST HTTP Request to '/login' path is done: We can see that inside the 'UsernamePasswordAuthenticationFilter.java' class
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  // The authentication manager will work with the authentication JPA provider implemented in 'JpaUserDetailsService.java' class
  // We need to pass the authentication manager (the component making the authentication using in this case 'JpaUserDetailsService.java'
  private AuthenticationManager authenticationManager;
  
  
  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
    // Changing the URL to make the login, by default is an Http Request POST to '/login' path
    setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
  }


  // We override this method from 'UsernamePasswordAuthenticationFilter' class. This method is responsible of the authentication process (login)
  // In background way, this method is going to work together with our authentication provider (Database, JPA (Hibernate, we implemented this in 'JpaUserDetailsService.java' class), etc)
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    // The obtainUsername() and obtainPassword() methods are inherited from 'UsernamePasswordAuthenticationFilter.java'
    String username = obtainUsername(request);
    String password = obtainPassword(request);

    // Equivalent way
//    String username = request.getParameter("username");
//    String password = request.getParameter("password");

    if (username == null) {
      username = "";
    }

    if (password == null) {
      password = "";
    }

    if (username != null && password != null) {
      logger.info("Username from request parameter (form-data): " + username);
      logger.info("Password from request parameter (form-data): " + password);
    }
    
    username = username.trim();
    
    // Creating the username-password authentication token. Is a container that contains the credentials
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
    
    // Returning the authentication result
    return this.authenticationManager.authenticate(authToken);
  }

  
}
