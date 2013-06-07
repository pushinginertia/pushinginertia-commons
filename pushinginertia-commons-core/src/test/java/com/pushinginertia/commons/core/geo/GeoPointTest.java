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
package com.pushinginertia.commons.core.geo;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeoPointTest {
	final GeoPoint p1 = new GeoPoint("49.284849", "-123.120389"); // 49°17′05.5″N, 123°07′13.4″W
	final GeoPoint p2 = new GeoPoint("49.220061", "-123.203583"); // 49°13′12″N, 123°12′13″W

	@Test
	public void testDistanceTo() {
		final BigDecimal distance = p1.distanceTo(p2); // 9.4 km
		final BigDecimal distanceRounded = distance.setScale(1, RoundingMode.HALF_UP);
		Assert.assertEquals(distanceRounded, new BigDecimal("9.4"));
	}

	@Test
	public void testDestinationPoint() {
		final GeoPoint destinationPoint = p1.destinationPoint(-140, 9.4);
		Assert.assertEquals(p2.getLat(), destinationPoint.getLat());
		Assert.assertEquals(p2.getLon(), destinationPoint.getLon());
	}
}
