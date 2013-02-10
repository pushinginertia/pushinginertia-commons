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

import junit.framework.TestCase;

public class SeoUtilsTest extends TestCase {
	public void testGenerateSlug() {
		assertNull(SeoUtils.generateSlug(null));

		// English
		assertEquals("Someones-WebPage", SeoUtils.generateSlug("Someone's WebPage"));
		assertEquals("Someones-WebPage", SeoUtils.generateSlug(" Someone's - WebPage-*- *"));

		// Korean
		assertEquals("지불-가능-최대예산", SeoUtils.generateSlug("지불 가능 최대예산"));

		// German
		assertEquals(
				"Die-Stadt-verfügt-über-ein-gut-ausgebautes-Netz-von-Verkehrsmitteln-sodaß-ein-Auto-nicht-nötig-ist",
				SeoUtils.generateSlug("Die Stadt verfügt über ein gut ausgebautes Netz von Verkehrsmitteln, sodaß ein Auto nicht nötig ist."));
	}
}
