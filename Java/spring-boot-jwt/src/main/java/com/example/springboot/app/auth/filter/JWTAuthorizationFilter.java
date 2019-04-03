package com.example.springboot.app.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

// This filter is executed for each HTTP Request when the 'Authorization' Header and then the 'Bearer' token are present. 
// If the 'Authorization' Header is not present this filter will not be executed
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
    // TODO Auto-generated constructor stub
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
    
    // Getting the JWT token
    boolean isTokenValid;
    Claims jwtToken = null;
    try {
      
      jwtToken = Jwts.parser()
                     .setSigningKey("Some.Secret.Key.123456".getBytes())
                     .parseClaimsJws(header.replace("Bearer ", "")) // parseClaimsJws() is making the JWT token validation. NOTE: Check the exceptions thrown by this method
                     .getBody(); 
      
      isTokenValid = true;
    }
    catch (JwtException | IllegalArgumentException ex) {
      isTokenValid = false;
    }
    
    // Creating the username-password authentication token. Is a container that contains the credentials
    // This is not the JWT token. It is a token managed internally by Spring Security. 
    UsernamePasswordAuthenticationToken authentication = null;
    if (isTokenValid) {
      String username = jwtToken.getSubject();
      Object roles = jwtToken.get("authorities");
      // Transforming from JSON to Object array: SimpleGrantedAuthority[] 
      // First parameter: the readValue() method expects the JSON content as a String: roles.toString().getBytes()
      // Second parameter: Array of Object implementing the GrantedAuthority interface: SimpleGrantedAuthority[].class
      Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper().readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
      
      // Creating the username-password authentication token.
      authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    // We assign the authentication token inside the Security Context. This sentence authenticates the User inside the Http Request
    // We are not using Sessions so in this way the User is authenticated inside the Http Request
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    // The chain filters continues
    chain.doFilter(request, response);
    
  }

  // Custom function to check if the HTTP Request requires authentication
  private boolean requiresAuthentication(String header) {
    if (header == null || !header.toLowerCase().startsWith("Bearer ")) {
      return false;
    }
    return true;
  }
  
  
}
