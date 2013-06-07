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

import java.util.Arrays;

public final class ArrayUtils {
	private ArrayUtils() {}

	/**
	 * Concatenates two arrays into one new array.
	 * @param first first array to concatenate
	 * @param second second array to concatenate
	 * @param <T> type of each item in the array
	 * @return concatenated array
	 */
	public static <T> T[] concat(final T[] first, final T[] second) {
		final T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * Prepends an item to the beginning of an array. The array is not mutated and a new array is instantiated.
	 * @param item item to prepend
	 * @param arr array to prepend the item to
	 * @param <T> type of each item in the array
	 * @return new array instance containing the item concatenated with the existing array
	 * @throws IllegalArgumentException if arr is null
	 */
	public static <T> T[] prepend(final T item, final T[] arr) throws IllegalArgumentException {
		ValidateAs.notNull(arr, "arr");
		final int length = arr.length;
		final T[] result = (T[])java.lang.reflect.Array.newInstance(arr.getClass().getComponentType(), length + 1);
		result[0] = item;
		System.arraycopy(arr, 0, result, 1, length);
		return result;
	}
}
