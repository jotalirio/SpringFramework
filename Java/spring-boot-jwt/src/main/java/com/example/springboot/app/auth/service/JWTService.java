package com.example.springboot.app.auth.service;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.Claims;

public interface JWTService {

  public String create(Authentication authResult) throws IOException;
  public boolean validate(String jwtToken);
  public Claims getClaims(String jwtToken);
  public String getUsername(String jwtToken);
  public Collection<? extends GrantedAuthority> getRoles(String jwtToken) throws IOException;
  public String resolve(String jwtToken);
  
}
