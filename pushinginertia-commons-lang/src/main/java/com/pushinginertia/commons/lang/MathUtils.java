package com.pushinginertia.commons.lang;

/**
 * Reusable math functionality.
 */
public class MathUtils {
	/**
	 * Divides a by b and rounds the result without using floating point arithmetic.
	 * @param a numerator
	 * @param b denominator
	 * @return result
	 */
	public static long integerDivisionRound(long a, long b) {
		return (a + (b/2)) / b;
	}
}
