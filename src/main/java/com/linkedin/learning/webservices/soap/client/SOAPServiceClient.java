package com.linkedin.learning.webservices.soap.client;

import com.linkedin.learning.webservices.soap.stubs.MySOAPEndpointService;
import com.linkedin.learning.webservices.soap.stubs.SalutationRequest;
import com.linkedin.learning.webservices.soap.stubs.SalutationResponse;

import java.util.logging.Logger;

import com.linkedin.learning.webservices.soap.stubs.MySOAPEndpoint;

public class SOAPServiceClient {
	
	public static void main(String[] args) {
		MySOAPEndpointService serviceClient = new MySOAPEndpointService();
		MySOAPEndpoint port = serviceClient.getMySOAPEndpointPort();
		
		SalutationRequest request = new SalutationRequest();
		request.setSalutation("Your Honor");
		
		SalutationResponse response = port.salute(request, "Tayo");
		
		Logger.getAnonymousLogger().info("The response is: "+response.getSalutationResponse());
		
	}

}
