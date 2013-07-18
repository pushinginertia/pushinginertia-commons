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

import java.util.HashSet;
import java.util.Set;

/**
 * Abstracts common logic on sets.
 */
public final class SetUtils {
	/**
	 * Performs an intersection of two sets, producing a new set without mutating the inputs.
	 * @param a first set
	 * @param b second set
	 * @param <T> type of the elements in each set
	 * @return new set containing the intersection of the two input sets
	 */
	public static <T> Set<T> intersection(final Set<T> a, final Set<T> b) {
		ValidateAs.notNull(a, "a");
		ValidateAs.notNull(b, "b");

		final Set<T> c = new HashSet<T>(a);
		c.retainAll(b);
		return c;
	}

	/**
	 * Subtracts set B from set A, producing a new set without mutating the inputs.
	 * @param a set to subtract from
	 * @param b set to subtract
	 * @param <T> type of the elements in each set
	 * @return new set containing the items in set A but not set B
	 */
	public static <T> Set<T> minus(final Set<T> a, final Set<T> b) {
		final Set<T> c = new HashSet<T>(a);
		c.removeAll(b);
		return c;
	}
}
