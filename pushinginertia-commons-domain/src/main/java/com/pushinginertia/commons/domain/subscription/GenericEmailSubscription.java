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
package com.pushinginertia.commons.domain.subscription;

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.domain.base.ModificationDateTimeTrackingDCO;
import com.pushinginertia.commons.domain.util.EmailSubscriptionUtils;
import org.joda.time.DateTime;

/**
 * Encapsulates the data required to track an email subscription.
 *
 * @param <I> defines the type for the unique ID/primary key
 * @param <U> defines the type for the user record that this subscription references
 */
public abstract class GenericEmailSubscription<I, U> extends ModificationDateTimeTrackingDCO {
	private static final long serialVersionUID = 1L;

	// TODO: add unsubscribe method

	private I id;
	private U user;
	private SubscriptionStatus status;
	private DateTime statusUpdatedTimestamp;
	/**
	 * Number of days to wait after one notification before generating another notification. Zero means instant notifications.
	 * e.g., 1, 7, 14
	 */
	private int notificationInterval;
	/** Timestamp of the last email sent to the user for this subscription */
	private DateTime lastNotificationTimestamp;
	/** Timestamp of the next email to send to the user for this subscription */
	private DateTime nextNotificationTimestamp;
	/** Timestamp of the last attempt to send an email - used to prevent infinite attempts when the email is due */
	private DateTime lastAttemptTimestamp;
	/**
	 * A count of the number of email notifications sent for this subscription.
	 */
	private long emailsSent;

	/**
	 * For instantiation by hibernate.
	 */
	protected GenericEmailSubscription() {}

	public GenericEmailSubscription(final U user, final SubscriptionStatus status, final int notificationInterval) {
		this.user = ValidateAs.notNull(user, "user");
		this.status = ValidateAs.notNull(status, "status");
		this.statusUpdatedTimestamp = DateTime.now();
		this.notificationInterval = ValidateAs.nonNegative(notificationInterval, "notificationInterval");
		this.nextNotificationTimestamp = DateTime.now();
		this.lastNotificationTimestamp = null; // nothing sent yet!
		this.emailsSent = 0;
	}

	/**
	 * Updates the last and next notification timestamps to reflect that a notification was sent.
	 * @param sentTimestamp the timestamp that the notification was sent at
	 */
	public void updateAfterNotificationSent(final DateTime sentTimestamp) {
		setLastNotificationTimestamp(sentTimestamp);
		setNextNotificationTimestamp(EmailSubscriptionUtils.calculateNextNotificationTimestamp(sentTimestamp, notificationInterval));
	}

	public I getId() {
		return id;
	}

	public void setId(final I id) {
		this.id = id;
	}

	public U getUser() {
		return user;
	}

	public void setUser(final U user) {
		this.user = user;
	}

	public SubscriptionStatus getStatus() {
		return status;
	}

	/**
	 * Use {@link #changeStatus(SubscriptionStatus)} so that {@link #statusUpdatedTimestamp} is updated at the same time.
	 * @param status
	 */
	protected void setStatus(final SubscriptionStatus status) {
		this.status = status;
	}

	public void changeStatus(final SubscriptionStatus status) {
		setStatus(status);
		setStatusUpdatedTimestamp(DateTime.now());
	}

	public DateTime getStatusUpdatedTimestamp() {
		return statusUpdatedTimestamp;
	}

	public void setStatusUpdatedTimestamp(final DateTime statusUpdatedTimestamp) {
		this.statusUpdatedTimestamp = statusUpdatedTimestamp;
	}

	public int getNotificationInterval() {
		return notificationInterval;
	}

	public void setNotificationInterval(final int notificationInterval) {
		this.notificationInterval = notificationInterval;
	}

	public DateTime getLastNotificationTimestamp() {
		return lastNotificationTimestamp;
	}

	public void setLastNotificationTimestamp(final DateTime lastNotificationTimestamp) {
		this.lastNotificationTimestamp = lastNotificationTimestamp;
	}

	public DateTime getNextNotificationTimestamp() {
		return nextNotificationTimestamp;
	}

	public void setNextNotificationTimestamp(final DateTime nextNotificationTimestamp) {
		this.nextNotificationTimestamp = nextNotificationTimestamp;
	}

	public DateTime getLastAttemptTimestamp() {
		return lastAttemptTimestamp;
	}

	public void setLastAttemptTimestamp(final DateTime lastAttemptTimestamp) {
		this.lastAttemptTimestamp = lastAttemptTimestamp;
	}

	public long getEmailsSent() {
		return emailsSent;
	}

	public void setEmailsSent(long emailsSent) {
		this.emailsSent = emailsSent;
	}

	public void incrementEmailsSent() {
		emailsSent++;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
				"id=" + id +
				", status=" + status +
				", statusUpdatedTimestamp=" + statusUpdatedTimestamp +
				", notificationInterval=" + notificationInterval +
				", lastNotificationTimestamp=" + lastNotificationTimestamp +
				", nextNotificationTimestamp=" + nextNotificationTimestamp +
				", lastAttemptTimestamp=" + lastAttemptTimestamp +
				", user=" + user +
				'}';
	}
}
