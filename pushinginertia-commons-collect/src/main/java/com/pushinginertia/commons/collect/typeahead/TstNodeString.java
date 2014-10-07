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

import com.pushinginertia.commons.lang.StringUtils;

/**
 *
 */
public class TstNodeString<S extends StringSearchable> extends TstNode<S> {
	private String fragment;

	protected TstNodeString(final String fragment) {
		this.fragment = fragment;
	}

	@Override
	protected char getFirstChar() {
		return fragment.charAt(0);
	}

	@Override
	protected boolean isStringFragment() {
		return true;
	}

	@Override
	protected int getCommonCharCount(final String search) {
		return StringUtils.longestCommonPrefixLength(fragment, search);
	}

	@Override
	protected String shortenFragment(final int length) {
		final String removedSubstring = fragment.substring(length);
		fragment = fragment.substring(0, length);
		return removedSubstring;
	}

	@Override
	protected int fragmentLength() {
		return fragment.length();
	}

	@Override
	protected boolean isPrefixOf(String superString) {
		return superString.startsWith(fragment);
	}

	@Override
	protected boolean startsWith(final String prefix) {
		return fragment.startsWith(prefix);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("TstNode{").append(fragment);
		if (left != null) {
			sb.append(", left=").append(left.getFirstChar());
		}
		if (middle != null) {
			sb.append(", child=").append(middle.getFirstChar());
		}
		if (right != null) {
			sb.append(", right=").append(right.getFirstChar());
		}
		sb.append('}');
		return sb.toString();
	}
}
