package com.pushinginertia.commons.io;

import junit.framework.TestCase;

public class FileStringUtilsTest extends TestCase {
	public void testGetFileExtension() {
		assertEquals("", FileUtils.getFileExtension('/', "README"));
		assertEquals("", FileUtils.getFileExtension('/', "/README"));
		assertEquals("", FileUtils.getFileExtension('/', "/dir.1/README"));

		assertEquals("txt", FileUtils.getFileExtension('/', "readme.txt"));
		assertEquals("txt", FileUtils.getFileExtension('/', "/readme.txt"));
		assertEquals("txt", FileUtils.getFileExtension('/', "/dir.1/readme.txt"));

		assertEquals("gz", FileUtils.getFileExtension('/', "file.tar.gz"));
		assertEquals("gz", FileUtils.getFileExtension('/', "/file.tar.gz"));
		assertEquals("gz", FileUtils.getFileExtension('/', "/dir.1/file.tar.gz"));
	}
}
