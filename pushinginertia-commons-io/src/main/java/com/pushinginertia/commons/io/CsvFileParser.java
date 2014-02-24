/* Copyright (c) 2011-2014 Pushing Inertia
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

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstracts the logic of iterating a CSV file and transforming it into an object representation by providing an
 * iterable facade. The client code must implement a visitor that performs the logic of parsing the CSV data into an
 * object representation.
 */
public class CsvFileParser<T> implements Iterable<T> {
	private final ParserIterator iterator;

	public interface IParserVisitor<T> {
		/**
		 * Consumes a line of CSV data and produces an object representation for that line.
		 * @param lineNumber line number from the input file
		 * @param lineArray CSV data broken into an array of strings
		 * @param line line as it was read from the file (for inclusion in an exception message to aid troubleshooting)
		 * @return transformed object representation (must not be null)
		 */
		public T visit(int lineNumber, String[] lineArray, String line);
	}

	private class ParserIterator<T> implements Iterator<T> {
		private final BufferedReader br;
		private final IParserVisitor<T> visitor;
		private int lineNumber;
		private String nextLine;

		public ParserIterator(final InputStream is, final IParserVisitor<T> visitor) {
			this.visitor = visitor;
			this.br = new BufferedReader(new InputStreamReader(is));
			this.lineNumber = 0;
			readAhead();
		}

		private void readAhead() {
			nextLine = null;
			try {
				while (br.ready()) {
					lineNumber++;
					final String line = br.readLine().trim();
					if (!line.isEmpty() && !line.trim().startsWith("#")) {
						nextLine = line.trim();
						return;
					}
				}
			} catch (final IOException e) {
				throw new RuntimeException("Failed to read input from file.", e);
			}

			try {
				br.close();
			} catch (final IOException e) {
				throw new RuntimeException("Failed to close input.", e);
			}
		}

		@Override
		public boolean hasNext() {
			return nextLine != null;
		}

		@Override
		public T next() {
			if (nextLine == null) {
				throw new NoSuchElementException("End of file.");
			}

			final String[] lineArray = nextLine.split("\\s*,\\s*");
			final T object = visitor.visit(lineNumber, lineArray, nextLine);
			if (object == null) {
				throw new IllegalStateException(
						visitor.getClass() + " cannot return null for input line " +
						lineNumber + ": " + nextLine);
			}

			readAhead();
			return object;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Cannot remove from file.");
		}
	}

	private CsvFileParser(final InputStream is, final IParserVisitor<T> visitor) {
		ValidateAs.notNull(is, "is");
		this.iterator = new ParserIterator(is, visitor);
	}

	@Override
	public Iterator<T> iterator() {
		return iterator;
	}

	/**
	 * Creates an instance that reads from a file packaged in the root directory of the JAR file containing a given
	 * class.
	 * @param loadClass Class that is being loaded (used to identify the file name of the CSV file).
	 * @param visitor visitor class that transforms the CSV input
	 * @param <T> type of the object representation
	 * @return new instance with the file opened for reading
	 * @throws FileNotFoundException if the input file cannot be found
	 */
	public static <T> CsvFileParser<T> fromResource(final Class loadClass, final IParserVisitor<T> visitor)
	throws FileNotFoundException {
		ValidateAs.notNull(loadClass, "loadClass");
		ValidateAs.notNull(visitor, "visitor");
		final String className = loadClass.getSimpleName();
		final String resourceName = '/' + className + ".csv";
		final InputStream is = loadClass.getResourceAsStream(resourceName);
		if (is == null) {
			throw new FileNotFoundException("File not found on classpath: " + resourceName);
		}
		return new CsvFileParser(is, visitor);
	}

	/**
	 * Creates an instance that reads from a specified file.
	 * @param file file to read
	 * @param visitor visitor class that transforms the CSV input
	 * @param <T> type of the object representation
	 * @return new instance with the file opened for reading
	 * @throws FileNotFoundException if the input file cannot be found
	 */
	public static <T> CsvFileParser<T> fromFile(final File file, final IParserVisitor<T> visitor)
	throws FileNotFoundException {
		ValidateAs.notNull(file, "file");
		ValidateAs.notNull(visitor, "visitor");
		final InputStream is = new FileInputStream(file);
		return new CsvFileParser(is, visitor);
	}
}
