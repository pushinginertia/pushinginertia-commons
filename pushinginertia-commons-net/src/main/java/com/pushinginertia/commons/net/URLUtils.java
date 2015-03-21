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
package com.pushinginertia.commons.net;

import com.pushinginertia.commons.core.validation.ValidateAs;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL business logic.
 */
public class URLUtils {
	/**
	 * Extracts the top-level domain from the host in a given URL (i.e., the output of {@link java.net.URL#getHost()}).
	 * See {@link #extractTopLevelDomainFromHost(String)} for a discussion of what logic is performed on the host.
	 * @param url URL to parse
	 * @return domain extracted from the host
	 * @throws MalformedURLException if the input URL cannot be parsed into a {@link URL} instance
	 */
	public static String extractTopLevelDomainFromUrl(final String url) throws MalformedURLException {
		ValidateAs.notNull(url, "url");
		final String host = new URL(url).getHost();
		return extractTopLevelDomainFromHost(host);
	}

	/**
	 * Extracts the top-level domain from a given host string. This is performed by returning the part of the string
	 * following the second-last dot in the input. If the input contains less than two dots, the original string is
	 * returned.
	 * @param host host to parse
	 * @return domain extracted from the host
	 */
	public static String extractTopLevelDomainFromHost(final String host) {
		ValidateAs.notNull(host, "host");
		boolean passedFirstDot = false;
		for (int i = host.length() - 1; i >= 0; i--) {
			if (host.charAt(i) == '.') {
				if (passedFirstDot) {
					return host.substring(i + 1);
				}
				passedFirstDot = true;
			}
		}
		return host;
	}

	/**
	 * Identifies the length of the scheme/protocol in a given URI.
	 * The scheme name consists of a sequence of characters beginning with a letter and followed by any combination
	 * of letters, digits, plus ("+"), period ("."), or hyphen ("-").
	 * @param uri URI to evaluate.
	 * @return -1 if no scheme is present or otherwise the length of the scheme.
	 * @see <a href="http://en.wikipedia.org/wiki/URI_scheme">http://en.wikipedia.org/wiki/URI_scheme</a>
	 */
	public static int uriSchemeLength(@Nonnull final String uri) {
		if (uri.length() == 0) {
			return -1;
		}

		// first character must be a letter
		final char firstChar = uri.charAt(0);
		if (firstChar < 'a' || firstChar > 'z') {
			return -1;
		}

		// evaluate remaining string
		for (int i = 1; i < uri.length(); i++) {
			final char c = uri.charAt(i);
			if (c == ':') {
				// end of the scheme
				return i;
			}
			if ((c < 'a' || c > 'z') && (c < '0' || c > '9') && c != '+' && c != '.' && c != '-') {
				// invalid character found before the colon
				return -1;
			}
		}
		return -1;
	}
}
