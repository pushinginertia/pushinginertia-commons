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

/**
 * A filter that is applied to a {@link StringSearchable} when a string fragment matches a string returned by
 * {@link StringSearchable#getSearchStrings()}. This allows specific results to be omitted if they don't also match some
 * other criteria.
 * @param <S> Type of the searchable object that this dictionary stores.
 */
public interface DictionarySearchResultFilter<S extends StringSearchable> {
	/**
	 * Performs some custom criteria against a matching result for the purpose of filtering unwanted results and ranking
	 * desired results. When more matches are found than the limit given in the search, only the highest ranked matches
	 * within the limit are returned. This means that matches assigned lower values by this method may be excluded in
	 * the resultset.
	 * @param match Matching object.
	 * @param searchString Search string fragment.
	 * @return -1 when the match should be excluded, >= 0 when the match should be included (indicating the rank
	 * assigned to this match).
	 */
	public int rank(S match, String searchString);
}
