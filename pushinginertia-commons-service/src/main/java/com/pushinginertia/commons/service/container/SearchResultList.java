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
package com.pushinginertia.commons.service.container;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * A useful container object that can be returned when a paginated search is performed in which it's necessary to
 * know the search result count and the actual results for the given page.
 */
public class SearchResultList<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * The total number of matches.
	 */
	private final long count;
	/**
	 * The results for the given page.
	 */
	private final List<T> matches;
	/**
	 * The page number that is represented in {@link #matches}. This is normally echoed from the requested page number,
	 * but in the case that the page number exceeds the actual number of pages of data available, this will be set to
	 * a different value. The first page is 1, not 0.
	 */
	private final int page;

	/**
	 * A useful container object that can be returned when a paginated search is performed in which it's necessary to
	 * know the search result count and the actual results for the given page.
	 * @param count The total number of matches.
	 * @param matches The results for the given page.
	 * @param page The page number that is represented in {@link #matches}. This is normally echoed from the requested
	 * page number, but in the case that the page number exceeds the actual number of pages of data available, this will
	 * be set to a different value. The first page is 1, not 0.
	 */
	public SearchResultList(final long count, final List<T> matches, final int page) {
		this.count = count;
		this.matches = Collections.unmodifiableList(matches);
		this.page = page;
	}

	public long getCount() {
		return count;
	}

	public List<T> getMatches() {
		return matches;
	}

	public int getPage() {
		return page;
	}

	@Override
	public String toString() {
		final int matchCount;
		if (matches == null) {
			matchCount = 0;
		} else {
			matchCount = matches.size();
		}
		return "SearchResultList{count=" + count + ", matchCount=" + matchCount + ", page=" + page + '}';
	}
}
