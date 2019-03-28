package com.example.springboot.app.controllers.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.app.controllers.InvoiceController;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.models.entity.Invoice;
import com.example.springboot.app.models.entity.InvoiceLine;
import com.example.springboot.app.models.entity.Product;
import com.example.springboot.app.services.ClientService;
import com.example.springboot.app.services.InvoiceService;
import com.example.springboot.app.services.ProductService;
import com.example.springboot.app.utils.Constants;

@Controller
@RequestMapping("/invoice")
// We are storing into the Session the invoice entity when we create new Invoice until the invoice's form 
// is sent to the 'Save' function, this method will process the form data and persist these data into the database
// The data to be persisted will be: the invoice, its client and the invoice's lines
@SessionAttributes(Constants.ATTRIBUTE_INVOICE_KEY) 
public class InvoiceControllerImpl implements InvoiceController {

  private static final String INVOICE_CREATE_VIEW = Constants.INVOICE_VIEWS_PATH + Constants.VIEW_CREATE;
  private final Logger LOGGER = LoggerFactory.getLogger(InvoiceControllerImpl.class);

  
  @Autowired
  private ClientService clientService;
  
  @Autowired
  private ProductService productService;
  
  @Autowired
  private InvoiceService invoiceService;
  
  
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

  @PostMapping("/create")
  @Override
  public String save(Invoice invoice, 
                     @RequestParam(name = "invoiceLine_id[]", required = false) Long[] invoiceLinesId, // These Ids are the product's id linked to each invoice's line
                     @RequestParam(name = "quantity[]", required = false) Integer[] quantities,
                     RedirectAttributes flash,
                     SessionStatus sessionStatus) {
    
    // For every invoiceLine we need to retrieve its related product using the 'product_id' passed from the HTML 'input' through the 'value' attribute
    for (int i = 0; i < invoiceLinesId.length; i++) {
      // We fetch the product by id
      Long product_id = invoiceLinesId[i];
      Optional<Product> product = this.productService.findById(product_id);
      
      // We create the invoice line containing the product in question and the quantity of product
      InvoiceLine invoiceLine = new InvoiceLine();
      invoiceLine.setProduct(product.get());
      invoiceLine.setQuantity(quantities[i]);
      
      // Adding the invoice line to the invoice
      invoice.addInvoiceLine(invoiceLine);
      LOGGER.info("(product_id: " + invoiceLinesId[i].toString() + ", " + "quantity: " + quantities[i].toString() + ")");
    }

    // Saving the invoice in the database
    this.invoiceService.save(invoice);
    // We clean the Invoice object from the Session after save it
    sessionStatus.setComplete();
    // Flash attribute
    String flashMessage = "The new invoice was created successfully !!!"; 
    flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_SUCCESS_KEY, flashMessage);
    return "redirect:/details/" + invoice.getClient().getId();
  }

  
}
