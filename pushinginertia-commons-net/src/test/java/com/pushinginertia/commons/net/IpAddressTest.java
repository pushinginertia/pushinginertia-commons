/*
 * Copyright (c) 2011-2012 Pushing Inertia
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
package com.pushinginertia.commons.net;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IpAddressTest {
	@Test
	public void comparisons() {
		final IpAddress ip = new IpAddress("100.100.100.0");
		assertFalse(ip.isLower("100.100.99.255"));
		assertFalse(ip.isLower("100.100.100.0"));
		assertTrue(ip.isLower("100.100.100.1"));

		assertTrue(ip.isHigher("100.100.99.255"));
		assertFalse(ip.isHigher("100.100.100.0"));
		assertFalse(ip.isHigher("100.100.100.1"));

		assertFalse(ip.isEqual("100.100.99.255"));
		assertTrue(ip.isEqual("100.100.100.0"));
		assertFalse(ip.isEqual("100.100.100.1"));

		assertTrue(ip.isBetween("100.100.100.0", "100.100.100.255"));
		assertFalse(ip.isBetween("100.100.100.1", "100.100.100.255"));

	}
}
