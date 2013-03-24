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

/**
 * Provides some common validation logic.
 */
public class ValidateAs {
	/**
	 * Validates that a variable is not null.
	 * @param value value to check
	 * @param name name of the value that will be echoed in the exception if the value is null
	 * @param <T> type of the value to check
	 * @return the given value
	 * @throws IllegalArgumentException if the value is null
	 */
	public static <T> T notNull(final T value, final String name) throws IllegalArgumentException {
		if (value == null) {
			throw new IllegalArgumentException("Value for [" + name + "] cannot be null.");
		}
		return value;
	}

	/**
	 * Validates that two values are not equal to each other via their {@link T#equals(Object)} methods.
	 * @param value1 first value to check
	 * @param value2 second value to check
	 * @param value1Name name of the value that will be echoed in the exception if the value is null
	 * @param value2Name name of the value that will be echoed in the exception if the value is null
	 * @param <T> type of the values to check
	 * @throws IllegalArgumentException if one of the values is null or {@link T#equals(Object)} returns true
	 */
	public static <T> void notEqual(final T value1, final T value2, final String value1Name, final String value2Name)
	throws IllegalArgumentException {
		notNull(value1, value1Name);
		notNull(value2, value2Name);

		if (value1.equals(value2)) {
			throw new IllegalArgumentException("Values for [" + value1Name + "] and [" + value2Name + "] cannot be equal.");
		}
	}

	/**
	 * Validates that a variable is a positive integer (i.e., greater than zero).
	 * @param value value to check
	 * @param name name of the value that will be echoed in the exception if the value is null
	 * @return the given value
	 * @throws IllegalArgumentException if the value is less than one
	 */
	public static int positive(final int value, final String name) throws IllegalArgumentException {
		if (value <= 0) {
			throw new IllegalArgumentException("Value for [" + name + "] must be positive: " + value);
		}
		return value;
	}

	/**
	 * Validates that a variable is a positive long (i.e., greater than zero).
	 * @param value value to check
	 * @param name name of the value that will be echoed in the exception if the value is null
	 * @return the given value
	 * @throws IllegalArgumentException if the value is less than one
	 */
	public static long positive(final long value, final String name) throws IllegalArgumentException {
		if (value <= 0) {
			throw new IllegalArgumentException("Value for [" + name + "] must be positive: " + value);
		}
		return value;
	}

	/**
	 * Validates that a variable is non-negative (i.e., zero or greater).
	 * @param value value to check
	 * @param name name of the value that will be echoed in the exception if the value is null
	 * @return the given value
	 * @throws IllegalArgumentException if the value is less than zero
	 */
	public static int nonNegative(final int value, final String name) throws IllegalArgumentException {
		if (value < 0) {
			throw new IllegalArgumentException("Value for [" + name + "] must be non-negative: " + value);
		}
		return value;
	}
}
