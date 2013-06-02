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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IpAddressRangeTest {
	@Test
	public void fromCidrNotation() {
		final IpAddressRange range = IpAddressRange.fromCidrNotation("173.199.64.0/18");
		assertEquals("173.199.64.0", range.getLowAddress().getIpAddress());
		assertEquals("173.199.127.255", range.getHighAddress().getIpAddress());
		final List<String> cidrList = range.getCidrNotationList();
		assertEquals(1, cidrList.size());
		assertEquals("173.199.64.0/18", cidrList.get(0));

		assertTrue(range.isInRange("173.199.64.0"));
		assertTrue(range.isInRange("173.199.127.255"));
		assertTrue(range.isInRange("173.199.120.2"));
		assertFalse(range.isInRange("173.199.63.255"));
	}

	@Test
	public void stringRange() {
		final IpAddressRange range = IpAddressRange.fromIpAddressRange("67.117.201.128", "67.117.201.143");
		assertEquals("67.117.201.128", range.getLowAddress().getIpAddress());
		assertEquals("67.117.201.143", range.getHighAddress().getIpAddress());
		final List<String> cidrList = range.getCidrNotationList();
		assertEquals(1, cidrList.size());
		assertEquals("67.117.201.128/28", cidrList.get(0));
	}

	@Test
	public void parseIpAddressRangeHyphen() {
		final IpAddressRange range = IpAddressRange.parse("67.117.201.128 - 67.117.201.143");
		assertEquals("67.117.201.128", range.getLowAddress().getIpAddress());
		assertEquals("67.117.201.143", range.getHighAddress().getIpAddress());
	}

	@Test
	public void parseIpAddressRangeCidr() {
		final IpAddressRange range = IpAddressRange.parse("67.117.201.128/28");
		assertEquals("67.117.201.128", range.getLowAddress().getIpAddress());
		assertEquals("67.117.201.143", range.getHighAddress().getIpAddress());
	}

	@Test
	public void parseIpAddressRangeSingle() {
		final IpAddressRange range = IpAddressRange.parse("67.117.201.128");
		assertEquals("67.117.201.128", range.getLowAddress().getIpAddress());
		assertEquals("67.117.201.128", range.getHighAddress().getIpAddress());
	}
}
