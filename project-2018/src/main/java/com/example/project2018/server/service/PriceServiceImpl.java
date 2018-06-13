package com.example.project2018.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project2018.server.model.Accommodation;
import com.example.project2018.server.model.Price;
import com.example.project2018.server.repository.PriceRepository;

@Transactional
@Service
public class PriceServiceImpl implements PriceService{

	@Autowired
	private PriceRepository repo;
	
	@Override
	public List<Price> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Price findOne(Long id) {
		// TODO Auto-generated method stub
		return repo.getOne(id);
	}

	@Override
	public Price delete(Long id) {
		Price price = repo.getOne(id);
		if(price == null) {
			throw new IllegalArgumentException("Tried to delete"
					+ "non-existant accommodation");
		}
		
		repo.deleteById(id);
		return price;
	}

	@Override
	public Price save(Price price) {
		// TODO Auto-generated method stub
		return repo.save(price);
	}

}
