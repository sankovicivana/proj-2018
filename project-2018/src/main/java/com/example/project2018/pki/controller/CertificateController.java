package com.example.project2018.pki.controller;


import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.example.project2018.pki.model.CertificateData;
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
	    "cdp": null,
	    "password":123
	}
	{
    "id": null,
    "serialNumber": "612788",
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
    "password": "123",
    "issuerName": null,
    "issuerEndDate": null,
    "issuerPassword": null,
    "issuerSerialNumber": null,
    "ca": true,
    "aia": "http://localhost:8080/certificate/612788",
    "cdp": "Putanja do CRL liste issuera"
}
*/
	//@PreAuthorize("hasAuthority('CREATE_CERTIFICATE')")
	@RequestMapping(value="/addSSC", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<SSCertificate> addSSCert(@RequestBody SSCertificate cert){
		System.out.println("test");
		System.out.println(cert.getStartDate());
		
		SSCertificate generatedCert = service.createSSCertificate(cert);
		//service.save(generatedCert);
		return new ResponseEntity<>(generatedCert, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('CREATE_CERTIFICATE')")
	@RequestMapping(value="/addIMC", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<?> addIMCert(@RequestBody SSCertificate cert){
		System.out.println("test");               
		
		SSCertificate generatedCert  = service.createIMCertificate(cert);
		if (generatedCert == null) {
			return new ResponseEntity<>("Sertifikat nije kreiran.", HttpStatus.FORBIDDEN);
		}
	return new ResponseEntity<>(generatedCert, HttpStatus.OK);
	}
	
	
	@GetMapping("/valid/{id}")
	public ResponseEntity<?> isValid(@PathVariable String id){
			service.isValid(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	 
	@PreAuthorize("hasRole('REGULAR')")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)	
	public ResponseEntity<?> getCert(@PathVariable String id){
		System.out.println("test getCert ");               
		
		 
		Certificate cert = service.getCertificate(id);
		System.out.println("CERTIFICATE \n"+cert);
		CertificateData cd = service.convertForDTO((X509Certificate) cert);
		X509Certificate c = (X509Certificate) cert;
		//Certificate
		
	return new ResponseEntity<>(cd, HttpStatus.OK);
	}
	@PreAuthorize("hasAuthority('READ_CERTIFICATE')")
	@RequestMapping(value="/getAll", method=RequestMethod.GET)
	public ResponseEntity<String> getCerts(){
		
		System.out.println("test getCerts ");            
		List<Certificate> certs = service.getCertificates();
		System.out.println(certs);
		
	return new ResponseEntity<>(certs.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/deleteAll", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteCerts(){
		
		System.out.println("test deleteCerts ");            
		service.deleteAllFromKeyStore();
		
		
	return new ResponseEntity<>("Brisanje uspesno.", HttpStatus.OK);
	}
	
	@GetMapping("getValidity/{id}")
	public ResponseEntity<String> getValidityBySerial(@PathVariable String id){
		
		service.checkValidationOCSP(id);
		
		
		
		
		return new ResponseEntity<>("Validacija je usepsna", HttpStatus.OK);
	}
	@RequestMapping(value="/revoke", method=RequestMethod.POST, consumes="application/json")
	
	public ResponseEntity<String> revokeCert(@RequestBody SSCertificate cert) throws CRLException, OperatorCreationException, IOException{
		System.out.println("test revoke");
		System.out.println(cert.getSerialNumber());
		System.out.println(cert.getIssuerSerialNumber());
		System.out.println(cert.getIssuerPassword());
		
		Certificate c = service.revoke(cert.getSerialNumber(), cert.getIssuerSerialNumber(), cert.getIssuerPassword());
		
	return new ResponseEntity<>(c.toString(), HttpStatus.OK);
	}

	
}