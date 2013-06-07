package com.pushinginertia.commons.domain.util;

import junit.framework.TestCase;
import org.joda.time.DateTime;

public class EmailSubscriptionUtilsTest extends TestCase {
	public void testCalculateNextNotificationTimestamp() {
		final DateTime last = new DateTime(2013, 1, 1, 12, 23, 34, 999); // 2013-01-01 12:23:34
		final DateTime next = EmailSubscriptionUtils.calculateNextNotificationTimestamp(last, 5);
		assertEquals(2013, next.getYear());
		assertEquals(1, next.getMonthOfYear());
		assertEquals(6, next.getDayOfMonth());
		assertEquals(12, next.getHourOfDay());
		assertEquals(23, next.getMinuteOfHour());
		assertEquals(34, next.getSecondOfMinute());
		assertEquals(0, next.getMillisOfSecond());
	}

	public void testCalculateNextNotificationTimestampNull() {
		final DateTime now = DateTime.now().withMillisOfSecond(0).plusDays(5);
		final DateTime next = EmailSubscriptionUtils.calculateNextNotificationTimestamp((DateTime)null, 5);
		assertEquals(now, next);
	}
}
