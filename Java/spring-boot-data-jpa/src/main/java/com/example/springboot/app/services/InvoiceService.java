package com.example.springboot.app.services;

import java.util.Optional;

import com.example.springboot.app.models.entity.Invoice;

public interface InvoiceService {

  public Optional<Invoice> findById(Long id);
  public void save(Invoice invoice);
  
}
