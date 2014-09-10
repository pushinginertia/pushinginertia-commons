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

import com.pushinginertia.commons.lang.logging.Timer;
import junit.framework.TestCase;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class BoundedMinHeapTest extends TestCase {
	public void testFindInsertIndex() {
		final int[] ranks = new int[]{0,0,2,2,4,5,6,7,9};
		assertEquals(2, BoundedMinHeap.findInsertIndex(0, ranks, ranks.length - 1));
		assertEquals(2, BoundedMinHeap.findInsertIndex(1, ranks, ranks.length - 1));
		assertEquals(4, BoundedMinHeap.findInsertIndex(2, ranks, ranks.length - 1));
		assertEquals(4, BoundedMinHeap.findInsertIndex(3, ranks, ranks.length - 1));
		assertEquals(5, BoundedMinHeap.findInsertIndex(4, ranks, ranks.length - 1));
		assertEquals(6, BoundedMinHeap.findInsertIndex(5, ranks, ranks.length - 1));
		assertEquals(7, BoundedMinHeap.findInsertIndex(6, ranks, ranks.length - 1));
		assertEquals(8, BoundedMinHeap.findInsertIndex(7, ranks, ranks.length - 1));
		assertEquals(8, BoundedMinHeap.findInsertIndex(8, ranks, ranks.length - 1));
	}

	public void testFindInsertIndexReverse() {
		final int[] ranks = new int[]{9,7,6,5,4,2,2,0,0};
		assertEquals(6, BoundedMinHeap.findInsertIndexReverseOrder(0, ranks, 0, ranks.length - 1));
		assertEquals(6, BoundedMinHeap.findInsertIndexReverseOrder(1, ranks, 0, ranks.length - 1));
		assertEquals(4, BoundedMinHeap.findInsertIndexReverseOrder(2, ranks, 0, ranks.length - 1));
		assertEquals(4, BoundedMinHeap.findInsertIndexReverseOrder(3, ranks, 0, ranks.length - 1));
		assertEquals(3, BoundedMinHeap.findInsertIndexReverseOrder(4, ranks, 0, ranks.length - 1));
		assertEquals(2, BoundedMinHeap.findInsertIndexReverseOrder(5, ranks, 0, ranks.length - 1));
		assertEquals(1, BoundedMinHeap.findInsertIndexReverseOrder(6, ranks, 0, ranks.length - 1));
		assertEquals(0, BoundedMinHeap.findInsertIndexReverseOrder(7, ranks, 0, ranks.length - 1));
		assertEquals(0, BoundedMinHeap.findInsertIndexReverseOrder(8, ranks, 0, ranks.length - 1));
	}

	public void testToListAscendingRank() {
		testToListAscendingRank(20, 5000);
		testToListAscendingRank(200, 5000);
		testToListAscendingRank(2000, 5000);
	}

	private void testToListAscendingRank(int heapSize, int numberRange) {
		final BoundedMinHeap<Integer> q = buildRandomQueue(heapSize, numberRange);
		final Timer timer = new Timer();
		final List<Integer> list = q.toListAscendingRank();
		assertEquals(heapSize, list.size());
		for (int i = 1; i < list.size(); i++) {
			assertTrue(list.get(i - 1) <= list.get(i));
		}
		System.out.println("heapSize=" + heapSize + ", numberRange=" + numberRange + ", time=" + timer.elapsedMs());
	}

	public void testToListDescendingRank() {
		testToListDescendingRank(20, 5000);
		testToListDescendingRank(200, 5000);
		testToListDescendingRank(2000, 5000);
	}

	private void testToListDescendingRank(int heapSize, int numberRange) {
		final BoundedMinHeap<Integer> q = buildRandomQueue(heapSize, numberRange);
		final Timer timer = new Timer();
		final List<Integer> list = q.toListDescendingRank();
		assertEquals(heapSize, list.size());
		for (int i = 1; i < list.size(); i++) {
			assertTrue(list.get(i - 1) >= list.get(i));
		}
		System.out.println("heapSize=" + heapSize + ", numberRange=" + numberRange + ", time=" + timer.elapsedMs());
	}

	private BoundedMinHeap<Integer> buildRandomQueue(int heapSize, int numberRange) {
		final BoundedMinHeap<Integer> q = new BoundedMinHeap<Integer>(heapSize);
		final Random rand = new SecureRandom();
		for (int i = 0; i < numberRange; i++) {
			final int item = rand.nextInt(numberRange);
			q.add(item, item);
		}
		return q;
	}
}
