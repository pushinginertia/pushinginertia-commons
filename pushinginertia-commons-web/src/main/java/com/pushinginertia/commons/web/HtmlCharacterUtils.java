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
package com.pushinginertia.commons.web;

import com.pushinginertia.commons.core.validation.ValidateAs;

/**
 * Utility methods to work with HTML characters.
 */
public final class HtmlCharacterUtils {
	private HtmlCharacterUtils() {}

	/**
	 * Converts a string to its HTML character entities. Each character will take the form &#FF where FF is the hex
	 * representation of the character.
	 * @param toTransform string to transform
	 * @return transformed output
	 * @throws IllegalArgumentException if the input is null
	 */
	public static String toCharacterEntities(final String toTransform) throws IllegalArgumentException {
		ValidateAs.notNull(toTransform, "toTransform");

		final StringBuilder sb = new StringBuilder();
		for (final char c: toTransform.toCharArray()) {
			sb.append("&#").append((byte)c);
		}
		return sb.toString();
	}
}
