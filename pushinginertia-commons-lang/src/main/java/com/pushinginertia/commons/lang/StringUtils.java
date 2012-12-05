package com.pushinginertia.commons.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * String utility methods.
 */
public class StringUtils {
	public static String removeDoubleSpaces(String s) {
		if (s == null)
			return null;
		return s.replaceAll("\\s\\s\\s*", " ");
	}

	/**
	 * Joins a list of strings into one string with a given separator.
	 * @param separator
	 * @param ss
	 * @return never null
	 */
	private static String join(final String separator, final String... ss) {
		final StringBuffer sb = new StringBuffer();
		for (String s: ss) {
			if (sb.length() > 0)
				sb.append(separator);
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * Returns the first N words in a string or the entire string if it has less than N words.
	 * @param s
	 * @param n
	 * @return null if s is null
	 */
	public static String firstNWords(String s, int n) {
		if (s == null)
			return null;

		final String[] sArr = s.split("\\s+", n + 1); // this might not work with newlines
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n && i < sArr.length; i++)
			sb.append(sArr[i]).append(' ');
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * Converts a map to a string containing name-value pairs.
	 * @param m
	 * @return
	 */
	public static String mapToString(Map<String, String> m) {
		final StringBuffer sb = new StringBuffer();
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
	 * @param s
	 * @return null if input is null
	 */
	public static Map<String, String> stringToMap(String s) {
		if (s == null)
			return null;
		final Map<String, String> m = new HashMap<String, String>();

		final StringBuffer sb = new StringBuffer();
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
	 * @param newlineString
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
}
