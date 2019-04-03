package com.example.springboot.app.auth.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.springboot.app.auth.service.JWTService;

// This filter is executed for each HTTP Request when the 'Authorization' Header and then the 'Bearer' token are present. 
// If the 'Authorization' Header is not present this filter will not be executed
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  private JWTService jwtService;
  
  public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
    super(authenticationManager);
    this.jwtService = jwtService;
  }

  // We override this method from the abstract class 'BasicAuthenticationFilter.java' to work with JWT authentication and not with 'Basic' authentication 
  // used by default 'BasicAuthenticationFilter.java
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
    // For instance: Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdXRob3JpdGllcyI6Ilt7XCJhdXRob3JpdHlcIjpcIlJPTEVfQURNSU5cIn0se1wiYXV0aG9yaXR5XCI6XCJST0xFX1VTRVJcIn1dIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE1NTQzMDQ3MDUsImV4cCI6MTU1NDMxODcwNn0.MBwTe_ZKJPWCcCy2Pyj9DqMk9IiSr-MwRUYk7GCGlk4WtY16q1fe8GfgGyM6GMW1H_D1VjO0jTuF7bFH8uVIew
    String header = request.getHeader("Authorization");
    
    if (!this.requiresAuthentication(header)) {
      chain.doFilter(request, response);
      return;
    }
    
    UsernamePasswordAuthenticationToken authentication = null;
    // Validating the jwtToken
    if (this.jwtService.validate(header)) {
      // Creating the username-password authentication token. Is a container that contains the credentials
      // This is not the JWT token. It is a token managed internally by Spring Security. 
      authentication = new UsernamePasswordAuthenticationToken(this.jwtService.getUsername(header), null, this.jwtService.getRoles(header));
    }

    // We assign the authentication token inside the Security Context. This sentence authenticates the User inside the Http Request
    // We are not using Sessions so in this way the User is authenticated inside the Http Request
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    // The chain filters continues
    chain.doFilter(request, response);
    
  }

  // Custom function to check if the HTTP Request requires authentication
  private boolean requiresAuthentication(String header) {
    if (header == null || !header.startsWith("Bearer ")) {
      return false;
    }
    return true;
  }
  
  
}
