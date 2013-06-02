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

import java.util.Map;
import java.util.TreeMap;

/**
 * Utility methods to mutate {@link java.util.Map}s.
 */
public class MapUtils {
	public static final String DEFAULT_SEPARATOR = "\n";

	/**
	 * Transforms a map to a string for logging purposes.
	 * @param map map to transform
	 * @param separator separator to insert between each key-value pair
	 * @param indent number of whitespace characters to insert before each key-value pair
	 * @param <K> type of the map's key
	 * @param <V> type of the map's value
	 * @return null if the input is null
	 */
	public static <K, V> String toString(final Map<K, V> map, final String separator, final int indent) {
		// return null if no input
		if (map == null)
			return null;

		// create an indentation string
		final String indentString = StringUtils.repeat(' ', indent);

		final StringBuilder sb = new StringBuilder();
		for (Map.Entry<K, V> e: map.entrySet()) {
			if (sb.length() > 0)
				sb.append(separator);
			sb.append(indentString);
			sb.append(e.getKey()).append('=').append(e.getValue());
		}
		return sb.toString();
	}

	/**
	 * Transforms a map to a string for logging purposes.
	 * @param map map to transform
	 * @param separator separator to insert between each key-value pair
	 * @param <K> type of the map's key
	 * @param <V> type of the map's value
	 * @return null if the input is null
	 */
	public static <K, V> String toString(final Map<K, V> map, final String separator) {
		return toString(map, separator, 0);
	}

	/**
	 * Implementation of {@link #toString(java.util.Map, String)} that uses {@link #DEFAULT_SEPARATOR} as a separator.
	 * @param map map to transform
	 * @param <K> type of the map's key
	 * @param <V> type of the map's value
	 * @return null if the input is null
	 */
	public static <K, V> String toString(final Map<K, V> map) {
		return toString(map, DEFAULT_SEPARATOR, 0);
	}

	/**
	 * Parses a string containing key-value pairs separated by a common deliminator into a map. This is useful for a
	 * simple representation of a list of key-value pairs, where the delimiter is not present within the keys or values.
	 * For example, the string representation of a map using newlines as a delimiter could be parsed by this method.
	 * If a value is not present for a key, it is added as an empty string.
	 * @param s string to parse
	 * @param delimiterRegex delimiter between key-value pairs
	 * @return map of items
	 */
	public static Map<String, String> parseDelimited(final String s, final String delimiterRegex) {
		ValidateAs.notNull(s, "s");
		ValidateAs.notNull(delimiterRegex, "delimiterRegex");

		final String[] items = s.split(delimiterRegex);
		final Map<String, String> map = new TreeMap<String, String>();
		for (final String item: items) {
			final String[] kvPair = item.split("=", 2);
			final String key = kvPair[0];
			if (kvPair.length == 1) {
				map.put(key, "");
			} else {
				map.put(key, kvPair[1]);
			}
		}
		return map;
	}
}
