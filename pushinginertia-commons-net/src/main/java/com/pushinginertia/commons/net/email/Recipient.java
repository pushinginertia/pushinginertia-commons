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
