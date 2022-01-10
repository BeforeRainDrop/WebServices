package com.linkedin.learning.webservices.rest.client;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.linkedin.learning.webservices.model.SalutationRequest;

public class RXRESTClient {

	public static void main(String[] args) {
		rxClientCall();
	}

	private static void rxClientCall() {
		/**
		 * 1) GET http://localhost:8080/java-ee-webservices/webapi/myresource/firstnames
		 * 
		 * 2) GET
		 * http://localhost:8080/java-ee-webservices/webapi/myresource/{firstname}/lastname
		 * 
		 * 3a)POST
		 * http://localhost:8080/java-ee-webservices/webapi/myresource/guest/{firstname_lastname}/salute?makeItWait=true
		 * 3b)PUT
		 * http://localhost:8080/java-ee-webservices/webapi/myresource/guest/{firstname_lastname}/goodbye
		 **/

		Logger logger = Logger.getLogger("RX JAX-RS Client API");

		Client client = ClientBuilder.newClient(); // 1 e 2 sequenziali
		Client saluteClient = ClientBuilder.newClient(); // 3 concorrenti
		Client goodbyeClient = ClientBuilder.newClient();

		String firstNameTarget = "http://localhost:8080/java-ee-webservices/webapi/myresource/firstnames";
		String lastNameTarget = "http://localhost:8080/java-ee-webservices/webapi/myresource/guest/{firstname}/lastname";
		String saluteTarget = "http://localhost:8080/java-ee-webservices/webapi/myresource/guest/{firstname_lastname}/salute?makeItWait=true";
		String goodbyeTarget = "http://localhost:8080/java-ee-webservices/webapi/myresource/guest/{firstname_lastname}/goodbye";

		// Request for salute and goodbye endpoint
		SalutationRequest salRequest = new SalutationRequest();
		salRequest.setSalutation("Dear");
		Entity<SalutationRequest> salutationEntity = Entity.entity(salRequest, MediaType.APPLICATION_JSON);

		// 1
		CompletionStage<List<String>> listOfNamesResponse = client.target(firstNameTarget)
				.request(MediaType.APPLICATION_JSON).rx().get(new GenericType<List<String>>() {
				}).exceptionally((ex) -> {
					logger.warning("something went wrong with list of names " + ex.getMessage());
					return null;
				});

		listOfNamesResponse.thenAcceptAsync((listOfNames) -> {
			logger.info("BEGIN - Call for lastName for " + listOfNames);
			listOfNames.forEach((firstName) -> {
				// 2
				logger.info("BEGIN - Call for lastName for " + firstName);
				CompletionStage<String> firstNameLastNameStage = client.target(lastNameTarget)
						.resolveTemplate("firstname", firstName)
						.request(MediaType.APPLICATION_JSON)
						.rx()
						.get(String.class)
						.exceptionally((ex) -> {
							logger.warning("something went wrong requesting lastname for " + firstName + ": "
									+ ex.getMessage());
							return null;
						});
				
				logger.info("END - Call for lastName for " + firstName);
				
				firstNameLastNameStage.thenAcceptAsync((lastName) -> {
					
					logger.info("Call for Salute " + firstName);
					saluteClient.target(saluteTarget)
						.resolveTemplate("firstname_lastname", firstName + "_" + lastName)
						.request(MediaType.APPLICATION_JSON)
						.rx()
						.post(salutationEntity)
						.toCompletableFuture()
						.exceptionally((exception) -> {
								logger.warning("Something went wrong saying goodbye to " + firstName + "_" + lastName
										+ ": " + exception.getMessage());
								return null;
						}).whenCompleteAsync((response, throwable) -> {
								Logger.getAnonymousLogger()
										.info("Response from the hello API: " + response.readEntity(String.class));
								Logger.getAnonymousLogger().info("Exception occurred? " + (throwable != null));
						});

					try {
						Response goodbyeResponse = goodbyeClient.target(goodbyeTarget)
								.resolveTemplate("firstname_lastname", firstName + "_" + lastName)
								.request(MediaType.APPLICATION_JSON).rx().put(salutationEntity).toCompletableFuture()
								.exceptionally((exception) -> {
									logger.warning("Something went wrong saying hello to " + firstName + "_" + lastName
											+ ": " + exception.getMessage());
									return null;
								}).get();
						logger.info("Response from the goodbye resource " + goodbyeResponse.readEntity(String.class));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
			});
		});
	}

}
