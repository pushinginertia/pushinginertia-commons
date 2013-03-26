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
package com.pushinginertia.commons.ui;

import com.pushinginertia.commons.lang.MathUtils;
import com.pushinginertia.commons.lang.ValidateAs;
import org.joda.time.Duration;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * Accepts a duration and represents it in days, weeks, or months, depending on the length of the duration.
 * It is divided into the appropriate unit of measure and then rounded. This is useful for displaying a duration in
 * a UI where it's appropriate to show an approximate representation of the duration.
 * <p>
 * The period consists of a count of time and a unit of measure for that time, represented as a resource lookup key.
 * <p>
 * Strings for the following resource keys must exist:
 * <ul>
 *     <li>TimePeriod.Minute</li>
 *     <li>TimePeriod.Minutes</li>
 *     <li>TimePeriod.Hour</li>
 *     <li>TimePeriod.Hours</li>
 *     <li>TimePeriod.Day</li>
 *     <li>TimePeriod.Days</li>
 *     <li>TimePeriod.Week</li>
 *     <li>TimePeriod.Weeks</li>
 *     <li>TimePeriod.Month</li>
 *     <li>TimePeriod.Months</li>
 * </ul>
 */
public class TimePeriod implements Serializable {
	private static final long serialVersionUID = 1L;

	private final long quantity;
	private final String descriptorResourceKey;

	public TimePeriod(final Duration duration) {
		ValidateAs.notNull(duration, "duration");
		final long days = duration.getStandardDays();

		// about 30.4 days in a month
		// N days: if dayCount <= 31
		// N weeks: if 31 < dayCount <= 61 and
		// N months: if 61 < dayCount
		final StringBuilder key = new StringBuilder("TimePeriod.");
		if (days < 1) {
			final long minutes = duration.getStandardMinutes();
			if (minutes < 60) {
				quantity = minutes;
				key.append("Minute");
			} else {
				quantity = duration.getStandardHours();
				key.append("Hour");
			}
		} else if (days <= 31) {
			quantity = days;
			key.append("Day");
		} else if (days <= 61) {
			quantity = MathUtils.integerDivisionRound(days, 7);
			key.append("Week");
		} else {
			quantity = MathUtils.integerDivisionRound(days, 30);
			key.append("Month");
		}
		if (quantity != 1)
			key.append('s');
		descriptorResourceKey = key.toString();
	}

	public String getDescriptorResourceKey() {
		return descriptorResourceKey;
	}

	public long getQuantity() {
		return quantity;
	}

	/**
	 * Looks up the appropriate resource string defined by {@link #getDescriptorResourceKey()} and applies a substitution
	 * on all instances of '${i}' with the quantity for the time period.
	 * @param rb resource bundle to use
	 * @return constructed string
	 */
	public String getString(final ResourceBundle rb) {
		final String s = rb.getString(descriptorResourceKey);
		return s.replace("${i}", Long.toString(quantity));
	}

	/**
	 * A simple interface used by {@link TimePeriod#getString(com.pushinginertia.commons.ui.TimePeriod.TimePeriodResourceBundle)}
	 * to enclose a custom resource bundle definition.
	 */
	public interface TimePeriodResourceBundle {
		public String getString(String resourceKey);
	}

	/**
	 * Looks up the appropriate resource string defined by {@link #getDescriptorResourceKey()} and applies a substitution
	 * on all instances of '${i}' with the quantity for the time period.
	 * @param rb resource bundle to use
	 * @return constructed string
	 */
	public String getString(final TimePeriodResourceBundle rb) {
		final String s = rb.getString(descriptorResourceKey);
		return s.replace("${i}", Long.toString(quantity));
	}
}
