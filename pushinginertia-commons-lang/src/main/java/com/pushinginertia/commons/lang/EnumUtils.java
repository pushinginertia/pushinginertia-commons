/* Copyright (c) 2011-2018 Pushing Inertia
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Abstractions of common enum boilerplate code.
 */
public final class EnumUtils {
	private static final Logger LOG = LoggerFactory.getLogger(EnumUtils.class);

	private EnumUtils() {}

	/**
	 * For when we want to ignore strings that aren't recognized in an enum
	 * type.
	 */
	@Nullable
	public static <E extends Enum<E>> E toEnumIgnoreUnknown(
			@Nonnull final String item,
			@Nonnull final Class<E> enumType) {
		try {
			return Enum.valueOf(enumType, item);
		} catch (final IllegalArgumentException e) {
			// something unknown
			final String msg = MessageFormat.format(
					"Encountered unknown {0}[{1}].",
					enumType.getSimpleName(),
					item);
			LOG.info(msg);
			return null;
		}
	}

	/**
	 * Transforms a collection of strings into a set of enums for those
	 * strings.
	 *
	 * @param items Collection to transform.
	 * @param enumType Enum type to parse from.
	 * @param throwWhenUnknown Either throw or ignore strings that don't
	 *                         resolve to enums.
	 * @throws IllegalArgumentException If #throwWhenUnknown is true and a
	 * string in the input doesn't resolve to one of the enums in the provided
	 * enum class.
	 */
	public static <E extends Enum<E>> Set<E> toEnumSet(
			@Nonnull final Collection<String> items,
			@Nonnull final Class<E> enumType,
			final boolean throwWhenUnknown) throws IllegalArgumentException {
		return items.stream()
				.map(item -> {
					if (throwWhenUnknown) {
						return Enum.valueOf(enumType, item);
					}
					return toEnumIgnoreUnknown(item, enumType);
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

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
