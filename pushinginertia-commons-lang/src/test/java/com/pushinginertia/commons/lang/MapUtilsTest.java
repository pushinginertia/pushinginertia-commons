package com.pushinginertia.commons.lang;

import junit.framework.TestCase;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtilsTest extends TestCase {
	public void testToString() {
		final Map<String, String> m = new LinkedHashMap<String, String>();
		m.put("a", "1");
		m.put("b", "2");

		final String s = MapUtils.toString(m, "\n");
		assertEquals("a=1\nb=2", s);
	}
}
