package com.linkedin.learning.webservices.soap;


import javax.jws.WebService;

import com.linkedin.learning.webservices.model.SalutationRequest;
import com.linkedin.learning.webservices.model.SalutationResponse;


@WebService
public class MySOAPEndpoint {

	public String getIt() {
		return "Got it!";
	}

	public SalutationResponse salute(SalutationRequest request, String guest) {
		SalutationResponse response = new SalutationResponse();
		response.setSalutationResponse("Hello, " + request.getSalutation() + " " + guest);
		return response;
	}

}
