package com.linkedin.learning.webservices.rest.config;

import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("webapi")
public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {
		packages("com.linkedin.learning.webservices.rest");
	}

}
