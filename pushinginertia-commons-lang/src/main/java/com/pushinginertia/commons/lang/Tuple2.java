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

import java.io.Serializable;

/**
 * A generic container for a set of two values.
 */
public class Tuple2<V1 extends Serializable, V2 extends Serializable> implements Serializable {
	private final V1 v1;
	private final V2 v2;

	public Tuple2(final V1 v1, final V2 v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	public V1 getV1() {
		return v1;
	}

	public V2 getV2() {
		return v2;
	}

	@Override
	public String toString() {
		return "Tuple2{v1=" + v1 + ", v2=" + v2 + '}';
	}
}
