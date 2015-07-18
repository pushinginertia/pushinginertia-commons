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

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.lang.StringUtils;
import com.pushinginertia.commons.net.IpAddress;
import com.pushinginertia.commons.net.IpAddressUtils;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.UUID;

/**
 * Utility methods for extracting information out of a {@link javax.servlet.http.HttpServletRequest}.
 */
public final class HttpServletRequestUtils {
	private static final Logger LOG = LoggerFactory.getLogger(HttpServletRequestUtils.class);

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

		final String xForwardedFor = req.getHeader(X_FORWARDED_FOR); // method is case insensitive
		if (xForwardedFor == null) {
			return req.getRemoteAddr();
		}

		final IpAddress remoteIpAddress = getRemoteIpAddressFromXForwardedFor(xForwardedFor);
		if (IpAddressUtils.isNonRoutable(remoteIpAddress)) {
			final String remoteAddr = req.getRemoteAddr();
			LOG.warn(
					X_FORWARDED_FOR + " reports a non-routable IP [" + xForwardedFor +
					"]. This should always report the remote IP address. Servlet reports remote addr [" +
					remoteAddr + "]. Full request:\n" +
					toString(req));
			if (!IpAddressUtils.isNonRoutable(new IpAddress(remoteAddr))) {
				// return the ip address reported by the servlet if it's routable
				return remoteAddr;
			}
		}
		return remoteIpAddress.getIpAddress();
	}

	private static IpAddress getRemoteIpAddressFromXForwardedFor(final String xForwardedFor) {
		if (xForwardedFor.contains(",")) {
			// sometimes the header is of form: client ip,proxy 1 ip,proxy 2 ip,...,proxy n ip
			// we just want the client
			final String first = xForwardedFor.split(",")[0].trim();
			return new IpAddress(first);
		}

		return new IpAddress(xForwardedFor);
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

	/**
	 * Converts all of the headers in a request to a newline-separated string of name-value pairs. This is required
	 * because not all implementations of {@link javax.servlet.http.HttpServletRequest} implement the toString method.
	 * @param req request received from the user agent
	 * @return null if req is null
	 */
	@SuppressWarnings("unchecked")
	public static String toString(final HttpServletRequest req) {
		ValidateAs.notNull(req, "req");

		final StringBuilder sb = new StringBuilder();
		for (Enumeration<String> headerNames = req.getHeaderNames(); headerNames.hasMoreElements();) {
			final String headerName = headerNames.nextElement();
			for (Enumeration<String> headers = req.getHeaders(headerName); headers.hasMoreElements();) {
				final String value = headers.nextElement();
				sb.append(headerName).append('=').append(value).append('\n');
			}
		}
		return sb.toString();
	}

	/**
	 * Searches for the first cookie in the given servlet request with a name matching the name to search.
	 * @param request Request containing cookies.
	 * @param name Cookie name to search.
	 * @return Matching cookie.
	 */
	public static Optional<Cookie> findCookie(final HttpServletRequest request, final String name) {
		final Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return Optional.empty();
		}

		return Arrays.stream(cookies)
				.filter(cookie -> name.equals(cookie.getName()))
				.findFirst();
	}

	/**
	 * Searches for the first cookie in the given servlet request with a name matching the name to search.
	 * @param request Request containing cookies.
	 * @param name Cookie name to search.
	 * @return Matching cookie value or null.
	 */
	public static String findCookieAsString(final HttpServletRequest request, final String name) {
		final Optional<Cookie> cookie = findCookie(request, name);
		if (cookie.isPresent()) {
			return cookie.get().getValue();
		}
		return null;
	}

	/**
	 * Searches for the first cookie in the given servlet request with a name matching the name to search and parses
	 * it into a UUID.
	 * @param request Request containing cookies.
	 * @param name Cookie name to search.
	 * @return Matching cookie value or null.
	 * @throws IllegalArgumentException If the cookie value cannot be parsed into a UUID.
	 */
	public static UUID findCookieAsUUID(
			final HttpServletRequest request,
			final String name) throws IllegalArgumentException {
		final Optional<Cookie> cookie = findCookie(request, name);
		if (cookie.isPresent()) {
			final String value = cookie.get().getValue();
			return StringUtils.parseUUID(value);
		}
		return null;
	}
}
