package com.example.springboot.app.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.app.controllers.ClientRestController;
import com.example.springboot.app.services.ClientService;
import com.example.springboot.app.view.xml.ClientList;

// The annotation @RestController combine the annotation @Controller and @ResponseBody so we can remove the keyword @ResponseBody after the method visibility
// so instead of doing this public @ResponseBody ClientList(){ ... } we can do that: public ClientList list() { ... }
@RestController
@RequestMapping("/api/clients")
public class ClientRestControllerImpl implements ClientRestController {

  @Autowired
  private ClientService clientService;
  
  // Only a JWT token generated for a user with role ROLE_ADMIN can get authorization fot this resource
//  @Secured("ROLE_ADMIN")
  @GetMapping(value = "/list")
  @Override
  public ClientList list() {
    return new ClientList(this.clientService.getClients());
  }
  
}
