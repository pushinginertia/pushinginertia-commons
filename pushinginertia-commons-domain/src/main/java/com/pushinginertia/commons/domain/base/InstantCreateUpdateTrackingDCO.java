/* Copyright (c) 2011-2016 Pushing Inertia
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
package com.pushinginertia.commons.domain.base;

import java.time.Instant;

/**
 * Common code to handle tables with created/modified timestamps.
 * The hibernate mapping file must have the timestamps defined as follows:
 * <pre>
 *    &lt;property name="createdAt" column="created_ts" type="com.pushinginertia.commons.domain.usertype.InstantUserType" not-null="true" insert="true" update="false"/&gt;
 *    &lt;property name="updatedAt" column="updated_ts" type="com.pushinginertia.commons.domain.usertype.InstantUserType" not-null="true" insert="true" update="true"/&gt;
 * </pre>
 */
public abstract class InstantCreateUpdateTrackingDCO extends BaseDCO {
	private static final long serialVersionUID = 1L;
	private Instant createdAt;
	private Instant updatedAt;

	protected InstantCreateUpdateTrackingDCO() {
		this.createdAt = Instant.now();
		this.updatedAt = this.createdAt;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public void onBeforeCreate() {
		super.onBeforeCreate();
		if (createdAt == null) {
			createdAt = Instant.now();
		}
		if (updatedAt == null) {
			updatedAt = Instant.now();
		}
	}

	@Override
	public void onBeforeUpdate() {
		super.onBeforeUpdate();
		this.updatedAt = Instant.now();
	}
}