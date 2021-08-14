/* Copyright (c) 2011-2012 Pushing Inertia
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
package com.pushinginertia.commons.web;

import com.pushinginertia.commons.lang.Tuple2;
import org.junit.Assert;
import org.junit.Test;

public class SeoUtilsTest {
	@Test
	public void generateSlugNull() {
		Assert.assertNull(SeoUtils.generateSlug(null));
	}

	@Test
	public void generateSlugEN() {
		// English
		Assert.assertEquals("Someones-WebPage", SeoUtils.generateSlug("Someone's WebPage"));
		Assert.assertEquals("Someones-WebPage", SeoUtils.generateSlug(" Someone’s - WebPage-*- *"));
		Assert.assertEquals("My-Web-Page-1", SeoUtils.generateSlug("My Web Page = (#1)"));
	}

	@Test
	public void generateSlugKO() {
		// Korean
		Assert.assertEquals("지불-가능-최대예산", SeoUtils.generateSlug("지불 가능 최대예산"));
	}

	@Test
	public void generateSlugDE() {
		// German
		Assert.assertEquals(
				"Die-Stadt-verfügt-über-ein-gut-ausgebautes-Netz-von-Verkehrsmitteln-sodaß-ein-Auto-nicht-nötig-ist",
				SeoUtils.generateSlug("Die Stadt verfügt über ein gut ausgebautes Netz von Verkehrsmitteln, sodaß ein Auto nicht nötig ist."));
	}

	@Test
	public void generateSlugCN() {
		// Chinese
		Assert.assertEquals(
				"999-限-家住",
				SeoUtils.generateSlug("，$999，限 （家住）"));
	}

	@Test
	public void parseTwoWordSlug() {
		final Tuple2<String, String> slugs = SeoUtils.parseTwoWordSlug("abcd-efgh");
		Assert.assertEquals("abcd", slugs.getV1());
		Assert.assertEquals("efgh", slugs.getV2());
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTwoWordSlugNull() {
		SeoUtils.parseTwoWordSlug(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTwoWordSlugEmpty() {
		SeoUtils.parseTwoWordSlug("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTwoWordSlugNoHyphen() {
		SeoUtils.parseTwoWordSlug("abcd");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTwoWordSlugFirstEmpty() {
		SeoUtils.parseTwoWordSlug("-abcd");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTwoWordSlugSecondEmpty() {
		SeoUtils.parseTwoWordSlug("abcd-");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseTwoWordSlugTooMany() {
		SeoUtils.parseTwoWordSlug("abcd-efgh-ijkl");
	}
}
