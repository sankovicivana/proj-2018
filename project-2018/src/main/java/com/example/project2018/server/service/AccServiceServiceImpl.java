package com.example.project2018.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project2018.server.model.AccService;
import com.example.project2018.server.model.Accommodation;
import com.example.project2018.server.repository.AccServiceRepository;

@Transactional
@Service
public class AccServiceServiceImpl implements AccServiceService{

	@Autowired
	private AccServiceRepository repo;
	
	@Override
	public List<AccService> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public AccService findOne(Long id) {
		// TODO Auto-generated method stub
		return repo.getOne(id);
	}

	@Override
	public AccService delete(Long id) {

		AccService acc = repo.getOne(id);
		if(acc == null) {
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant accommodation");
		}
		
		repo.deleteById(id);
		return acc;
	}

	@Override
	public AccService save(AccService service) {
		// TODO Auto-generated method stub
		return repo.save(service);
	}

}
