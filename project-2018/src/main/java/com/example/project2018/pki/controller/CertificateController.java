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

	@Autowired
	private SSCertificateService service;
	
/*	Postman POST zahtev
	{
	    "id": null,
	    "serialNumber": null,
	    "commonName": "cn",
	    "surname": "sn",
	    "givenName": "gn",
	    "organization": "o",
	    "organizationUnit": "ou",
	    "country": "rs",
	    "email": "pera@pera",
	    "startDate": "04/01/2018",
	    "endDate": "04/07/2018",
	    "purpose": "EMAIL",
	    "issuer": null,
	    "ca": false,
	    "aia": null,
	    "cdp": null
	}
*/
	@RequestMapping(value="/addSSC", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<SSCertificate> addSSCert(@RequestBody SSCertificate cert){
		System.out.println("test");
		System.out.println(cert.getStartDate());
		
		SSCertificate generatedCert = service.createSSCertificate(cert);
		service.save(generatedCert);
		return new ResponseEntity<>(generatedCert, HttpStatus.OK);
	}
	
@RequestMapping(value="/addIMC", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<SSCertificate> addIMCert(@RequestBody SSCertificate cert){
		System.out.println("test");
		
		service.createIMCertificate(cert);
		
	return new ResponseEntity<>(cert, HttpStatus.OK);
	}
	
}