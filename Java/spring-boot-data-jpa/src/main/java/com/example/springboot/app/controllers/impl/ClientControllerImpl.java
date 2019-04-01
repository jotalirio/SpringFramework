package com.example.springboot.app.controllers.impl;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springboot.app.controllers.ClientController;
import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.services.ClientService;
import com.example.springboot.app.services.UploadFileService;
import com.example.springboot.app.utils.Constants;
import com.example.springboot.app.utils.paginator.PageRender;

@Controller
// We are storing in the Session the client entity when we create new Client or update an existing one
// In this way, we remove the input type = hidden with the Client´s id from the Form in the create.html file
@SessionAttributes(Constants.ATTRIBUTE_CLIENT_KEY) 
public class ClientControllerImpl implements ClientController {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private ClientService clientService;
  
  @Autowired
  private UploadFileService uploadFileService;
  
    // Use this method with IClientDao or IClientDaoCrudRepository
//  @RequestMapping(value = "/list", method = RequestMethod.GET)
//  @Override
//  public String listClients(Model model) {
//    List<Client> clients = this.clientService.getClients();
//    model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_LIST_CLIENTS);
//    model.addAttribute(Constants.ATTRIBUTE_CLIENTS_KEY, clients);
//    return Constants.VIEW_LIST;
//  }

  @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public String listClients(@RequestParam(name = "page", defaultValue = Constants.FIRST_PAGE) int page, Model model, 
                            Authentication authentication, HttpServletRequest request) {
    
    if (authentication != null) {
      LOGGER.info("Hi authenticated user, your username is: ".concat(authentication.getName()));
    }
    
    // We can retrieve the Authentication object in a static way instead passing it to the method by parameter
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      LOGGER.info("Retrieving the Authentication object in a static way -> Authenticated user, username: ".concat(auth.getName()));
    }
    
    // We checks the User's role
    if (this.hasRole("ROLE_ADMIN")) {
      LOGGER.info("Using the custom 'hasRole()' method -> Hi ".concat(auth.getName()).concat(", you have access to this resource !!!"));
    }
    else {
      LOGGER.info("Using the custom 'hasRole()' method -> Hi ".concat(auth.getName()).concat(", you do NOT have access to this resource !!!"));
    }
    
    // Getting the object that wraps the HttpServletRequest object in order to check if the user is authorised to access (checking the role)
//    SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
//    if (securityContext.isUserInRole("ADMIN")) {
    SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
    if (securityContext.isUserInRole("ROLE_ADMIN")) {

      LOGGER.info("Using the 'SecurityContextHolderAwareRequestWrapper' -> Hi ".concat(auth.getName()).concat(", you have access to this resource !!!"));
    }
    else {
      LOGGER.info("Using the 'SecurityContextHolderAwareRequestWrapper' -> Hi ".concat(auth.getName()).concat(", you do NOT have access to this resource !!!"));
    }
    // Checking the role using the 'HttpServletRequest' directly
    if (request.isUserInRole("ROLE_ADMIN")) {
      LOGGER.info("Using the 'HttpServletRequest' -> Hi ".concat(auth.getName()).concat(", you have access to this resource !!!"));
    }
    else {
      LOGGER.info("Using the 'HttpServletRequest' -> Hi ".concat(auth.getName()).concat(", you do NOT have access to this resource !!!"));
    }

    
    Pageable pageRequest = PageRequest.of(page, Constants.RESULTS_PER_PAGE);
    Page<Client> clients = this.clientService.getClients(pageRequest);
    PageRender<Client> pageRender = new PageRender("/list", clients);
    model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_LIST_CLIENTS);
    model.addAttribute(Constants.ATTRIBUTE_CLIENTS_KEY, clients);
    model.addAttribute(Constants.ATTRIBUTE_PAGE_RENDER_KEY, pageRender);
    return Constants.VIEW_LIST;
  }
  
  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/create", method = RequestMethod.GET)
  @Override
  public String create(Map<String, Object> model) {
    Client client = new Client();
    model.put(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_NEW_CLIENT);
    model.put(Constants.ATTRIBUTE_CLIENT_KEY, client);
    return Constants.VIEW_CREATE;
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/create", method = RequestMethod.POST)
  @Override
  public String save(@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus sessionStatus) {
    // If the form data has errors, return to the create View showing the form
    if(result.hasErrors()) {
      model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_NEW_CLIENT);
      return Constants.VIEW_CREATE;
    }
    // Checking the photo field
    if(!photo.isEmpty()) {
      // Deleting the previous Client´s photo
      if(client.getId() != null && client.getId() > 0 
          && client.getPhoto() != null && !client.getPhoto().isEmpty()) {
        
        // Deleting the previous Client´s photo
        if(!this.uploadFileService.deleteImage(client.getPhoto())) {
          LOGGER.warn("There was a problem deleting the previous client´s photo '" + client.getPhoto() + "'.");
        }
      }
      // Saving the  new client´s photo
      String uniqueFilename = this.uploadFileService.createImage(photo);
      if(uniqueFilename != null) {
        // Flash message
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_INFO_KEY, "The photo was uploaded successfully '" + photo.getOriginalFilename() + "'");
        // Setting the photo in Client object
        client.setPhoto(uniqueFilename);
      }
      else {
        // Flash message
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "There was a problem saving the photo '" + photo.getOriginalFilename() + "', please edit the profile and try again.");       
      }              
    }
    // Flash attribute
    String flashMessage = (client.getId() != null && client.getId() > 0) ? "The client was updated successfully !!!" : "The new client was created successfully !!!"; 
    this.clientService.save(client);
    // We clean the Client object from the Session after save it
    sessionStatus.setComplete();
    // Flash attribute
    flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_SUCCESS_KEY, flashMessage);
    return "redirect:/" + Constants.VIEW_LIST;
  }  
  
