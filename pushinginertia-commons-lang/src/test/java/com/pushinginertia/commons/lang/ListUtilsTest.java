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

import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ListUtilsTest {

	private static final int QUICKSELECT_LIST_MAX_SIZE = 2000;

	public enum SomeEnumeration {
		A,
		C,
		B,
		D
	}

	public boolean hasRepeatedDigit(int i) {
		int quotient = i;
		int remainder;
		boolean[] digits = {false,false,false,false,false,false,false,false,false,false};
		while (quotient > 0) {
			remainder = quotient % 10;
			quotient = quotient / 10;
			if (digits[remainder]) {
				return true;
			}
			digits[remainder] = true;
		}
		return false;
	}

	@Test
	public void testHasRepeatingDigit() {
		Assert.assertEquals(true, hasRepeatedDigit(99));
		Assert.assertEquals(true, hasRepeatedDigit(77));
		Assert.assertEquals(true, hasRepeatedDigit(162343));
		Assert.assertEquals(true, hasRepeatedDigit(237823));
		Assert.assertEquals(false, hasRepeatedDigit(12345));
	}

	@Test
	public void sequentialList() {
		final List<Integer> list = ListUtils.sequentialList(1, 5);
		Assert.assertEquals(5, list.size());
		Assert.assertEquals(1, (int)list.get(0));
		Assert.assertEquals(2, (int)list.get(1));
		Assert.assertEquals(3, (int)list.get(2));
		Assert.assertEquals(4, (int)list.get(3));
		Assert.assertEquals(5, (int)list.get(4));
	}

	@Test
	public void toListWithMax() {
		final List<Integer> in = Arrays.asList(1, 2, 3, 4, 5);
		Assert.assertEquals(3, ListUtils.toListWithMax(in, 3).size());
		Assert.assertEquals(1, (int)ListUtils.toListWithMax(in, 3).get(0));
		Assert.assertEquals(2, (int)ListUtils.toListWithMax(in, 3).get(1));
		Assert.assertEquals(3, (int)ListUtils.toListWithMax(in, 3).get(2));
	}

	@Test
	public void intArrayToList() {
		final int[] array = {1,2,3,4};
		final List<Integer> list = ListUtils.intArrayToList(array);
		Assert.assertEquals(4, list.size());
		Assert.assertEquals(1, (int)list.get(0));
		Assert.assertEquals(2, (int)list.get(1));
		Assert.assertEquals(3, (int)list.get(2));
		Assert.assertEquals(4, (int)list.get(3));
	}

	@Test
	public void asSortedList() {
		final Set<SomeEnumeration> s =
				new HashSet<SomeEnumeration>(Arrays.asList(SomeEnumeration.A, SomeEnumeration.B, SomeEnumeration.C, SomeEnumeration.D));
		final List<SomeEnumeration> l = ListUtils.asSortedList(s);
		Assert.assertNotNull(l);
		Assert.assertEquals(4, l.size());
		Assert.assertEquals(SomeEnumeration.A, l.get(0));
		Assert.assertEquals(SomeEnumeration.C, l.get(1));
		Assert.assertEquals(SomeEnumeration.B, l.get(2));
		Assert.assertEquals(SomeEnumeration.D, l.get(3));
	}

	@Test
	public void cloneAndRemove() {
		final List<String> sourceList = Collections.unmodifiableList(Arrays.asList("a", "b", "c", "d"));

		final List la = ListUtils.cloneAndRemove(sourceList, "a");
		Assert.assertEquals(3, la.size());
		Assert.assertEquals(4, sourceList.size());
		Assert.assertFalse(la.contains("a"));

		final List le = ListUtils.cloneAndRemove(sourceList, "e");
		Assert.assertEquals(4, le.size());
		Assert.assertEquals(4, sourceList.size());
	}

	@Test
	public void randomSampleFloyd() {
		final List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);

		for (int i = 0; i <= list.size(); i++) {
			final Set<Integer> randomValues = ListUtils.randomSampleFloyd(list, i);
			Assert.assertEquals(i, randomValues.size());
			for (final Integer randomValue: randomValues) {
				Assert.assertTrue(randomValue >= 1);
				Assert.assertTrue(randomValue <= list.get(list.size() - 1));
			}
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void randomSampleFloydFail() {
		final List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30);
		ListUtils.randomSampleFloyd(list, 31);
	}

	public static class DefaultComparator<T extends Comparable<T>> implements Comparator<T> {
		@Override
		public int compare(final T o1, final T o2) {
			return o1.compareTo(o2);
		}
	}

	@Test
	public void quickselect() {
		final Random rand = new SecureRandom();

		// run this test on 20 randomly generated lists
		for (int pass = 0; pass < 20; pass++) {
			final int size = rand.nextInt(QUICKSELECT_LIST_MAX_SIZE) + 1;
			final List<Integer> list = new ArrayList<Integer>(size);
			for (int i = 0; i < size; i++) {
				list.add(rand.nextInt(QUICKSELECT_LIST_MAX_SIZE * 2));  // we want some collisions with same values
			}

			// sort the list to compare the identified value against the expected value
			final List<Integer> sortedList = new ArrayList<Integer>(list);
			Collections.sort(sortedList);

			// assert that selection works for every value of k in the list
			for (int k = 0; k < size; k++) {
				// make a copy so we always work with the same source list
				final List<Integer> listCopy = new ArrayList<Integer>(list);
				final Integer kthValue;
				if (pass % 2 == 0) {
					// every second pass, use the comparator instead of natural ordering
					kthValue = ListUtils.quickselect(listCopy, k, new DefaultComparator<Integer>());
				} else {
					kthValue = ListUtils.quickselect(listCopy, k);
				}
				Assert.assertEquals(kthValue, sortedList.get(k));
			}
		}
	}
}
