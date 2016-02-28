/* Copyright (c) 2011-2016 Pushing Inertia
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

import javax.annotation.Nonnull;

/**
 * Abstractions of common enum boilerplate code.
 */
public final class EnumUtils {
	private EnumUtils() {}

	/**
	 * Transforms an enumeration into a string that could be presented to a user.
	 */
	@Nonnull
	public static <E extends Enum<E>> String toCamelCaseString(@Nonnull final E enumValue) {
		final char[] name = enumValue.name().toCharArray();
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
