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
package com.pushinginertia.commons.service.helper;

/**
 * Fails with a {@link EntityNotFoundException} exception when an entity is not found.
 */
public class AssertEntityFoundAction<E> extends LoadEntityAction<E> {
	static class EntityNotFoundException extends RuntimeException {
		public EntityNotFoundException(String message) {
			super(message);
		}

	}

	@Override
	public <I> void onEntityNotFound(final I input) {
		throw new EntityNotFoundException("Entity not found for input: " + input);
	}
}
