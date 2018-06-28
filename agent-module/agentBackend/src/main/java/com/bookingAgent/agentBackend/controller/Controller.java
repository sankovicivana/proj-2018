package com.bookingAgent.agentBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookingAgent.agentBackend.conf.AgentClient;
import com.bookingAgent.agentBackend.ws.Accommodation;
import com.bookingAgent.agentBackend.ws.GetAccommodationRequest;
import com.bookingAgent.agentBackend.ws.GetAccommodationResponse;

@RestController
public class Controller {

	@Autowired
	AgentClient ac;
	@RequestMapping(value="/test", method=RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Accommodation> test(){
		
		GetAccommodationRequest request = new GetAccommodationRequest();
		request.setId(3);
		System.out.println(request);
		GetAccommodationResponse response = ac.getResponse(request);
		
		System.out.println(response.getAccommodation());
		Accommodation acc = new Accommodation();
		acc.setId(response.getAccommodation().getId());
		acc.setName(response.getAccommodation().getName());
	return new ResponseEntity<Accommodation>(acc, HttpStatus.OK);
	}
}
