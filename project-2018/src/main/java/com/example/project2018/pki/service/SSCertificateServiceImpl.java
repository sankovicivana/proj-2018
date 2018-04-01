package com.example.project2018.pki.service;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project2018.pki.data.IssuerData;
import com.example.project2018.pki.data.SubjectData;
import com.example.project2018.pki.model.SSCertificate;
import com.example.project2018.pki.repository.CertificateRepository;
import com.example.project2018.pki.util.CertificateGenerator;
import com.example.project2018.pki.util.DataUtil;



@Service
public class SSCertificateServiceImpl implements SSCertificateService {
	
	@Autowired
	CertificateRepository certRepo;
	
	@Override
	public SSCertificate createSSCertificate(SSCertificate cert) {
		// TODO Auto-generated method stub
		try {
			
			Security.addProvider(new BouncyCastleProvider());
			DataUtil du = new DataUtil();
			
			//prvo generisemo podatke
			SubjectData subjectData = du.generateSubjectData(cert);
			
			//generisemo kljuceve za issuera
			
			KeyPair keyPairIssuer = du.generateKeyPair();
	
			//generisemo podatke issuera
			IssuerData issuerData = du.generateIssuerData(keyPairIssuer.getPrivate(), cert);
			
			CertificateGenerator cg = new CertificateGenerator();
			X509Certificate certificate = cg.generateCertificate(subjectData, issuerData);
			
			//certificate treba da se sacuva u jks kao i privatni kljuc
			
			
			System.out.println("\n===== Podaci o izdavacu sertifikata =====");
			System.out.println(certificate.getIssuerX500Principal().getName());
			System.out.println("\n===== Podaci o vlasniku sertifikata =====");
			System.out.println(certificate.getSubjectX500Principal().getName());
			System.out.println("\n===== Sertifikat =====");
			System.out.println("-------------------------------------------------------");
			System.out.println(certificate);
			System.out.println("-------------------------------------------------------");
			
			//Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca
			certificate.verify(keyPairIssuer.getPublic());
			System.out.println("\nValidacija uspesna :)");
			
			
			cert.setAIA("Verovatno neka putanja..");
			cert.setCDP("Mozda je i ovo neka putanja..");
			cert.setCA(true);
			cert.setSerialNumber(subjectData.getSerialNumber());
			
			KeyPair anotherPair = du.generateKeyPair();
			certificate.verify(anotherPair.getPublic());
		} catch(CertificateException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("\nValidacija neuspesna :(");
			e.printStackTrace();
		}
	return cert;

}

	@Override
	public SSCertificate createIMCertificate(SSCertificate cert) {
		// TODO Auto-generated method stub
		try {
			//cert sadrzi podatke o sertifikatu koji se kreira i koji potpisuje
			SSCertificate issuerCert = cert.getIssuer();
			
			Security.addProvider(new BouncyCastleProvider());
			
			DataUtil du = new DataUtil();
			
			//prvo generisemo podatke
			SubjectData subjectData = du.generateSubjectData(cert);
			
			//generisemo kljuceve za issuera
			
			KeyPair keyPairIssuer = du.generateKeyPair();
	
			//generisemo podatke issuera
			IssuerData issuerData = du.generateIssuerData(keyPairIssuer.getPrivate(), issuerCert);
			
			CertificateGenerator cg = new CertificateGenerator();
			X509Certificate certificate = cg.generateCertificate(subjectData, issuerData);
			
			System.out.println("\n===== Podaci o izdavacu sertifikata =====");
			System.out.println(certificate.getIssuerX500Principal().getName());
			System.out.println("\n===== Podaci o vlasniku sertifikata =====");
			System.out.println(certificate.getSubjectX500Principal().getName());
			System.out.println("\n===== Sertifikat =====");
			System.out.println("-------------------------------------------------------");
			System.out.println(certificate);
			System.out.println("-------------------------------------------------------");
			
			//Moguce je proveriti da li je digitalan potpis sertifikata ispravan, upotrebom javnog kljuca izdavaoca
			certificate.verify(keyPairIssuer.getPublic());
			System.out.println("\nValidacija uspesna :)");
			
			KeyPair anotherPair = du.generateKeyPair();
			certificate.verify(anotherPair.getPublic());
		} catch(CertificateException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("\nValidacija neuspesna :(");
			e.printStackTrace();
		}
	return cert;
	}

	@Override
	public List<SSCertificate> searchCa(boolean bool) {
		// TODO Auto-generated method stub
		List<SSCertificate> retVal = certRepo.findByIsCA(bool);
		return retVal;
	}

	@Override
	public List<SSCertificate> findAll() {
		// TODO Auto-generated method stub
		return certRepo.findAll();
	}

	@Override
	public SSCertificate findOne(Long id) {
		// TODO Auto-generated method stub
		return certRepo.getOne(id);
	}

	@Override
	public SSCertificate save(SSCertificate cert) {
		// TODO Auto-generated method stub	
		return certRepo.save(cert);
	}

	@Override
	public SSCertificate delete(Long id) {
		// TODO Auto-generated method stub
		SSCertificate forDel = certRepo.getOne(id);
		
		if (forDel == null) {
			throw new IllegalArgumentException("Prokusali ste da izbrisete nepostojece naseljeno mesto.");
		}
		System.out.println("Brisem " + forDel.getGivenName());
		certRepo.deleteById(id);;
		
		return forDel;
	}

	@Override
	public SSCertificate findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
