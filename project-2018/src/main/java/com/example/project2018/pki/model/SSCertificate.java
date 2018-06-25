package com.example.project2018.pki.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class SSCertificate {

	
	private Long id;
	private String serialNumber;
	
	@NotEmpty
	private String commonName;
	@NotEmpty
	private String	surname;
	@NotEmpty
	private String givenName;
	@NotEmpty
	private String organization;
	@NotEmpty
	private String organizationUnit;
	@NotEmpty
	private String country;
	
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	private String startDate;
	@NotEmpty
	private String endDate;
	private String purpose;
	private String AIA;
	private String CDP;
	@NotEmpty
	private String password;
	private String issuerName;
	private String issuerEndDate;
	private String issuerPassword;
	private String issuerSerialNumber;
	private boolean isCA;
	

	
	public String getIssuerName() {
		return issuerName;
	}

	

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}



	public String getIssuerEndDate() {
		return issuerEndDate;
	}



	public void setIssuerEndDate(String issuerEndDate) {
		this.issuerEndDate = issuerEndDate;
	}



	public String getIssuerPassword() {
		return issuerPassword;
	}



	public void setIssuerPassword(String issuerPassword) {
		this.issuerPassword = issuerPassword;
	}

	public SSCertificate() {
		super();
	}

	
	
	public SSCertificate(Long id, String commonName, String surname, String givenName, String organization,
			String organizationUnit, String country, String email, String startDate, String endDate, String purpose,
			String aIA, String cDP, boolean isCA) {
		super();
		this.id = id;
		this.commonName = commonName;
		this.surname = surname;
		this.givenName = givenName;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.country = country;
		this.email = email;
		this.startDate = startDate;
		this.endDate = endDate;
		this.purpose = purpose;
		AIA = aIA;
		CDP = cDP;
		
		this.isCA = isCA;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
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

	


	public boolean isCA() {
		return isCA;
	}


	public void setCA(boolean isCA) {
		this.isCA = isCA;
	}



	public String getSerialNumber() {
		return serialNumber;
	}



	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getIssuerSerialNumber() {
		return issuerSerialNumber;
	}



	public void setIssuerSerialNumber(String issuerSerialNumber) {
		this.issuerSerialNumber = issuerSerialNumber;
	}
	
	

	
}
