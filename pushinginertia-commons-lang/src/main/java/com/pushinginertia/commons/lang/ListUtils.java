/* Copyright (c) 2011-2014 Pushing Inertia
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
import java.util.Comparator;
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

	public static List<Integer> toListWithMax(final Collection<Integer> collection, final int max) {
		ValidateAs.notNull(collection, "collection");
		final List<Integer> list = new ArrayList<>();
		for (final Integer i: collection) {
			if (i <= max) {
				list.add(i);
			}
		}
		return list;
	}

	/**
	 * Returns an unmodifiable List containing the primitive integers given in an array.
	 * @param array Array of ints to transform into a List object.
	 * @return Unmodifiable list.
	 */
	public static List<Integer> intArrayToList(final int[] array) {
		ValidateAs.notNull(array, "array");
		final List<Integer> list = new ArrayList<>(array.length);
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
	 * @param m number of items to select
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

	/**
	 * Swaps two elements in a list.
	 * @param list List to mutate.
	 * @param index1 First index.
	 * @param index2 Second index.
	 * @param <T> Type of the elements in the list.
	 */
	private static <T> void swap(final List<T> list, final int index1, final int index2) {
		if (index1 != index2) {
			final T value = list.get(index1);
			list.set(index1, list.get(index2));
			list.set(index2, value);
		}
	}

	/**
	 * An implementation of the partition step in a quicksort.
	 * @param list Entire list to partition.
	 * @param startIndex Starting index of the sublist to partition.
	 * @param endIndex Ending index of the sublist to partition.
	 * @param pivotIndex Index of the pivot element.
	 * @param <T> Type of the items in the list.
	 * @return The new index that the pivot item has been moved to.
	 */
	public static <T extends Comparable<T>> int partition(
			final List<T> list,
			final int startIndex,
			final int endIndex,
			final int pivotIndex) {
		return partition(list, null, startIndex, endIndex, pivotIndex);
	}

	/**
	 * An implementation of the partition step in a quicksort.
	 * @param list Entire list to partition.
	 * @param comparator The comparator to determine the order of the list. A null value indicates that the elements'
	 * natural ordering should be used.
	 * @param startIndex Starting index of the sublist to partition.
	 * @param endIndex Ending index of the sublist to partition.
	 * @param pivotIndex Index of the pivot element.
	 * @param <T> Type of the items in the list. If comparator is null, this must implement
	 * {@link java.lang.Comparable}.
	 * @return The new index that the pivot item has been moved to.
	 */
	public static <T> int partition(
			final List<T> list,
			final Comparator<T> comparator,
			final int startIndex,
			final int endIndex,
			final int pivotIndex) {
		if (startIndex > endIndex) {
			throw new IllegalArgumentException(
					"startIndex " + startIndex + " cannot be greater than endIndex " + endIndex);
		}
		if (pivotIndex < startIndex || pivotIndex > endIndex) {
			throw new IllegalArgumentException(
					"pivotIndex " + pivotIndex + " is not in the range [" + startIndex + ".." + endIndex + "].");
		}
		if (startIndex == endIndex) {
			// nothing to do
			return pivotIndex;
		}

		final T pivotValue = list.get(pivotIndex);
		swap(list, startIndex, pivotIndex);  // move the pivot to the start of the sublist
		int left = startIndex;
		int right = endIndex;
		while (left < right) {
			while (left <= right && compare(comparator, list.get(left), pivotValue) <= 0) {
				// move left to the right until an item > pivotValue is found
				left++;
			}
			while (left <= right && compare(comparator, list.get(right), pivotValue) > 0) {
				// move right to the left until an item <= pivotValue is found
				right--;
			}
			if (left < right) {
				swap(list, left, right);
			}
		}
		list.set(startIndex, list.get(right));
		list.set(right, pivotValue);
		return right;
	}

	private static <T> int compare(final Comparator<T> comparator, final T o1, final T o2) {
		if (comparator != null) {
			return comparator.compare(o1, o2);
		}
		if (!(o1 instanceof Comparable && o2 instanceof Comparable)) {
			throw new IllegalArgumentException(
					"Input must implement Comparable interface or a comparator must be given.");
		}
		return ((Comparable<T>)o1).compareTo(o2);
	}

	/**
	 * Finds the kth largest value in a list of unsorted items. This is useful for identifying the median in a list
	 * where k = n/2 and n is the size of the list. The implementation is based on the quickselect algorithm by Floyd
	 * and Rivest. The input list is mutated and items are moved. Complexity is O(n) on average.
	 * @param list Source list of items (order of items will be mutated).
	 * @param k Indicates the kth item to select.
	 * @param <T> Type of the items in the list.
	 * @return kth item.
	 * @see <a href="http://www.stat.cmu.edu/~ryantibs/papers/median.pdf">http://www.stat.cmu.edu/~ryantibs/papers/median.pdf</a>
	 */
	public static <T extends Comparable<T>> T quickselect(final List<T> list, final int k) {
		return quickselect(list, k, null);
	}

	/**
	 * Finds the kth largest value in a list of unsorted items. This is useful for identifying the median in a list
	 * where k = n/2 and n is the size of the list. The implementation is based on the quickselect algorithm by Floyd
	 * and Rivest. The input list is mutated and items are moved. Complexity is O(n) on average.
	 * @param list Source list of items (order of items will be mutated).
	 * @param k Indicates the kth item to select.
	 * @param comparator The comparator to determine the order of the list. A null value indicates that the elements'
	 * natural ordering should be used.
	 * @param <T> Type of the items in the list.
	 * @return kth item.
	 * @see <a href="http://www.stat.cmu.edu/~ryantibs/papers/median.pdf">http://www.stat.cmu.edu/~ryantibs/papers/median.pdf</a>
	 */
	public static <T> T quickselect(
			final List<T> list,
			final int k,
			final Comparator<T> comparator) {
		ValidateAs.indexInList(list, k, "list");

		int start = 0;
		int end = list.size() - 1;
		final Random rand = new SecureRandom();

		while (start < end) {
			final int randPivotIndex = rand.nextInt(end - start + 1) + start;  // select a random item within the range
			final int pivotIndex = partition(list, comparator, start, end, randPivotIndex);
			if (pivotIndex == k) {
				return list.get(k);
			}
			if (pivotIndex < k) {
				start = pivotIndex + 1;
			} else {
				end = pivotIndex - 1;
			}
		}

		return list.get(start);
	}
}
