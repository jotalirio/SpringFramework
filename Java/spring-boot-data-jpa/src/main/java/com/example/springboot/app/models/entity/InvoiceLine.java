package com.example.springboot.app.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  @ManyToOne
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
