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
package com.pushinginertia.commons.domain.validation;

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.domain.base.IUser;

/**
 * Provides some common validation logic specific to domain objects.
 */
public final class DomainValidate {
	private DomainValidate() {}

	/**
	 * Validates that a user is assigned a given role name.
	 * @param user user to test
	 * @param roleName role name to test
	 * @return the user passed as input to support method chaining
	 * @throws IllegalArgumentException if the user is null or is not assigned the given role name
	 */
	public static <U extends IUser> U userHasRole(final U user, final String roleName) throws IllegalArgumentException {
		ValidateAs.notNull(user, "user");
		if (!user.isAssignedRoleName(roleName)) {
			throw new IllegalArgumentException("User is not assigned role [" + roleName + "]: " + user);
		}
		return user;
	}
}
