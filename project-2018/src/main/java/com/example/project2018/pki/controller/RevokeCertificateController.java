package com.example.project2018.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.pki.model.CertificateData;
import com.example.project2018.pki.model.ResponseText;
import com.example.project2018.pki.repository.CertificateDataRepository;

@RestController
@RequestMapping("/revokecontroller")
public class RevokeCertificateController {

	@Autowired
	private CertificateDataRepository repository;
	
	@RequestMapping(value = "/revokeCertificate", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public ResponseEntity revokeCertificate(@RequestBody CertificateData cd) {
		String serialNumber = "";
		System.out.println("---------------------");
		System.out.println(cd.getSerialNumber());
		try{
			serialNumber = cd.getSerialNumber().trim();
			if(serialNumber == null || serialNumber.equals(""))
				return null;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println(serialNumber);
			CertificateData cda = repository.findBySerialNumber(serialNumber);
			//cda.setStatus(true);
			repository.save(cda);
	

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity(new ResponseText("Sertifikat je uspesno povucen"),HttpStatus.OK);
	}
	
	
}
