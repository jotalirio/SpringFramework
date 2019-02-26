package com.example.springboot.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.springboot.app.models.entity.Client;

public interface IClientDaoPagingAndSortingRepository extends PagingAndSortingRepository<Client, Long> {

}
