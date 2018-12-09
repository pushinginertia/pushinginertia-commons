/* Copyright (c) 2011-2018 Pushing Inertia
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class EnumUtilsTest {
	enum X {
		A, B,
	}

	@Test
	public void toEnumSet() {
		final List<String> input = Arrays.asList("A", "X");
		final HashSet<X> expected = new HashSet<>(Arrays.asList(X.A));

		Assert.assertEquals(
				expected,
				EnumUtils.toEnumSet(
						input,
						X.class,
						false));
	}

	@Test(expected = IllegalArgumentException.class)
	public void toEnumSetThrowWhenUnknown() {
		final List<String> input = Arrays.asList("A", "X");
		final HashSet<X> expected = new HashSet<>(Arrays.asList(X.A));

		Assert.assertEquals(
				expected,
				EnumUtils.toEnumSet(
						input,
						X.class,
						true));
	}

	private enum ToCamelCaseString {
		ONE_TWO_THREE("One Two Three"),
		FOUR_FIVE_6_SEVEN("Four Five 6 Seven");

		private ToCamelCaseString(final String expectedValue) {
			this.expectedValue = expectedValue;
		}

		private final String expectedValue;
	}

	@Test
	public void toCamelCaseString() {
		Stream.of(ToCamelCaseString.values())
				.forEach(val -> Assert.assertEquals(val.expectedValue, EnumUtils.toCamelCaseString(val)));
	}
}