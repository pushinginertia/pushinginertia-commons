/* Copyright (c) 2011-2012 Pushing Inertia
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
package com.pushinginertia.commons.web;

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.lang.CharUtils;
import com.pushinginertia.commons.lang.Tuple2;

/**
 * Methods that assist with SEO.
 */
public class SeoUtils {
	/**
	 * characters to delete:
	 * !"#$%'*,.:;<=>?@[\]^`{|}~
	 * quotation marks
	 * #127-#141, #143-#149, #153-#157, #161-#191, #215, #247
	 */
	public static final char[] DELETE =
			{'!', '"', '#', '$', '%', '\'', '*', ',', '.', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '`', '{', '|', '}', '~', '(', ')', '=',
			 '\u2018', '\u2019', '\u201C', '\u201D',
			 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141,
			 143, 144, 145, 146, 147, 148, 149,
			 153, 154, 155, 156, 157,
			 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191,
			 215,
			 247};
	/**
	 * characters to convert to hyphen:
	 * &+/_()
	 * #150-#152, #160
	 * Chinese characters 65288, 65289, 65292 (parentheses and comma)
	 */
	public static final char[] REPLACE_HYPHEN =
			{'-', ' ', '&', '+', '/', '_', '(', ')',
			 150, 151, 152, 160,
			 65288, 65289, 65292};

	/**
	 * Generates a slug (SEO-friendly short text) based on a title of a page by removing punctuation and formatting and
	 * replacing these characters with hyphens. Unicode characters are not modified, so non-English characters will
	 * appear in the output unchanged.
	 * @param name name or title given to a page
	 * @return null iff input is null
	 */
	public static String generateSlug(final String name) {
		if (name == null) {
			return null;
		}

		// TODO: support maximum words in the slug or maximum output length

		// TODO: possible future enhancement to strip unicode characters or transliterate them into Roman characters
		// A useful place to start:
		// http://stackoverflow.com/questions/1673544/how-do-i-detect-unicode-characters-in-a-java-string
		// java.text.Normalizer.normalize will remove diacritical marks (e.g., ç, ñ, é => c, n, e)

		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < name.length(); i++) {
			final char c = name.charAt(i);
			if (CharUtils.inCharArray(c, DELETE) >= 0) {
				// do nothing
			} else if (CharUtils.inCharArray(c, REPLACE_HYPHEN) >= 0) {
				// replace with hyphen only if the previous character is not a hyphen
				if (sb.length() > 0 && sb.charAt(sb.length() - 1) != '-')
					sb.append('-');
			} else {
				sb.append(c);
			}
		}

		// remove any trailing hyphens
		while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '-') {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	/**
	 * Parses a slug with two words separated by a hyphen into a {@link Tuple2} containing the two words.
	 * @param slug slug to parse
	 * @return words separated into a data structure
	 * @throws IllegalArgumentException if the input is not valid
	 */
	public static Tuple2<String, String> parseTwoWordSlug(final String slug) throws IllegalArgumentException {
		ValidateAs.notNull(slug, "slug");

		if (slug.indexOf('-') < 0) {
			throw new IllegalArgumentException("Slug [" + slug + "] does not contain two words.");
		}

		final String[] slugs = slug.split("-");
		if (slugs.length != 2 || slugs[0].length() == 0 || slugs[1].length() == 0) {
			throw new IllegalArgumentException("Slug [" + slug + "] does not contain two words.");
		}

		return new Tuple2<String, String>(slugs[0], slugs[1]);
	}
}
