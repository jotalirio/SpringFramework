package com.example.springboot.backend.apirest.controllers.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

  @GetMapping("/clients/{id}")
//  @ResponseStatus(code = HttpStatus.OK)  // Is redundant because automatically is returning this Code 200 if everything works fine
  @Override
  public Client findById(@PathVariable Long id) {
    return this.clientService.findById(id);
  }

  @PostMapping("/clients")
  @ResponseStatus(code = HttpStatus.CREATED)
  @Override
  public Client create(@RequestBody Client client) { // Spring maps automatically the JSON client with a Client entity
    return this.clientService.save(client);
  }

  @PutMapping("/clients/{id}")
  @ResponseStatus(code = HttpStatus.CREATED)
  @Override
  public Client update(@RequestBody Client client, @PathVariable Long id) {
    Client currentClient = this.clientService.findById(id);
    currentClient.setName(client.getName());
    currentClient.setSurname(client.getSurname());
    currentClient.setEmail(client.getEmail());
    return this.clientService.save(currentClient);
  }

  @DeleteMapping("/clients/{id}")
  @ResponseStatus(code = HttpStatus.NO_CONTENT)
  @Override
  public void delete(@PathVariable Long id) {
    this.clientService.delete(id);
  }
  
}
