package com.example.springboot.app.view.json;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.utils.Constants;

// In JSON we do not need a Wrapper class like with XML format because the serialisation of Collections objects is done automatically
@Component("list.json")
public class ClientListJsonView extends MappingJackson2JsonView {

  // This method is overriden from the 'MappingJackson2JsonView' class. We use this method to filter some elements passed to the view
  @SuppressWarnings("unchecked")
  @Override
  protected Object filterModel(Map<String, Object> model) {

    // We are going to remove the information that we do not need to generate the JSON file.
    model.remove(Constants.ATTRIBUTE_TITLE_KEY);
    model.remove(Constants.ATTRIBUTE_PAGE_RENDER_KEY);
    
    // Getting the Client list from the view
    Page<Client> clients = (Page<Client>) model.get(Constants.ATTRIBUTE_CLIENTS_KEY);
    
    // To avoid information regarding the pagination, we have to replace the Page<Client> with a List<Client> 
    // The list will content the Client list for the page in question, not all the Clients
    // For instance, if the current page has 5 clients, the Client list rendered into the JSON file will have these 5 Clients.
    model.remove(Constants.ATTRIBUTE_CLIENTS_KEY);
    model.put(Constants.ATTRIBUTE_CLIENTS_KEY, clients.getContent());
    
    // We are passing the modified model
    return super.filterModel(model);
  }

}
