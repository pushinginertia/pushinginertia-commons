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

import com.pushinginertia.commons.lang.Tuple2;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileParserTest {
	@Test
	public void iterate() throws FileNotFoundException {
		final CsvFileParser.IParserVisitor<Tuple2<String, String>> visitor = new CsvFileParser.IParserVisitor<Tuple2<String, String>>() {
			@Override
			public Tuple2<String, String> visit(final int lineNumber, final String[] lineArray, final String line) {
				return new Tuple2<String, String>(lineArray[0], lineArray[1]);
			}
		};

		final CsvFileParser<Tuple2<String, String>> parser = CsvFileParser.fromResource(CsvFileParserTest.class, visitor);

		final List<Tuple2<String, String>> list = new ArrayList<Tuple2<String, String>>();
		for (final Tuple2<String, String> object: parser) {
			list.add(object);
		}

		Assert.assertEquals(2, list.size());
		Assert.assertEquals(new Tuple2<String, String>("A1", "A2"), list.get(0));
		Assert.assertEquals(new Tuple2<String, String>("B1", "B2"), list.get(1));
	}
}
