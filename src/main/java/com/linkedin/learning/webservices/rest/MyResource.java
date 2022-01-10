package com.linkedin.learning.webservices.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.linkedin.learning.webservices.model.SalutationRequest;
import com.linkedin.learning.webservices.model.SalutationResponse;

//@Singleton
@Path("myresource")
public class MyResource {

	@PathParam("guest")
	String guestName;

	@DefaultValue("Anonymous")
	@QueryParam("guestFromQuery")
	String queryParamGuest;

	private final List<String> firstNames = new ArrayList<>();

	private final Map<String, String> firstNameLastName = new HashMap<>();

	@PostConstruct
	public void initialize() {
		firstNames.add("Tayo");
		firstNames.add("Vaibhav");
		firstNames.add("Dillon");
		firstNames.add("Erika");
		firstNames.add("Manmayee");
		firstNames.add("Adan");
		firstNames.add("Michael");
		firstNameLastName.put("Tayo", "Koleoso");
		firstNameLastName.put("Vaibhav", "Goodman");
		firstNameLastName.put("Dillon", "Yousob");
		firstNameLastName.put("Erika", "Hills");
		firstNameLastName.put("Manmayee", "Brightman");
		firstNameLastName.put("Adan", "Mortez");
		firstNameLastName.put("Michael", "Menace");

	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@Context UriInfo urlInformation) {
		return "Got it! " + queryParamGuest;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/firstnames")
	public List<String> getFirstNames() {
		return firstNames;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/guest/{firstname}/lastname")
	public String getLastNames(@PathParam("firstname") String firstName) {
		return firstNameLastName.get(firstName);
	}

	@Path("/guest/{guest:[a-zA-Z][a-zA-Z_0-9]*}/goodbye")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response goodbye(SalutationRequest request, @PathParam("guest") String guest,
			@QueryParam("makeItWait") boolean makeItWait) {
		SalutationResponse response = new SalutationResponse();
		if (makeItWait) {
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		response.setSalutationResponse("Goodbye, " + request.getSalutation() + " " + guest);
		Response responseWrapper = Response.ok(response).build();
		return responseWrapper;
	}

	@Path("/guest/{guest:[a-zA-Z][a-zA-Z_0-9]*}/salute")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salute(SalutationRequest request, @PathParam("guest") String guest,
			@QueryParam("makeItWait") boolean makeItWait) {
		SalutationResponse response = new SalutationResponse();
		if (makeItWait) {
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		response.setSalutationResponse("Hello, " + request.getSalutation() + " " + guest);
		Response responseWrapper = Response.ok(response).build();
		return responseWrapper;
	}

	@Path("/guest/salute")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response saluteWithForm(@FormParam("salutation") String salutation, @FormParam("guest") String guest) {
		SalutationResponse response = new SalutationResponse();

		response.setSalutationResponse("Hello, " + salutation + " " + guest);
		Response responseWrapper = Response.ok(response).build();
		return responseWrapper;
	}

}
