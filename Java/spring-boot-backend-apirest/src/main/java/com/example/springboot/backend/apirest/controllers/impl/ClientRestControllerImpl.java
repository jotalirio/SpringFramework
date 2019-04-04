package com.example.springboot.backend.apirest.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.backend.apirest.controllers.ClientRestController;
import com.example.springboot.backend.apirest.models.entity.Client;
import com.example.springboot.backend.apirest.services.ClientService;

//@CrossOrigin(origins = {"http://localhost:4200"}, methods = {RequestMethod.GET})
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientRestControllerImpl implements ClientRestController {

  @Autowired
  private ClientService clientService;
  
  @GetMapping("/clients")
  @Override
  public List<Client> findAll() {
    return this.clientService.findAll();
  }

}
