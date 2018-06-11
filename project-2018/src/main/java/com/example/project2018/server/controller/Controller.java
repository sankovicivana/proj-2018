package com.example.project2018.server.controller;

import java.security.cert.Certificate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project2018.server.dto.UserDTO;
import com.example.project2018.server.model.users.User;



@RestController
@RequestMapping(value="/api")
public class Controller {


	@PreAuthorize("hasAuthority('CREATE_CERTIFICATE')")
	@RequestMapping(value="/createCert", method=RequestMethod.GET)
	public ResponseEntity<String> createCert(){
		
		
	return new ResponseEntity<>("Create certificate ", HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('READ_CERTIFICATE')")
	@RequestMapping(value="/readCert", method=RequestMethod.GET)
	public ResponseEntity<String> readCert(){
		
		
	return new ResponseEntity<>("Read certificate ", HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public ResponseEntity<String> admin(){
		
		
	return new ResponseEntity<>("Admin ", HttpStatus.OK);
	}
	@PreAuthorize("hasRole('AGENT')")
	@RequestMapping(value="/agent", method=RequestMethod.GET)
	public ResponseEntity<String> agent(){
		
		
	return new ResponseEntity<>("Agent ", HttpStatus.OK);
	}
	
	
	
}
