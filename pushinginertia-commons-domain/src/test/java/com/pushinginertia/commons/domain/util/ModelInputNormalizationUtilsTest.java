package com.pushinginertia.commons.domain.util;

import org.junit.Assert;
import org.junit.Test;

public class ModelInputNormalizationUtilsTest {
	@Test
	public void removeDupes() {
		Assert.assertArrayEquals(
				new String[]{"", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("", "Last")
		);
		Assert.assertArrayEquals(
				new String[]{"First", ""},
				ModelInputNormalizationUtils.removeNameDupes("First", "")
		);
		Assert.assertArrayEquals(
				new String[]{"First", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("First", "Last")
		);
		Assert.assertArrayEquals(
				new String[]{"First", "First"},
				ModelInputNormalizationUtils.removeNameDupes("First", "First")
		);
		Assert.assertArrayEquals(
				new String[]{"First", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("First Last", "Last")
		);
		Assert.assertArrayEquals(
				new String[]{"First", "First"},
				ModelInputNormalizationUtils.removeNameDupes("First First", "First")
		);
		Assert.assertArrayEquals(
				new String[]{"First Middle", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("First Middle", "Last")
		);
		Assert.assertArrayEquals(
				new String[]{"First Middle", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("First Middle Last", "Last")
		);
		Assert.assertArrayEquals(
				new String[]{"First Last-Last", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("First Last-Last", "Last")
		);
		Assert.assertArrayEquals(
				new String[]{"First", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("First", "First Last")
		);
		Assert.assertArrayEquals(
				new String[]{"Last", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("Last", "Last Last")
		);
		Assert.assertArrayEquals(
				new String[]{"Last", "Last"},
				ModelInputNormalizationUtils.removeNameDupes("Last", "Last Last")
		);
	}
}