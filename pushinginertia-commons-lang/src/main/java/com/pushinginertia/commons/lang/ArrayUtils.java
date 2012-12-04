package com.pushinginertia.commons.lang;

import java.util.Arrays;

public class ArrayUtils {
	/**
	 * Concatenates two arrays into one new array.
	 * @param first first array to concatenate
	 * @param second second array to concatenate
	 * @param <T> type of each item in the array
	 * @return concatenated array
	 */
	public static <T> T[] concat(T[] first, T[] second) {
		final T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * Prepends an item to the beginning of an array
	 * @param item
	 * @param arr cannot be null
	 * @param <T>
	 * @return
	 */
	public static <T> T[] prepend(T item, T[] arr) {
		if (arr == null) {
			throw new IllegalArgumentException("arr is null");
		}
		final int length = arr.length;
		final T[] result = (T[])java.lang.reflect.Array.newInstance(arr.getClass().getComponentType(), length + 1);
		result[0] = item;
		System.arraycopy(arr, 0, result, 1, length);
		return result;
	}
}