//  @Secured("ROLE_ADMIN")
//  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // We can use this annotation instead '@Secured', they both are equivalent
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')") // We can use this annotation instead '@Secured', they both are equivalent
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  @Override
  public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
    // Getting the Client to be updated
    Client client = null;
    // Client exists
    if(id > 0) {
      /* Using 'fetch = FetchType.LAZY' JPA strategy */
//      client = this.clientService.findOne(id);
      /* Using LEFT JOIN FETCH */
      client = this.clientService.fetchByIdWithInvoices(id);
      if(client == null) {
        // Flash attribute
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID does not exist in the database !!!");
        return "redirect:/" + Constants.VIEW_LIST;
      }
    }
    else { 
      // Client does not exist
      // Flash attribute
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID can not be null !!!");
      return "redirect:" + Constants.VIEW_LIST;
    }
    model.put(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_EDIT_CLIENT);
    model.put(Constants.ATTRIBUTE_CLIENT_KEY, client);
    return Constants.VIEW_CREATE;
  }
  
  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  @Override
  public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
    // Client exists
    if(id > 0) {
      // Fetching the client
      /* Using 'fetch = FetchType.LAZY' JPA strategy */ 
//      Client client = this.clientService.findOne(id);
      /* Using LEFT JOIN FETCH */ 
      Client client = this.clientService.fetchByIdWithInvoices(id);
      if(client == null) {
        // Flash attribute
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID does not exist in the database !!!");
        return "redirect:/" + Constants.VIEW_LIST;
      }
            
      this.clientService.delete(id);
      // Flash attribute
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_SUCCESS_KEY, "The client was removed successfully !!!");
      // Deleting the Client´s photo
      if(this.uploadFileService.deleteImage(client.getPhoto())) {
        // Flash attribute
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_INFO_KEY, "The client´s photo '" + client.getPhoto() + "' was deleted successfully !!!");
      }
      else {
        LOGGER.warn("There was a problem deleting the client´s photo '" + client.getPhoto() + "'.");
      }
    }
    else {
      // Flash attribute
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID must not be 0 !!!");
    }
    return "redirect:/" + Constants.VIEW_LIST;
  }
  
//  @Secured("ROLE_USER")
  @PreAuthorize("hasRole('ROLE_USER')") // We can use this annotation instead '@Secured', they both are equivalent
  @GetMapping(value="/details/{id}")
  @Override
  public String details(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
    Client client = null;
    // Client exists
    if(id > 0) {
      // Fetching the client
      /* Using 'fetch = FetchType.LAZY' JPA strategy */
//      client = this.clientService.findOne(id);
      /* Using LEFT JOIN FETCH */ 
      client = this.clientService.fetchByIdWithInvoices(id);
      if(client == null) {
        // Flash attribute
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client does not exist in the database !!!");
        return "redirect:/" + Constants.VIEW_LIST;
      }
    }
    else {
      // Flash attribute
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID must not be 0 !!!");
      return "redirect:/" + Constants.VIEW_LIST;
    }
    model.put(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_CLIENT_DETAILS + client.getName() + " " + client.getSurname());
    model.put(Constants.ATTRIBUTE_CLIENT_KEY, client);
    return Constants.VIEW_DETAILS;
  }
  
  // Using .+ Spring avoid to split the filename deleting the image extension
//  @Secured({"ROLE_USER", "ROLE_ADMIN"})
  @Secured({"ROLE_USER"})
  @GetMapping(value = "/uploads/images/{filename:.+}")
  public ResponseEntity<Resource> getPhoto(@PathVariable(value = "filename") String fileName) {
    Resource resource = this.uploadFileService.loadImage(fileName);
    ResponseEntity<Resource> responseEntity = null;
    if(resource != null && resource.exists() && resource.isReadable()) {
      responseEntity = ResponseEntity.ok()
                       .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +  resource.getFilename() + "\"")
                       .body(resource);
    }
    else {
      responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(this.uploadFileService.getImageNotFound());
    }
    return responseEntity;
  }
  
  
  private boolean hasRole(String role) {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context == null) {
      return false;
    }
    Authentication auth = context.getAuthentication();
    if (auth == null) {
      return false;
    }
    


    // Any class 'Role' or representing a 'Role' has to implement the 'GrantedAuthority' interface
    // A collection of any class that inherits from GrantedAuthority
    Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
    if (authorities ==  null) {
      return false;
    }
    /*
     * 
     
    for (GrantedAuthority authority : authorities) {
      if (role.equals(authority.getAuthority())) {
        LOGGER.info("Hi user ".concat(auth.getName()).concat(", you role is: ").concat(authority.getAuthority()));
        return true;
      }
    }
    return false;
    
    *
    */
    
    // Equivalent way to the previous one using for: Using the 'SimpleGrantedAuthority' class that implements the 'GrantedAuthority' interface
    return authorities.contains(new SimpleGrantedAuthority(role));
    
  }
  
}
