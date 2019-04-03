package com.example.springboot.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.example.springboot.app.models.entity.Client;
import com.example.springboot.app.utils.Constants;

// We are going to export the Client list. So the name will be the same that the 'list.html' view
// Without using the extension '.csv' it would work perfectly if we only have one ViewResolver linked to the 'list.html'
//@Component("list")
@Component("list.csv")
public class ClientCsvView extends AbstractView {

  
  public ClientCsvView() {
    // This setContentType() is from the 'AbstractView' class
    setContentType("text/csv");
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
    response.setContentType(getContentType()); // This getContentType() is from the 'AbstractView' class
    
    // Getting the Client list from the view
    Page<Client> clients = (Page<Client>) model.get(Constants.ATTRIBUTE_CLIENTS_KEY);
   
    // If we want to create a physical file we have to indicate the path to the physical file
//    ICsvBeanWriter beanWriter = new CsvBeanWriter(new FileWriter("pathToTheFileToBeCreated"), CsvPreference.STANDARD_PREFERENCE);
    // But we are writing the content in the Http response
    ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
    
    // Fields to be include inside the file
    String[] header = {"id", "name", "surname", "email", "creationDate"};
    beanWriter.writeHeader(header);
    
    // Iterating the clients and storing them into the CSV file
    for (Client client : clients) {
      beanWriter.write(client, header);
    }
    
    // Closing the resource
    beanWriter.close();
  }

  // We overrides this function from 'AbstractView' class
  @Override
  protected boolean generatesDownloadContent() {
    // We return true because is a downloadable content
    return true;
  }

}
