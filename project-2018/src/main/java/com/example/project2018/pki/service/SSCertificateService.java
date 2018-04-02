package com.example.project2018.pki.service;

import java.security.cert.X509Certificate;
import java.util.List;

import com.example.project2018.pki.model.SSCertificate;


public interface SSCertificateService {

	
	public SSCertificate createSSCertificate(SSCertificate cert);

	public SSCertificate createIMCertificate(SSCertificate cert);

}
