package com.example.project2018.pki.service;

import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.operator.OperatorCreationException;

import com.example.project2018.pki.model.CertificateData;
import com.example.project2018.pki.model.SSCertificate;


public interface SSCertificateService {

	
	public SSCertificate createSSCertificate(SSCertificate cert);

	public SSCertificate createIMCertificate(SSCertificate cert);
	
	boolean isValid(String serialNumber);
	
	Certificate revoke(String serialNumber,String issuerAlias, String issuerPassword) throws CRLException, IOException, OperatorCreationException;
	
	public Certificate getCertificate(String alias);
	public List<Certificate> getCertificates(); 
	public boolean deleteAllFromKeyStore();
	
	public boolean isRevoked(Certificate cert);
	
	public void checkValidationOCSP(String serialnumber);
	public boolean isCa(Certificate cert);
	
	public CertificateData convertForDTO(X509Certificate cert);
}


