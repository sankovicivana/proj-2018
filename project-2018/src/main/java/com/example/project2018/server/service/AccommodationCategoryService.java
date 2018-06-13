package com.example.project2018.server.service;

import java.util.List;

import com.example.project2018.server.model.AccommodationCategory;

public interface AccommodationCategoryService {

	List<AccommodationCategory> findAll();
	AccommodationCategory findOne(Long id);
	AccommodationCategory delete(Long id);
	AccommodationCategory save(AccommodationCategory ac);
	
}
