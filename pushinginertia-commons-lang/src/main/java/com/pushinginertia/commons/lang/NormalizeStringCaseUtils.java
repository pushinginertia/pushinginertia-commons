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
package com.pushinginertia.commons.lang;

import com.pushinginertia.commons.core.validation.ValidateAs;

/**
 * Utility class that normalizes the case of input strings.
 */
public final class NormalizeStringCaseUtils {
	private static final String WORD_SEPARATORS = " .-_/()";

	private NormalizeStringCaseUtils() {}

	/**
	 * Indicates the case to transform a string into.
	 */
	public enum TargetCase {
		/** Title Case */
		TITLE,
		/** Sentence case. */
		SENTENCE
	}

	/**
	 * Indicates the scope inside a string that case conversion applies to.
	 */
	public enum Scope {
		PER_WORD,
		ENTIRE_STRING
	}

	/**
	 * Normalizes the case of an input string into the desired case.
	 * @param s input string
	 * @param targetCase case to transform to
	 * @param scope indicates what the criteria should apply to
	 * @param uppercaseCriteria only perform conversion if the string is > N% uppercase
	 * @param lowercaseCriteria only perform conversion if the string is > N% lowercase
	 * @return normalized string
	 */
	public static String normalizeCase(final String s, final TargetCase targetCase, final Scope scope, final int uppercaseCriteria, final int lowercaseCriteria) {
		ValidateAs.notNull(s, "s");
		ValidateAs.notNull(targetCase, "targetCase");
		ValidateAs.notNull(scope, "scope");

		int uppercaseCount = 0;
		int lowercaseCount = 0;
		int countableChars = 0;
		final StringBuilder buffer = new StringBuilder();
		final StringBuilder out = new StringBuilder();
		for (final char c: s.toCharArray()) {
			if (isSeparator(c)) {
				// new word separator
				if (scope == Scope.PER_WORD) {
					if (buffer.length() > 0) {
						final boolean convert =
								satisfiesCriteria(countableChars, uppercaseCriteria, uppercaseCount) ||
								satisfiesCriteria(countableChars, lowercaseCriteria, lowercaseCount);
						copyChars(convert, targetCase, out, buffer);
						lowercaseCount = 0;
						uppercaseCount = 0;
						countableChars = 0;
					}
					out.append(c);
				} else {
					buffer.append(c);
				}
			} else {
				countableChars++;
				if (Character.isLowerCase(c)) {
					lowercaseCount++;
					buffer.append(c);
				} else if (Character.isUpperCase(c)) {
					uppercaseCount++;
					buffer.append(c);
				} else {
					buffer.append(c);
				}
			}
		}
		if (buffer.length() > 0) {
			final boolean convert =
					satisfiesCriteria(countableChars, uppercaseCriteria, uppercaseCount) ||
					satisfiesCriteria(countableChars, lowercaseCriteria, lowercaseCount);
			copyChars(convert, targetCase, out, buffer);
		}
		return out.toString();
	}

	private static void copyChars(final boolean convert, final TargetCase targetCase, final StringBuilder out, final StringBuilder buffer) {
		if (convert) {
			if (targetCase == TargetCase.TITLE)
				out.append(toTitleCase(buffer));
			else
				out.append(toSentenceCase(buffer));
		} else {
			out.append(buffer);
		}
		buffer.setLength(0);
	}

	public static boolean satisfiesCriteria(final int length, final int criteria, final int charCount) {
		final int minCharsForCriteria = minCharsForCriteria(length, criteria);
		return (charCount >= minCharsForCriteria);
	}

	/**
	 * Identifies the number of characters in a string of a given length that match the given percentage. The output is
	 * equal to the rounded value of ((length * criteria) / 100).
	 * @param length string length
	 * @param criteria percentage of characters
	 * @return number of characters that are required to satisfy the given percentage, rounded down or up
	 */
	static int minCharsForCriteria(final int length, final int criteria) {
		final int i = length * criteria;
		final int remainder = i % 100;
		if (remainder >= 50)
			return (i / 100) + 1;
		return i / 100;
	}

	private static boolean isSeparator(char c) {
		return WORD_SEPARATORS.indexOf(c) >= 0;
	}

	/**
	 * Converts an input string into sentence case, capitalizing the first word in the string and every word that
	 * follows a period.
	 * @param s input string
	 * @return string transformed into sentence case
	 */
	public static String toSentenceCase(final String s) {
		final StringBuilder sb = new StringBuilder(s);
		return toSentenceCase(sb).toString();
	}

	private static StringBuilder toSentenceCase(final StringBuilder sb) {
		boolean capitalizeNext = true;
		for (int i = 0; i < sb.length(); i++) {
			final char c = sb.charAt(i);
			if (c == '.' || c == '&') {
				capitalizeNext = true;
			} else if (capitalizeNext && !isSeparator(c)) {
				sb.setCharAt(i, Character.toTitleCase(c));
				capitalizeNext = false;
			} else if (!Character.isLowerCase(c)) {
				sb.setCharAt(i, Character.toLowerCase(c));
			}
		}
		return sb;
	}

	/**
	 * Converts an input string into title case, capitalizing the first character of every word.
	 * @param s input string
	 * @return string transformed into title case
	 */
	public static String toTitleCase(final String s) {
		final StringBuilder sb = new StringBuilder(s);
		return toTitleCase(sb).toString();
	}

	private static StringBuilder toTitleCase(final StringBuilder sb) {
		boolean capitalizeNext = true;
		for (int i = 0; i < sb.length(); i++) {
			final char c = sb.charAt(i);
			if (isSeparator(c) || c == '&') {
				capitalizeNext = true;
			} else if (capitalizeNext) {
				sb.setCharAt(i, Character.toTitleCase(c));
				capitalizeNext = false;
			} else if (!Character.isLowerCase(c)) {
				sb.setCharAt(i, Character.toLowerCase(c));
			}
		}
		return sb;
	}
}