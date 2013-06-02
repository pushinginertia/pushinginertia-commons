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
package com.pushinginertia.commons.core.init;

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Initializes a list from a CSV resource file packaged in the root directory of the JAR file containing a given class.
 */
public abstract class CsvResourceListInitializer<T> implements ListInitializer<T> {
	/**
	 * Class that is being loaded (used to identify the file name of the CSV file).
	 */
	private final Class classToInit;

	/**
	 * Initializes a list from a CSV resource file packaged in the root directory of the JAR file containing a given class.
	 * @param classToInit Class that is being loaded (used to identify the file name of the CSV file).
	 */
	protected CsvResourceListInitializer(final Class classToInit) {
		this.classToInit = ValidateAs.notNull(classToInit, "classToInit");
	}

	@Override
	public List<T> newList() {
		try {
			return innerInit();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Constructs the file name of the CSV resource to look up by using {@link #classToInit}.
	 * @return file name of file to read
	 */
	protected String getResourceName() {
		final String className = classToInit.getSimpleName();
		return '/' + className + ".csv";
	}

	/**
	 * Opens a new resource stream for the given file/resource name.
	 * @param resourceName name of resource to open
	 * @return null if the resource does not exist
	 */
	protected InputStream getResourceStream(final String resourceName) {
		return classToInit.getResourceAsStream(resourceName);
	}

	private List<T> innerInit() throws IOException {
		final String resourceName = getResourceName();
		final InputStream is = getResourceStream(resourceName);
		if (is == null) {
			throw new RuntimeException("Resource does not exist: " + resourceName);
		}

		final BufferedReader br = new BufferedReader(new InputStreamReader(is));
		int lineNumber = 0;
		final List<T> list = new ArrayList<T>();
		try {
			while (br.ready()) {
				lineNumber++;
				final String s = br.readLine().trim();
				if (!s.isEmpty() && !s.startsWith("#")) {
					final String[] ss = s.split("\\s*,\\s*");
					list.add(transformCsvData(lineNumber, ss, s));
				}
			}
			return list;
		} finally {
			br.close();
		}
	}

	protected abstract T transformCsvData(int lineNumber, String[] csvData, String rawData);
}
