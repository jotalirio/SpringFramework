package com.example.springboot.app.view.xml;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.utils.Constants;

// We are going to export the Client list. So the name will be the same that the 'list.html' view
// Without using the extension '.xml' it would work perfectly if we only have one ViewResolver linked to the 'list.html'
@Component("list.xml")
public class ClientListXmlView extends MarshallingView {

  @Autowired  
  public ClientListXmlView(Jaxb2Marshaller marshaller) {
    super(marshaller);
  }

  // This method is overriden from the 'MarshallingView' class
  @SuppressWarnings("unchecked")
  @Override
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    
    // We are going to remove the information that we do not need to generate the XML file. We only need the 'Page<Client> list'
    model.remove(Constants.ATTRIBUTE_TITLE_KEY);
    model.remove(Constants.ATTRIBUTE_PAGE_RENDER_KEY);
    
    // Getting the Client list from the view
    Page<Client> clients = (Page<Client>) model.get(Constants.ATTRIBUTE_CLIENTS_KEY);
    
    // Now we replace the Page<Client> with a List<Client> contained inside the Wrapper class 'ClientList.java'.
    // The list will content the Client list for the page in question, not all the Clients
    // For instance, if the current page has 5 clients, the Client list rendered into the XML will have these 5 Clients.
    model.remove(Constants.ATTRIBUTE_CLIENTS_KEY);
    model.put(Constants.ATTRIBUTE_CLIENT_LIST_KEY, new ClientList(clients.getContent()));
    
    // We are passing the modified model
    super.renderMergedOutputModel(model, request, response);
  }

  
}
