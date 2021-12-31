package com.linkedin.learning.webservices.model;

import java.io.Serializable;
import java.util.Objects;

public class SalutationResponse implements Serializable {

	private static final long serialVersionUID = 6118507437859101698L;

	private String salutationResponse;

	public String getSalutationResponse() {
		return salutationResponse;
	}

	public void setSalutationResponse(String salutationResponse) {
		this.salutationResponse = salutationResponse;
	}

	@Override
	public int hashCode() {
		return Objects.hash(salutationResponse);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		SalutationResponse other = (SalutationResponse) obj;
		return Objects.equals(salutationResponse, other.salutationResponse);
	}

	@Override
	public String toString() {
		return "SalutationResponse [salutationResponse=" + salutationResponse + "]";
	}

}
