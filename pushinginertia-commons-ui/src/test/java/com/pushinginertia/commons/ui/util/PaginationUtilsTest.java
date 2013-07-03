package com.pushinginertia.commons.ui.util;

import org.junit.Assert;
import org.junit.Test;

public class PaginationUtilsTest {
	@Test
	public void calcFirstItemOnPage() {
		Assert.assertEquals(1, PaginationUtils.calcFirstItemOnPage(1, 10));
		Assert.assertEquals(11, PaginationUtils.calcFirstItemOnPage(2, 10));
	}

	@Test(expected = IllegalArgumentException.class)
	public void calcFirstItemOnPageFail() {
		PaginationUtils.calcFirstItemOnPage(0, 10);
	}
}
