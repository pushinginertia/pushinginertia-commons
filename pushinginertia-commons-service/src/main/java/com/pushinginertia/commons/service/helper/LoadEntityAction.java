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
package com.pushinginertia.commons.service.helper;

/**
 * Executes arbitrary logic when an entity is loaded.  For example, an exception can be thrown
 * if an entity is not found, or validation actions can be carried out when a matching entity is found.
 */
public abstract class LoadEntityAction<E> {
	/**
	 * Executed when an entity is found. This can be used to perform custom validation.
	 * @param entity entity found by the service
	 */
	public void onEntityFound(final E entity) {
	}

	/**
	 * Executed when no entity is found.
	 * @param input the input criteria for the search
	 */
	public <I> void onEntityNotFound(final I input) {
	}

	/**
	 * Called by the service loading an entity when the load operation completes. This method triggers the
	 * {@link #onEntityFound(Object)} and {@link #onEntityNotFound(I)} methods, depending on whether the entity
	 * passed in as input is null or not.
	 * @param input the input criteria for the search
	 * @param entity entity loaded by the service or null
	 * @return the entity passed in
	 */
	public final <I> E onLoadFinished(final I input, final E entity) {
		if (entity == null) {
			onEntityNotFound(input);
		} else {
			onEntityFound(entity);
		}
		return entity;
	}
}
