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

import java.io.File;

/**
 * String-related manipulation of file names and paths.
 */
public class FileStringUtils {
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
