package com.example.project2018.pki.data;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;

public class SubjectData {
	
	private PublicKey publicKey;
	private X500Name x500name;
	private String serialNumber;
	private Date startDate;
	private Date endDate;
	private boolean isCA;
	private String aia;
	private String cdp;
	private String purpose;
	private String password;
	private KeyPair keyPair;
	public SubjectData(){}

	
	public SubjectData(KeyPair keyPair, X500Name x500name, String serialNumber, Date startDate, Date endDate,
			boolean isCA, String aia, String cdp, String purpose, String password) {
		super();
		this.keyPair=keyPair;
		this.x500name = x500name;
		this.serialNumber = serialNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCA = isCA;
		this.aia = aia;
		this.cdp = cdp;
		this.purpose = purpose;
		this.password = password;
	}





	public boolean isCA() {
		return isCA;
	}


	public void setCA(boolean isCA) {
		this.isCA = isCA;
	}


	public String getAia() {
		return aia;
	}


	public void setAia(String aia) {
		this.aia = aia;
	}


	public String getCdp() {
		return cdp;
	}


	public void setCdp(String cdp) {
		this.cdp = cdp;
	}


	public String getPurpose() {
		return purpose;
	}


	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public X500Name getX500name() {
		return x500name;
	}

	public void setX500name(X500Name x500name) {
		this.x500name = x500name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public KeyPair getKeyPair() {
		return keyPair;
	}


	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}
	
}
