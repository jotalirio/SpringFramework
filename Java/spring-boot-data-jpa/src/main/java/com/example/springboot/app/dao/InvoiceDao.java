package com.example.springboot.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.springboot.app.models.entity.Invoice;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

  // Using LEFT JOIN FETCH to get all the relations from Invoice in one shot: The client, the invoice's lines and the products linked to the invoice's line
  @Query("SELECT i FROM Invoice i LEFT JOIN FETCH i.client c LEFT JOIN FETCH i.invoiceLines l LEFT JOIN FETCH l.product WHERE i.id=?1")
  public Invoice fetchByIdWithClientWithInvoiceLineWithProduct(Long id);
  
  /* Output using the Spring JPA 'LAZY' strategy
   * 
   * Without using LEFT JOIN FETCH, Spring JPA is making as many queries as it is necessary in order to fetch all the information when an invoice's details is consulted following the LAZY strategy
   * In this case is making one query for the invoice, another for the client, another for the invoice's lines and another 4 more related to the each invoice line product
   * 
   * 
   *  2019-03-29 10:10:49.112 DEBUG 22056 --- [nio-8080-exec-5] org.hibernate.SQL                        : select invoice0_.id as id1_1_0_, invoice0_.client_id as client_i5_1_0_, invoice0_.creation_date as creation2_1_0_, invoice0_.description as descript3_1_0_, invoice0_.observations as observat4_1_0_ from invoices invoice0_ where invoice0_.id=?
   *  2019-03-29 10:10:49.129 DEBUG 22056 --- [nio-8080-exec-5] org.hibernate.SQL                        : select client0_.id as id1_0_0_, client0_.creation_date as creation2_0_0_, client0_.email as email3_0_0_, client0_.name as name4_0_0_, client0_.photo as photo5_0_0_, client0_.surname as surname6_0_0_ from clients client0_ where client0_.id=?
   *  2019-03-29 10:10:49.133 DEBUG 22056 --- [nio-8080-exec-5] org.hibernate.SQL                        : select invoicelin0_.invoice_id as invoice_4_2_0_, invoicelin0_.id as id1_2_0_, invoicelin0_.id as id1_2_1_, invoicelin0_.product_id as product_3_2_1_, invoicelin0_.quantity as quantity2_2_1_ from invoices_lines invoicelin0_ where invoicelin0_.invoice_id=?
   *  2019-03-29 10:10:49.138 DEBUG 22056 --- [nio-8080-exec-5] org.hibernate.SQL                        : select product0_.id as id1_3_0_, product0_.creation_date as creation2_3_0_, product0_.name as name3_3_0_, product0_.price as price4_3_0_ from products product0_ where product0_.id=?
   *  2019-03-29 10:10:49.141 DEBUG 22056 --- [nio-8080-exec-5] org.hibernate.SQL                        : select product0_.id as id1_3_0_, product0_.creation_date as creation2_3_0_, product0_.name as name3_3_0_, product0_.price as price4_3_0_ from products product0_ where product0_.id=?
   *  2019-03-29 10:10:49.143 DEBUG 22056 --- [nio-8080-exec-5] org.hibernate.SQL                        : select product0_.id as id1_3_0_, product0_.creation_date as creation2_3_0_, product0_.name as name3_3_0_, product0_.price as price4_3_0_ from products product0_ where product0_.id=?
   *  2019-03-29 10:10:49.146 DEBUG 22056 --- [nio-8080-exec-5] org.hibernate.SQL                        : select product0_.id as id1_3_0_, product0_.creation_date as creation2_3_0_, product0_.name as name3_3_0_, product0_.price as price4_3_0_ from products product0_ where product0_.id=?
   * 
   */
  
  /* Output using LEFT JOIN FETCH
   * 
   * Using LEFT JOIN FETCH we are retrieving the same information but using only one query
   * 
   * 
   * select invoice0_.id as id1_1_0_, 
   *        client1_.id as id1_0_1_, 
   *        invoicelin2_.id as id1_2_2_, 
   *        product3_.id as id1_3_3_, 
   *        invoice0_.client_id as client_i5_1_0_, 
   *        invoice0_.creation_date as creation2_1_0_, 
   *        invoice0_.description as descript3_1_0_, 
   *        invoice0_.observations as observat4_1_0_, 
   *        client1_.creation_date as creation2_0_1_, 
   *        client1_.email as email3_0_1_, 
   *        client1_.name as name4_0_1_, 
   *        client1_.photo as photo5_0_1_, 
   *        client1_.surname as surname6_0_1_, 
   *        invoicelin2_.product_id as product_3_2_2_, 
   *        invoicelin2_.quantity as quantity2_2_2_, 
   *        invoicelin2_.invoice_id as invoice_4_2_0__, 
   *        invoicelin2_.id as id1_2_0__, 
   *        product3_.creation_date as creation2_3_3_, 
   *        product3_.name as name3_3_3_, 
   *        product3_.price as price4_3_3_ 
   *        
   *        from invoices invoice0_ 
   *        
   *        left outer join clients client1_ on invoice0_.client_id=client1_.id 
   *        left outer join invoices_lines invoicelin2_ on invoice0_.id=invoicelin2_.invoice_id 
   *        left outer join products product3_ on invoicelin2_.product_id=product3_.id 
   *             
   *        where invoice0_.id=?
   * 
   */
  
}
