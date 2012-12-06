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

import java.util.List;

import static org.junit.Assert.assertEquals;

public class IpAddressUtilsTest {
	@Test(expected = IllegalArgumentException.class)
	public void toIpNumberNull() {
		IpAddressUtils.toIpNumber(null);
	}

	@Test
	public void toIpNumber() {
		assertEquals(3401532416L, IpAddressUtils.toIpNumber("202.191.68.0"));
		assertEquals(3401190660L, IpAddressUtils.toIpNumber("202.186.13.4"));
	}

	@Test
	public void toIpAddress() {
		assertEquals("202.191.68.0", IpAddressUtils.toIpAddress(3401532416L));
		assertEquals("202.186.13.4", IpAddressUtils.toIpAddress(3401190660L));
	}

	@Test
	public void toCidrNotationList1() {
		final List<String> l = IpAddressUtils.toCidrNotationList("67.117.201.128", "67.117.201.143");
		assertEquals(1, l.size());
		assertEquals("67.117.201.128/28", l.get(0));
	}

	@Test
	public void toCidrNotationList2() {
		final List<String> l = IpAddressUtils.toCidrNotationList("1.1.1.111", "1.1.1.120");
		assertEquals(3, l.size());
		assertEquals("1.1.1.111/32", l.get(0));
		assertEquals("1.1.1.112/29", l.get(1));
		assertEquals("1.1.1.120/32", l.get(2));
	}
}