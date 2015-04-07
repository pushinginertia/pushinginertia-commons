package com.pushinginertia.commons.net;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.Cookie;
import java.util.concurrent.TimeUnit;

public final class CookieFactory {
	private CookieFactory() {}

	/**
	 * Creates a new cookie.
	 * @param name Name of the cookie.
	 * @param value Value of the cookie.
	 * @param maxAgeDays Maximum age of the cookie in days.
	 * @param domain Optional domain within which this cookie should be presented.
	 * @return New cookie instance.
	 * @throws IllegalArgumentException	If the cookie name contains illegal characters
	 *					(for example, a comma, space, or semicolon)
	 *					or it is one of the tokens reserved for use
	 *					by the cookie protocol.
	 */
	public static Cookie newCookie(
			@Nonnull final String name,
			@Nonnull final String value,
			final int maxAgeDays,
			@Nullable final String domain) throws IllegalArgumentException {
		final Cookie cookie = new Cookie(name, value);
		if (domain != null && !domain.isEmpty()) {
			if (domain.charAt(0) == '.') {
				cookie.setDomain(domain);
			} else {
				cookie.setDomain('.' + domain);
			}
		}
		cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(maxAgeDays));
		return cookie;
	}
}
