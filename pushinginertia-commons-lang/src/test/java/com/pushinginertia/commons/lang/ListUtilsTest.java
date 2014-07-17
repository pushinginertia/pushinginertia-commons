/* Copyright (c) 2011-2012 Pushing Inertia
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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtilsTest {
	public enum SomeEnumeration {
		A,
		C,
		B,
		D
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
}
