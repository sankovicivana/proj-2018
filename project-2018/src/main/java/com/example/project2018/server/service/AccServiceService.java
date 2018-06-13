package com.example.project2018.server.service;

import java.util.List;

import com.example.project2018.server.model.AccService;

public interface AccServiceService {

	List<AccService> findAll();
	AccService findOne(Long id);
	AccService delete(Long id);
	AccService save(AccService service);
	
}
