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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Ternary Search Tree.
 *
 * Source: http://trasahin.blogspot.co.uk/2012/06/concurrent-ternary-search-tree.html
 *
 * @see <a href="http://www.drdobbs.com/database/184410528">drdobbs</a>
 * @see <a href="http://en.wikipedia.org/wiki/Ternary_search_tree">Ternary search tree</a>
 */
public abstract class TstNode<S extends StringSearchable> {
	/**
	 * An interface to provide a visitor pattern when searching the tree.
	 */
	public interface NodeVisitor<S extends StringSearchable> {
		/**
		 * Called whenever a match is found.
		 * @param searchable Matching searchable instance.
		 * @param searchString Search string that this result matches.
		 */
		public void visit(S searchable, String searchString);
	}


	/**
	 * Left neighbour of the current node. This is considered to be the same index of the
	 */
	protected TstNode<S> left = null;
	protected TstNode<S> right = null;
	protected TstNode<S> middle = null;
	/**
	 * References all searchables that terminate at this node.
	 */
	protected List<S> matches = null;

	protected TstNode() {
	}

	public static <S extends StringSearchable> TstNode newRoot(final String word, final S searchable) {
		final TstNode<S> root = new TstNodeString<S>(word);
		root.addMatch(searchable);
		return root;
	}

	private void addMatch(final S searchable) {
		if (matches == null) {
			matches = new ArrayList<S>();
		}
		matches.add(searchable);
	}

	protected abstract char getFirstChar();
	protected abstract boolean isStringFragment();
	protected abstract int getCommonCharCount(final String search);

	/**
	 * Shortens the fragment to the given length, returning the substring that was removed.
	 * @param length Length to shorten the fragment to.
	 * @return Substring that was removed.
	 */
	protected abstract String shortenFragment(final int length);
	protected abstract int fragmentLength();

	/**
	 * Indicates if the text contained in this node matches the first characters in the given string. That is, the
	 * text contained in this node is a substring of the given string at its first index.
	 * @param superString Superstring to compare.
	 * @return Indicator.
	 */
	protected abstract boolean isPrefixOf(String superString);
	protected abstract boolean startsWith(String prefix);

	/**
	 * Finds all strings that match a given prefix.
	 * @param prefix Prefix to match against.
	 * @param visitor Visitor instance that will be called for each {@link StringSearchable} matching the given prefix.
	 */
	public void searchPrefix(final String prefix, final NodeVisitor<S> visitor) {
		// find the first node in the tree that matches the prefix
		final TstNode<S> root = innerSearchPrefix(prefix);
		if (root == null) {
			return;
		}

		// we want to omit the left/right children on the first match so add the middle child to the queue
		if (root.matches != null) {
			for (final S searchable: root.matches) {
				visitor.visit(searchable, prefix);
			}
		}
		if (root.middle == null) {
			return;
		}

		final Queue<TstNode<S>> q = new LinkedList<TstNode<S>>();
		q.add(root.middle);
		while (!q.isEmpty()) {
			final TstNode<S> node = q.poll();
			if (node.left != null) {
				q.add(node.left);
			}
			if (node.middle != null) {
				q.add(node.middle);
			}
			if (node.right != null) {
				q.add(node.right);
			}
			if (node.matches != null) {
				for (final S searchable: node.matches) {
					visitor.visit(searchable, prefix);
				}
			}
		}
	}

	/**
	 * Finds all strings that match a given prefix.
	 * @param prefix Prefix to match against.
	 * @return List of all searchables containing a string with the given prefix.
	 */
	private TstNode<S> innerSearchPrefix(final String prefix) {
		TstNode<S> node = this;
		int i = 0;
		while (node != null) {
			final char searchChar = prefix.charAt(i);
			final char nodeChar = node.getFirstChar();
			if (searchChar < nodeChar) {
				node = node.left;
			} else if (searchChar == nodeChar) {
				final String remainingWord = prefix.substring(i);
				// node = "abc"
				// remainingWord:
				// "a"    matches
				// "ab"   matches
				// "abc"  matches
				// "abcd" continue to traverse further (node is prefix of remainingWord)
				// "ax"   fail no match
				if (node.isPrefixOf(remainingWord)) {
					// consume the length of this node and continue traversing
					i += node.fragmentLength();
					if (prefix.length() == i) {
						return node;
					}
					node = node.middle;
				} else if (node.startsWith(remainingWord)) {
					// partial match
					return node;
				} else {
					// no match
					return null;
				}
			} else {
				node = node.right;
			}
		}

		// not found
		return null;
	}

