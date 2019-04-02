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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "invoices")
public class Invoice implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotEmpty
  private String description;
  
  private String observations;
  
//  @NotNull
  @Temporal(TemporalType.DATE)
  @Column(name = "creation_date")
//  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private Date creationDate;
  
  // An invoice is related to only one Client but a Client may to have many invoices so many invoices linked to one Client
  // Automatically the foreign key 'client_id' is created on 'invoices' table
  // but optionally we can use the @JoinColumn annotation to specify explicit the foreign key 'client_id' on 'invoices' table
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "client_id")
  private Client client;

  // A Invoice may have many invoices lines
  // We will fetch the invoice's lines linked to an invoice from Invoice class but we are not fetching the invoice linked to an invoice line from the InvoiceLine
  // so in this case we have a unidirectional mapping between Invoice and InvoiceLine classes.
  // Using the @JoinColumn annotation we are creating explicitly the foreign key 'invoice_id' inside the 'invoices_lines' table in order to make the relation
  // between an invoice and its invoice's lines. We are doing that in this way due to the unidirectional mapping/relation between Invoice and InvoiceLine classes.
  // So in this case we are not using the 'mappedBy' attribute
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "invoice_id")
  private List<InvoiceLine> invoiceLines;
  
  
  public Invoice() {
    this.invoiceLines = new ArrayList<InvoiceLine>();
  }

  
  // This method is creating the invoice's creation Date automatically before to persist the invoice in question inside of the database
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

  // This annotation avoid this attribute inside the XML serialisation
  // We need to do that because when the invoices are fetched then 
  // the client is fetched another time and after that the invoices
  // are fetched another time in this way until infinite... generating
  // an infinite loop
  @XmlTransient
  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public List<InvoiceLine> getInvoiceLines() {
    return invoiceLines;
  }

  public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
    this.invoiceLines = invoiceLines;
  }
  
  
  /* Methods */
  
  public void addInvoiceLine(InvoiceLine invoiceLine) {
    this.invoiceLines.add(invoiceLine);
  }
  
  public Double getTotal() {
    
//    Double totalAmount = 0.0;
//    for (InvoiceLine invoiceLine : this.invoiceLines) {
//      totalAmount += invoiceLine.calculateAmount();
//    }
//    return totalAmount;
    
    // Using streams
    return this.invoiceLines.stream().mapToDouble( it -> it.calculateAmount()).reduce(0.0, Double::sum);
  }
}
