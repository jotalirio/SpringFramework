package com.example.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String description;
  private String observations;
  
  @NotNull
  @Temporal(TemporalType.DATE)
  @Column(name = "creation_date")
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private Date creationDate;
  
  // An invoice is related to only one Client but a Client may to have many invoices so many invoices linked to one Client
  @ManyToOne(fetch = FetchType.LAZY)
  private Client client;

  // This method is creating the invoice Date automatically before to persist the invoice inside of the database
  @PrePersist
  public void prePersist() {
    this.creationDate = new Date();
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getObservations() {
    return observations;
  }

  public void setObservations(String observations) {
    this.observations = observations;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

}
