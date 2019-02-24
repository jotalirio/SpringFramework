package com.example.springboot.app.controllers.impl;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.springboot.app.controllers.IClientController;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.services.IClientService;
import com.example.springboot.app.utils.Constants;

@Controller
public class ClientControllerImpl implements IClientController {

  @Autowired
  IClientService clientService;
  
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @Override
  public String listClients(Model model) {
    List<Client> clients = this.clientService.getClients();
    model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_LIST_CLIENTS);
    model.addAttribute(Constants.ATTRIBUTE_CLIENTS_KEY, clients);
    return Constants.VIEW_LIST;
  }

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  @Override
  public String create(Map<String, Object> model) {
    Client client = new Client();
    model.put(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_NEW_CLIENT);
    model.put(Constants.ATTRIBUTE_CLIENT_KEY, client);
    return Constants.VIEW_CREATE;
  }

  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @Override
  public String save(@Valid Client client, BindingResult result, Model model) {
    model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_NEW_CLIENT);
    // If the form data has errors, return to the create View showing the form
    if(result.hasErrors()) {
      return Constants.VIEW_CREATE;
    }
    this.clientService.save(client);
    return "redirect:" + Constants.VIEW_LIST;
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  @Override
  public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model) {
    // Getting the Client to be updated
    Client client = null;
    // Client exists
    if(id > 0) {
      client = this.clientService.findOne(id);
    }
    else { 
      // Client does not exist
      return "redirect:" + Constants.VIEW_LIST;
    }
    model.put(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_EDIT_CLIENT);
    model.put(Constants.ATTRIBUTE_CLIENT_KEY, client);
    return Constants.VIEW_CREATE;
  }

}
