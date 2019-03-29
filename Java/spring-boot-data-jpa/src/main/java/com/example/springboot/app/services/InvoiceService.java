package com.example.springboot.app.services;

import com.example.springboot.app.models.entity.Invoice;

public interface InvoiceService {

  public Invoice findById(Long id);
  public Invoice fetchByIdWithClientWithInvoiceLineWithProduct(Long id);
  public void delete(Long id);
  public void save(Invoice invoice);
  
}
