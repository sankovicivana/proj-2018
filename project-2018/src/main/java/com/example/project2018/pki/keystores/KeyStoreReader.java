package com.example.project2018.pki.keystores;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.project2018.pki.data.IssuerData;



@Component
public class KeyStoreReader {

	private KeyStore keyStore;
	@Value("${keystore.path}")
	private String ksFile;
	@Value("${keystore.pass}")
	private String ksPass;
	
	
	public KeyStoreReader() {
		
		try {                                            //SUN-JDK provider which contains two types of cryptographic services
			                                             //(MessageDigests and Signatures)
		  keyStore = KeyStore.getInstance("JKS", "SUN"); //JKS-Java KeyStore
		 
		}
		 catch(KeyStoreException e) {
			e.printStackTrace();
		}
		 catch(NoSuchProviderException e) {
			e.printStackTrace();
		}
	}
	public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
		try {
			//Datoteka se ucitava
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			keyStore.load(in, password);
			//Iscitava se sertifikat koji ima dati alias
			Certificate cert = keyStore.getCertificate(alias);
			//Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
			PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
			return new IssuerData(issuerName, privKey);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}	
	//funkcija koja ucitava sertifikat iz KeyStora sa zadatim alijasom
	public Certificate readCertificate(String KeyStoreFile,String KeyPassword,String alias) {
		
		try {
			//kreira se instanca keyStor-a
			keyStore = KeyStore.getInstance("JKS", "SUN");
			//ucitavaju se podaci iz keyStoreFile-a
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(KeyStoreFile));
			keyStore.load(bis,KeyPassword.toCharArray());			
			
			//ako je pronadjen alijas u keyStore, vrati sertifikat sa tim alijasom.
			if(keyStore.isKeyEntry(alias)) {
				Certificate cert = keyStore.getCertificate(alias);
				return cert;
			}
			
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//Ucitavanje privatnog kljuca iz keyStor-a
	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());
			
			if(ks.isKeyEntry(alias)) {
				PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
				return pk;
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getAliases(){
		
		try {
			
				Enumeration<String> aliases = keyStore.aliases();
				List<String> retVal = Collections.list(aliases);
				return retVal;
			} catch (KeyStoreException e) {
				System.out.println("Greska pri citanju iz jks-a.");
				e.printStackTrace();
			}
		
		return null;
	}
	public boolean deleteCertificate(String alias) {
		
		try {
			keyStore.deleteEntry(alias);
			return true;
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
