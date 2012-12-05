/* Copyright (c) 2011-2012 Pushing Inertia
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Logic that manipulates Java lists.
 */
public class ListUtils {
	/**
	 * Sorts a collection of objects into an indexed list. For example, a {@link java.util.Set} could be passed in and
	 * its members would be sorted into a list.
	 * @param c collection of objects
	 * @param <T> type of the objects in the input collection
	 * @return sorted list
	 */
	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		final List<T> list = new ArrayList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}
}
