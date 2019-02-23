package com.example.springboot.app.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springboot.app.controllers.IClientController;
import com.example.springboot.app.services.IClientService;
import com.example.springboot.app.utils.Constants;

@Controller
public class ClientControllerImpl implements IClientController {

  @Autowired
  IClientService clientService;
  
  @RequestMapping(value="/list", method=RequestMethod.GET)
  @Override
  public String listClients(Model model) {
    model.addAttribute(Constants.ATTRIBUTE_TITLE, Constants.CLIENTS_LIST);
    model.addAttribute(Constants.ATTRIBUTE_CLIENTS, clientService.getClients());
    return Constants.VIEW_LIST;
  }

}
