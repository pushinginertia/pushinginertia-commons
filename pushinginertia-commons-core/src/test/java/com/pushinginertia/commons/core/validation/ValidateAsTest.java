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
package com.pushinginertia.commons.core.validation;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ValidateAsTest {
	@Test(expected = IllegalArgumentException.class)
	public void notNull() {
		ValidateAs.notNull(null, "nullParameter");
	}

	@Test
	public void notEmpty() {
		ValidateAs.notEmpty("abc", "emptyParameter");
	}

	@Test(expected = IllegalArgumentException.class)
	public void notEmptyFail() {
		ValidateAs.notEmpty("", "emptyParameter");
	}

	@Test
	public void trimmedNotEmpty() {
		Assert.assertEquals("abc", ValidateAs.trimmedNotEmpty(" abc ", "emptyParameter"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void trimmedNotEmptyFail() {
		ValidateAs.trimmedNotEmpty("  ", "emptyParameter");
	}

	@Test
	public void notEqual() {
		ValidateAs.notEqual("abc", "def", "string1", "string2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void notEqualFail() {
		ValidateAs.notEqual("abc", "abc", "string1", "string2");
	}

	@Test
	public void equal() {
		ValidateAs.equal("abc", "abc", "string1", "string2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void equalFail() {
		ValidateAs.equal("abc", "def", "string1", "string2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void positive0() {
		ValidateAs.positive(0, "zeroParameter");
	}

	@Test(expected = IllegalArgumentException.class)
	public void positiveMinus1() {
		ValidateAs.positive(-1, "minusOneParameter");
	}

	@Test
	public void mapDoesNotContainKey() {
		final Map<String, String> m = new HashMap<String, String>();

		// positive case
		ValidateAs.mapDoesNotContainKey(m, "a");

		// negative case
		m.put("a", "1");
		try {
			ValidateAs.mapDoesNotContainKey(m, "a");
			Assert.fail("Expected IllegalArgumentException");
		} catch (final IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void ofLength() {
		ValidateAs.ofLength("abc", 3, "string1");
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofLengthFail() {
		ValidateAs.ofLength("abc", 2, "string1");
	}

	@Test
	public void allUppercase() {
		ValidateAs.allUppercase("ABC", "string");
	}

	@Test(expected = IllegalArgumentException.class)
	public void allUppercaseFail1() {
		ValidateAs.allUppercase("ABc", "string");
	}

	@Test(expected = IllegalArgumentException.class)
	public void allUppercaseFail2() {
		ValidateAs.allUppercase("ABC1", "string");
	}
}
