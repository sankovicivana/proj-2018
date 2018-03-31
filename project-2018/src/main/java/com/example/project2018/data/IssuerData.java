package com.example.project2018.data;

import java.security.PrivateKey;
import org.bouncycastle.asn1.x500.X500Name;

public class IssuerData {
	
	private X500Name x500Name;
	private PrivateKey privateKey;
	
	
	public IssuerData(){}
	
	public IssuerData(X500Name x500Name, PrivateKey privateKey) {
		super();
		this.x500Name = x500Name;
		this.privateKey = privateKey;
	}
	public X500Name getX500Name() {
		return x500Name;
	}
	public void setX500Name(X500Name x500Name) {
		this.x500Name = x500Name;
	}
	public PrivateKey getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}
	
	
}
