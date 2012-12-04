package com.pushinginertia.commons.web;

import junit.framework.TestCase;

/**
 */
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
