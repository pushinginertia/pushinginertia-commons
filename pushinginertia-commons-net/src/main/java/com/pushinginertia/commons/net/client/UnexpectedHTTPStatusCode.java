package com.pushinginertia.commons.net.client;

/**
 * Thrown when the server responds with an unexpected HTTP status code.
 */
public class UnexpectedHTTPStatusCode extends HttpConnectException {
	private final String host;
	private final int statusCode;
	private final String statusMessage;

	UnexpectedHTTPStatusCode(String host, int statusCode, String statusMessage) {
		super("Bad HTTP status code [" + statusCode + ": " + statusMessage + "] reported by host: " + host);
		this.host = host;
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	public String getHost() {
		return host;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}
}
