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

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetUtilsTest {
	@Test
	public void intersection() {
		final Set<Integer> a = new HashSet<Integer>(Arrays.asList(1, 2, 3));
		final Set<Integer> b = new HashSet<Integer>(Arrays.asList(2,3,4,5,6));
		final Set<Integer> intersection = SetUtils.intersection(a, b);
		Assert.assertEquals(2, intersection.size());
		Assert.assertTrue(intersection.contains(2));
		Assert.assertTrue(intersection.contains(3));
	}

	@Test
	public void minus() {
		final Set<Integer> a = new HashSet<Integer>(Arrays.asList(1,2,3));
		final Set<Integer> b = new HashSet<Integer>(Arrays.asList(2,3,4,5,6));
		final Set<Integer> minus = SetUtils.minus(a, b);
		Assert.assertEquals(1, minus.size());
		Assert.assertTrue(minus.contains(1));
	}
}
