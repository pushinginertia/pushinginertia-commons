/* Copyright (c) 2011-2015 Pushing Inertia
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
package com.pushinginertia.commons.collect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A collection of children nodes for a parent node.
 * @param <T> Type of the node.
 */
public class NodeChildren<T extends NodeChildren.Node<T>> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Set<T> children;

	/**
	 * Methods that must exist in a node to support a parent/children relationship.
	 * @param <T> Type of the node.
	 */
	public interface Node<T extends Node<T>> {
		public long getId();
		public T getParent();
		public NodeChildren<T> getChildren();
	}

	public NodeChildren() {
	}

	/**
	 * Instantiates a new instance as a root node. This node will point to all given nodes with no parents, defined as
	 * {@link NodeChildren.Node#getParent()} == null. The remaining nodes with parents will be added as children in
	 * their parent nodes' {@link NodeChildren} instances.
	 * @param unsortedList A list of nodes. Each node must have its parent reference set, but the children references
	 *                     will be computed.
	 */
	public NodeChildren(final List<T> unsortedList) {
		final Map<Long, T> nodeMap = new HashMap<>();

		while (!unsortedList.isEmpty()) {
			for (final Iterator<T> itr = unsortedList.iterator(); itr.hasNext();) {
				final T node = itr.next();
				nodeMap.put(node.getId(), node);

				final T parentCategory = node.getParent();
				if (parentCategory != null) {
					final Long parentCategoryId = parentCategory.getId();
					if (nodeMap.containsKey(parentCategoryId)) {
						nodeMap.get(parentCategoryId).getChildren().addChild(node);
						itr.remove();
					}
				} else {
					addChild(node);
					itr.remove();
				}
			}
		}
	}

	public void addChild(final T child) {
		if (children == null) {
			children = new HashSet<>();
		}
		children.add(child);
	}

	public int size() {
		if (children == null) {
			return 0;
		}
		return children.size();
	}

	public boolean contains(final T child) {
		return children != null && children.contains(child);
	}

	public Set<T> getChildren() {
		return children;
	}

	/**
	 * Returns the children as a sorted list using the given comparator.
	 * @param comparator Defines the sort order.
	 * @return Sorted list (never null).
	 */
	public List<T> getChildrenSorted(final Comparator<T> comparator) {
		if (children == null) {
			return Collections.emptyList();
		}

		final List<T> list = new ArrayList<>(children);
		list.sort(comparator);
		return list;
	}
}
