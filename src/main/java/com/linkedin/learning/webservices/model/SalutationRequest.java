package com.linkedin.learning.webservices.model;

import java.io.Serializable;
import java.util.Objects;

public class SalutationRequest implements Serializable {

	private static final long serialVersionUID = -7592966308119269612L;

	private String salutation;

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(salutation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		SalutationRequest other = (SalutationRequest) obj;
		return Objects.equals(salutation, other.salutation);
	}

	@Override
	public String toString() {
		return "SalutationRequest [salutation=" + salutation + "]";
	}

}
