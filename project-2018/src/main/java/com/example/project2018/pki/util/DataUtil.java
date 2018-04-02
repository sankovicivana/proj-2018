package com.example.project2018.pki.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import com.example.project2018.pki.data.IssuerData;
import com.example.project2018.pki.data.SubjectData;
import com.example.project2018.pki.model.SSCertificate;





public class DataUtil {
	
	
	
	public DataUtil() {}
	
	public IssuerData generateIssuerData(PrivateKey issuerKey, SSCertificate cert) {
		
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		//Sta je ovde User ID, mozda id ulogovanog usera koji je kreirao.. 
		//ovo nije dobro jer SS sertifikat bi trebao da ima isti UID
		
		String uid = Integer.toString(n);
		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
	    builder.addRDN(BCStyle.CN, cert.getCommonName());
	    builder.addRDN(BCStyle.SURNAME, cert.getSurname());
	    builder.addRDN(BCStyle.GIVENNAME, cert.getGivenName());
	    builder.addRDN(BCStyle.O, cert.getOrganization());
	    builder.addRDN(BCStyle.OU, cert.getOrganizationUnit());
	    builder.addRDN(BCStyle.C, cert.getCountry());
	    builder.addRDN(BCStyle.E, cert.getEmail());
	    //UID (USER ID) je ID korisnika
	    builder.addRDN(BCStyle.UID, uid);

		//Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
	    // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
	    // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
		return new IssuerData(builder.build(), issuerKey );
	}

	
	public SubjectData generateSubjectData(SSCertificate cert) {
		try {
			KeyPair keyPairSubject = generateKeyPair();
			
			//Datumi od kad do kad vazi sertifikat
			//SimpleDateFormat iso8601Formater = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat iso8601Formater = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate = iso8601Formater.parse(cert.getStartDate().toString());
			Date endDate = iso8601Formater.parse(cert.getEndDate().toString());
			
			//Serijski broj sertifikata
			Random rnd = new Random();
			int n = 100000 + rnd.nextInt(900000);
			String sn = Integer.toString(n);
			n = 100000 + rnd.nextInt(900000);
			String uid = Integer.toString(n);
			//klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
			X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		    builder.addRDN(BCStyle.CN, cert.getCommonName());
		    builder.addRDN(BCStyle.SURNAME, cert.getSurname());
		    builder.addRDN(BCStyle.GIVENNAME, cert.getGivenName());
		    builder.addRDN(BCStyle.O, cert.getOrganization());
		    builder.addRDN(BCStyle.OU, cert.getOrganizationUnit());
		    builder.addRDN(BCStyle.C, cert.getCountry());
		    builder.addRDN(BCStyle.E, cert.getEmail());
		    //UID (USER ID) je ID korisnika
		    builder.addRDN(BCStyle.UID, uid);
		    
		    //Kreiraju se podaci za sertifikat, sto ukljucuje:
		    // - javni kljuc koji se vezuje za sertifikat
		    // - podatke o vlasniku
		    // - serijski broj sertifikata
		    // - od kada do kada vazi sertifikat
		    return new SubjectData(keyPairSubject, builder.build(), sn, startDate, endDate, cert.isCA(), cert.getAIA(), cert.getCDP(), cert.getPurpose(), cert.getPassword());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public KeyPair generateKeyPair() {
        try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
        return null;
	}
	
}
