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

import java.util.Collection;

/**
 * Boilerplate code for working with collections.
 */
public final class CollectionUtils {
	/**
	 * Sums each value in a given collection of numbers.
	 * @param collection collection containing values to sum
	 * @return sum of all values
	 */
	public static long calculateSumOfValues(final Collection<? extends Number> collection) {
		ValidateAs.notNull(collection, "collection");

		long l = 0;
		for (final Number i: collection) {
			l += i.longValue();
		}
		return l;
	}

	/**
	 * Multiplies all values in a given collection of numbers together.
	 * @param collection collection containing values to take the product of
	 * @return product of all values
	 */
	public static long calculateProductOfValues(final Collection<? extends Number> collection) {
		ValidateAs.notNull(collection, "collection");

		long l = 1;
		for (final Number i: collection) {
			l *= i.longValue();
		}
		return l;
	}

	private CollectionUtils() {}
}
