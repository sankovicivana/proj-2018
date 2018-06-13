package com.example.project2018.server.service;

import java.util.List;

import com.example.project2018.server.model.Accommodation;

public interface AccommodationService {

	List<Accommodation> findAll();
	Accommodation findOne(Long id);
	Accommodation save(Accommodation accommodation);
	Accommodation delete(Long id);
	List<Accommodation> search(String addressPlace, int numberOfPeople);
	
	
}
