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

import junit.framework.TestCase;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TstDictionaryTest extends TestCase {
	private static final DictionarySearchResultFilter<TestStringSearchable> FILTER =
			new DictionarySearchResultFilter<TestStringSearchable>() {
				private final Random rand = new SecureRandom();

				public int rank(final TestStringSearchable match, final String searchString) {
					// order randomly
					return rand.nextInt(10);
				}
			};

	public static class TestStringSearchable implements StringSearchable {
		private static final long serialVersionUID = 1L;

		private final Set<String> searchStrings;

		public TestStringSearchable(final String... searchStrings) {
			this.searchStrings = new LinkedHashSet<String>(searchStrings.length);
			Collections.addAll(this.searchStrings, searchStrings);
		}

		public Set<String> getSearchStrings() {
			return searchStrings;
		}

		@Override
		public String toString() {
			return "TestStringSearchable{" + searchStrings + '}';
		}

		@SuppressWarnings("SimplifiableIfStatement")
		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			return searchStrings.equals(((TestStringSearchable) o).searchStrings);
		}

		@Override
		public int hashCode() {
			return searchStrings.hashCode();
		}
	}

	public void testSearch() {
		final TestStringSearchable sfu =
				new TestStringSearchable(
						"sfu",
						"simon fraser university",
						"sfu burnaby",
						"sfu vancouver",
						"sfu surrey");
		final TestStringSearchable sfuSurrey = new TestStringSearchable("sfu surrey");
		final TestStringSearchable ubc =
				new TestStringSearchable(
						"ubc",
						"university of british columbia",
						"university of british Columbia, vancouver campus",
						"university of british Columbia, okanagan campus");

		final TstDictionary<TestStringSearchable> d = new TstDictionary<TestStringSearchable>(Dictionary.DEFAULT_PREPROCESSOR);

		d.addSearchable(sfu);
		assertMatches(d.searchExactMatch("sfu surrey", FILTER, 1), sfu);
		assertMatches(d.searchExactMatch("sfusurrey", FILTER, 1), sfu);
		assertMatches(d.searchExactMatch("sfu", FILTER, 1), sfu);
		assertMatches(d.searchExactMatch("simon fraser university", FILTER, 1), sfu);
		assertMatches(d.searchExactMatch("simonfraser university", FILTER, 1), sfu);
		assertMatches(d.searchExactMatch("simon fraser universit", FILTER, 1));
		assertMatches(d.searchExactMatch("simonfraser universit", FILTER, 1));
		assertMatches(d.searchExactMatch("simonfra ser universit", FILTER, 1));
		assertMatches(d.searchPrefix("sf", FILTER, 1), sfu);
		assertMatches(d.searchPrefix("simon fraser", FILTER, 1), sfu);
		assertMatches(d.searchPrefix("simonfraser", FILTER, 1), sfu);
		assertMatches(d.searchPrefix("simon", FILTER, 1), sfu);

		d.addSearchable(sfuSurrey);
		assertMatchesOneOf(d.searchExactMatch("sfu surrey", FILTER, 1), sfu, sfuSurrey);
		assertMatches(d.searchExactMatch("sfu surrey", FILTER, 2), sfu, sfuSurrey);
		assertMatches(d.searchExactMatch("sfu", FILTER, 1), sfu);
		assertMatches(d.searchExactMatch("simon fraser university", FILTER, 1), sfu);
		assertMatchesOneOf(d.searchPrefix("sf", FILTER, 1), sfu, sfuSurrey);
		assertMatches(d.searchPrefix("sf", FILTER, 2), sfu, sfuSurrey);
		assertMatches(d.searchPrefix("simon fraser", FILTER, 1), sfu);
		assertMatches(d.searchPrefix("simon", FILTER, 1), sfu);

		d.addSearchable(ubc);
		assertMatches(d.searchExactMatch("ubc", FILTER, 1), ubc);

		assertMatches(d.searchExactMatch("no match", FILTER, 1));
	}

	private static void assertMatches(final List<TestStringSearchable> matches, final TestStringSearchable... expecteds) {
		assertNotNull(matches);
		assertEquals(expecteds.length, matches.size());
		for (final TestStringSearchable expected: expecteds) {
			// order doesn't matter
			assertTrue(matches.contains(expected));
		}
	}

	private static void assertMatchesOneOf(final List<TestStringSearchable> matches, final TestStringSearchable... expecteds) {
		assertNotNull(matches);
		assertEquals(1, matches.size());
		for (final TestStringSearchable expected: expecteds) {
			if (expected.equals(matches.get(0))) {
				return;
			}
		}
		fail();
	}
}
