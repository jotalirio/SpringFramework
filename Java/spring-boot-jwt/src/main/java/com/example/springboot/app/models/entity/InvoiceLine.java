package com.example.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "invoices_lines")
public class InvoiceLine implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer quantity;

  // One product may to be linked to many invoices lines
  // Automatically the foreign key 'product_id' is created on invoices_lines' table
  // but optionally we can use the @JoinColumn annotation to specify explicit the foreign key 'product_id' on 'invoices_lines' table
  // We need this foreign key to create the relation between the 'invoices_lines' and 'products' tables
  
  // We have problems to serialize the Product object into JSON because is trying to serialize the Product proxy object used by the FetchType LAZY
  // This proxy object is not the Product object original is a proxy object and has complementary fields. So we need to change to EAGER to retrieve
  // Immediately the product linked to a invoice line and in this case the Product object is the original one and no a proxy object
  // But this is not a proper solution so we are going to continue using LAZY and adopting another better solution: @JsonIgnoreProperties
  // With this solution we are avoiding the serialization of these complementary fields from the Product proxy object
  
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @ManyToOne(fetch = FetchType.LAZY)
  // @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  private Product product;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
  
  
  /* Methods */

  public Double calculateAmount() {
    return this.quantity.doubleValue() * this.product.getPrice();
  }
  
}
