package com.example.project2018.server.controller;

import java.security.cert.Certificate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="/api")
public class Controller {


	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public ResponseEntity<String> test(){
		
		
	return new ResponseEntity<>("Hello ", HttpStatus.OK);
	}
}
