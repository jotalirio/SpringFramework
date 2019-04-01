package com.example.springboot.app.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.app.dao.UserDao;
import com.example.springboot.app.models.entity.Role;

@Service("jpaUserDetailsService") // The Bean (component) name
public class JpaUserDetailsService implements UserDetailsService {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private UserDao userDao;
  
  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // We get the user
    com.example.springboot.app.models.entity.User user = (com.example.springboot.app.models.entity.User) this.userDao.findByUsername(username);   
    if (user == null) {
      LOGGER.error("ERROR login: the user does not exist !!!");
      throw new UsernameNotFoundException("ERROR login: the user does not exist !!!");
    }
    // We get the User's authorities (roles)
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for(Role role : user.getRoles()) {
      authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
    }
    if (authorities.isEmpty()) {
      LOGGER.error("ERROR login: the user has not assigned roles !!!");
      throw new UsernameNotFoundException("ERROR login: the user has not assigned roles !!!!");
    }
    return new User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
  }

}
