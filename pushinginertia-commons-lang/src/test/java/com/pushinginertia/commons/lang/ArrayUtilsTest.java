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

import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilsTest {
	@Test
	public void prepend() {
		final String[] in = {"a","b"};
		final String[] out = ArrayUtils.prepend("item", in);
		Assert.assertEquals(3, out.length);
		Assert.assertEquals("item", out[0]);
		Assert.assertEquals("a", out[1]);
		Assert.assertEquals("b", out[2]);
	}
}
