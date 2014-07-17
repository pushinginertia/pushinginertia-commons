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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Logic that manipulates Java lists.
 */
public final class ListUtils {
	private ListUtils() {}

	/**
	 * Creates a sequentially numbered list for a given inclusive range.
	 * @param min Lowest number to place in the list.
	 * @param max Highest number to place in the list.
	 * @return List of size max - min + 1.
	 */
	public static List<Integer> sequentialList(final int min, final int max) {
		if (min > max) {
			throw new IllegalArgumentException("Min cannot be greater than max: " + min + " > " + max);
		}
		final List<Integer> l = new ArrayList<Integer>(max - min + 1);
		for (int i = min; i <= max; i++) {
			l.add(i);
		}
		return l;
	}

	/**
	 * Returns an unmodifiable List containing the primitive integers given in an array.
	 * @param array Array of ints to transform into a List object.
	 * @return Unmodifiable list.
	 */
	public static List<Integer> intArrayToList(final int[] array) {
		ValidateAs.notNull(array, "array");
		final List<Integer> list = new ArrayList<Integer>(array.length);
		for (final int item: array) {
			list.add(item);
		}
		return Collections.unmodifiableList(list);
	}

	/**
	 * Sorts a collection of objects into an indexed list. For example, a {@link java.util.Set} could be passed in and
	 * its members would be sorted into a list.
	 * @param c collection of objects
	 * @param <T> type of the objects in the input collection
	 * @return sorted list
	 */
	public static <T extends Comparable<? super T>> List<T> asSortedList(final Collection<T> c) {
		final List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}

	/**
	 * Clones a list with a given element removed. The output list will be the same size as the input list or will contain
	 * one less item.
	 * @param list list to clone
	 * @param itemToRemove item to remove
	 * @param <T> type of the items in the list
	 * @return cloned list with the given item removed (if that item exists in the source list)
	 */
	public static <T> List<T> cloneAndRemove(final List<T> list, final T itemToRemove) {
		ValidateAs.notNull(list, "list");
		ValidateAs.notNull(itemToRemove, "itemToRemove");
		final List<T> clonedList = new ArrayList<T>(list);
		clonedList.remove(itemToRemove);
		return clonedList;
	}

	/**
	 * Produces a random sample from a list of items using Floyd's algorithm. The returned set retains the order in
	 * which the items were inserted, which means that iterating on the set will give the items in a random order.
	 * @param list source list of items
	 * @param m number of items to
	 * @param <T> type of the items in the list
	 * @return subset of the list of the given size
	 * @throws IllegalArgumentException if m is greater than the size of the list
	 * @see <a href="http://eyalsch.wordpress.com/2010/04/01/random-sample/">http://eyalsch.wordpress.com/2010/04/01/random-sample/</a>
	 */
	public static <T> Set<T> randomSampleFloyd(final List<T> list, final int m) throws IllegalArgumentException {
		// input validation
		ValidateAs.notNull(list, "list");
		final int listSize = list.size();
		if (m > listSize) {
			throw new IllegalArgumentException("Value of m [" + m + "] is greater than the list size [" + listSize + "].");
		}

		final Random rnd = new SecureRandom();
		final Set<T> res = new LinkedHashSet<T>(m);
		for (int i = listSize - m; i < listSize; i++) {
			final int pos = rnd.nextInt(i + 1);
			final T item = list.get(pos);
			if (res.contains(item)) {
				res.add(list.get(i));
			} else {
				res.add(item);
			}
		}
		return res;
	}
}
