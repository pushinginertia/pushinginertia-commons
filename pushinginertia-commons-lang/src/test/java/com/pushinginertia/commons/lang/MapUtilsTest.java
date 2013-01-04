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
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtilsTest {
	private Map<String, String> m;

	@Before
	public void setUp() {
		m = new LinkedHashMap<String, String>();
		m.put("a", "1");
		m.put("b", "2");
	}

	@Test
	public void testToString() {
		final String s = MapUtils.toString(m, "\n");
		Assert.assertEquals("a=1\nb=2", s);
	}

	@Test
	public void toStringIndent() {
		final String s = MapUtils.toString(m, "\n", 4);
		Assert.assertEquals("    a=1\n    b=2", s);
	}
}
