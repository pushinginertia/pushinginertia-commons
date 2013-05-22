/* Copyright (c) 2011-2013 Pushing Inertia
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
package com.pushinginertia.commons.lang;

import junit.framework.TestCase;

public class NormalizeStringCaseUtilsTest extends TestCase {
	public void testToTitleCase() {
		assertEquals("Abcd", NormalizeStringCaseUtils.toTitleCase("abcd"));
		assertEquals("Abcd", NormalizeStringCaseUtils.toTitleCase("ABCD"));
		assertEquals("Abcd", NormalizeStringCaseUtils.toTitleCase("aBcd"));
		assertEquals("Abcd Efgh", NormalizeStringCaseUtils.toTitleCase("abcd efgh"));
		assertEquals("Abcd Efgh", NormalizeStringCaseUtils.toTitleCase("ABCD EFGH"));
		assertEquals("Abcd Efgh", NormalizeStringCaseUtils.toTitleCase("aBcd eFgh"));
		assertEquals("Abcd (Efgh Ijkl)", NormalizeStringCaseUtils.toTitleCase("abcd (efgh ijkl)"));
		assertEquals("Abcd/Efgh", NormalizeStringCaseUtils.toTitleCase("abcd/efgh"));
	}

	public void testToSentenceCase() {
		assertEquals("Abcd", NormalizeStringCaseUtils.toSentenceCase("abcd"));
		assertEquals("Abcd", NormalizeStringCaseUtils.toSentenceCase("ABCD"));
		assertEquals("Abcd", NormalizeStringCaseUtils.toSentenceCase("aBcd"));
		assertEquals("Abcd efgh", NormalizeStringCaseUtils.toSentenceCase("abcd efgh"));
		assertEquals("Abcd efgh", NormalizeStringCaseUtils.toSentenceCase("ABCD EFGH"));
		assertEquals("Abcd efgh", NormalizeStringCaseUtils.toSentenceCase("aBcd eFgh"));
		assertEquals("Abcd efgh. Ijkl.", NormalizeStringCaseUtils.toSentenceCase("abcd efgh. ijkl."));
		assertEquals("Abcd efgh. Ijkl.", NormalizeStringCaseUtils.toSentenceCase("ABCD EFGH. IJKL."));
		assertEquals("Abcd efgh. Ijkl.", NormalizeStringCaseUtils.toSentenceCase("aBcd eFgh. ijKl."));
	}

	public void testSatisfiesCriteria() {
		assertEquals(5, NormalizeStringCaseUtils.minCharsForCriteria(5, 100));
		assertEquals(5, NormalizeStringCaseUtils.minCharsForCriteria(5, 95));
		assertEquals(5, NormalizeStringCaseUtils.minCharsForCriteria(5, 90));
		assertEquals(4, NormalizeStringCaseUtils.minCharsForCriteria(5, 85));
		assertEquals(4, NormalizeStringCaseUtils.minCharsForCriteria(5, 80));
		assertEquals(4, NormalizeStringCaseUtils.minCharsForCriteria(5, 75));
		assertEquals(4, NormalizeStringCaseUtils.minCharsForCriteria(5, 70));
		assertEquals(3, NormalizeStringCaseUtils.minCharsForCriteria(5, 65));
		assertEquals(3, NormalizeStringCaseUtils.minCharsForCriteria(5, 60));
	}

	public void testNormalizeCase() {
		assertEquals("Abcde Fghij", NormalizeStringCaseUtils.normalizeCase("ABCDE fghij", NormalizeStringCaseUtils.TargetCase.TITLE, NormalizeStringCaseUtils.Scope.PER_WORD, 100, 100));
		assertEquals("Abcde fGhij", NormalizeStringCaseUtils.normalizeCase("ABCDe fGhij", NormalizeStringCaseUtils.TargetCase.TITLE, NormalizeStringCaseUtils.Scope.PER_WORD, 80, 100));
		assertEquals("Abcde fghij.", NormalizeStringCaseUtils.normalizeCase("ABCDE FGHIJ.", NormalizeStringCaseUtils.TargetCase.SENTENCE, NormalizeStringCaseUtils.Scope.ENTIRE_STRING, 100, 100));
		assertEquals("Abcde fghij.", NormalizeStringCaseUtils.normalizeCase("abcde fghij.", NormalizeStringCaseUtils.TargetCase.SENTENCE, NormalizeStringCaseUtils.Scope.ENTIRE_STRING, 100, 100));
	}
}
