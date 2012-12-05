package com.pushinginertia.commons.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Common operations performed on files.
 */
public class FileUtils {
	private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

	/**
	 * Writes a given byte array to disk using the given file name.
	 * @param path path on the file system to write the file to
	 * @param fileName file name to create/overwrite
	 * @param bytes byte array to write to disk
	 * @throws java.io.IOException if the file cannot be written
	 */
	public static void writeFileToDisk(final String path, final String fileName, final byte[] bytes)
	throws IOException {
		final File f = new File(path, fileName);
		writeFileToDisk(f, bytes);
	}

	/**
	 * Writes a given byte array to disk using the given file name.
	 * @param file
	 * @param bytes byte array to write to disk
	 * @throws java.io.IOException if the file cannot be written
	 */
	public static void writeFileToDisk(final File file, final byte[] bytes)
	throws IOException {
		LOG.info("Saving file of {} bytes to disk: {}", bytes.length, file.getAbsolutePath());
		// TODO: make it configurable if an existing file should be overwritten or not

		final OutputStream out = new FileOutputStream(file);
		out.write(bytes);
		out.close();
	}

	/**
	 * Identifies the file extension for a given file name and returns the extension part, or an empty string if the
	 * file has no extension.
	 * @param fileName file name to parse
	 * @return never null
	 */
	public static String getFileExtension(final String fileName) {
		return getFileExtension(File.separatorChar, fileName);
	}

	/**
	 * Identifies the file extension for a given file name and returns the extension part, or an empty string if the
	 * file has no extension.
	 * @param dirSeparator character used to separate directories on the running OS
	 * @param fileName file name to parse
	 * @return never null
	 */
	public static String getFileExtension(final char dirSeparator, final String fileName) {
		final int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex < 0) {
			return "";
		}

		final int dirSeparatorIndex = fileName.lastIndexOf(dirSeparator);
		if (dotIndex < dirSeparatorIndex) {
			return "";
		}

		return fileName.substring(dotIndex + 1);
	}
}