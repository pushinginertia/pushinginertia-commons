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
package com.pushinginertia.commons.ui.i18n;

/**
 * Anything implementing this interface has a method that defines a lookup key existing in the appropriate resource
 * bundle. This is often used for enums where each enumeration resolves to a unique string.
 */
public interface IResourceLookupKey {
	/**
	 * Generates the resource lookup key for the instance.
	 * @return never null
	 */
	public String getResourceLookupKey();
}
