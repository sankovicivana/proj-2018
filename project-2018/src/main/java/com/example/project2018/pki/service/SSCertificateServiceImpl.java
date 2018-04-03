package com.example.project2018.pki.service;

import java.io.IOException;
import java.security.KeyPair;

import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CRLConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.project2018.pki.data.IssuerData;
import com.example.project2018.pki.data.SubjectData;
import com.example.project2018.pki.keystores.KeyStoreReader;
import com.example.project2018.pki.keystores.KeyStoreWriter;
import com.example.project2018.pki.model.SSCertificate;
import com.example.project2018.pki.util.CRLUtils;
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
	
	//@Value("${crl.file}")
	private String crlFile;
	
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

	@Override
	public boolean isValid(String serialNumber) {
		
		X509Certificate cert = (X509Certificate) keyStoreReader.readCertificate(ksFile, ksPass, serialNumber);
		
		X509CRL crl = CRLUtils.openFromFile(crlFile);
		
		try {
			cert.checkValidity();
		if(crl.isRevoked(cert))
		
			return false;
		else
					return true;
				}
				catch (CertificateExpiredException e) {
					return false;
				}
				catch (CertificateNotYetValidException e) {
					return false;
				}
		
		
	}

	@Override
	public Certificate revoke(String serialNumber, String issuerAlias, String issuerPassword)
			throws CRLException, IOException, OperatorCreationException {
		
		X509Certificate cert = (X509Certificate) keyStoreReader.readCertificate(ksFile, ksPass, serialNumber);
		
		X509CRL crl = CRLUtils.openFromFile(crlFile);
				
				IssuerData issuer = keyStoreReader.readIssuerFromStore(ksFile, 
						issuerAlias, 
						ksPass.toCharArray(), 
						issuerPassword.toCharArray());
				
				PrivateKey pk = issuer.getPrivateKey();
				
				X500Name CA = issuer.getX500Name();
				
				Date today = Calendar.getInstance().getTime();
				X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(CA, today);
				
				JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
				builder.setProvider("BC");
				ContentSigner contentSigner = builder.build(pk);
				
				crlBuilder.addCRL(new X509CRLHolder(crl.getEncoded()));
				//crlBuilder.addCRLEntry(cert.getSerialNumber(), today, CRLReason.UNSPECIFIED);
				X509CRLHolder holder = crlBuilder.build(contentSigner);
				JcaX509CRLConverter cnv = new JcaX509CRLConverter();
				cnv.setProvider("BC");
				X509CRL newCRL = cnv.getCRL(holder);					
				
				CRLUtils.saveCRLfile(crlFile, newCRL);
				return cert ;
	}

	@Override
	public Certificate getCertificate(String alias) {
		List<String> aliases = keyStoreReader.getAliases();
		System.out.println(aliases);
		if (aliases.contains(alias)) {
			
			Certificate cert = keyStoreReader.readCertificate(ksFile, ksPass, alias);
			return cert;
		} else {
			System.out.println("Trazeni sertifikat nije pronadjen.");
			return null;

		}
	}

	@Override
	public List<Certificate> getCertificates() {
		List<Certificate> certificates = new ArrayList<>();
		
		List<String> aliases = keyStoreReader.getAliases();
		for (String alias : aliases) {
			System.out.println("Velicina " + aliases.size());
			System.out.println("Alias " + alias);

			Certificate cert = keyStoreReader.readCertificate(ksFile, ksPass, alias);
			System.out.println("Cert " + cert);
			certificates.add(cert);
			
			
		}
		return certificates;
	}

	@Override
	public boolean deleteAllFromKeyStore() {
		List<String> aliases = keyStoreReader.getAliases();
		for (String alias : aliases) {
			
			if (keyStoreWriter.deleteCertificate(alias) == false)
				return false;	
		}
		return true;
	}

	//Provera da li je sertifikat povucen
	@Override
	public boolean isRevoked(Certificate cert) {
		
		X509CRL crl = CRLUtils.openFromFile(crlFile);
			
			if(crl.isRevoked(cert)) {
				
				return true;
				
			}
		
		return false;
	}

	@Override
	public boolean isCa(Certificate cert) {
		X509Certificate c = (X509Certificate) cert;
		
		if (c.getBasicConstraints() == -1) {
			return false;
		} else {
			return true;
		}

	}
	
	//treba proveriti da li je sertifikat validan, i treba napraviti servis za iscitavanje svih CA==true issuera
	
	
	
}
