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

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.util.TreeSet;

/**
 * Encapsulates a combination of N choose K and includes a payload that can be attached to the combination.
 *
 * @param <P> Type of the payload contained within each {@link Combination} instance.
 */
public class Combination<P extends Combination.Payload<P>> {
	private final Key key;
	private final P payload;

	public interface Payload<P> {
		/**
		 * Adds the payloads from two combinations together to produce a combination for a larger value of K. This is
		 * taken from an N choose X and an N choose Y combination where X+Y equals the value of K that this payload is
		 * for. This should be an immutable operation and not mutate either {@link Payload} instance.
		 * @param rhs Payload to add to this one.
		 * @return A new instance in which whatever additions or compositions have been applied.
		 */
		public P add(P rhs);

		/**
		 * Merges a given payload into this instance. This is a mutating operation that mutates this instance but does
		 * not mutate the given instance.
		 * @param rhs Payload to merge into this one.
		 */
		public void merge(P rhs);
	}

	/**
	 * Combines two combinations into one by merging the the two combinations' {@link Combination.Key}s and adding the
	 * payloads together.
	 * @param left Left combination.
	 * @param right Right combination.
	 */
	public Combination(final Combination<P> left, final Combination<P> right) {
		ValidateAs.notNull(left, "left");
		ValidateAs.notNull(right, "right");
		this.key = new Key(left.getKey(), right.getKey());
		this.payload = left.payload.add(right.payload);
		if (this.payload == null) {
			throw new IllegalStateException(left.payload.getClass().getName() + ".add(rhs) cannot return null.");
		}
	}

	public Combination(final int index, final P payload) {
		this.key = new Key(index);
		this.payload = ValidateAs.notNull(payload, "payload");
	}

	public Key getKey() {
		return key;
	}

	public int size() {
		return key.size();
	}

	public P getPayload() {
		return payload;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		return key.equals(((Combination)o).key);
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public String toString() {
		return key.toString();
	}

	/**
	 * Represents the indexes for this combination of N choose K. The count of indexes equals K. Note that internally
	 * the indexes are zero-based, so an index of {0,1} indicates the first two items of N for this combination.
	 */
	public static class Key {
		private final TreeSet<Integer> indexes;  // must be ordered

		private Key(final Key left, final Key right) {
			this.indexes = new TreeSet<Integer>(left.indexes);
			this.indexes.addAll(right.indexes);
		}

		private Key(final int index) {
			this.indexes = new TreeSet<Integer>();
			this.indexes.add(index);
		}

		public int size() {
			return indexes.size();
		}

		public int getRightIndex() {
			return indexes.last();
		}

		public int getOnlyIndex() {
			if (indexes.size() != 1) {
				throw new IllegalStateException("Index count is not 1");
			}
			return indexes.last();
		}

		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			return indexes.equals(((Key)o).indexes);
		}

		@Override
		public int hashCode() {
			return indexes.hashCode();
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			for (final int i: indexes) {
				if (sb.length() > 0) {
					sb.append(',');
				}
				sb.append(i);
			}
			return '{' + sb.toString() + '}';
		}
	}
}
