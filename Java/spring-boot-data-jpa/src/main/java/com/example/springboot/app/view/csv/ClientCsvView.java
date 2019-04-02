package com.example.springboot.app.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

// We are going to export the Client list. So the name will be the same that the 'list.html' view
@Component("list")
public class ClientCsvView extends AbstractView {

  
  public ClientCsvView() {
    // This setContentType() is from the 'AbstractView' class
    setContentType("text/csv");
  }

  
  @Override
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    response.setHeader("Content-Disposition", "attachment; filename=\"clients.csv\"");
    response.setContentType(getContentType()); // This getContentType() is from the 'AbstractView' class
    
  }

  // We overrides this function from 'AbstractView' class
  @Override
  protected boolean generatesDownloadContent() {
    // We return true because is a downloadable content
    return true;
  }

}
