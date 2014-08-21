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
package com.pushinginertia.commons.core.math;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class KCombinationsTest {
	public static class TestPayload implements Combination.Payload<TestPayload> {
		private Set<Integer> vals;

		public TestPayload(final int... vals) {
			this.vals = new TreeSet<Integer>();
			for (final int val: vals) {
				this.vals.add(val);
			}
		}

		public TestPayload add(final TestPayload rhs) {
			final TestPayload p = new TestPayload();
			p.vals.addAll(this.vals);
			p.vals.addAll(rhs.vals);
			return p;
		}

		public void merge(final TestPayload rhs) {
			vals.addAll(rhs.vals);
		}

		public int size() {
			return vals.size();
		}

		public boolean containsValue(final int i) {
			return vals.contains(i);
		}
	}

	@Test
	public void test1() {
		final List<TestPayload> payloadList = new ArrayList<TestPayload>();
		payloadList.add(new TestPayload(800, 950));
		final KCombinations<TestPayload> nk = new KCombinations<TestPayload>(payloadList);
		Assert.assertEquals(1, nk.size());
		Assert.assertEquals(1, nk.rowCount());
		Assert.assertEquals(1, nk.rowSize(0));
		final TestPayload merged = nk.merge(new TestPayload());
		Assert.assertEquals(2, merged.size());
		Assert.assertTrue(merged.containsValue(800));
		Assert.assertTrue(merged.containsValue(950));
	}

	@Test
	public void test2() {
		final List<TestPayload> payloadList = new ArrayList<TestPayload>();
		payloadList.add(new TestPayload(800, 950));
		payloadList.add(new TestPayload(850, 1000, 1200));
		final KCombinations<TestPayload> nk = new KCombinations<TestPayload>(payloadList);
		Assert.assertEquals(3, nk.size());
		Assert.assertEquals(2, nk.rowCount());
		Assert.assertEquals(2, nk.rowSize(0));
		Assert.assertEquals(1, nk.rowSize(1));
		final TestPayload merged = nk.merge(new TestPayload());
		Assert.assertEquals(5, merged.size());
		Assert.assertTrue(merged.containsValue(800));
		Assert.assertTrue(merged.containsValue(850));
		Assert.assertTrue(merged.containsValue(950));
		Assert.assertTrue(merged.containsValue(1000));
		Assert.assertTrue(merged.containsValue(1200));
	}

	@Test
	public void test3() {
		final List<TestPayload> payloadList = new ArrayList<TestPayload>();
		payloadList.add(new TestPayload(800, 950));
		payloadList.add(new TestPayload(850, 1000, 1200));
		payloadList.add(new TestPayload(1200));
		final KCombinations<TestPayload> nk = new KCombinations<TestPayload>(payloadList);
		Assert.assertEquals(7, nk.size());
		Assert.assertEquals(3, nk.rowCount());
		Assert.assertEquals(3, nk.rowSize(0));
		Assert.assertEquals(3, nk.rowSize(1));
		Assert.assertEquals(1, nk.rowSize(2));
		final TestPayload merged = nk.merge(new TestPayload());
		Assert.assertEquals(5, merged.size());
		Assert.assertTrue(merged.containsValue(800));
		Assert.assertTrue(merged.containsValue(850));
		Assert.assertTrue(merged.containsValue(950));
		Assert.assertTrue(merged.containsValue(1000));
		Assert.assertTrue(merged.containsValue(1200));
	}

	@Test
	public void test4() {
		final List<TestPayload> payloadList = new ArrayList<TestPayload>();
		payloadList.add(new TestPayload(800, 950));
		payloadList.add(new TestPayload(850, 1000, 1200));
		payloadList.add(new TestPayload(1200));
		payloadList.add(new TestPayload(750));
		final KCombinations<TestPayload> nk = new KCombinations<TestPayload>(payloadList);
		Assert.assertEquals(15, nk.size());
		Assert.assertEquals(4, nk.rowCount());
		Assert.assertEquals(4, nk.rowSize(0));
		Assert.assertEquals(6, nk.rowSize(1));
		Assert.assertEquals(4, nk.rowSize(2));
		Assert.assertEquals(1, nk.rowSize(3));
		final TestPayload merged = nk.merge(new TestPayload());
		Assert.assertEquals(6, merged.size());
		Assert.assertTrue(merged.containsValue(750));
		Assert.assertTrue(merged.containsValue(800));
		Assert.assertTrue(merged.containsValue(850));
		Assert.assertTrue(merged.containsValue(950));
		Assert.assertTrue(merged.containsValue(1000));
		Assert.assertTrue(merged.containsValue(1200));
	}

	@Test
	public void test5() {
		final List<TestPayload> payloadList = new ArrayList<TestPayload>();
		payloadList.add(new TestPayload(800, 950));
		payloadList.add(new TestPayload(850, 1000, 1200));
		payloadList.add(new TestPayload(1200));
		payloadList.add(new TestPayload(750));
		payloadList.add(new TestPayload(800, 850));
		final KCombinations<TestPayload> nk = new KCombinations<TestPayload>(payloadList);
		Assert.assertEquals(31, nk.size());
		Assert.assertEquals(5, nk.rowCount());
		Assert.assertEquals(5, nk.rowSize(0));
		Assert.assertEquals(10, nk.rowSize(1));
		Assert.assertEquals(10, nk.rowSize(2));
		Assert.assertEquals(5, nk.rowSize(3));
		Assert.assertEquals(1, nk.rowSize(4));
		final TestPayload merged = nk.merge(new TestPayload());
		Assert.assertEquals(6, merged.size());
		Assert.assertTrue(merged.containsValue(750));
		Assert.assertTrue(merged.containsValue(800));
		Assert.assertTrue(merged.containsValue(850));
		Assert.assertTrue(merged.containsValue(950));
		Assert.assertTrue(merged.containsValue(1000));
		Assert.assertTrue(merged.containsValue(1200));
	}
}
