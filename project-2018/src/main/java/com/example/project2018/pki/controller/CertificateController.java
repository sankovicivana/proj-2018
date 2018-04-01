package com.example.project2018.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.pki.model.SSCertificate;
import com.example.project2018.pki.service.SSCertificateService;



@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

	//@Autowired
	//private SSCertificateService service;
	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<SSCertificate> addCert(@RequestBody SSCertificate cert){
		System.out.println("test");
		
		//service.generateCertificate(cert);
		
	return new ResponseEntity<>(cert, HttpStatus.OK);
	}
	
}