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
package com.pushinginertia.commons.lang;

import java.util.Map;

/**
 * Utility methods to mutate {@link java.util.Map}s.
 */
public class MapUtils {
	public static final String DEFAULT_SEPARATOR = "\n";

	/**
	 * Transforms a map to a string for logging purposes.
	 * @param map map to transform
	 * @param separator separator to insert between each key-value pair
	 * @param <K> type of the map's key
	 * @param <V> type of the map's value
	 * @return null if the input is null
	 */
	public static <K, V> String toString(final Map<K, V> map, final String separator) {
		if (map == null)
			return null;

		final StringBuilder sb = new StringBuilder();
		for (Map.Entry<K, V> e: map.entrySet()) {
			if (sb.length() > 0)
				sb.append(separator);
			sb.append(e.getKey()).append('=').append(e.getValue());
		}
		return sb.toString();
	}

	/**
	 * Implementation of {@link #toString(java.util.Map, String)} that uses {@link #DEFAULT_SEPARATOR} as a separator.
	 * @param map map to transform
	 * @param <K> type of the map's key
	 * @param <V> type of the map's value
	 * @return null if the input is null
	 */
	public static <K, V> String toString(final Map<K, V> map) {
		return toString(map, DEFAULT_SEPARATOR);
	}
}
