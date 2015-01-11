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

import com.pushinginertia.commons.lang.ListUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NodeChildrenTest {
	public static class NodeTest implements NodeChildren.Node<NodeTest> {
		private final long id;
		private final NodeTest parent;
		private final NodeChildren<NodeTest> children = new NodeChildren<>();

		public NodeTest(final long id, final NodeTest parent) {
			this.id = id;
			this.parent = parent;
		}

		@Override
		public long getId() {
			return id;
		}

		@Override
		public NodeTest getParent() {
			return parent;
		}

		@Override
		public NodeChildren<NodeTest> getChildren() {
			return children;
		}
	}

	private static final NodeTest N_0 = new NodeTest(1, null);
	private static final NodeTest N_1 = new NodeTest(2, null);
	private static final NodeTest N_00 = new NodeTest(3, N_0);
	private static final NodeTest N_01 = new NodeTest(4, N_0);
	private static final NodeTest N_010 = new NodeTest(5, N_01);
	private static final NodeTest N_0100 = new NodeTest(6, N_010);

	@Test
	public void mapNodeTestChildren() {
		final List<NodeTest> list = new ArrayList<>();
		list.add(N_0);
		list.add(N_1);
		list.add(N_00);
		list.add(N_01);
		list.add(N_010);
		list.add(N_0100);

		// iterate 20 times with random ordering
		for (int i = 0; i < 20; i++) {
			final List<NodeTest> randomList = new ArrayList<>(ListUtils.randomSampleFloyd(list, list.size()));
			assertBlogCategoriesAreMappedToCorrectChildren(randomList);
		}
	}

	public void assertBlogCategoriesAreMappedToCorrectChildren(final List<NodeTest> list) {
		final NodeChildren<NodeTest> root = new NodeChildren<>(list);
		assertContains(root, N_0, N_1);
		assertContains(N_0.getChildren(), N_00, N_01);
		assertContains(N_1.getChildren());
		assertContains(N_00.getChildren());
		assertContains(N_01.getChildren(), N_010);
		assertContains(N_010.getChildren(), N_0100);
	}

	private void assertContains(final NodeChildren<NodeTest> nodeChildren, final NodeTest... nodes) {
		Assert.assertEquals(nodes.length, nodeChildren.size());
		for (final NodeTest node: nodes) {
			Assert.assertTrue(nodeChildren.contains(node));
		}
	}
}