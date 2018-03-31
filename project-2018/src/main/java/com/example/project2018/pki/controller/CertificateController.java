package com.example.project2018.pki.controller;

import java.security.Certificate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.pki.model.SSCertificate;



@RestController
@RequestMapping(value = "/certificate")
public class CertificateController {

	
	@RequestMapping(value="/add", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<SSCertificate> addCert(@RequestBody SSCertificate cert){
		System.out.println("test");
		
		//pozvati servis koji ce kreirati novi sertifikat
	return new ResponseEntity<>(cert, HttpStatus.OK);
	}
	
}