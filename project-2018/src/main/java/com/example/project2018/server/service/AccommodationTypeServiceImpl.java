package com.example.project2018.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project2018.server.model.Accommodation;
import com.example.project2018.server.model.AccommodationType;
import com.example.project2018.server.repository.AccommodationTypeRepository;

@Transactional
@Service
public class AccommodationTypeServiceImpl implements AccommodationTypeService{

	@Autowired
	private AccommodationTypeRepository repo;
	
	@Override
	public List<AccommodationType> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public AccommodationType findOne(Long id) {
		// TODO Auto-generated method stub
		return repo.getOne(id);
	}

	@Override
	public AccommodationType delete(Long id) {

		AccommodationType acc = repo.getOne(id);
		if(acc == null) {
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant accommodation");
		}
		
		repo.deleteById(id);
		return acc;
	}

	@Override
	public AccommodationType save(AccommodationType at) {
		// TODO Auto-generated method stub
		return repo.save(at);
	}

}
