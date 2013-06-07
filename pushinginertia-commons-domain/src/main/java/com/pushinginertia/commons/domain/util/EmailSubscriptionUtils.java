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
package com.pushinginertia.commons.domain.util;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Logic to handle changes to subscription data.
 */
public final class EmailSubscriptionUtils {
	private EmailSubscriptionUtils() {}

	/**
	 * Calculates the next notification date as the last notification date plus the notification interval. If no
	 * last notification date is given, then the calculated date is based on the current time plus the notification
	 * interval.
	 * @param lastNotificationTimestamp null is ok
	 * @param notificationInterval number of days
	 * @return calculated date/time
	 */
	public static DateTime calculateNextNotificationTimestamp(final DateTime lastNotificationTimestamp, final int notificationInterval) {
		if (lastNotificationTimestamp == null) {
			return DateTime.now().withMillisOfSecond(0).plusDays(notificationInterval);
		}

		return lastNotificationTimestamp.withMillisOfSecond(0).plusDays(notificationInterval);
	}

	/**
	 * Provided for backward compatibility but better to use {@link #calculateNextNotificationTimestamp(org.joda.time.DateTime, int)}.
	 * @param lastNotificationTimestamp null is ok
	 * @param notificationInterval number of days
	 * @return calculated date/time
	 */
	public static DateTime calculateNextNotificationTimestamp(final Date lastNotificationTimestamp, final int notificationInterval) {
		if (lastNotificationTimestamp == null) {
			return calculateNextNotificationTimestamp((Date)null, notificationInterval);
		}
		return calculateNextNotificationTimestamp(new DateTime(lastNotificationTimestamp), notificationInterval);
	}
}
