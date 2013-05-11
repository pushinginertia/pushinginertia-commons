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
package com.pushinginertia.commons.lang.logging;

import org.junit.Assert;
import org.junit.Test;

public class TimerTest {
	private static class TestTimer extends Timer {
		private TestTimer() {
			super(0);
		}

		@Override
		protected long nanoTime() {
			return 1000000; // 1 elapsed second
		}
	}

	@Test
	public void elapsedMs() {
		final TestTimer t = new TestTimer();
		Assert.assertEquals(1, t.elapsedMs());
	}
}
