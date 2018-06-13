package com.example.project2018.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project2018.server.model.Accommodation;
import com.example.project2018.server.repository.AccommodationRepository;

@Transactional
@Service
public class AccommodationServiceImpl implements AccommodationService{
	
	@Autowired
	private AccommodationRepository repo;

	@Override
	public List<Accommodation> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Accommodation findOne(Long id) {
		// TODO Auto-generated method stub
		return repo.getOne(id);
	}

	@Override
	public Accommodation save(Accommodation accommodation) {
		// TODO Auto-generated method stub
		return repo.save(accommodation);
	}

	@Override
	public Accommodation delete(Long id) {

		Accommodation acc = repo.getOne(id);
		if(acc == null) {
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant accommodation");
		}
		
		repo.deleteById(id);
		return acc;
	}

	@Override
	public List<Accommodation> search(String addressPlace, int numberOfPeople) {
		// TODO Auto-generated method stub
		return null;
	}

}
