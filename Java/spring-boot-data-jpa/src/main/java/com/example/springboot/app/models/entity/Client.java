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

@Entity
@Table(name = "Clients")
public class Client implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @NotEmpty
  public String name;

  @NotEmpty
  public String surname;

  @NotEmpty
  @Email
  public String email;

  @NotNull
  @Column(name = "creation_date")
  @Temporal(TemporalType.DATE)
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  public Date creationDate;

  private String photo;

  // A Client may have many invoices
  // 'mappedBy' indicates the attribute 'client' in Invoice class. 
  // Using 'mappedBy' we have a bidirectional mapping between the Client and Invoice classes through the 'invoices' and 'client' attributes
  // so we can fetch the invoices linked to a Client and the Client from a Invoice as well 
  // Automatically the foreign key 'client_id' pointing to 'clients' table is created inside of 'invoices' table
  @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<Invoice> invoices;

  
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
