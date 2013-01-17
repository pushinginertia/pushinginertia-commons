package com.pushinginertia.commons.net.email;

import java.util.Locale;

/**
 * Represents an email recipient and an optional locale that the recipient belongs to.
 */
public class Recipient extends NameEmail {
	private Locale locale;

	public Recipient(final String name, final String email) {
		super(name, email);
		this.locale = null;
	}

	/**
	 * Identifies the language code for the recipient's locale, if a locale has been set.
	 * @return language code or null if no locale is defined
	 * @see java.util.Locale#getLanguage()
	 */
	public String getLanguage() {
		if (locale == null) {
			return null;
		}
		return locale.getLanguage();
	}

	/**
	 * Returns the locale for the recipient, or the given default locale if no locale has been set for this recipient.
	 * @param defaultLocale
	 * @return
	 */
	public Locale getLocale(final Locale defaultLocale) {
		if (locale == null) {
			return defaultLocale;
		}

		return locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public Recipient setLocale(Locale locale) {
		this.locale = locale;
		return this;
	}
}
