package com.example.project2018.pki.service;

import java.security.cert.X509Certificate;


import com.example.project2018.pki.model.SSCertificate;


public interface SSCertificateService {

	
	public X509Certificate generateCertificate(SSCertificate cert);
	
	
	
}
