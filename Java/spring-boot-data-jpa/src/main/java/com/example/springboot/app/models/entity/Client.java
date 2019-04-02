package com.example.springboot.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Clients")
public class Client implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String name;

  @NotEmpty
  private String surname;

  @NotEmpty
  @Email
  private String email;

  // The @JsonFormat is used to format the creationDate field when we are creating the JSON export file
  @NotNull
  @Column(name = "creation_date")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
  private Date creationDate;

  private String photo;

  // A Client may have many invoices
  // 'mappedBy' indicates the attribute 'client' in Invoice class. 
  // Using 'mappedBy' we have a bidirectional mapping between the Client and Invoice classes through the 'invoices' and 'client' attributes
  // so we can fetch the invoices linked to a Client and the Client from a Invoice as well 
  // Automatically the foreign key 'client_id' pointing to 'clients' table is created inside of 'invoices' table
  
  // The @JsonIgnore annotation avoid this attribute inside the JSON serialisation
  // We need to do that because when the invoices are fetched then 
  // the client is fetched another time and after that the invoices
  // are fetched another time in this way until infinite... generating
  // an infinite loop
    
  // We are going to solve the infinite loop due to the relation between Client and Invoice using the @JasonManagedReference in the Client class
  // and the annotation @JsonBackReference in the Invoice class
  // In this way now we can show the Clients and their invoices inside the JSON avoiding the infinite loop
  @JsonManagedReference
  // @JsonIgnore
  @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Invoice> invoices;

  
  public Client() {
    this.invoices = new ArrayList<Invoice>();
  }

  
  /* Getters and Setters */
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public List<Invoice> getInvoices() {
    return invoices;
  }

  public void setInvoices(List<Invoice> invoices) {
    this.invoices = invoices;
  }

  
  /* Methods */

  public void addInvoice(Invoice invoice) {
    this.invoices.add(invoice);
  }


  @Override
  public String toString() {
    return name + " " + surname;
  }

}
