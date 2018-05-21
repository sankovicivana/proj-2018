package com.example.project2018.pki.model.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Korisnici")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "Ime")
	private String name;
	
	@Column(name = "Prezime")
	private String lastname;
	
	@Column(name = "Lozinka")
	private String password;
	
	@Column(name = "Grad")
	private String city; 	
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Telefon")
	private String phone;
	
	@Column(name = "Uloga")
	private Role role;
	
	public User() {
		
	}
	
	public User(String name,String lastname,String password,String city,String email,String phone) {
		
		this.name = name;
		this.lastname = lastname;
		this.password = password;
		this.city = city;
		this.email = email;
		this.phone = phone;
		
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
}
