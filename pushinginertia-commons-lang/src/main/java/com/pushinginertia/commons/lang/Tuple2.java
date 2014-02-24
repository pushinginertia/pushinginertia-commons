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

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.io.Serializable;

/**
 * A generic container for a set of two values.
 */
public class Tuple2<V1 extends Serializable, V2 extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;

	private final V1 v1;
	private final V2 v2;

	public Tuple2(final V1 v1, final V2 v2) {
		this.v1 = ValidateAs.notNull(v1, "v1");
		this.v2 = ValidateAs.notNull(v2, "v2");
	}

	public V1 getV1() {
		return v1;
	}

	public V2 getV2() {
		return v2;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Tuple2 tuple2 = (Tuple2)o;
		return v1.equals(tuple2.v1) && v2.equals(tuple2.v2);
	}

	@Override
	public int hashCode() {
		return 31 * v1.hashCode() + v2.hashCode();
	}

	@Override
	public String toString() {
		return "Tuple2{v1=" + v1 + ", v2=" + v2 + '}';
	}
}
