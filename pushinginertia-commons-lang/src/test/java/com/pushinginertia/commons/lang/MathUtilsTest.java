package com.pushinginertia.commons.lang;

import junit.framework.TestCase;

public class MathUtilsTest extends TestCase {
	public void testIntegerDivisionRound() {
		assertEquals(0, MathUtils.integerDivisionRound(0, 7));
		assertEquals(0, MathUtils.integerDivisionRound(1, 7));
		assertEquals(0, MathUtils.integerDivisionRound(2, 7));
		assertEquals(0, MathUtils.integerDivisionRound(3, 7));
		assertEquals(1, MathUtils.integerDivisionRound(4, 7));
		assertEquals(1, MathUtils.integerDivisionRound(5, 7));
		assertEquals(1, MathUtils.integerDivisionRound(6, 7));
		assertEquals(1, MathUtils.integerDivisionRound(7, 7));
		assertEquals(1, MathUtils.integerDivisionRound(8, 7));
		assertEquals(1, MathUtils.integerDivisionRound(9, 7));
		assertEquals(1, MathUtils.integerDivisionRound(10, 7));
		assertEquals(2, MathUtils.integerDivisionRound(11, 7));
		assertEquals(2, MathUtils.integerDivisionRound(12, 7));
		assertEquals(2, MathUtils.integerDivisionRound(13, 7));
		assertEquals(2, MathUtils.integerDivisionRound(14, 7));
		assertEquals(2, MathUtils.integerDivisionRound(15, 7));
		assertEquals(2, MathUtils.integerDivisionRound(16, 7));
		assertEquals(2, MathUtils.integerDivisionRound(17, 7));

		assertEquals(1, MathUtils.integerDivisionRound(39, 30));
		assertEquals(1, MathUtils.integerDivisionRound(40, 30));
		assertEquals(1, MathUtils.integerDivisionRound(41, 30));
		assertEquals(1, MathUtils.integerDivisionRound(42, 30));
		assertEquals(1, MathUtils.integerDivisionRound(43, 30));
		assertEquals(1, MathUtils.integerDivisionRound(44, 30));
		assertEquals(2, MathUtils.integerDivisionRound(45, 30));
		assertEquals(2, MathUtils.integerDivisionRound(46, 30));
		assertEquals(2, MathUtils.integerDivisionRound(47, 30));
		assertEquals(2, MathUtils.integerDivisionRound(48, 30));
		assertEquals(2, MathUtils.integerDivisionRound(49, 30));
		assertEquals(2, MathUtils.integerDivisionRound(50, 30));
		assertEquals(2, MathUtils.integerDivisionRound(51, 30));
		assertEquals(2, MathUtils.integerDivisionRound(52, 30));
		assertEquals(2, MathUtils.integerDivisionRound(53, 30));
		assertEquals(2, MathUtils.integerDivisionRound(54, 30));
		assertEquals(2, MathUtils.integerDivisionRound(55, 30));
		assertEquals(2, MathUtils.integerDivisionRound(56, 30));
		assertEquals(2, MathUtils.integerDivisionRound(57, 30));
		assertEquals(2, MathUtils.integerDivisionRound(58, 30));
		assertEquals(2, MathUtils.integerDivisionRound(59, 30));
		assertEquals(2, MathUtils.integerDivisionRound(60, 30));

	}
}
