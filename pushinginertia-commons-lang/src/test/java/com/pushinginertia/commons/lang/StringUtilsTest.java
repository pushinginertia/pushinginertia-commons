package com.pushinginertia.commons.lang;

import junit.framework.TestCase;

import java.util.Map;
import java.util.TreeMap;

public class StringUtilsTest extends TestCase {
	public void testFirstNWords() {
		assertEquals("a b c", StringUtils.firstNWords("a b c d e f g h i j", 3));
		assertEquals("a b", StringUtils.firstNWords("a b", 3));
	}

	public void testStringToMap() {
		final Map<String, String> m = StringUtils.stringToMap("a=b&&&c=d&&e&f&&=g&&&&&&&h=i\nii&j=");
		assertEquals(5, m.size());
		assertTrue(m.containsKey("a"));
		assertEquals("b&", m.get("a"));
		assertTrue(m.containsKey("c"));
		assertEquals("d&e", m.get("c"));
		assertTrue(m.containsKey("f&"));
		assertEquals("g&&&", m.get("f&"));
		assertTrue(m.containsKey("h"));
		assertEquals("i\nii", m.get("h"));
		assertTrue(m.containsKey("j"));
		assertNull(m.get("j"));
	}

	public void testMapToString() {
		Map<String, String> m = new TreeMap<String, String>();
		m.put("a", "b&");
		m.put("c", "d&e");
		m.put("f&", "g&&&");
		m.put("h", "i\nii");
		assertEquals("a=b&&&c=d&&e&f&&=g&&&&&&&h=i\nii", StringUtils.mapToString(m));
	}

	public void testNewlineStringToList() {
		assertEquals(3, StringUtils.newlineStringToList("a\nb\nc", 0).size());
		assertEquals(2, StringUtils.newlineStringToList("a\nb\nc", 2).size());
		assertEquals("a", StringUtils.newlineStringToList("   a  \n b \t\nc  \t", 0).get(0));
		assertEquals("b", StringUtils.newlineStringToList("   a  \n b \t\nc  \t", 0).get(1));
		assertEquals("c", StringUtils.newlineStringToList("   a  \n b \t\nc  \t", 0).get(2));
		assertEquals(3, StringUtils.newlineStringToList("a\n    \nb\nc", 0).size());
	}

	public void testAddTrailingCharIfMissing() {
		assertEquals("abc/", StringUtils.addTrailingCharIfMissing("abc", '/'));
		assertEquals("abc/", StringUtils.addTrailingCharIfMissing("abc/", '/'));
	}

	public void testStripTrailingCharIfPresent() {
		assertEquals("abc", StringUtils.stripTrailingCharIfPresent("abc", '/'));
		assertEquals("abc", StringUtils.stripTrailingCharIfPresent("abc/", '/'));
	}
}
