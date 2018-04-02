package com.example.project2018.pki.service;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.KeyStoreBuilderParameters;
import javax.swing.KeyStroke;

import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.project2018.pki.data.IssuerData;
import com.example.project2018.pki.data.SubjectData;
import com.example.project2018.pki.keystores.KeyStoreReader;
import com.example.project2018.pki.keystores.KeyStoreWriter;
import com.example.project2018.pki.model.SSCertificate;

import com.example.project2018.pki.util.CertificateGenerator;
import com.example.project2018.pki.util.DataUtil;




@Service
public class SSCertificateServiceImpl implements SSCertificateService {
	
	
	// Putanja do fajla sa kljucevima, definisana je properties fajlu
	//String ksFile = "./files/keystores/primer.jks";

	@Value("${controller.path}")
	private String cPath;
	@Value("${keystore.pass}")
	private String ksPass;
	@Value("${keystore.path}")
	private String ksFile;
	@Autowired
	private KeyStoreWriter keyStoreWriter;
	@Autowired
	private KeyStoreReader keyStoreReader;
	
	@Override
	public SSCertificate createSSCertificate(SSCertificate cert) {
		// TODO Auto-generated method stub
		
			
			Security.addProvider(new BouncyCastleProvider());
			DataUtil du = new DataUtil();
			
			//Jer je Root Certificate Authority
			cert.setCA(true);
			
			//prvo generisemo podatke
			SubjectData subjectData = du.generateSubjectData(cert);
			
			//generisemo kljuceve 			
			KeyPair keyPairSubject = du.generateKeyPair();
	
			//generisemo podatke issuera koji potpisuje sa svojim privatnim kljucem
			//IssuerData issuerData = du.generateIssuerData(keyPairSubject.getPrivate(), cert);
			IssuerData issuerData = new IssuerData(subjectData.getX500name(), keyPairSubject.getPrivate());
			
			CertificateGenerator cg = new CertificateGenerator();
			X509Certificate certificate = cg.generateCertificate(subjectData, issuerData);
			
			cert.setAIA(cPath + subjectData.getSerialNumber());
			cert.setCDP("Putanja do CRL liste issuera");
			
			cert.setSerialNumber(subjectData.getSerialNumber());
			
			//certificate treba da se sacuva u jks kao i privatni kljuc
			//alias ce biti serijski broj, da li je to dobro resenje?
			String alias = subjectData.getSerialNumber();
			
			//password za privatni kljuc koji je zadao korisnik
			System.out.println(subjectData.getPassword().toCharArray());
			
			keyStoreWriter.write(alias, keyPairSubject.getPrivate(), subjectData.getPassword().toCharArray(), certificate);
			
			//Citanje sertifikata, prosledjujemo putanju fajla, password keystora i alias sertifikata			
			Certificate certt = keyStoreReader.readCertificate(ksFile, ksPass, alias);
			
			
			//Za citanje kljuca prosledjujemo putanju dofajla, pass keystora, alias serifikata, i pass kljuca koju je korisnik prosledio
			PrivateKey pk = keyStoreReader.readPrivateKey(ksFile, ksPass, alias, subjectData.getPassword());
			
			//Citanje issuera
			IssuerData issuerDat = keyStoreReader.readIssuerFromStore(ksFile, alias, ksPass.toCharArray(),subjectData.getPassword().toCharArray() );
			
			
			System.out.println("\n===== Podaci o izdavacu sertifikata =====");
			System.out.println(certificate.getIssuerX500Principal().getName());
			System.out.println("\n===== Podaci o vlasniku sertifikata =====");
			System.out.println(certificate.getSubjectX500Principal().getName());
			System.out.println("\n===== Sertifikat =====");
			System.out.println("-------------------------------------------------------");
			System.out.println(certificate);
			System.out.println("-------------------------------------------------------");
			
			
			
		
	return cert;

}

	@Override
	public SSCertificate createIMCertificate(SSCertificate cert) {
		
		Security.addProvider(new BouncyCastleProvider());
		DataUtil du = new DataUtil();
		
		//Jer je Intermediate Certificate Authority
		cert.setCA(true);
		
		
		
		String issuerPassword = cert.getIssuerPassword();
		String issuerAlias = cert.getIssuerSerialNumber();
		
		IssuerData issuerData = keyStoreReader.readIssuerFromStore(ksFile, issuerAlias, ksPass.toCharArray(), issuerPassword.toCharArray());
		SubjectData subjectData = du.generateSubjectData(cert);
		
		//za test
		cert.setSerialNumber(subjectData.getSerialNumber());
		
		CertificateGenerator cg = new CertificateGenerator();
		X509Certificate certificate = cg.generateCertificate(subjectData, issuerData);
		
		keyStoreWriter.write(subjectData.getSerialNumber(), subjectData.getKeyPair().getPrivate(), subjectData.getPassword().toCharArray(), certificate);
		//za testiranje
		
		cert.setIssuerSerialNumber(issuerAlias);
		
		System.out.println("\n===== Podaci o izdavacu sertifikata =====");
		System.out.println(certificate.getIssuerX500Principal().getName());
		System.out.println("\n===== Podaci o vlasniku sertifikata =====");
		System.out.println(certificate.getSubjectX500Principal().getName());
		System.out.println("\n===== Sertifikat =====");
		System.out.println("-------------------------------------------------------");
		System.out.println(certificate);
		System.out.println("-------------------------------------------------------");
		
	return cert;
	}
	
	//treba proveriti da li je sertifikat validan, i treba napraviti servis za iscitavanje svih CA==true issuera
	

}
