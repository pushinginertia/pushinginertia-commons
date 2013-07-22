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
package com.pushinginertia.commons.core;

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Accepts a list of static regular expressions that are then applied to input strings to test for a positive match.
 * This can be used when a single regular expression is not sufficient to match a string.
 */
public class RegexMatchSet implements Serializable {
	private static final long serialVersionUID = 1L;

	private final List<Pattern> dataSet;

	public RegexMatchSet(final Collection<String> dataSet) {
		ValidateAs.notNull(dataSet, "dataSet");
		this.dataSet = new ArrayList<Pattern>(dataSet.size());
		for (final String regex: dataSet) {
			final Pattern pattern = Pattern.compile(regex);
			this.dataSet.add(pattern);
		}
	}

	public RegexMatchSet(final String... regexValues) {
		dataSet = new ArrayList<Pattern>(regexValues.length);
		for (final String regex: regexValues) {
			final Pattern pattern = Pattern.compile(regex);
			dataSet.add(pattern);
		}
	}

	/**
	 * Returns the number of regular expressions contained in this instance.
	 * @return regular expression count
	 */
	public int count() {
		return dataSet.size();
	}

	/**
	 * Tests if a given string matches one of the regular expressions contained within this instance.
	 * @param s string to test
	 * @return true when the given string matches a regular expression
	 */
	public boolean matches(final String s) {
		for (final Pattern regex: dataSet) {
			final Matcher m = regex.matcher(s);
			if (m.matches()) {
				return true;
			}
		}
		return false;
	}
}
