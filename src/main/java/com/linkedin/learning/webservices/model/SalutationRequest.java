package com.linkedin.learning.webservices.model;

import java.io.Serializable;

public class SalutationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7592966308119269612L;
	
	
	private String salutation;


	public String getSalutation() {
		return salutation;
	}


	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}


	@Override
	public String toString() {
		return "SalutationRequest [salutation=" + salutation + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((salutation == null) ? 0 : salutation.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalutationRequest other = (SalutationRequest) obj;
		if (salutation == null) {
			if (other.salutation != null)
				return false;
		} else if (!salutation.equals(other.salutation))
			return false;
		return true;
	}

}
