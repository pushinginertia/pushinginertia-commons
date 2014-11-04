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
package com.pushinginertia.commons.core.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Computes a list of N rows where each row represents the combinations for N choose K for K in 1..N. Each
 * combination in each row also contains a payload. This is applied by taking two previous combinations from rows 1
 * and K-1 and calling {@link Combination.Payload#add(Object)} to produce a new payload. This works fine for N
 * up to about 16. After that, it becomes inefficient and optimizations would need to be made.
 */
public class KCombinations<P extends Combination.Payload<P>> {
	private final List<Map<Combination.Key, Combination<P>>> rows;
	private int size;

	/**
	 * Computes a list of N rows where each row represents the combinations for N choose K for K in 1..N. Each
	 * combination in each row also contains a payload. This is applied by taking two previous combinations from rows 1
	 * and K-1 and calling {@link Combination.Payload#add(Object)} to produce a new payload. This works fine for N
	 * up to about 16. After that, it becomes inefficient and optimizations would need to be made.
	 *
	 * @param initialPayloads List of initial payloads for N choose 1. The size of this list defines N and all N choose
	 * K combinations for K &gt; 1 are calculated from this initial list.
	 */
	public KCombinations(final List<P> initialPayloads) {
		final int n = initialPayloads.size();
		if (n > 16) {
			throw new IllegalArgumentException("n is too high for this algorithm.");
		}
		this.rows = new ArrayList<Map<Combination.Key, Combination<P>>>(n);
		for (int i = 0; i < n; i++) {
			rows.add(new HashMap<Combination.Key, Combination<P>>());
		}
		this.size = 0;

		for (int i = 0; i < n; i++) {
			add(new Combination<P>(i, initialPayloads.get(i)));
		}

		for (int k = 1; k < n; k++) {
			computeK(k);
		}
	}

	/**
	 * The number of combinations identified for N choose K for K in 1..N.
	 * @return Combination count.
	 */
	public int size() {
		return size;
	}

	/**
	 * Merges all of the payloads into a single payload.
	 * @param payload Empty payload to merge everything into.
	 * @return Merged payload.
	 */
	public P merge(final P payload) {
		for (final Map<Combination.Key, Combination<P>> row: rows) {
			for (final Combination<P> combination: row.values()) {
				payload.merge(combination.getPayload());
			}
		}
		return payload;
	}

	int rowCount() {
		return rows.size();
	}

	int rowSize(final int k) {
		if (k >= rows.size()) {
			throw new IllegalArgumentException("k must be < n");
		}
		return rows.get(k).size();
	}

	private void add(final Combination<P> combination) {
		final Combination.Key key = combination.getKey();
		final int k = key.size();
		if (k > rows.size()) {
			throw new IllegalArgumentException("k (" + k + ") must be < n (" + rows.size() + ")");
		}
		final Map<Combination.Key, Combination<P>> row = rows.get(k - 1);
		if (row.containsKey(key)) {
			throw new IllegalArgumentException("Combination already exists: " + combination);
		}
		row.put(key, combination);
		size++;
	}

	private void computeK(final int k) {
		if (k < 1) {
			throw new IllegalArgumentException("k must be >= 2");
		}
		final Map<Combination.Key, Combination<P>> row0 = rows.get(0);
		final Map<Combination.Key, Combination<P>> rowPrevious = rows.get(k - 1);

		for (final Combination<P> combinationPrevious: rowPrevious.values()) {
			final int r = combinationPrevious.getKey().getRightIndex();
			for (final Combination<P> combinationFirst: row0.values()) {
				final int i = combinationFirst.getKey().getOnlyIndex();
				if (r < i) {
					add(new Combination<P>(combinationPrevious, combinationFirst));
				}
			}
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final Map<Combination.Key, Combination<P>> row: rows) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append('[');
			final StringBuilder sbRow = new StringBuilder();
			for (final Combination<P> combination : row.values()) {
				if (sbRow.length() > 0) {
					sbRow.append(',');
				}
				sbRow.append(combination);
			}
			sb.append(sbRow);
			sb.append(']');
		}
		return "R{" + sb.toString() + '}';
	}
}
