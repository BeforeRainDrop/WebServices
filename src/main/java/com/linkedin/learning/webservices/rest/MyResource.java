package com.linkedin.learning.webservices.rest;

import com.linkedin.learning.webservices.model.SalutationRequest;
import com.linkedin.learning.webservices.model.SalutationResponse;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    @Path("/guest/{guest}/salute")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salute(SalutationRequest request, @PathParam("guest") String guest) {
    	SalutationResponse response = new SalutationResponse();
    	
    	response.setSalutationResponse("Ciao, " + request.getSalutation() + " " + guest);
    	Response responseWrapper = Response.ok(response).build();
    	return responseWrapper;
    	
    	
    }
}
