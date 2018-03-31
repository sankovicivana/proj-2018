package com.example.project2018.pki.model;

import java.util.Date;

import com.example.project2018.pki.data.IssuerData;
import com.example.project2018.pki.data.SubjectData;



public class SSCertificate {

	
	private Long id;
	private String commonName;
	private String	surname;
	private String giveNamme;
	private String organization;
	private String organizationUnit;
	private String country;
	private String email;
	private Date startDate;
	private Date endDate;
	private String purpose;
	private String AIA;
	private String CDP;
	private SSCertificate issuer;
	private boolean isCA;
	

	public SSCertificate() {
		super();
	}

	
	
	public SSCertificate(Long id, String commonName, String surname, String giveNamme, String organization,
			String organizationUnit, String country, String email, Date startDate, Date endDate, String purpose,
			String aIA, String cDP, SSCertificate issuer, boolean isCA) {
		super();
		this.id = id;
		this.commonName = commonName;
		this.surname = surname;
		this.giveNamme = giveNamme;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.country = country;
		this.email = email;
		this.startDate = startDate;
		this.endDate = endDate;
		this.purpose = purpose;
		AIA = aIA;
		CDP = cDP;
		this.issuer = issuer;
		this.isCA = isCA;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		id = id;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGiveNamme() {
		return giveNamme;
	}

	public void setGiveNamme(String giveNamme) {
		this.giveNamme = giveNamme;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrganizationUnit() {
		return organizationUnit;
	}

	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getAIA() {
		return AIA;
	}

	public void setAIA(String aIA) {
		AIA = aIA;
	}

	public String getCDP() {
		return CDP;
	}

	public void setCDP(String cDP) {
		CDP = cDP;
	}

	public SSCertificate getIssuer() {
		return issuer;
	}

	public void setIssuer(SSCertificate issuer) {
		this.issuer = issuer;
	}


	public boolean isCA() {
		return isCA;
	}


	public void setCA(boolean isCA) {
		this.isCA = isCA;
	}
	
	

	
}
