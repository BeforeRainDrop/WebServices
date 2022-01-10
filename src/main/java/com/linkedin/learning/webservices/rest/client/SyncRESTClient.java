package com.linkedin.learning.webservices.rest.client;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.linkedin.learning.webservices.model.SalutationRequest;




public class SyncRESTClient {

	public static void main(String[] args) {
//		syncClientCall();
		asyncRESTClient();
	}

	private static void syncClientCall() {

		Client client = ClientBuilder.newClient();
		SalutationRequest request = new SalutationRequest();
		request.setSalutation("Cara");
		Entity<SalutationRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		
		String targetResource = "http://localhost:8080/java-ee-webservices/webapi/myresource/guest/{guest}/salute";
		Response response = client.target(targetResource)
				.resolveTemplate("guest", "Giov")
				.request(MediaType.APPLICATION_JSON)
				.post(entity);
		
		Logger.getAnonymousLogger().info("Response code: " + response.getStatus());
		Logger.getAnonymousLogger().info("Response from the server: " + response.readEntity(String.class));
	}
	
	private static void asyncRESTClient() {
		Client client = ClientBuilder.newClient();
		SalutationRequest request = new SalutationRequest();
		request.setSalutation("Ground");
		Entity<SalutationRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		
		String targetResource = "http://localhost:8080/java-ee-webservices/webapi/myresource/guest/{guest}/salute";
		Future<Response> futureResponse = client.target(targetResource)
				.resolveTemplate("guest", "control")
				.queryParam("makeItWait", true)
				.request(MediaType.APPLICATION_JSON)
				.async() //Questo lo fa diventare Asincrono di default è sincrono
				.post(entity);
		
		Response response = null;
		Logger logger = Logger.getLogger("Async REST client");
		
		try {
			logger.info("Ground control to Major Tom");
			Thread.sleep(1000);
			logger.info("Ground control to Major Tom");
			Thread.sleep(1000);
			logger.info("Ground control to Major Tom");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
		try {
			while (!futureResponse.isDone()) {}
			response = futureResponse.get(4, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		
		Logger.getAnonymousLogger().info("Response code: " + response.getStatus());
		Logger.getAnonymousLogger().info("Response from the server: " + response.readEntity(String.class));
	}

}
