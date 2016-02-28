/* Copyright (c) 2011-2016 Pushing Inertia
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

import java.util.stream.Stream;

public class EnumUtilsTest {
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