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

import org.joda.time.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Provides a very simple timer for logging the time it takes to complete units of work.
 */
public class Timer {
	private final long startNs;

	/**
	 * Provides a very simple timer for logging the time it takes to complete units of work.
	 */
	public Timer() {
		this.startNs = nanoTime();
	}

	/**
	 * Package rotected method available to initialize the start time in a unit test.
	 * @param startNs
	 */
	Timer(final long startNs) {
		this.startNs = startNs;
	}

	/**
	 * The elapsed time in milliseconds since this object was instantiated.
	 * @return elapsed time in milliseconds
	 */
	public long elapsedMs() {
		final long elapsedNs = nanoTime() - startNs;
		return TimeUnit.NANOSECONDS.toMillis(elapsedNs);
	}

	/**
	 * Creates a {@link Duration} instance containing the elapsed milliseconds since this object was instantiated.
	 * @return new immutable instance
	 */
	public Duration toDuration() {
		return new Duration(elapsedMs());
	}

	/**
	 * Returns the current nanotime by calling {@link System#nanoTime()}. Wrapped in a method for easy overriding in a
	 * unit test.
	 * @return value of {@link System#nanoTime()}
	 */
	protected long nanoTime() {
		return System.nanoTime();
	}
}
