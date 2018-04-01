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

public class KeyStoreReader {

	private KeyStore keyStore;
	
	
	
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
	
	//Ucitava privatni kljuc iz keyStor-a
	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {
			//kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			//ucitavamo podatke
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
	
	
}
