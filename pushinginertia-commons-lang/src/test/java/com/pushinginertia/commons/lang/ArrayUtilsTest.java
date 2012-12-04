package com.pushinginertia.commons.lang;

import junit.framework.TestCase;

public class ArrayUtilsTest extends TestCase {
	public void testPrepend() {
		final String[] in = {"a","b"};
		final String[] out = ArrayUtils.prepend("item", in);
		assertEquals(3, out.length);
		assertEquals("item", out[0]);
		assertEquals("a", out[1]);
		assertEquals("b", out[2]);
	}
}
