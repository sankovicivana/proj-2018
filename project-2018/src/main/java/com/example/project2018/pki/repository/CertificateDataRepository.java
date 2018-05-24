package com.example.project2018.pki.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.project2018.pki.model.CertificateData;


public interface CertificateDataRepository  extends CrudRepository<CertificateData, Integer>{

	CertificateData findBySerialNumber(String serialNumber);
	
}
