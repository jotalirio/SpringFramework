package com.example.springboot.app.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.app.dao.InvoiceDao;
import com.example.springboot.app.models.entity.Invoice;
import com.example.springboot.app.services.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

  @Autowired
  private InvoiceDao invoiceDao;
  
  @Transactional
  @Override
  public void save(Invoice invoice) {
    this.invoiceDao.save(invoice);
  }
  
  @Transactional
  @Override
  public void delete(Long id) {
    this.invoiceDao.deleteById(id);
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<Invoice> findById(Long id) {
    return this.invoiceDao.findById(id);
  }

  @Transactional(readOnly = true)
  @Override
  public Invoice fetchByIdWithClientWithInvoiceLineWithProduct(Long id) {
    return this.invoiceDao.fetchByIdWithClientWithInvoiceLineWithProduct(id);
  }

  
}
