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

import com.pushinginertia.commons.lang.ValidateAs;

import java.io.Serializable;

/**
 * Encapsulates the information required to construct a multipart email with all headers and both text and html content.
 */
public class EmailMessage implements Serializable {
	private static final long serialVersionUID = 2L;

	private NameEmail sender;
	private Recipient recipient;
	private NameEmail replyTo;
	private String bounceEmailAddress;
	private String subject;
	private String senderIpAddress;
	/**
	 * The two-character country code of the country that {@link #senderIpAddress} resolves to.
	 */
	private String senderIpAddressCountryId;
	private String textContent;
	private String htmlContent;

	public EmailMessage(
			final NameEmail sender, final Recipient recipient,
			final String subject,
			final String textContent) {
		this(sender, recipient, subject, textContent, EmailUtils.textToHtml(textContent));
	}

	public EmailMessage(
			final NameEmail sender, final Recipient recipient,
			final String subject,
			final String textContent, final String htmlContent) {
		this.sender = ValidateAs.notNull(sender, "sender");
		this.recipient = ValidateAs.notNull(recipient, "recipient");
		this.subject = ValidateAs.notNull(subject, "subject");
		this.textContent = ValidateAs.notNull(textContent, "textContent");
		this.htmlContent = ValidateAs.notNull(htmlContent, "htmlContent");

		this.replyTo = null;
		this.bounceEmailAddress = null;
		this.senderIpAddress = null;
		this.senderIpAddressCountryId = null;
	}

	public NameEmail getSender() {
		return sender;
	}

	public EmailMessage setSender(final NameEmail sender) {
		this.sender = sender;
		return this;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public EmailMessage setRecipient(final Recipient recipient) {
		this.recipient = recipient;
		return this;
	}

	public NameEmail getReplyTo() {
		return replyTo;
	}

	public EmailMessage setReplyTo(final NameEmail replyTo) {
		this.replyTo = replyTo;
		return this;
	}

	public String getBounceEmailAddress() {
		return bounceEmailAddress;
	}

	public EmailMessage setBounceEmailAddress(final String bounceEmailAddress) {
		if (bounceEmailAddress.indexOf('@') < 0) {
			throw new IllegalArgumentException("bounceEmailAddress does not contain an '@' sign.");
		}
		this.bounceEmailAddress = bounceEmailAddress;
		return this;
	}

	public String getSubject() {
		return subject;
	}

	public EmailMessage setSubject(final String subject) {
		this.subject = subject;
		return this;
	}

	public String getSenderIpAddress() {
		return senderIpAddress;
	}

	public EmailMessage setSenderIpAddress(final String senderIpAddress) {
		this.senderIpAddress = senderIpAddress;
		return this;
	}

	public String getSenderIpAddressCountryId() {
		return senderIpAddressCountryId;
	}

	public EmailMessage setSenderIpAddressCountryId(final String senderIpAddressCountryId) {
		this.senderIpAddressCountryId = senderIpAddressCountryId;
		return this;
	}

	public String getTextContent() {
		return textContent;
	}

	public EmailMessage setTextContent(final String textContent) {
		this.textContent = textContent;
		return this;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public EmailMessage setHtmlContent(final String htmlContent) {
		this.htmlContent = htmlContent;
		return this;
	}
}