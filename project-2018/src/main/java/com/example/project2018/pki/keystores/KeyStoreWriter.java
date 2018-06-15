package com.example.project2018.pki.keystores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyStoreWriter {

	
	private KeyStore keyStore;
	@Value("${keystore.path}")
	private String ksFile;
	@Value("${keystore.pass}")
	private String ksPass;
	//String ksFile = "./files/keystores/keystore.jks";
	//private String ksPass = "pass";

	public KeyStoreWriter() {
		
		try {
		  keyStore = KeyStore.getInstance("JKS","SUN");
		  
		  
		}
		catch(KeyStoreException e) {
			e.printStackTrace();
		}
		catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}	
	@PostConstruct
	private void init()
	{
		
		if(new File(ksFile).exists()){
			System.out.println(ksFile + "****T E S T****");
			//baca exception,pa sam zakomentarisao.
			loadKeyStore(ksFile, ksPass.toCharArray());
			
		} else
			loadKeyStore(null, ksPass.toCharArray());
	}
	public void loadKeyStore(String fileName, char[] password) {
		try {
			if(fileName != null) {
				//baca exception,pa sam zakomentarisao.
				keyStore.load(new FileInputStream(fileName), password);
			} else {
				//Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
				keyStore.load(null, null);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void saveKeyStore(String fileName,char[] password) {
		
		try {
			keyStore.store(new FileOutputStream(fileName),password);
		} 
		catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void write(String alias,PrivateKey privateKey,char[] password,Certificate certificate) {
		
		try {
			keyStore.setKeyEntry(alias,privateKey, password,new Certificate[] {certificate});	
			saveKeyStore(ksFile, ksPass.toCharArray());
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}
	
	public boolean deleteCertificate(String alias) {
		
		try {
			keyStore.deleteEntry(alias);
			saveKeyStore(ksFile, ksPass.toCharArray());
			return true;
		} catch (KeyStoreException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	}