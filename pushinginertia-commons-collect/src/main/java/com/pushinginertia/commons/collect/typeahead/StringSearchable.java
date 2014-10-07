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
package com.pushinginertia.commons.collect.typeahead;

import java.io.Serializable;
import java.util.Set;

/**
 * An object containing a collection of search strings that are considered identifiers for the object when performing a
 * dictionary search.
 *
 * It's important that any implementations of this also implement {@link Object#hashCode()} and
 * {@link Object#equals(Object)} correctly, or result filtering will produce unpredictable results.
 */
public interface StringSearchable extends Serializable {
	/**
	 * Returns a list of strings that identify this object.
	 */
	public Set<String> getSearchStrings();
}
