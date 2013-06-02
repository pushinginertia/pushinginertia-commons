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
package com.pushinginertia.commons.net;

import com.pushinginertia.commons.core.init.CsvResourceListInitializer;
import com.pushinginertia.commons.core.init.ListInitializer;
import edu.jwetherell.algorithms.dataStructures.IntervalTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * Contains a set of IP intervals, each mapping to a value that identifies the IP range.
 */
public class IpIntervalMappings<T> {
	private static final Logger LOG = LoggerFactory.getLogger(IpIntervalMappings.class);

	private final IntervalTree tree;

	public IpIntervalMappings(final ListInitializer<IntervalTree.IntervalData<T>> initializer) {
		final List<IntervalTree.IntervalData<T>> list = initializer.newList();
		tree = new IntervalTree<T>(list);
	}

	public T get(final IpAddress ipAddress) {
		final IntervalTree.IntervalData<T> data = tree.query(ipAddress.getIpNumber());
		if (data == null) {
			return null;
		}
		final Set<T> matches = data.matches();
		return matches.iterator().next();
	}

	/**
	 * Indicates if the given IP address exists in at least one interval in this mapping instance.
	 * @param ipAddress IP address to test
	 * @return true if an interval contains the given IP address
	 */
	public boolean exists(final IpAddress ipAddress) {
		final IntervalTree.IntervalData<T> data = tree.query(ipAddress.getIpNumber());
		return data != null;
	}

	/**
	 * A convenience class that initializes the dataset from a CSV file packaged within the root of the JAR file
	 * containing this class.
	 */
	public static class CsvInitializer extends CsvResourceListInitializer<IntervalTree.IntervalData<String>> {
		public CsvInitializer(final Class c) {
			super(c);
		}

		@Override
		protected IntervalTree.IntervalData<String> transformCsvData(int lineNumber, String[] csvData, final String rawData) {
			if (csvData.length < 2) {
				throw new IllegalArgumentException("Two fields are required on line " + lineNumber + ": " + rawData);
			}

			final String ipBlock = csvData[0];
			final String netName = csvData[1];
			final IpAddressRange range = IpAddressRange.parse(ipBlock);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Adding " + range + " => " + netName);
			}
			return new IntervalTree.IntervalData<String>(
					range.getLowAddress().getIpNumber(),
					range.getHighAddress().getIpNumber(),
					netName);
		}
	}
}
