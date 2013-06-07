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
package com.pushinginertia.commons.web.payments.paypal;

import com.pushinginertia.commons.net.client.HttpConnectException;
import com.pushinginertia.commons.net.client.HttpsPostClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Not ready yet.
 */
public class PayPalVerifyIPN {
	private static final Logger LOG = LoggerFactory.getLogger(PayPalVerifyIPN.class);

	public static final String PAYPAL_PATH = "/cgi-bin/webscr";
	public static final String ENCODING_UTF8 = "UTF-8"; // TODO: use Consts.UTF_8
	public static final String DEFAULT_USER_AGENT = "PayPal Verification";

	private static final PayPalVerifyIPN DEFAULT = new PayPalVerifyIPN();

	private final String encoding;
	private final String userAgent;

	private PayPalVerifyIPN() {
		this(ENCODING_UTF8, DEFAULT_USER_AGENT);
	}

	/**
	 * Creates a new instance with custom encoding and user agent values.
	 * @param encoding character encoding of the message that will be sent to the remote host (see {@link java.nio.charset.Charset#name()})
	 * @param userAgent value to set the user agent to in the HTTPS POST message
	 */
	public PayPalVerifyIPN(final String encoding, final String userAgent) {
		this.encoding = encoding;
		this.userAgent = userAgent;
	}

	/**
	 * Returns the default instance, which uses {@link #ENCODING_UTF8} character encoding and sends the value
	 * {@link #DEFAULT_USER_AGENT} as the user agent.
	 * @return static instance
	 */
	public static PayPalVerifyIPN getDefaultInstance() {
		return DEFAULT;
	}

	/**
	 * Sends an acknowledgement to PayPal's server by echoing the parameters that we received. PayPal responds with
	 * a simple 'VERIFIED' or 'INVALID' to indicate if the given payload originated from them. The payload must echo
	 * the parameters from request believed to have originated from PayPal with a cmd=_notifyvalidate prepended.
	 * @param host host to connect to
	 * @param parameters parameters received in the request from paypal (also in same order as given by PayPal's callback)
	 * @return true if the payload originated from paypal
	 * @throws HttpConnectException if the connection cannot be completed
	 */
	public boolean queryPaypal(final String host, final LinkedHashMap<String, String> parameters) throws HttpConnectException {
		// 1. construct the parameter list to send to the paypal server
		final Map<String, String> pCopy = new LinkedHashMap<String, String>();
		pCopy.put("cmd", "_notify-validate");
		pCopy.putAll(parameters);

		// 2. send acknowledgement to paypal server: echo the parameters in the same order given from paypal's specs:
		// Before you can trust the contents of the message, you must first verify that the message came
		// from PayPal. To verify the message, you must send back the contents in the exact order they
		// were received and precede it with the command _notify-validate, as follows:
		// https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_notifyvalidate&k1=v1&k2=v2&...&kn=vn
		final long startNs = System.nanoTime();
		final HttpsPostClient client = new HttpsPostClient();
		client.setHostName(host);
		client.setPath(PAYPAL_PATH);
		client.setUserAgent(userAgent);
		final String response = client.sendMessage(pCopy, encoding);
		final long deltaMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

		// 2. paypal server responds with single message VERIFIED or INVALID
		final boolean verificationSuccessful = "VERIFIED".equals(response);
		if (verificationSuccessful) {
			LOG.info("Verified payload against PayPal in " + deltaMs + " ms: {response=" + response + '}');
		} else {
			LOG.error("PayPal reported invalid payload in " + deltaMs + " ms: {response=" + response + '}');
		}
		return verificationSuccessful;
	}
}
