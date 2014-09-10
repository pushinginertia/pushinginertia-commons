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
package com.pushinginertia.commons.collect;

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.lang.Tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Extends {@link java.util.PriorityQueue} into a min-heap with a maximum size. Each inserted element must also have an
 * integer rank associated with it, which is used to maintain the heap. When the heap reaches its maximum size, the
 * element with the lowest rank (the root) is removed when an item is added that is >= the currently highest ranked
 * element.
 */
public class BoundedMinHeap<T extends Serializable> {
	private final PriorityQueue<ItemWithRank<T>> queue;
	private final int maxSize;
	private Integer maxRank = null;

	private static class ItemWithRank<T extends Serializable> extends Tuple2<T, Integer>
			implements Comparable<ItemWithRank<T>> {
		private static final long serialVersionUID = 1L;

		private ItemWithRank(final T item, final Integer score) {
			super(item, score);
		}

		@SuppressWarnings("NullableProblems")
		public int compareTo(final ItemWithRank<T> rhs) {
			return getV2().compareTo(rhs.getV2());
		}
	}

	public BoundedMinHeap(final int size) {
		this.queue = new PriorityQueue<ItemWithRank<T>>(size);
		this.maxSize = size;
	}

	/**
	 * Adds an item with a given rank. When the heap reaches its maximum size, the element with the lowest rank (the
	 * root) is removed if the given rank is >= the currently highest ranked element.
	 * @param item Item to add to the heap.
	 * @param rank Rank associated with the item.
	 */
	public void add(final T item, final Integer rank) {
		ValidateAs.notNull(rank, "rank");
		final ItemWithRank<T> itemWithRank = new ItemWithRank<T>(item, rank);

		if (queue.size() < maxSize) {
			if (maxRank == null) {
				maxRank = rank;
			} else if (maxRank.compareTo(rank) < 0) {
				maxRank = rank;
			}
			queue.add(itemWithRank);
			return;
		}

		final int result = maxRank.compareTo(rank);
		if (result <= 0) {
			maxRank = rank;
			queue.poll();  // remove the root, which is the min value that we no longer want
			queue.add(itemWithRank);
		}
	}

	/**
	 * Returns the number of elements curently stored in the min-heap.
	 * @return Number of elements.
	 */
	public int size() {
		return queue.size();
	}

	/**
	 * Identifies the index to insert a key into a sorted array. The search index is bounded between 0 and maxIndex,
	 * inclusive. The returned index is the insert position for the given key, meaning that elements from the insert
	 * position and onward must be shifted over. If the key to insert is >= the int at maxIndex, then maxIndex is
	 * returned.
	 * @param key Key to insert.
	 * @param arr Array to search.
	 * @param maxIndex Upper bound on the array.
	 * @return Index to insert the key into.
	 */
	static int findInsertIndex(final int key, final int[] arr, final int maxIndex) {
		int lo = 0;
		int hi = maxIndex;
		while (lo < hi) {
			int k = (lo + hi) / 2;
			if (arr[k] <= key) {
				lo = k + 1;
			} else {
				hi = k;
			}
		}
		return lo;
	}

	/**
	 * Identifies the index to insert a key into an array sorted in reverse order. The search index is bounded between
	 * loIndex and hiIndex, inclusive. The returned index is the insert position for the given key, meaning that
	 * elements from the insert position and onward must be shifted over. If the key to insert is >= the int at
	 * maxIndex, then maxIndex is returned.
	 * @param key Key to insert.
	 * @param arr Array to search.
	 * @param loIndex Lower bound on the array.
	 * @param hiIndex Upper bound on the array.
	 * @return Index to insert the key into.
	 */
	static int findInsertIndexReverseOrder(final int key, final int[] arr, final int loIndex, final int hiIndex) {
		int lo = loIndex;
		int hi = hiIndex;
		while (lo < hi) {
			int k = hi - ((hi - lo) / 2);
			if (arr[k] > key) {
				lo = k;
			} else {
				hi = k - 1;
			}
		}
		return lo;
	}

	/**
	 * Converts the items within the heap into a sorted list ordered by descending rank. Internally, an insertion sort
	 * is performed since a min-heap has the property of being close to being sorted. This is generally an O(n)
	 * operation.
	 * @return List of items sorted by descending rank.
	 */
	public List<T> toListDescendingRank() {
		final int size = queue.size();
		final List<T> l = new ArrayList<T>(size);
		for (int i = 0; i < size - 1; i++) {
			l.add(null);
		}
		final int[] ranks = new int[size];
		int len = size;
		T item;
		int rank;
		for (final ItemWithRank<T> itemWithRank: queue) {
			item = itemWithRank.getV1();
			rank = itemWithRank.getV2();
			if (len == size) {
				l.add(item);
				ranks[len - 1] = rank;
			} else {
				if (rank >= ranks[len]) {
					l.set(len - 1, item);
					ranks[len - 1] = rank;
				} else {
					// insertion sort
					int insertIndex = findInsertIndexReverseOrder(rank, ranks, len, size - 1);
					for (int i = len - 1; i < insertIndex; i++) {
						ranks[i] = ranks[i + 1];
						l.set(i, l.get(i + 1));
					}
					ranks[insertIndex] = rank;
					l.set(insertIndex, item);
				}
			}
			len--;
		}
		return l;
	}

	/**
	 * Converts the items within the heap into a sorted list ordered by ascending rank. Internally, an insertion sort
	 * is performed since a min-heap has the property of being close to being sorted. This is generally an O(n)
	 * operation.
	 * @return List of items sorted by ascending rank.
	 */
	public List<T> toListAscendingRank() {
		final List<T> l = new ArrayList<T>(queue.size());
		final int[] ranks = new int[queue.size()];
		int len = 0;
		T item;
		int rank;
		for (final ItemWithRank<T> itemWithRank: queue) {
			item = itemWithRank.getV1();
			rank = itemWithRank.getV2();
			if (len == 0) {
				l.add(item);
				ranks[0] = rank;
			} else {
				if (ranks[len - 1] <= rank) {
					l.add(item);
					ranks[len] = rank;
				} else {
					// insertion sort
					int insertIndex = findInsertIndex(rank, ranks, len - 1);
					ranks[len] = ranks[len - 1];
					l.add(l.get(len - 1));
					for (int i = len - 1; i > insertIndex; i--) {
						ranks[i] = ranks[i - 1];
						l.set(i, l.get(i - 1));
					}
					ranks[insertIndex] = rank;
					l.set(insertIndex, item);
				}
			}
			len++;
		}
		return l;
	}
}
