package com.example.project2018.server.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String name;
	
	@ManyToOne
	private AccommodationType type;
	
	@ManyToOne
	private AccommodationCategory category;
	
	@OneToMany
	private List<AccService> services;
	
	@Column
	private float grade;
	
	@Column
	private String description;
	
	@Column
	private int maxPeople;
	
	@Column
	private String picture;
	
	@OneToMany
	private List<Price> prices;
	
	@Column
	private String addressPlace;
	
	@Column
	private String addressStreet;
	
	@Column
	private String addressNumber;

	public Accommodation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccommodationType getType() {
		return type;
	}

	public void setType(AccommodationType type) {
		this.type = type;
	}

	public AccommodationCategory getCategory() {
		return category;
	}

	public void setCategory(AccommodationCategory category) {
		this.category = category;
	}

	public List<AccService> getServices() {
		return services;
	}

	public void setServices(List<AccService> services) {
		this.services = services;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxPeople() {
		return maxPeople;
	}

	public void setMaxPeople(int maxPeople) {
		this.maxPeople = maxPeople;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public String getAddressPlace() {
		return addressPlace;
	}

	public void setAddressPlace(String addressPlace) {
		this.addressPlace = addressPlace;
	}

	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	
}