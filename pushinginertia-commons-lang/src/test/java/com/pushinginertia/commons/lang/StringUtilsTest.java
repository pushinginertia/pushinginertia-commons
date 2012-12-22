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

import java.util.Map;
import java.util.TreeMap;

public class StringUtilsTest {
	@Test
	public void firstNWords() {
		Assert.assertEquals("a b c", StringUtils.firstNWords("a b c d e f g h i j", 3));
		Assert.assertEquals("a b", StringUtils.firstNWords("a b", 3));
	}

	@Test
	public void stringToMap() {
		final Map<String, String> m = StringUtils.stringToMap("a=b&&&c=d&&e&f&&=g&&&&&&&h=i\nii&j=");
		Assert.assertEquals(5, m.size());
		Assert.assertTrue(m.containsKey("a"));
		Assert.assertEquals("b&", m.get("a"));
		Assert.assertTrue(m.containsKey("c"));
		Assert.assertEquals("d&e", m.get("c"));
		Assert.assertTrue(m.containsKey("f&"));
		Assert.assertEquals("g&&&", m.get("f&"));
		Assert.assertTrue(m.containsKey("h"));
		Assert.assertEquals("i\nii", m.get("h"));
		Assert.assertTrue(m.containsKey("j"));
		Assert.assertNull(m.get("j"));
	}

	@Test
	public void mapToString() {
		Map<String, String> m = new TreeMap<String, String>();
		m.put("a", "b&");
		m.put("c", "d&e");
		m.put("f&", "g&&&");
		m.put("h", "i\nii");
		Assert.assertEquals("a=b&&&c=d&&e&f&&=g&&&&&&&h=i\nii", StringUtils.mapToString(m));
	}

	@Test
	public void newlineStringToList() {
		Assert.assertEquals(3, StringUtils.newlineStringToList("a\nb\nc", 0).size());
		Assert.assertEquals(2, StringUtils.newlineStringToList("a\nb\nc", 2).size());
		Assert.assertEquals("a", StringUtils.newlineStringToList("   a  \n b \t\nc  \t", 0).get(0));
		Assert.assertEquals("b", StringUtils.newlineStringToList("   a  \n b \t\nc  \t", 0).get(1));
		Assert.assertEquals("c", StringUtils.newlineStringToList("   a  \n b \t\nc  \t", 0).get(2));
		Assert.assertEquals(3, StringUtils.newlineStringToList("a\n    \nb\nc", 0).size());
	}

	@Test
	public void addTrailingCharIfMissing() {
		Assert.assertEquals("abc/", StringUtils.addTrailingCharIfMissing("abc", '/'));
		Assert.assertEquals("abc/", StringUtils.addTrailingCharIfMissing("abc/", '/'));
	}

	@Test
	public void stripTrailingCharIfPresent() {
		Assert.assertEquals("abc", StringUtils.stripTrailingCharIfPresent("abc", '/'));
		Assert.assertEquals("abc", StringUtils.stripTrailingCharIfPresent("abc/", '/'));
	}

	@Test
	public void replaceAllCaseInsensitive() {
		Assert.assertEquals("DEF DEF DEF", StringUtils.replaceAllCaseInsensitive("abc ABC Abc", "Abc", "DEF"));
		Assert.assertEquals("DEF\nDEF DEF", StringUtils.replaceAllCaseInsensitive("abc\nABC Abc", "Abc", "DEF"));
	}
}
