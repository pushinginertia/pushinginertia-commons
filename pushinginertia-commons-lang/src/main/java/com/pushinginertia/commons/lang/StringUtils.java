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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utility methods.
 */
public final class StringUtils {
	private StringUtils() {}

	/**
	 * Constructs a string containing a given character repeated N times.
	 * @param c character to repeat
	 * @param n number of times to repeat the character
	 * @return string of length n
	 * @throws IllegalArgumentException if n is negative
	 */
	public static String repeat(final char c, final int n) throws IllegalArgumentException {
		if (n < 0) {
			throw new IllegalArgumentException("n must be non-negative: " + n);
		}
		final StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	public static final char[] WHITESPACES = {'\t', ' '};

	/**
	 * Replaces all occurrences of multiple whitespace with a single space (#32) character.
	 * @param s string to replace
	 * @return resulting string, or null if the input is null
	 */
	public static String removeDoubleSpaces(final String s) {
		if (s == null) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		boolean prevWhitespace = false;
		for (final char c: s.toCharArray()) {
			final boolean isWhitespace = CharUtils.inCharArray(c, WHITESPACES) >= 0;
			if (!isWhitespace || !prevWhitespace) {
				sb.append(c);
			}
			prevWhitespace = isWhitespace;
		}
		return sb.toString();
	}

	/**
	 * Joins a list of strings into one string with a given separator.
	 * @param separator separator to insert between strings
	 * @param ss list of strings to join
	 * @return never null
	 */
	private static String join(final String separator, final String... ss) {
		final StringBuilder sb = new StringBuilder();
		for (String s: ss) {
			if (sb.length() > 0)
				sb.append(separator);
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * Returns the first N words in a string or the entire string if it has less than N words.
	 * @param searchString string to search
	 * @param wordCount number of words to return
	 * @param maxLength Hard maximum character length of the output string (will be cut at the last word that falls
	 * within this limit). If the first word's length exceeds this limit, it will be truncated and returned as the
	 * output.
	 * @return null if s is null
	 */
	public static String firstNWords(final String searchString, final int wordCount, final int maxLength) {
		if (searchString == null) {
			return null;
		}
		ValidateAs.positive(maxLength, "maxLength");

		final String[] sArr = searchString.split("\\s+", wordCount + 1); // this might not work with newlines
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordCount && i < sArr.length; i++) {
			final String s = sArr[i];
			if (i == 0) {
				// first word always gets included, but is truncated if it will exceed the hard maximum char length
				sb.append(StringUtils.truncate(s, maxLength));
			} else {
				if (sb.length() + s.length() > maxLength) {
					// break when the length will exceed the hard maximum, but only starting on the second word
					break;
				}
				sb.append(s);
			}
			sb.append(' ');
		}
		if (sb.length() > 0) {
			// remove the trailing whitespace
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Transforms a map to a string containing name-value pairs separated by '&'.
	 * @param m map to transform
	 * @return resulting string
	 */
	public static String mapToString(final Map<String, String> m) {
		final StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> e: m.entrySet()) {
			sb.append(e.getKey().replace("&", "&&")).append('=');
			final String value = e.getValue();
			if (value != null)
				sb.append(e.getValue().replace("&", "&&"));
			sb.append('&');
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Converts a string containing name-value pairs to a map. Values that are undefined (i.e., string length is zero)
	 * are added as null rather than not empty strings.
	 * @param s string to transform
	 * @return null if input is null
	 */
	public static Map<String, String> stringToMap(final String s) {
		if (s == null)
			return null;
		final Map<String, String> m = new HashMap<String, String>();

		final StringBuilder sb = new StringBuilder();
		int equalSignIdx = -1;
		int ampersandCount = 0;
		for (char c: s.toCharArray()) {
			// check if we're at the end of one or more ampersands
			if (c != '&' && ampersandCount > 0) {
				// first append the number of ampersands encountered, divided by 2
				for (int i = 0; i < ampersandCount / 2; i++)
					sb.append('&');

				if (ampersandCount % 2 > 0) {
					// hit the end of a name-value pair
					if (equalSignIdx > 0) {
						final String value = sb.substring(equalSignIdx);
						m.put(sb.substring(0, equalSignIdx), value.length() == 0? null: value);
					}
					equalSignIdx = -1;
					sb.setLength(0);
				}

				ampersandCount = 0;
			}
			switch (c) {
				case '&':
					ampersandCount++;
					break;
				case '=':
					if (equalSignIdx < 0)
						equalSignIdx = sb.length();
					break;
				default:
					sb.append(c);
					break;
			}
		}
		for (int i = 0; i < ampersandCount / 2; i++)
			sb.append('&');
		if (equalSignIdx > 0) {
			final String value = sb.substring(equalSignIdx);
			m.put(sb.substring(0, equalSignIdx), value.length() == 0? null: value);
		}

		return m;
	}

	/**
	 * Converts a string separated by newlines into a List object. Leading and trailing whitespace is stripped from each
	 * line, and lines with no text are ignored.
	 * @param newlineString a string with newlines separating sub-strings
	 * @param limit maximum number of lines to include in the list (zero for no limit)
	 * @return null if newlineString is null
	 */
	public static List<String> newlineStringToList(final String newlineString, final int limit) {
		if (newlineString == null)
			return null;

		final List<String> l = new ArrayList<String>();
		for (final String s: newlineString.split("\\n")) {
			final String sTrimmed = s.trim();
			if (sTrimmed.length() > 0) {
				l.add(sTrimmed);
				if (limit > 0 && l.size() >= limit)
					return l;
			}
		}
		return l;
	}

	/**
	 * Adds a character to the end of the given string if the string doesn't already end with the given character.
	 * Useful for file paths when a trailing slash or backslash must be added so that the path can easily be
	 * concatenated with a file name.
	 * @param s string to examine
	 * @param c character to append to the end of the string
	 * @return input string with the given character added to the end if necessary
	 */
	public static String addTrailingCharIfMissing(final String s, final char c) {
		if (s.endsWith(Character.toString(c))) {
			return s;
		}
		return s + c;
	}

	/**
	 * Strips the last character from the given string if the string ends with that character.
	 * @param s string to examine
	 * @param c character to remove from the end of the string
	 * @return input string with the given character removed from the end if necessary
	 */
	public static String stripTrailingCharIfPresent(final String s, final char c) {
		if (s.endsWith(Character.toString(c))) {
			return s.substring(0, s.length() - 1);
		}
		return s;
	}

	/**
	 * Performs a case insensitive replacement of all occurrences of a given string with a replacement value.
	 * @param s string to modify
	 * @param searchString case insensitive substring to locate
	 * @param replacementString replacement value for all matches
	 * @return modified string
	 */
	public static String replaceAllCaseInsensitive(final String s, final String searchString, final String replacementString) {
		ValidateAs.notNull(s, "s");
		ValidateAs.notNull(searchString, "searchString");
		ValidateAs.notNull(replacementString, "replacementString");
		final Pattern p = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
		final Matcher m = p.matcher(s);
		return m.replaceAll(replacementString);
	}

	/**
	 * Truncates a string exceeding a given length to that length.
	 * @param s string to truncate
	 * @param length length to truncate the string to
	 * @return truncated string
	 */
	public static String truncate(final String s, final int length) {
		ValidateAs.notNull(s, "s");
		ValidateAs.nonNegative(length, "length");

		if (s.length() <= length) {
			return s;
		}
		return s.substring(0, length);
	}

	/**
	 * Removes all occurrences of a set of characters from a given string.
	 * @param value string to remove characters from
	 * @param removeCharArr array containing characters that will be stripped from the string
	 * @return string containing no characters in the given array
	 */
	public static String removeChars(final String value, final char[] removeCharArr) {
		ValidateAs.notNull(value, "value");

		final StringBuilder sb = new StringBuilder(value.length());
		for (final char c: value.toCharArray()) {
			final int idx = CharUtils.inCharArray(c, removeCharArr);
			if (idx < 0) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Identifies the various unicode character subsets contained in a given string.
	 * @param value value to parse
	 * @return set of unicode character subsets
	 */
	public static Set<Character.UnicodeBlock> unicodeBlocksInString(final String value) {
		ValidateAs.notNull(value, "value");

		final Set<Character.UnicodeBlock> set = new HashSet<Character.UnicodeBlock>();
		for (final char c: value.toCharArray()) {
			final Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
			set.add(block);
		}
		return set;
	}

	/**
	 * Identifies if every character in a given string is a latin character. This is defined as
	 * {@link Character.UnicodeBlock#of(char)} returning {@link Character.UnicodeBlock#BASIC_LATIN} for every character
	 * in the string.
	 * @param value value to parse
	 * @return true if the string is latin (or empty)
	 */
	public static boolean isLatin(final String value) {
		ValidateAs.notNull(value, "value");

		for (final char c: value.toCharArray()) {
			final Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
			if (!Character.UnicodeBlock.BASIC_LATIN.equals(block)) {
				return false;
			}
		}

		return true;
	}
}
