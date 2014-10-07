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
package com.pushinginertia.commons.collect.typeahead;

/**
 *
 */
public class TstNodeChar extends TstNode {
	private final char nodeChar;

	protected TstNodeChar(final char nodeChar) {
		this.nodeChar = nodeChar;
	}

	@Override
	protected char getFirstChar() {
		return nodeChar;
	}

	@Override
	protected boolean isStringFragment() {
		return false;
	}

	@Override
	protected int getCommonCharCount(final String search) {
		if (search.length() > 0 && nodeChar == search.charAt(0)) {
			return 1;
		}
		return 0;
	}

	@Override
	protected String shortenFragment(int length) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected int fragmentLength() {
		return 1;
	}

	@Override
	protected boolean isPrefixOf(String superString) {
		return superString.length() > 0 && superString.charAt(0) == nodeChar;
	}

	@Override
	protected boolean startsWith(String prefix) {
		return prefix.length() == 0 || prefix.charAt(0) == nodeChar;
	}
}
