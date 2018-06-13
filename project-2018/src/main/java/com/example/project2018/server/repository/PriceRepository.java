package com.example.project2018.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project2018.server.model.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long>{

}
