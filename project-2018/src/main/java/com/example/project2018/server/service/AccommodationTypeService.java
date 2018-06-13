package com.example.project2018.server.service;

import java.util.List;

import com.example.project2018.server.model.AccommodationType;

public interface AccommodationTypeService {

	List<AccommodationType> findAll();
	AccommodationType findOne(Long id);
	AccommodationType delete(Long id);
	AccommodationType save(AccommodationType at);
	
}
