package com.example.project2018.pki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.pki.model.CertificateData;
import com.example.project2018.pki.repository.CertificateDataRepository;

@RestController
@RequestMapping("/loadCertificate")
public class DownloadCertificateController {

	@Autowired
	private CertificateDataRepository repository;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/load", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public CertificateData downloadCertificate(@RequestBody CertificateData cd) {
		String serialNumber = "";
		CertificateData cdata = null;
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
			cdata = repository.findBySerialNumber(serialNumber);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cdata;
	}
	
}
