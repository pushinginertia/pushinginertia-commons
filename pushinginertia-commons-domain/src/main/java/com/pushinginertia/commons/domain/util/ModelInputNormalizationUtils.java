package com.pushinginertia.commons.domain.util;

import javax.annotation.Nonnull;

/**
 * Utility methods to normalize user inputs.
 */
public class ModelInputNormalizationUtils {

	/**
	 * This is used when inputs for a user's first and last name are provided, and the user makes an input error by
	 * entering both his/her first and last names in the first name input and duplicating the last name in the last name
	 * input, or vice versa. Some examples:
	 * {@code {"first middle last", "last"} => {"first middle", "last"}},
	 * {@code {"first", "first last"} => {"first", "last"}},
	 * {@code {"first last", "last"} => {"first", "last"}}
	 * @param first First name.
	 * @param last Last name.
	 * @return Array containing the first and last names with any necessary substring removals.
	 */
	@Nonnull
	public static String[] removeNameDupes(@Nonnull final String first, @Nonnull final String last) {
		final String firstLower = first.toLowerCase();
		final String lastLower = last.toLowerCase();
		final int lastLength = last.length();
		final int firstLength = first.length();

		if (lastLength < firstLength &&
			firstLower.endsWith(lastLower) &&
			firstLower.charAt(firstLength - lastLength - 1) == ' ') {
			// last name appears at end of first name
			return new String[]{first.substring(0, firstLength - lastLength - 1), last};
		}

		if (firstLength < lastLength && lastLower.startsWith(firstLower) && lastLower.charAt(firstLength) == ' ') {
			// first name appears at beginning of last name
			return new String[]{first, last.substring(firstLength + 1)};
		}

		// no changes required
		return new String[]{first, last};
	}
}
