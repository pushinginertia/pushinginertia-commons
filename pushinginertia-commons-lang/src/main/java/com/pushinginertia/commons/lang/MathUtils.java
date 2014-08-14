/* Copyright (c) 2011-2014 Pushing Inertia
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

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.math.BigDecimal;

/**
 * Reusable math functionality.
 */
public final class MathUtils {
	private MathUtils() {}

	/**
	 * Divides a by b and rounds the result without using floating point arithmetic.
	 * @param a numerator
	 * @param b denominator
	 * @return result
	 */
	public static long integerDivisionRound(long a, long b) {
		return (a + (b/2)) / b;
	}

	public static BigDecimal min(final BigDecimal bd1, final BigDecimal bd2) {
		ValidateAs.notNull(bd1, "bd1");
		ValidateAs.notNull(bd2, "bd2");
		if (bd1.compareTo(bd2) < 0) {
			return bd1;
		}
		return bd2;
	}
}
