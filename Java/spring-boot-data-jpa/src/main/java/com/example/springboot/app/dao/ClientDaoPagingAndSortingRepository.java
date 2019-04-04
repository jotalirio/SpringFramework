package com.example.springboot.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.springboot.app.models.entity.Client;

public interface ClientDaoPagingAndSortingRepository extends PagingAndSortingRepository<Client, Long> {

 // Query at Entity level (HQL -> Hibernate Query Language), not at Table level
 // Using LEFT JOIN FETCH to get all the relations from Client in one shot: In this case his invoices
 @Query("SELECT c FROM Client c LEFT JOIN FETCH c.invoices i WHERE c.id=?1")
 public Client fetchByIdWithInvoices(Long id);
 
 /* Output using the Spring JPA 'LAZY' strategy
  * 
  * Without using LEFT JOIN FETCH, Spring JPA is making as many queries as it is necessary in order to fetch all the information when an client's details is consulted following the LAZY strategy
  * In this case is making one query for the client and another more to get the client's invoices
  * 
  * 
  * 2019-03-29 10:32:40.523 DEBUG 22056 --- [io-8080-exec-10] org.hibernate.SQL:  select client0_.id as id1_0_0_, client0_.creation_date as creation2_0_0_, client0_.email as email3_0_0_, client0_.name as name4_0_0_, client0_.photo as photo5_0_0_, client0_.surname as surname6_0_0_ from clients client0_ where client0_.id=?
  * 2019-03-29 10:32:40.538 DEBUG 22056 --- [io-8080-exec-10] org.hibernate.SQL:  select invoices0_.client_id as client_i5_1_0_, invoices0_.id as id1_1_0_, invoices0_.id as id1_1_1_, invoices0_.client_id as client_i5_1_1_, invoices0_.creation_date as creation2_1_1_, invoices0_.description as descript3_1_1_, invoices0_.observations as observat4_1_1_ from invoices invoices0_ where invoices0_.client_id=?
  * 
  */
 
 /* Output using LEFT JOIN FETCH
  * 
  * Using LEFT JOIN FETCH we are retrieving the same information but using only one query
  * 
  * 
  * select client0_.id as id1_0_0_, 
  *        invoices1_.id as id1_1_1_, 
  *        client0_.creation_date as creation2_0_0_, 
  *        client0_.email as email3_0_0_, 
  *        client0_.name as name4_0_0_, 
  *        client0_.photo as photo5_0_0_, 
  *        client0_.surname as surname6_0_0_, 
  *        invoices1_.client_id as client_i5_1_1_, 
  *        invoices1_.creation_date as creation2_1_1_, 
  *        invoices1_.description as descript3_1_1_, 
  *        invoices1_.observations as observat4_1_1_, 
  *        invoices1_.client_id as client_i5_1_0__, 
  *        invoices1_.id as id1_1_0__ 
  * 
  * from clients client0_ 
  * 
  * left outer join invoices invoices1_ on client0_.id=invoices1_.client_id 
  * 
  * where client0_.id=?
  *
  * 
  */

}
