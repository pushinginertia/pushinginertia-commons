package com.pushinginertia.commons.net.email;

import java.io.Serializable;

/**
 * Simple bean that encapsulates a name and email address.
 */
public class NameEmail implements Serializable {
	private static final long serialVersionUID = 1L;

	private final String name;
	private final String email;

	public NameEmail(final String name, final String email) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null.");
		}
		if (email == null) {
			throw new IllegalArgumentException("email cannot be null.");
		}
		if (email.indexOf('@') < 0) {
			throw new IllegalArgumentException("email does not contain an '@' sign - check that the name and email did not get mixed up.");
		}
		this.name = name;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String asHeaderValue() {
		return "\"" + name + "\" <" + email + ">";
	}

	@Override
	public String toString() {
		return "NameEmail{" + asHeaderValue() + "}";
	}
}
