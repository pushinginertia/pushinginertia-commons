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
package com.pushinginertia.commons.domain.util;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

public class EmailSubscriptionUtilsTest {
	@Test
	public void testCalculateNextNotificationTimestamp() {
		final DateTime last = new DateTime(2013, 1, 1, 12, 23, 34, 999); // 2013-01-01 12:23:34
		final DateTime next = EmailSubscriptionUtils.calculateNextNotificationTimestamp(last, 5);
		Assert.assertEquals(2013, next.getYear());
		Assert.assertEquals(1, next.getMonthOfYear());
		Assert.assertEquals(6, next.getDayOfMonth());
		Assert.assertEquals(12, next.getHourOfDay());
		Assert.assertEquals(23, next.getMinuteOfHour());
		Assert.assertEquals(34, next.getSecondOfMinute());
		Assert.assertEquals(0, next.getMillisOfSecond());
	}

	@Test
	public void testCalculateNextNotificationTimestampNull() {
		final DateTime now = DateTime.now().withMillisOfSecond(0).plusDays(5);
		final DateTime next = EmailSubscriptionUtils.calculateNextNotificationTimestamp(null, 5);
		Assert.assertEquals(now, next);
	}
}
