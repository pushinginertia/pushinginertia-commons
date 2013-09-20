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

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Encapsulates the optional headers that might be added to an outbound email.
 */
public class EmailMessageHeaders implements Serializable {
	private static final long serialVersionUID = 1L;

	private final Map<String, String> headers;

	private EmailMessageHeaders(final Map<String, String> headers) {
		this.headers = Collections.unmodifiableMap(headers);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public static class Builder implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * IP address of the sender of the email.
		 */
		public static final String X_ORIGINATING_IP = "X-Originating-IP";
		/**
		 * The two-character country code of the country that {@link #X_ORIGINATING_IP} resolves to.
		 */
		public static final String X_ORIGINATING_COUNTRY = "X-Originating-Country";
		/**
		 * Points to an email address or a URL where the user can unsubscribe easily from future mailings.
		 */
		public static final String LIST_UNSUBSCRIBE = "List-Unsubscribe";

		private final Map<String, String> headers = new TreeMap<String, String>();

		public Builder() {
		}

		/**
		 * Adds a new header.
		 * @param name name of the header (can be one of the constants in this class or any custom header)
		 * @param value value for the header
		 * @throws IllegalArgumentException if the header name to add has already been added
		 */
		public void add(final String name, final String value) throws IllegalArgumentException {
			ValidateAs.mapDoesNotContainKey(headers,  name);
			ValidateAs.notNull(value, "value");
			headers.put(name, value);
		}

		/**
		 * @see #add(String, String)
		 */
		public void add(final String name, final long value) throws IllegalArgumentException {
			add(name, Long.toString(value));
		}

		public EmailMessageHeaders build() {
			return new EmailMessageHeaders(headers);
		}

		@Override
		public String toString() {
			return "EmailMessageHeaders.Builder" + headers;
		}
	}

	@Override
	public String toString() {
		return "EmailMessageHeaders" + headers;
	}
}
