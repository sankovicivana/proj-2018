package com.example.project2018.server.endpoints;

import javax.wsdl.extensions.soap12.SOAP12Binding;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.SoapVersion;

import com.example.project2018.server.soap.Accommodation;
import com.example.project2018.server.soap.GetAccommodationRequest;
import com.example.project2018.server.soap.GetAccommodationResponse;
import com.sun.xml.internal.ws.api.model.soap.SOAPBinding;

@Endpoint
public class TestEndpoint {

	private static final String NAMESPACE_URI = "http://server.project2018.example.com/soap";
	
	@PayloadRoot(localPart = "getAccommodationRequest", namespace = NAMESPACE_URI)
	@ResponsePayload
	public GetAccommodationResponse getAccomodation(@RequestPayload GetAccommodationRequest request) {
		GetAccommodationResponse response = new GetAccommodationResponse();
		Accommodation acc = new Accommodation();
		acc.setId(1);
		acc.setName("Smestaj 1");
		response.setAccommodation(acc);
		return response;
	}
	
}
