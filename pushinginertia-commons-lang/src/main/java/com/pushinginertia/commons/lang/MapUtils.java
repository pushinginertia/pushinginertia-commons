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
