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

import java.util.Collection;
import java.util.List;

/**
 * Stores a dictionary of strings provided by {@link StringSearchable}s and performs efficient searches when given
 * string fragments.
 * @param <S> Type of the searchable object that this dictionary stores.
 */
public interface Dictionary<S extends StringSearchable> {
	public interface StringPreprocessor {
		public String preprocess(String s);
	}

	public static final StringPreprocessor DEFAULT_PREPROCESSOR = new DictionaryDefaultStringPreprocessor();

	/**
	 * Adds a collection of {@link StringSearchable} instances to the dictionary.
	 * @param searchables Collection of instances to add.
	 */
	public void addSearchables(Collection<S> searchables);

	/**
	 * Adds a {@link StringSearchable} instance to the dictionary.
	 * @param searchable Instance to add.
	 */
	public void addSearchable(S searchable);

	/**
	 * Performs a search in the dictionary for a given string, returning only exact matches.
	 * @param searchString Full search string.
	 * @param filter Filter to apply to matching results.
	 * @param limit Maximum results to return (ordered by
	 * {@link DictionarySearchResultFilter#rank(StringSearchable, String)}.
	 * @return List of matches in sorted order.
	 */
	public List<S> searchExactMatch(String searchString, DictionarySearchResultFilter<S> filter, int limit);

	/**
	 * Performs a search in the dictionary for a given string, returning only strings for which the search string is a
	 * prefix.
	 * @param searchPrefix Search string prefix.
	 * @param filter Filter to apply to matching results.
	 * @param limit Maximum results to return (ordered by
	 * {@link DictionarySearchResultFilter#rank(StringSearchable, String)}.
	 * @return List of matches in sorted order.
	 */
	public List<S> searchPrefix(String searchPrefix, DictionarySearchResultFilter<S> filter, int limit);
}
