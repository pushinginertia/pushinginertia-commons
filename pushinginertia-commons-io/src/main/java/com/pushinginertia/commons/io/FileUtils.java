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
	 * @param file specifies the file's path and name
	 * @param bytes byte array to write to disk
	 * @throws java.io.IOException if the file cannot be written
	 */
	public static void writeFileToDisk(final File file, final byte[] bytes)
	throws IOException {
		LOG.info("Saving {} byte file to disk: {}", bytes.length, file.getAbsolutePath());
		// TODO: make it configurable if an existing file should be overwritten or not

		final OutputStream out = new FileOutputStream(file);
		out.write(bytes);
		out.close();
	}
}