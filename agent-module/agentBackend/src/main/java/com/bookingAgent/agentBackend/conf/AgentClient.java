package com.bookingAgent.agentBackend.conf;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import com.bookingAgent.agentBackend.ws.GetAccommodationRequest;
import com.bookingAgent.agentBackend.ws.GetAccommodationResponse;

public class AgentClient extends WebServiceGatewaySupport {

    public GetAccommodationResponse getResponse(GetAccommodationRequest request){
        return (GetAccommodationResponse) getWebServiceTemplate().marshalSendAndReceive(request);

    }

}
