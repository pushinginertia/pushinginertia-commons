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

import com.pushinginertia.commons.collect.BoundedMinHeap;
import com.pushinginertia.commons.core.validation.ValidateAs;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dictionary that uses a ternary search tree as its data structure.
 * @param <S> Type of the searchable object that this dictionary stores.
 */
public class TstDictionary<S extends StringSearchable> extends DefaultDictionary<S> {
	private TstNode root;
	private boolean readOnly = false;

	private static class DefaultVisitor<S extends StringSearchable> implements TstNode.NodeVisitor<S> {
		private final DictionarySearchResultFilter<S> filter;
		private final Set<StringSearchable> seenResults;
		private final BoundedMinHeap<S> results;

		private DefaultVisitor(final DictionarySearchResultFilter<S> filter, final int limit) {
			this.filter = ValidateAs.notNull(filter, "filter");
			this.seenResults = new HashSet<StringSearchable>();
			this.results = new BoundedMinHeap<S>(limit);
		}

		public void visit(final S searchable, final String searchString) {
			if (!seenResults.contains(searchable)) {
				// only examine this if it hasn't been visited before
				seenResults.add(searchable);

				// rank the result
				final int rank = filter.rank(searchable, searchString);
				if (rank >= 0) {
					// add if ranking permits it
					results.add(searchable, rank);
				}
			}
		}

		public List<S> resultList() {
			// order by the highest ranked result first
			return results.toListDescendingRank();
		}
	}

	public TstDictionary(final StringPreprocessor preprocessor) {
		super(preprocessor);
	}

	public void addSearchables(final Collection<S> searchables) {
		for (final S searchable: searchables) {
			addSearchable(searchable);
		}
	}

	public void addSearchable(final S searchable) {
		if (readOnly) {
			throw new IllegalStateException("Dictionary is readonly.");
		}
		for (final String searchString: searchable.getSearchStrings()) {
			final String processedSearchString = preprocessor.preprocess(searchString);
			if (root == null) {
				root = TstNode.newRoot(processedSearchString, searchable);
			} else {
				root.insert(processedSearchString, searchable);
			}
		}
	}

	public void setReadOnly() {
		readOnly = true;
	}

	public List<S> searchExactMatch(
			final String searchString,
			final DictionarySearchResultFilter<S> filter,
			final int limit) {
		final DefaultVisitor<S> visitor = new DefaultVisitor<S>(filter, limit);
		root.searchExactMatch(preprocessor.preprocess(searchString), visitor);
		return visitor.resultList();
	}

	public List<S> searchPrefix(String searchPrefix, DictionarySearchResultFilter<S> filter, int limit) {
		final DefaultVisitor<S> visitor = new DefaultVisitor<S>(filter, limit);
		root.searchPrefix(preprocessor.preprocess(searchPrefix), visitor);
		return visitor.resultList();
	}
}
