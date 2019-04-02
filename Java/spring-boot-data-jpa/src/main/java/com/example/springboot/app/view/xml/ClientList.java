package com.example.springboot.app.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.example.springboot.app.models.entity.Client;

// This class is a Wrapper containing a Client list. We used this class to convert a Client list into XML
// With JSON we do not need to do that because is automatically done. We only need to do that when we are working
// over XML format

// If we do not indicate the name then by default it will be the Class's name: ClientList
// so in this case 'clients' will be the XML file root element: <clients></clients>
@XmlRootElement(name = "clients")
public class ClientList {

  // This is the name of each element inside the XML tree: <clients><client></client></clients>
  @XmlElement(name = "client")
  List<Client> clients;

  public ClientList() {

  }

  public ClientList(List<Client> clients) {
    this.clients = clients;
  }

  
  // Only Getter
  public List<Client> getClients() {
    return clients;
  }

}
