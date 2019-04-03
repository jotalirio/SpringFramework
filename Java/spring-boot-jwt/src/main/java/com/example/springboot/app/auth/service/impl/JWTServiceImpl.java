package com.example.springboot.app.auth.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.example.springboot.app.auth.SimpleGrantedAuthorityMixin;
import com.example.springboot.app.auth.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl implements JWTService {

  public static final String SECRET = Base64Utils.encodeToString("Some.Secret.Key.123456".getBytes());
  public static final long EXPIRATION_DATE = 14000400L;
  public static final String JWT_TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_AUTHORIZATION = "Authorization";
  
  @Override
  public String create(Authentication authResult) throws IOException {
    String username = ((User) authResult.getPrincipal()).getUsername();
    Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
    Claims claims = Jwts.claims();
    claims.put("authorities", new ObjectMapper().writeValueAsString(roles)); // roles in JSON format
    
    // Other way to get the 'username'
//    String username = authResult.getName();
    
    // The claims, username, issueAt, expiration date conforms the Pay Load part
    // Creating the JWT token
    String jwtToken = Jwts.builder()
                          .setClaims(claims)
                          .setSubject(username) 
                          .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                          .setIssuedAt(new Date()) // Creation date
                          .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE)) // Expiration day, by default: Milliseconds + 3600000L (1 hour). 14000400 = 3600000 * 4 hours
                          .compact();
    return jwtToken;
  }

  @Override
  public boolean validate(String jwtToken) {
    boolean isTokenValid;
    try {
      
      this.getClaims(jwtToken);
      isTokenValid = true;
    }
    catch (JwtException | IllegalArgumentException ex) {
      isTokenValid = false;
    }
    return isTokenValid;
  }

  @Override
  public Claims getClaims(String jwtToken) {
    // Getting the JWT token Claims
    Claims jwtTokenClaims = Jwts.parser().setSigningKey(SECRET.getBytes())
                                         .parseClaimsJws(this.resolve(jwtToken)) // parseClaimsJws() is making the JWT token validation. NOTE: Check the exceptions thrown by this method
                                         .getBody(); 
    return jwtTokenClaims;
  }

  @Override
  public String getUsername(String jwtToken) {
    return this.getClaims(jwtToken).getSubject();
  }

  @Override
  public Collection<? extends GrantedAuthority> getRoles(String jwtToken) throws IOException {
    // Transforming from JSON to Object array: SimpleGrantedAuthority[] 
    // First parameter: the readValue() method expects the JSON content as a String: roles.toString().getBytes()
    // Second parameter: Array of Object implementing the GrantedAuthority interface: SimpleGrantedAuthority[].class
    Object roles = this.getClaims(jwtToken).get("authorities");
    Collection<? extends GrantedAuthority> authorities = Arrays.asList(new ObjectMapper().addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                                                                                         .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class));
    return authorities;
  }

  @Override
  public String resolve(String jwtToken) {
    if (jwtToken != null && jwtToken.startsWith(JWT_TOKEN_PREFIX)) {
      return jwtToken.replace(JWT_TOKEN_PREFIX, "");
    }
    return null;
  }

}
