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

import com.pushinginertia.commons.lang.ValidateAs;

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
}
