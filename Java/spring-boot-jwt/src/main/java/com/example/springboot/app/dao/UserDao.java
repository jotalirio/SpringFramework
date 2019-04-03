package com.example.springboot.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.springboot.app.models.entity.User;


// Note: We can extends from JpaRepository (has different methods) but extending CrudRepository is enough
public interface UserDao extends CrudRepository<User, Long> {

  // REMINDER: These queries are done at Entity level (over entities), it is not MySQL queries.
  
  // Through the method's name the JPQL query will be executed -> "SELECT u FROM User u WHERE u.username = ?1"
  public User findByUsername(String username);
  
  // Another option is to give a custom name (whatever we want) and using the @Query to indicate which query to be executed:
  @Query("SELECT u FROM User u WHERE u.username = ?1")
  public User fetchByUsername(String username);
}