	public void searchExactMatch(final String searchString, final NodeVisitor<S> visitor) {
		TstNode<S> node = this;
		int i = 0;
		while (node != null) {
			final char searchChar = searchString.charAt(i);
			final char nodeChar = node.getFirstChar();
			if (searchChar < nodeChar) {
				node = node.left;
			} else if (searchChar == nodeChar) {
				final String remainingWord = searchString.substring(i);
				if (!node.isPrefixOf(remainingWord)) {
					return;
				}
				i += node.fragmentLength();
				if (searchString.length() == i) {
					if (node.matches != null) {
						for (final S searchable: node.matches) {
							visitor.visit(searchable, searchString);
						}
					}
					return;
				}
				node = node.middle;
			} else {
				node = node.right;
			}
		}
	}

	public TstNode insert(final String word, final S searchable) {
		int i = 0;
		char ch;
		TstNode<S> node = this;  // start with the root (this)
		while (i < word.length()) {
			ch = word.charAt(i);
			char nodeChar = node.getFirstChar();
			if (ch < nodeChar) {
				final String remainingFragment = word.substring(i);
				node = node.insertLeft(remainingFragment);
			} else if (ch > nodeChar) {
				final String remainingFragment = word.substring(i);
				node = node.insertRight(remainingFragment);
			} else {
				// does this node represent a char or string fragment?
				if (isStringFragment()) {
					// we know the first character matches this node, but how many more characters match?
					// identify fragment that hasn't yet been consumed
					final String remainingFragment = word.substring(i);
					// replace the fragment to only what's common
					final int commonCharCount = node.getCommonCharCount(remainingFragment);
					i += commonCharCount;
					if (i == word.length()) {
						node.addMatch(searchable);
						break;
					}
					if (node.fragmentLength() > commonCharCount) {
						node = node.split(commonCharCount);
					} else {
						node = node.insertMiddle(remainingFragment.substring(commonCharCount));
						// TODO: add count as parameter and do substring in method
					}
				}
			}
		}
		return node;
	}

	private TstNode<S> insertMiddle(final String fragment) {
		if (middle == null) {
			middle = new TstNodeString<S>(fragment);
		}
		return middle;
	}

	private TstNode<S> insertLeft(final String fragment) {
		if (left == null) {
			left = new TstNodeString<S>(fragment);
		}
		return left;
	}

	private TstNode<S> insertRight(final String fragment) {
		if (right == null) {
			right = new TstNodeString<S>(fragment);
		}
		return right;
	}

	/**
	 * Split this node into two. This node contains the characters common to what the node already contains and the
	 * fragment being inserted. A new child is added with the characters removed from this node (and its child is set to
	 * this node's child).
	 * <p>
	 * Insert a new child for what was removed.
	 * <pre>
	 * FROM:
	 * this node: "fraser international college"
	 * inserting: "fic at sfu"
	 *
	 * TO:
	 * this node: "f"
	 * child:     "raser international college"
	 * child.left "ic at sfu"
	 * </pre>
	 * @param length Number of characters to remove from this node's string.
	 * @return New middle node.
	 */
	private TstNode<S> split(final int length) {
		final String removedSubstring = shortenFragment(length);
		final TstNode<S> existingMiddle = middle;
		middle = new TstNodeString<S>(removedSubstring);
		middle.matches = matches;
		middle.middle = existingMiddle;
		matches = null;
		return middle;
	}
}
