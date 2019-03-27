package com.example.springboot.app.controllers.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.app.controllers.InvoiceController;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.models.entity.Invoice;
import com.example.springboot.app.services.ClientService;
import com.example.springboot.app.utils.Constants;

@Controller
@RequestMapping("/invoice")
// We are storing into the Session the invoice entity when we create new Invoice until the invoice's form 
// is sent to the 'Save' function, this method will process the form data and persist these data into the database
// The data to be persisted will be: the invoice, its client and the invoice's lines
@SessionAttributes(Constants.ATTRIBUTE_INVOICE_KEY) 
public class InvoiceControllerImpl implements InvoiceController {

  private static final String INVOICE_CREATE_VIEW = Constants.INVOICE_VIEWS_PATH + Constants.VIEW_CREATE;
  
  
  @Autowired
  private ClientService clientService;
  
  @GetMapping("/create/{clientId}")
  @Override
  public String create(@PathVariable(value = "clientId") Long clientId, Map<String, Object> model, RedirectAttributes flash) {
    
    Optional<Client> client = this.clientService.findOne(clientId);
    
    if(!client.isPresent()) {
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID does not exist in the database !!!");
      return "redirect:/" + Constants.VIEW_LIST;
    }
    
    // Here we make the relation between the invoice and its client
    Invoice invoice = new Invoice();
    invoice.setClient(client.get());
    
    // Passing the invoice instance to the view
    model.put(Constants.ATTRIBUTE_INVOICE_KEY, invoice);
    model.put(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_NEW_INVOICE);
    
    return INVOICE_CREATE_VIEW;
  }

}
