package com.bookingAgent.agentBackend.conf;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.bookingAgent.agentBackend.ws.GetAccommodationRequest;
import com.bookingAgent.agentBackend.ws.GetAccommodationResponse;
import com.sun.xml.internal.messaging.saaj.soap.ver1_2.Message1_2Impl;

public class AgentClient extends WebServiceGatewaySupport {

    public GetAccommodationResponse getResponse(GetAccommodationRequest request){
    	return (GetAccommodationResponse) getWebServiceTemplate().marshalSendAndReceive(request);

    }

}
