package com.example.springboot.app.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.springboot.app.auth.service.JWTService;
import com.example.springboot.app.auth.service.impl.JWTServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// This filter is executed each time the User wants to log in
// This filter is going to be called when a POST HTTP Request to '/login' path is done: We can see that inside the 'UsernamePasswordAuthenticationFilter.java' class
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  // The authentication manager will work with the authentication JPA provider implemented in 'JpaUserDetailsService.java' class
  // We need to pass the authentication manager (the component making the authentication using in this case 'JpaUserDetailsService.java'
  private AuthenticationManager authenticationManager;
  private JWTService jwtService;
  
  public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    // Changing the URL to make the login, by default is an Http Request POST to '/login' path
    setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
  }


  // We override this method from 'UsernamePasswordAuthenticationFilter' class. This method is responsible of the authentication process (login)
  // In background way, this method is going to work together with our authentication provider (Database, JPA (Hibernate, we implemented this in 'JpaUserDetailsService.java' class), etc)
  // This method is executed by the 'doFilter()' method inside the abstract class 'AbstractAuthenticationProcessingFilter.java'
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {

    // The obtainUsername() and obtainPassword() methods are inherited from 'UsernamePasswordAuthenticationFilter.java'
    String username = obtainUsername(request);
    String password = obtainPassword(request);

    // Equivalent way
//    String username = request.getParameter("username");
//    String password = request.getParameter("password");

    if (username != null && password != null) {
      logger.info("Username from request parameter (form-data): " + username);
      logger.info("Password from request parameter (form-data): " + password);
    }
    else { // For instance, when the data is sent in RAW data (not processed data, for instance, a JSON object with the username and password)
      
      // Transform the data from the request input stream
      // Here we have the not processed data through 'request.getInputStream()'.
      com.example.springboot.app.models.entity.User user = null;
      try {
        
        // We have to convert from JSON to Object: the User entity
        user = new ObjectMapper().readValue(request.getInputStream(), com.example.springboot.app.models.entity.User.class);
      
        // Getting User's data
        username = user.getUsername();
        password = user.getPassword();
        logger.info("Username from request parameter (raw): " + username);
        logger.info("Password from request parameter (raw): " + password);
        
      } catch (JsonParseException e) {
        e.printStackTrace();
      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } 
    }
    
    username = username.trim();
    
    // Creating the username-password authentication token. Is a container that contains the credentials
    // This is not the JWT token. It is a token managed internally by Spring Security. 
    // We will use this token to get the username and password to generate the JWT token
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
    
    // Returning the authentication result
    return this.authenticationManager.authenticate(authToken);
  }

  // We override this method located inside the abstract class 'AbstractAuthenticationProcessingFilter.java'
  // We are going to do something when the authentication process is successfully
  // The parameter 'authResult' is our 'authToken' but now this token is already authenticated (attribute authenticated = true)
  // and contains all the User's data: username, roles, etc
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
      Authentication authResult) throws IOException, ServletException {

    // Creating the JWT token
    String jwtToken = this.jwtService.create(authResult);

    // Passing the token in the response's 'Authorization' Header
    response.addHeader(JWTServiceImpl.HEADER_AUTHORIZATION, JWTServiceImpl.JWT_TOKEN_PREFIX.concat(jwtToken));
    
    // It is a good practise to pass the JWT token in JSON format inside the response's Body
    Map<String, Object> body = new HashMap<String, Object>();
    body.put("token", jwtToken);
    body.put("user", (User) authResult.getPrincipal()); // We pass the User (Principal) as well
    body.put("message", String.format("Hi %s, you have logged in successfully !!!", ((User) authResult.getPrincipal()).getUsername())); // We pass a success message
    
    // To pass this data into the response we need to retrieve the 'getWriter()'. We pass the 'body' as an JSON object
    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    
    // We indicate the HTTP Status and Content-Type
    response.setStatus(200);
    response.setContentType("application/json");
    
  }

  // We override this method located inside the abstract class 'AbstractAuthenticationProcessingFilter.java'
  // We are going to do something when the authentication process fails so we can managed authentication errors (for instance, 401, not authorized)
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException failed) throws IOException, ServletException {
    
    Map<String, Object> body = new HashMap<String, Object>();
    body.put("message", "Authentication ERROR: username or password incorrect !!!"); // Per security reason we do not indicate which parameter is the incorrect one
    body.put("error", failed.getMessage()); // Original fail message

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setStatus(401);
    response.setContentType("application/json");
  }

  
  
  
  
  
}
