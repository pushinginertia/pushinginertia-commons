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
}
