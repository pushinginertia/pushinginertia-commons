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
package com.pushinginertia.commons.net.util;

import com.pushinginertia.commons.lang.ValidateAs;
import org.apache.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

/**
 * Utility methods for extracting information out of a {@link javax.servlet.http.HttpServletRequest}.
 */
public final class HttpServletRequestUtils {
	public static final String HOST = "Host";
	public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
	public static final String X_FORWARDED_FOR = "X-Forwarded-For";

	private HttpServletRequestUtils() {}

	/**
	 * Parses the host name from the request.
	 * @param req request received from the user agent
	 * @return null if unable to parse
	 */
	public static String getRequestHostName(final HttpServletRequest req) {
		// maybe we are behind a proxy
		String header = req.getHeader(X_FORWARDED_HOST);
		if (header != null) {
			// we are only interested in the first header entry
			header = new StringTokenizer(header, ",").nextToken().trim();
		}
		if (header == null)
			header = req.getHeader(HOST);

		return header;
	}

	/**
	 * Identifies the remote IP address from a request, even if the application is running behind a forwarding agent.
	 * @param req request received from the user agent
	 * @return null if the ip address cannot be identified
	 */
	public static String getRemoteIpAddress(final HttpServletRequest req) {
		ValidateAs.notNull(req, "req");

		final String remoteAddr = req.getHeader(X_FORWARDED_FOR); // method is case insensitive
		if (remoteAddr == null)
			return req.getRemoteAddr();

		if (remoteAddr.contains(",")) {
			// sometimes the header is of form: client ip,proxy 1 ip,proxy 2 ip,...,proxy n ip
			// we just want the client
			return remoteAddr.split(",")[0].trim();
		}

		return remoteAddr;
	}

	/**
	 * Identifies the user agent from a request.
	 * @param req request received from the user agent
	 * @return null if no user agent in the request
	 */
	public static String getUserAgent(final HttpServletRequest req) {
		ValidateAs.notNull(req, "req");
		return req.getHeader(HttpHeaders.USER_AGENT);
	}
}
