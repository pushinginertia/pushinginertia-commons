/* Copyright (c) 2011-2017 Pushing Inertia
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
package com.pushinginertia.commons.net.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A base implementation of an http client that allows configuration of the connection properties and handles
 * HTTP POST request/response processing.
 * @param <C> the type of the connection object that will be instantiated by {@link #configureConnection(int)}
 */
public abstract class AbstractHttpPostClient<C extends HttpURLConnection> {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractHttpPostClient.class);

	private String hostName;
	private int port;
	private String path;
	private int connectionTimeoutMillis = 15000;
	private String userAgent = "JavaHttpPostClient";

	protected abstract String getUrl();

	public void setHostName(final String hostName) {
		LOG.debug("Setting host to: " + hostName);
		this.hostName = hostName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setPort(final int port) {
		LOG.debug("Setting port to: " + port);
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(final String path) {
		LOG.debug("Setting path to: " + path);
		this.path = path;
	}

	public int getConnectionTimeout() {
		return connectionTimeoutMillis;
	}

	/**
	 * Sets the connection timeout to a custom value. See {@link #connectionTimeoutMillis} for the default setting.
	 * @param timeoutMillis new value in milliseconds to set the connection timeout to
	 */
	public void setConnectionTimeout(final int timeoutMillis) {
		LOG.debug("Setting connection timeout to: " + timeoutMillis + "ms");
		this.connectionTimeoutMillis = timeoutMillis;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(final String userAgent) {
		LOG.debug("Setting user agent to: " + userAgent);
		this.userAgent = userAgent;
	}

	/**
	 * Sends a message of name-value pairs to the remote host and returns its
	 * response. If an error occurs while communicating with the server, the
	 * request is retried.
	 *
	 * @param parameters Name-value pairs to post.
	 * @param encoding How to encode the POST parameters.
	 * @param maxAttempts Maximum number of attempts to make before failing.
	 * @return Response received from the host.
	 * @throws HttpConnectException If the connection to the remote host cannot
	 * be completed or there is a problem encoding the request. In the event of
	 * multiple failures, only the last failure is thrown but all are logged.
	 */
	@Nullable
	public String sendMessageWithRetry(
			@Nonnull final Map<String, String> parameters,
			@Nonnull final String encoding,
			final int maxAttempts) throws HttpConnectException {
		int i = 0;
		while (true) {
			i++;
			try {
				return sendMessage(parameters, encoding);
			} catch (final HttpConnectException e) {
				LOG.error(
						"Failed attempt " + i + " to send POST request to server.",
						e);
				if (i >= maxAttempts) {
					throw e;
				}
			}
		}
	}

	/**
	 * Sends a message of name-value pairs to the remote host and returns its response.
	 * @param postParameters name-value pairs to post
	 * @param encoding character encoding
	 * @return response received from the host
	 * @throws HttpConnectException if the connection to the remote host cannot be completed or there is a problem encoding the request
	 */
	public String sendMessage(final Map<String, String> postParameters, final String encoding) throws HttpConnectException {
		final StringBuilder sb = new StringBuilder();
		try {
			for (final Map.Entry<String, String> param: postParameters.entrySet()) {
				if (sb.length() > 0)
					sb.append('&');
				sb.append(URLEncoder.encode(param.getKey(), encoding));
				sb.append('=');
				sb.append(URLEncoder.encode(param.getValue(), encoding));
			}

			return sendMessage(sb.toString(), encoding);
		} catch (UnsupportedEncodingException e) {
			LOG.error("Unable to encode parameter.", e);
			throw new HttpConnectException("Unable to encode parameter.", e);
		}
	}

	/**
	 * Sends a message of name-value pairs to the remote host and returns its response.
	 * @param requestPayload encoded name-value pairs using {@link URLEncoder#encode(String, String)}
	 * @param encoding character encoding
	 * @return response received from the host
	 * @throws HttpConnectException if a connection to the remote host cannot be completed
	 * @see #sendMessage(java.util.Map, String)
	 */
	public String sendMessage(final String requestPayload, final String encoding) throws HttpConnectException {
		LOG.info("Sending payload to host: " + requestPayload);

		// 1. create a connection instance
		final C con = configureConnection(requestPayload.length());

		// 2. send request
		connectAndSend(con, requestPayload);

		// 3. check for a 200 OK response code
		verifyResponseCode(con);

		// 4. retrieve response message
		return getResponseMessage(con, encoding);
	}

	/**
	 * Creates an http connection to the remote host so that a POST request can be made. This only creates the
	 * connection instance and configures it, but does not actually open the remote connection.
	 *
	 * @param contentLength number of bytes in the payload to send
	 * @return never null
	 * @throws HttpConnectException if a problem occurs trying to instantiate the connection object
	 */
	protected C configureConnection(final int contentLength) throws HttpConnectException {
		try {
			final URL u = new URL(getUrl());
			@SuppressWarnings("unchecked") final C con = (C) u.openConnection();
			con.setDoOutput(true);						  // indicates a POST request
			con.setRequestMethod("POST");
			con.setFixedLengthStreamingMode(contentLength); // content length is known so set it for efficiency
			con.setConnectTimeout(getConnectionTimeout());  // default value is zero (never time out)
			con.setRequestProperty("Accept", "application/xml");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("User-Agent", userAgent);
			return con;
		} catch (Exception e) {
			final String msg = "Cannot open connection to [" + getUrl() + "]: " + e.getMessage();
			LOG.error(getClass().getSimpleName(), msg, e);
			throw new HttpConnectException(msg, e);
		}
	}

	/**
	 * Opens the TCP connection to the remote host and sends the given payload.
	 *
	 * @param con instantiated connection
	 * @param payload data to send
	 * @throws HttpConnectException if there is a communication problem with the remote host
	 */
	protected void connectAndSend(final URLConnection con, final String payload) throws HttpConnectException {
		try {
			final OutputStream os = new BufferedOutputStream(con.getOutputStream());
			os.write(payload.getBytes());
			os.flush();
		} catch (UnknownHostException e) {
			// thrown when the host name cannot be resolved
			final String msg = "Cannot resolve host [" + getHostName() + "]: " + e.getMessage();
			LOG.error(getClass().getSimpleName(), msg, e);
			throw new HttpConnectException(msg, e);
		} catch (SocketTimeoutException e) {
			final String msg = "Timed out waiting for connection to [" + getUrl() + "]: " + e.getMessage();
			LOG.error(getClass().getSimpleName(), msg, e);
			throw new HttpConnectException(msg, e);
		} catch (SocketException e) {
			final String msg = "Failed to connect to [" + getUrl() + "]: " + e.getMessage();
			LOG.error(getClass().getSimpleName(), msg, e);
			throw new HttpConnectException(msg, e);
		} catch (Exception e) {
			final String msg = "A communication error occurred while trying to send payload to [" + getUrl() + "]: " + e.getMessage();
			LOG.error(getClass().getSimpleName(), msg, e);
			throw new HttpConnectException(msg, e);
		}
	}

	/**
	 * Verifies that the response code is {@link java.net.HttpURLConnection#HTTP_OK}.
	 *
	 * @param con instantiated connection
	 * @throws HttpConnectException if the response code is bad or some communication problem with the remote host occurs
	 */
	protected void verifyResponseCode(final HttpURLConnection con) throws HttpConnectException {
		try {
			if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				final UnexpectedHTTPStatusCode e = new UnexpectedHTTPStatusCode(
						getHostName(),
						con.getResponseCode(),
						con.getResponseMessage());
				LOG.error(getClass().getSimpleName(), e.getMessage());
				throw e;
			}
		} catch (HttpConnectException e) {
			// don't double-wrap the exception!
			throw e;
		} catch (Exception e) {
			final String msg = "An unexpected error occurred while reading the response code from [" + getUrl() + "]: " + e.getMessage();
			LOG.error(getClass().getSimpleName(), msg, e);
			throw new HttpConnectException(msg, e);
		}
	}

	/**
	 * Retrieves the response message from the remote host.
	 *
	 * @param con instantiated connection
	 * @param encoding encoding to use in the response
	 * @return response message from the remote host or null if none exists
	 * @throws HttpConnectException if there is a problem retrieving the message
	 */
	protected String getResponseMessage(final HttpURLConnection con, final String encoding) throws HttpConnectException {
		try {
			final InputStream is = con.getInputStream();
			final Scanner s = new Scanner(is, encoding);
			s.useDelimiter("\\A"); // \A is the beginning of input
			if (s.hasNext())
				return s.next();
			return null;
		} catch (NoSuchElementException e) {
			// no input
			return null;
		} catch (Exception e) {
			final String msg = "An unexpected error occurred while trying to retrieve the response message from [" + getUrl() + "]: " + e.getMessage();
			LOG.error(getClass().getSimpleName(), msg, e);
			throw new HttpConnectException(msg, e);
		}
	}
}
