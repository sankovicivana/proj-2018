package com.example.project2018.pki.service.impl;
/*
package com.example.project2018.pki.service.impl;


import java.security.cert.X509Certificate;
import java.math.BigInteger;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;

import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import com.example.project2018.pki.model.SSCertificate;
import com.example.project2018.pki.service.SSCertificateService;






public class SSCertificateImpl implements SSCertificateService {

	public SubjectData subjectData;
	public IssuerData  issuerData;
	
	@Override
	public X509Certificate generateCertificate(SSCertificate sscert) {
		
		try{
			
			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			//Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
			builder = builder.setProvider("BC");

			//Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
			ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
			
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500Name(),
					new BigInteger(subjectData.getSerialNumber()),
					subjectData.getStartDate(),
					subjectData.getEndDate(),
					subjectData.getX500name(),
					subjectData.getPublicKey());	
		
			//Generise se sertifikat
			X509CertificateHolder certHolder = certGen.build((ContentSigner) sscert);

			//Builder generise sertifikat kao objekat klase X509CertificateHolder
			//Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			//Konvertuje objekat u sertifikat
			return certConverter.getCertificate(certHolder);
			
		} catch (CertificateEncodingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (OperatorCreationException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public X509Certificate generateCertificate(SSCertificate cert) {
		// TODO Auto-generated method stub
		return null;
	}
		
		
		
	}
	*/

	