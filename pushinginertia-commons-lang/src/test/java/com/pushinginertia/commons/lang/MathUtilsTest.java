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

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class MathUtilsTest {
	@Test
	public void testIntegerDivisionRound() {
		Assert.assertEquals(0, MathUtils.integerDivisionRound(0, 7));
		Assert.assertEquals(0, MathUtils.integerDivisionRound(1, 7));
		Assert.assertEquals(0, MathUtils.integerDivisionRound(2, 7));
		Assert.assertEquals(0, MathUtils.integerDivisionRound(3, 7));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(4, 7));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(5, 7));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(6, 7));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(7, 7));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(8, 7));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(9, 7));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(10, 7));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(11, 7));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(12, 7));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(13, 7));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(14, 7));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(15, 7));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(16, 7));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(17, 7));

		Assert.assertEquals(1, MathUtils.integerDivisionRound(39, 30));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(40, 30));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(41, 30));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(42, 30));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(43, 30));
		Assert.assertEquals(1, MathUtils.integerDivisionRound(44, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(45, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(46, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(47, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(48, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(49, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(50, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(51, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(52, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(53, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(54, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(55, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(56, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(57, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(58, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(59, 30));
		Assert.assertEquals(2, MathUtils.integerDivisionRound(60, 30));
	}

	@Test
	public void min() {
		Assert.assertEquals(BigDecimal.ZERO, MathUtils.min(BigDecimal.ZERO, BigDecimal.ONE));
		Assert.assertEquals(BigDecimal.ZERO, MathUtils.min(BigDecimal.ONE, BigDecimal.ZERO));
		Assert.assertEquals(BigDecimal.ZERO, MathUtils.min(BigDecimal.ZERO, BigDecimal.ZERO));
		Assert.assertEquals(BigDecimal.ONE, MathUtils.min(BigDecimal.TEN, BigDecimal.ONE));
		Assert.assertEquals(BigDecimal.ONE, MathUtils.min(BigDecimal.ONE, BigDecimal.TEN));

		Assert.assertEquals(BigDecimal.ONE, MathUtils.min(BigDecimal.ONE, null));
		Assert.assertEquals(BigDecimal.ONE, MathUtils.min(null, BigDecimal.ONE));
		Assert.assertNull(MathUtils.min(null, null));
	}

	@Test
	public void max() {
		Assert.assertEquals(BigDecimal.ONE, MathUtils.max(BigDecimal.ZERO, BigDecimal.ONE));
		Assert.assertEquals(BigDecimal.ONE, MathUtils.max(BigDecimal.ONE, BigDecimal.ZERO));
		Assert.assertEquals(BigDecimal.ZERO, MathUtils.max(BigDecimal.ZERO, BigDecimal.ZERO));
		Assert.assertEquals(BigDecimal.TEN, MathUtils.max(BigDecimal.TEN, BigDecimal.ONE));
		Assert.assertEquals(BigDecimal.TEN, MathUtils.max(BigDecimal.ONE, BigDecimal.TEN));

		Assert.assertEquals(BigDecimal.ONE, MathUtils.max(BigDecimal.ONE, null));
		Assert.assertEquals(BigDecimal.ONE, MathUtils.max(null, BigDecimal.ONE));
		Assert.assertNull(MathUtils.max(null, null));
	}
}
