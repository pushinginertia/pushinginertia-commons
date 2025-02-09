/* Copyright (c) 2011-2017 Pushing Inertia
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

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String utility methods.
 */
public final class StringUtils {
	private StringUtils() {}

	/**
	 * Greatest code point for a 3 byte UTF character. Anything above this would require 4 bytes.
	 */
	private static final int UTF8_3BYTE_MAX_CODE_POINT = 0xFFFF;

	/**
	 * Identifies if a given string can be converted to a number.
	 * @param s string to test
	 * @return true if the string represents a numeric value
	 * @throws IllegalArgumentException if the input is null
	 */
	public static boolean isNumeric(final String s) throws IllegalArgumentException {
		ValidateAs.notNull(s, "s");
		if (s.isEmpty()) {
			return false;
		}

		int i;
		if (s.charAt(0) == '-') {
			if (s.length() == 1) {
				// catch the case when the input is "-"
				return false;
			}
			i = 1;
		} else {
			i = 0;
		}
		for (; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

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

	public static String stripLeadingTrailingCharacters(final String s, final char[] charsToStrip) {
		final char[] chars = s.toCharArray();
		int startIdx = 0;
		int endIdx = s.length();

		while (startIdx < endIdx && (CharUtils.inCharArray(chars[startIdx], charsToStrip) != -1)) {
			startIdx++;
		}
		while (endIdx > startIdx && (CharUtils.inCharArray(chars[endIdx - 1], charsToStrip) != -1)) {
			endIdx--;
		}
		return s.substring(startIdx, endIdx);
	}

	/**
	 * Strips multiple occurrences of a given collection of characters from a
	 * string. Can be used to replace multiple whitespace with a single space.
	 */
	@Nonnull
	public static String stripRepeatedCharacters(
			@Nonnull final String s,
			final char[] charsToStrip) {
		final StringBuilder sb = new StringBuilder();

		final char[] chars = s.toCharArray();
		int startIdx = -1;

		for (int i = 0; i < s.length(); i++) {
			if (CharUtils.inCharArray(chars[i], charsToStrip) != -1) {
				if (startIdx == -1) {
					// Previous character was not in charsToStrip
					startIdx = i;
				} else if (chars[i - 1] != chars[i]) {
					// Two characters to strip but they are different.
					sb.append(chars[i - 1]);
					startIdx = i;
				}
			} else if (startIdx != -1) {
				sb.append(chars[i - 1]);
				sb.append(chars[i]);
				startIdx = -1;
			} else {
				sb.append(chars[i]);
			}
		}

		if (startIdx != -1) {
			sb.append(chars[s.length() - 1]);
		}

		return sb.toString();
	}

	/**
	 * Supplementary characters are characters in the Unicode standard whose code points are above U+FFFF, and are
	 * comprised of 4 bytes. Some systems don't support these characters (such as MySQL prior to 5.5) and this method
	 * provides a way to either strip out these characters or replace them with something else.
	 * @param encodedString String to transform.
	 * @param replacementChar Character to insert as a replacement. Can be null, in which case the supplementary
	 * characters are simply stripped. A good replacement string is "\uFFFD", which is reserved as the unicode
	 * replacement character when an incoming character is unknown or unrepresentable.
	 * @return Transformed string.
	 * @see <a href="http://www.fileformat.info/info/unicode/char/0fffd/index.htm">Unicode Character 'REPLACEMENT CHARACTER' (U+FFFD)</a>
	 */
	public static String replaceUTF8SupplementaryChars(final String encodedString, final String replacementChar)  {
		ValidateAs.notNull(encodedString, "encodedString");

		final int length = encodedString.length();
		final StringBuilder sb = new StringBuilder(length);
		int i = 0;
		while (i < length) {
			final int codepoint = encodedString.codePointAt(i);

			if (codepoint > UTF8_3BYTE_MAX_CODE_POINT) {
				if (replacementChar != null) {
					sb.append(replacementChar);
				}
			} else {
				if (Character.isValidCodePoint(codepoint)) {
					sb.appendCodePoint(codepoint);
				} else {
					if (replacementChar != null) {
						sb.append(replacementChar);
					}
				}
			}
			i += Character.charCount(codepoint);
		}
		return sb.toString();
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
	 * @throws IllegalArgumentException if the input is null
	 */
	public static String truncate(final String s, final int length) throws IllegalArgumentException {
		ValidateAs.notNull(s, "s");
		ValidateAs.nonNegative(length, "length");

		if (s.length() <= length) {
			return s;
		}
		return s.substring(0, length);
	}

	/**
	 * Truncates a string exceeding a given length to that length and does not fail if the input is null.
	 * @param s string to truncate
	 * @param length length to truncate the string to
	 * @return truncated string or null if the input is null
	 */
	public static String truncateNullable(final String s, final int length) {
		if (s == null) {
			return null;
		}
		return truncate(s, length);
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

		final Set<Character.UnicodeBlock> set = new HashSet<>();
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

	/**
	 * Removes accented characters from a string, replacing them with English character equivalents.
	 * @param value Value to normalize.
	 */
	public static String removeAccents(final String value) {
		// normalize
		final String decomposed = Normalizer.normalize(value, Normalizer.Form.NFD);
		// remove diacritics
		return decomposed.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}

	/**
	 * Counts the number of times a given character appears in a string.
	 * @param search character to search
	 * @param s string to examine
	 * @return number of times the character appears in the string
	 */
	public static int charFrequencyInString(final char search, final String s) {
		if (s == null) {
			return 0;
		}

		int i = 0;
		for (final char c: s.toCharArray()) {
			if (c == search) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Identifies the length of the longest prefix that is common to two strings.
	 * @param s1 First string to compare.
	 * @param s2 Second string to compare.
	 * @return Length of the longest common prefix.
	 */
	public static int longestCommonPrefixLength(final String s1, final String s2) {
		ValidateAs.notNull(s1, "s1");
		ValidateAs.notNull(s2, "s2");
		final int len = Math.min(s1.length(), s2.length());
		for (int i = 0; i < len; i++) {
			if (s1.charAt(i) != s2.charAt(i)) {
				return i;
			}
		}
		return len;
	}

	/**
	 * Instantiates a new UUID from a string that may or may not have had its hyphens stripped. e.g.,
	 * 00000000000000000000000000000000 vs. 00000000-0000-0000-0000-000000000000.
	 * @param uuid String representation to parse.
	 * @return Parsed UUID object.
	 * @throws java.lang.IllegalArgumentException If the input is not parseable.
	 */
	@Nonnull
	public static UUID parseUUID(@Nonnull final String uuid) throws IllegalArgumentException{
		if (uuid.length() == 32) {
			// assume hyphens have been stripped and need to be re-added so it can be parsed
			final byte[] bytes = hexStringToByteArray(uuid);
			final ByteBuffer buf = ByteBuffer.wrap(bytes);
			return new UUID(buf.getLong(), buf.getLong());
		}
		return UUID.fromString(uuid);

	}

	public static byte[] hexStringToByteArray(String s) {
		final int len = s.length();
		if (len % 2 != 0) {
			throw new IllegalArgumentException("expected hex string of even length");
		}
		final byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			final int c0 = Character.digit(s.charAt(i), 16);
			if (c0 < 0) {
				throw new IllegalArgumentException(
					String.format("illegal character '%c' at index %d", s.charAt(i), i)
				);
			}
			final int c1 = Character.digit(s.charAt(i + 1), 16);
			if (c1 < 0) {
				throw new IllegalArgumentException(
					String.format("illegal character '%c' at index %d", s.charAt(i + 1), i + 1)
				);
			}
			data[i / 2] = (byte) (
				(c0 << 4) + c1
			);
		}
		return data;
	}

	/**
	 * Identifies if a given string is a sequence of hex characters and of a given number of bytes.
	 * @param s String to test.
	 * @param expectedBytes
	 * @return
	 */
	public static boolean isHexOfLength(@Nonnull final String s, final int expectedBytes) {
		return s.length() == expectedBytes * 2 && s.chars().allMatch(i -> Character.digit(i, 16) != -1);
	}

	/**
	 * Transforms an uppercased string with underscores into a string that
	 * could be presented to a user.
	 */
	@Nonnull
	public static String toCamelCaseString(@Nonnull final String s) {
		final char[] name = s.toCharArray();
		final StringBuilder sb = new StringBuilder(name.length);
		for (int i = 0; i < name.length; i++) {
			if (i == 0 || name[i - 1] == '_') {
				sb.append(Character.toUpperCase(name[i]));
			} else if (name[i] == '_') {
				sb.append(' ');
			} else {
				sb.append(Character.toLowerCase(name[i]));
			}
		}
		return sb.toString();
	}
}
