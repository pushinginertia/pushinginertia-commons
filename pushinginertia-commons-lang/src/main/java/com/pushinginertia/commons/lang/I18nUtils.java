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
package com.pushinginertia.commons.lang;

/**
 * Logic to support internationalization.
 */
public class I18nUtils {
	public static final String ZERO = "Zero";
	public static final String ONE = "One";
	public static final String MANY = "Many";

	/**
	 * Generates a singular or plural resource lookup key for a given prefix. The output will be of the form
	 * "&lt;prefix&gt;.&lt;singularity-key&gt;" where singularity-key is one of {@link #ONE} or {@link #MANY}.
	 * @param prefix prefix of the resource lookup key
	 * @param isSingular indicates if the key represents a singular or plural value
	 * @return generated key
	 */
	public static String generateResourceLookupKey(final String prefix, final boolean isSingular) {
		if (isSingular) {
			return prefix + '.' + ONE;
		}
		return prefix + '.' + MANY;
	}
}
