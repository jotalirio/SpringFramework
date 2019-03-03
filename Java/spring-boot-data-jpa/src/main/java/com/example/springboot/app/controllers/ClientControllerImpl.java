package com.example.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.services.IClientService;
import com.example.springboot.app.utils.Constants;
import com.example.springboot.app.utils.paginator.PageRender;

@Controller
// We are storing in the Session the client entity when we create new Client or update an existing one
// In this way, we remove the input type = hidden with the Client´s id from the Form in the create.html file
@SessionAttributes(Constants.ATTRIBUTE_CLIENT_KEY) 
public class ClientControllerImpl implements IClientController {

  private static final String UPLOADS_DIRECTORY_FULL_PATH = Constants.STATIC_RESOURCES_DIRECTORY_PATH + Constants.UPLOADS_DIRECTORY + Constants.UPLOADS_IMAGES_DIRECTORY;
  private static final String EXTERNAL_UPLOADS_DIRECTORY_FULL_PATH = Constants.EXTERNAL_DIRECTORY_PATH + Constants.EXTERNAL_UPLOADS_DIRECTORY + Constants.EXTERNAL_UPLOADS_IMAGES_DIRECTORY;

  @Autowired
  IClientService clientService;
  
//  @RequestMapping(value = "/list", method = RequestMethod.GET)
//  @Override
//  public String listClients(Model model) {
//    List<Client> clients = this.clientService.getClients();
//    model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_LIST_CLIENTS);
//    model.addAttribute(Constants.ATTRIBUTE_CLIENTS_KEY, clients);
//    return Constants.VIEW_LIST;
//  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public String listClients(@RequestParam(name = "page", defaultValue = Constants.FIRST_PAGE) int page, Model model) {
    Pageable pageRequest = PageRequest.of(page, Constants.RESULTS_PER_PAGE);
    Page<Client> clients = this.clientService.getClients(pageRequest);
    PageRender<Client> pageRender = new PageRender("/list", clients);
    model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_LIST_CLIENTS);
    model.addAttribute(Constants.ATTRIBUTE_CLIENTS_KEY, clients);
    model.addAttribute(Constants.ATTRIBUTE_PAGE_RENDER_KEY, pageRender);
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
  public String save(@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus sessionStatus) {
    model.addAttribute(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_NEW_CLIENT);
    // If the form data has errors, return to the create View showing the form
    if(result.hasErrors()) {
      return Constants.VIEW_CREATE;
    }
    
    // Checking the photo field
    if(!photo.isEmpty()) {
      
      // Path resourcesDirectory = Paths.get(UPLOADS_DIRECTORY_FULL_PATH);
      // String rootPath = resourcesDirectory.toFile().getAbsolutePath();
      
      // Now we are going to use an external directory to store the Client´s profile photo
      String rootPath = EXTERNAL_UPLOADS_DIRECTORY_FULL_PATH;
      
      try {
        
        byte[] bytes = photo.getBytes();
        Path pathToPhoto = Paths.get(rootPath + "//" + photo.getOriginalFilename());
        Files.write(pathToPhoto, bytes);
        // Flash message
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_INFO_KEY, "The photo was uploaded successfully '" + photo.getOriginalFilename() + "'");
        // Setting the photo in Client object
        client.setPhoto(photo.getOriginalFilename());
        
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
    }
    
    // Flash attribute
    String flashMessage = (client.getId() != null && client.getId() > 0) ? "The client was updated successfully !!!" : "The new client was created successfully !!!"; 
    this.clientService.save(client);
    // We clean the Client object from the Session after save it
    sessionStatus.setComplete();
    // Flash attribute
    flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_SUCCESS_KEY, flashMessage);
    return "redirect:" + Constants.VIEW_LIST;
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  @Override
  public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
    // Getting the Client to be updated
    Optional<Client> client = null;
    // Client exists
    if(id > 0) {
      client = this.clientService.findOne(id);
      if(!client.isPresent()) {
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

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  @Override
  public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
    // Client exists
    if(id > 0) {
      // Fetching the client
      Optional<Client> client = this.clientService.findOne(id);
      if(!client.isPresent()) {
        // Flash attribute
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID does not exist in the database !!!");
        return "redirect:/" + Constants.VIEW_LIST;
      }
      this.clientService.delete(id);
      // Flash attribute
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_SUCCESS_KEY, "The client was removed successfully !!!");
    }
    else {
      // Flash attribute
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID must not be 0 !!!");
    }
    return "redirect:/" + Constants.VIEW_LIST;
  }

  @GetMapping(value="/details/{id}")
  @Override
  public String details(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
    Optional<Client> client = null;
    // Client exists
    if(id > 0) {
      // Fetching the client
      client = this.clientService.findOne(id);
      if(!client.isPresent()) {
        // Flash attribute
        flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID does not exist in the database !!!");
        return "redirect:/" + Constants.VIEW_LIST;
      }
    }
    else {
      // Flash attribute
      flash.addFlashAttribute(Constants.ATTRIBUTE_FLASH_ERROR_KEY, "The client ID must not be 0 !!!");
      return "redirect:/" + Constants.VIEW_LIST;
    }
    model.put(Constants.ATTRIBUTE_TITLE_KEY, Constants.ATTRIBUTE_TITLE_VALUE_CLIENT_DETAILS + client.get().getName());
    model.put(Constants.ATTRIBUTE_CLIENT_KEY, client.get());
    return Constants.VIEW_DETAILS;
  }
  
  

}
