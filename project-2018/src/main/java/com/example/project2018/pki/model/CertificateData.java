package com.example.project2018.pki.model;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "certificateData")
public class CertificateData  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String serialNumber;
	private String subject;
	private String issuer;
	private String validFrom;
	private String validUntil;
	private String publicKey;
	private String signatureAlgorithm;
	private String purpose;
	private String aia;
	private String cdp;
	private CertificateType type;
	private boolean status;
	
	
	public CertificateData() {
		super();
		
	}
	
	public CertificateData(String serialNumber,String subject,String issuer,String validForm,
			String publicKey,String signatureAlgorithm,String purpose,String aia,String cdp,CertificateType type,
	boolean status) {
		super();
	
		this.serialNumber = serialNumber;
		this.subject = subject;
		this.issuer = issuer;
		this.validFrom = validForm;
		this.publicKey = publicKey;
		this.signatureAlgorithm = signatureAlgorithm;
		this.purpose = purpose;
		this.aia = aia;
		this.cdp = cdp;
		this.type = type;
		this.status = status;
		
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(String validUntil) {
		this.validUntil = validUntil;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}
	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	public CertificateType getType() {
		return type;
	}
	public void setType(CertificateType type) {
		this.type = type;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	




	

}
