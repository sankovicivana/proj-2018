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
public class CheckStatusController {
	
	/*SSCertificateRepository i u njenu findbyCertificateSN*/
	/*@Autowired
	private SSCertificateRepository certificateRepository;*/
	@Autowired
	private SSCertificateService service;
	
	
	
	@RequestMapping(value="/checkStatus", method = RequestMethod.POST)
	public ResponseEntity<String> checkStatus(@RequestBody SSCertificate ssc){
		String serialNumber="";
		System.out.println(ssc.getSerialNumber());
		
		try{
		// trim() method removes whitespace from both ends of a string. 
		serialNumber=ssc.getSerialNumber().trim();
		if(serialNumber == null || serialNumber.equals("")){
			return null;
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		/*try {
			System.out.println(serialNumber);
			//treba mi get serialNumber
			SSCertificate cda = service.getCertificate()
			if(cda.isRevoked()) {
				System.out.println("Povucen");
				return new ResponseEntity<String>("Povucen", HttpStatus.OK);
				
			}else {
				return new ResponseEntity<String>("Nije povucen", HttpStatus.OK);
			}

		} catch (Exception e) {
			e.printStackTrace();
			
		}*/
		
		
		
		return new ResponseEntity<String>("OK", HttpStatus.OK);
	}
}
