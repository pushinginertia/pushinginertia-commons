/* Copyright (c) 2011-2013 Pushing Inertia
 * All rights reserved.  http://pushinginertia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
			throw new IllegalArgumentException("email [" + email + "] does not contain an '@' sign - check that the name [" + name + "] and email did not get mixed up.");
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
