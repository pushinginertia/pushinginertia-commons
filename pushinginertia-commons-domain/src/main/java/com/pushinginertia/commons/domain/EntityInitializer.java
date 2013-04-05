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
package com.pushinginertia.commons.domain;

/**
 * Allows custom initialization logic to be performed on an entity at the DAO level when it is loaded from the database,
 * before the session is ended. An example is custom behaviour to execute on lazy-loaded properties of an entity.
 * @param <T> type of the entity being loaded
 */
public interface EntityInitializer<T> {
	/**
	 * Initializes a given entity after loading it from the database.
	 * @param entity entity to initialize (never null)
	 */
	public void initialize(T entity);
}
