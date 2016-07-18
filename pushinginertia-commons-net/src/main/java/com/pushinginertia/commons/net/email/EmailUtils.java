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
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import javax.annotation.Nonnull;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Map;

/**
 * Common email logic.
 */
public final class EmailUtils {
	private static final String UTF_8 = "utf-8";

	/**
	 * Converts a text message into an HTML message, replacing all newlines with &lt;br&gt; tags and escaping the '&lt;'
	 * and '&gt;' characters;
	 * @param s message in text format
	 * @return message in html format
	 */
	public static String textToHtml(final String s) {
		final StringBuilder sb = new StringBuilder();
		for (final char c: s.toCharArray()) {
			switch (c) {
				case '\n':
					sb.append("<br/>");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Populates a newly instantiated {@link MultiPartEmail} with the given arguments.
	 * @param email email instance to populate
	 * @param smtpHost SMTP host that the message will be sent to
	 * @param smtpPort SMTP port to connect to on the given host.
	 * @param msg container object for the email's headers and contents
	 * @throws IllegalArgumentException if the inputs are not valid
	 * @throws EmailException if a problem occurs constructing the email
	 */
	public static void populateMultiPartEmail(
			@Nonnull final MultiPartEmail email,
			@Nonnull final String smtpHost,
			final int smtpPort,
			@Nonnull final EmailMessage msg)
	throws IllegalArgumentException, EmailException {
		ValidateAs.notNull(email, "email");
		ValidateAs.notNull(smtpHost, "smtpHost");
		ValidateAs.notNull(msg, "msg");

		email.setHostName(smtpHost);
		email.setSmtpPort(smtpPort);
		email.setCharset(UTF_8);
		email.setFrom(msg.getSender().getEmail(), msg.getSender().getName(), UTF_8);
		email.addTo(msg.getRecipient().getEmail(), msg.getRecipient().getName(), UTF_8);

		final NameEmail replyTo = msg.getReplyTo();
		if (replyTo != null) {
			email.addReplyTo(replyTo.getEmail(), replyTo.getName(), UTF_8);
		}

		final String bounceEmailAddress = msg.getBounceEmailAddress();
		if (bounceEmailAddress != null) {
			email.setBounceAddress(bounceEmailAddress);
		}

		email.setSubject(msg.getSubject());

		final String languageId = msg.getRecipient().getLanguage();
		if (languageId != null) {
			email.addHeader("Language", languageId);
			email.addHeader("Content-Language", languageId);
		}

		// add optional headers
		final EmailMessageHeaders headers = msg.getHeaders();
		if (headers != null) {
			for (final Map.Entry<String, String> header: headers.getHeaders().entrySet()) {
				email.addHeader(header.getKey(), header.getValue());
			}
		}

		// generate email body
		try {
			final MimeMultipart mm = new MimeMultipart("alternative; charset=UTF-8");

			final MimeBodyPart text = new MimeBodyPart();
			text.setContent(msg.getTextContent(), "text/plain; charset=UTF-8");
			mm.addBodyPart(text);

			final MimeBodyPart html = new MimeBodyPart();
			html.setContent(msg.getHtmlContent(), "text/html; charset=UTF-8");
			mm.addBodyPart(html);

			email.setContent(mm);
		} catch (MessagingException e) {
			throw new EmailException(e);
		}

	}

	private EmailUtils() {}
}
