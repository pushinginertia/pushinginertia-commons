package com.pushinginertia.commons.web;

import com.pushinginertia.libraries.ext.java.CharUtil;

/**
 * Methods that assist with SEO.
 */
public class SeoUtils {
	/**
	 * characters to delete:
	 * !"#$%'*,.:;<=>?@[\]^`{|}~
	 * #127-#141, #143-#149, #153-#157, #161-#191, #215, #247
	 */
	public static final char[] DELETE =
			{'!', '"', '#', '$', '%', '\'', '*', ',', '.', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '`', '{', '|', '}', '~',
			 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141,
			 143, 144, 145, 146, 147, 148, 149,
			 153, 154, 155, 156, 157,
			 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191,
			 215,
			 247};
	/**
	 * characters to convert to hyphen:
	 * &+/_
	 * #150-#152, #160
	 */
	public static final char[] REPLACE_HYPHEN =
			{'-', ' ', '&', '+', '/', '_', 150, 151, 152, 160};
	// characters to keave untouched:
	//  ()-

	/**
	 * Generates a slug (SEO-friendly short text) based on a title of a page by removing punctuation and formatting and
	 * replacing these characters with hyphens. Unicode characters are not modified, so non-English characters will
	 * appear in the output unchanged.
	 * @param name name or title given to a page
	 * @return null iff input is null
	 */
	public static String generateSlug(final String name) {
		if (name == null)
			return null;

		// TODO: support maximum words in the slug or maximum output length

		// TODO: possible future enhancement to strip unicode characters or transliterate them into Roman characters
		// A useful place to start:
		// http://stackoverflow.com/questions/1673544/how-do-i-detect-unicode-characters-in-a-java-string
		// java.text.Normalizer.normalize will remove diacritical marks (e.g., ç, ñ, é => c, n, e)

		final StringBuilder sb = new StringBuilder();

		for (int i = 0; i < name.length(); i++) {
			final char c = name.charAt(i);
			if (CharUtil.inCharArray(c, DELETE) >= 0) {
				// do nothing
			} else if (CharUtil.inCharArray(c, REPLACE_HYPHEN) >= 0) {
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
}
