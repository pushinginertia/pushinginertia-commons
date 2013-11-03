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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class StringUtilsTest {
	@Test
	public void isNumeric() {
		Assert.assertTrue(StringUtils.isNumeric("999"));
		Assert.assertTrue(StringUtils.isNumeric("-999"));
		Assert.assertFalse(StringUtils.isNumeric(""));
		Assert.assertFalse(StringUtils.isNumeric(" 999"));
		Assert.assertFalse(StringUtils.isNumeric("-"));
		Assert.assertFalse(StringUtils.isNumeric("9x9"));
	}

	@Test
	public void repeat() {
		Assert.assertEquals("", StringUtils.repeat('x', 0));
		Assert.assertEquals("x", StringUtils.repeat('x', 1));
		Assert.assertEquals("  ", StringUtils.repeat(' ', 2));
		Assert.assertEquals("xxx", StringUtils.repeat('x', 3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void repeatErr() {
		StringUtils.repeat('x', -1);
	}

	@Test
	public void removeDoubleSpaces() {
		Assert.assertEquals("a b", StringUtils.removeDoubleSpaces("a      b"));
		Assert.assertEquals("a b cdef g", StringUtils.removeDoubleSpaces("a   b   cdef g"));
	}

	@Test
	public void firstNWords() {
		Assert.assertEquals("a b c", StringUtils.firstNWords("a b c d e f g h i j", 3, 100));
		Assert.assertEquals("a b", StringUtils.firstNWords("a b", 3, 100));
		Assert.assertEquals("word1 word2", StringUtils.firstNWords("word1 word2 word3 word4", 5, 15));
		Assert.assertEquals("123456789012345", StringUtils.firstNWords("12345678901234567890 word2 word3 word4", 5, 15));
		Assert.assertEquals("word1", StringUtils.firstNWords("word1 12345678901234567890 word3 word4", 5, 15));
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

	@Test
	public void truncate() {
		Assert.assertEquals("abc", StringUtils.truncate("abc", 4));
		Assert.assertEquals("abc", StringUtils.truncate("abc", 3));
		Assert.assertEquals("ab", StringUtils.truncate("abc", 2));
		Assert.assertEquals("a", StringUtils.truncate("abc", 1));
		Assert.assertEquals("", StringUtils.truncate("abc", 0));
	}

	@Test
	public void removeChars() {
		Assert.assertEquals("abc", StringUtils.removeChars("<abc>", new char[]{'<', '>'}));
		Assert.assertEquals("abc", StringUtils.removeChars("abc", new char[]{'<', '>'}));
	}

	@Test
	public void unicodeBlocksInString() {
		// a string with hangul, Japanese, Chinese, Latin
		final String s = "중국어 日本語 简化 ﾌﾗﾝｽ語 abc";
		Assert.assertEquals(20, s.length());
		final Set<Character.UnicodeBlock> set = StringUtils.unicodeBlocksInString(s);
		Assert.assertEquals(4, set.size());
		Assert.assertTrue(set.contains(Character.UnicodeBlock.BASIC_LATIN));
		Assert.assertTrue(set.contains(Character.UnicodeBlock.HANGUL_SYLLABLES));
		Assert.assertTrue(set.contains(Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS));
		Assert.assertTrue(set.contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS));
	}

	@Test
	public void isLatin() {
		Assert.assertTrue(StringUtils.isLatin(""));
		Assert.assertTrue(StringUtils.isLatin("abc"));
		Assert.assertFalse(StringUtils.isLatin("중국어"));
	}
}
