package com.example.springboot.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.springboot.app.models.entity.Invoice;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

}
