package com.example.project2018.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project2018.server.model.AccommodationCategory;
import com.example.project2018.server.repository.AccommodationCategoryRepository;

@Transactional
@Service
public class AccommodationCategoryServiceImpl implements AccommodationCategoryService{

	@Autowired
	private AccommodationCategoryRepository repo;
	
	@Override
	public List<AccommodationCategory> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public AccommodationCategory findOne(Long id) {
		// TODO Auto-generated method stub
		return repo.getOne(id);
	}

	@Override
	public AccommodationCategory delete(Long id) {

		AccommodationCategory acc = repo.getOne(id);
		if(acc == null) {
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant accommodation");
		}
		
		repo.deleteById(id);
		return acc;
	}

	@Override
	public AccommodationCategory save(AccommodationCategory ac) {
		// TODO Auto-generated method stub
		return repo.save(ac);
	}

	
	
}
