package com.example.project2018.server.service;

import java.util.List;

import com.example.project2018.server.model.Price;

public interface PriceService {

	List<Price> findAll();
	Price findOne(Long id);
	Price delete(Long id);
	Price save(Price price);
	
}
