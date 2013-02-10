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
package com.pushinginertia.commons.ui.i18n;

import com.pushinginertia.commons.ui.i18n.I18nUtils;
import org.junit.Assert;
import org.junit.Test;

public class I18nUtilsTest {
	@Test
	public void generateResourceLookupKey() {
		Assert.assertEquals("prefix.One", I18nUtils.generateResourceLookupKey("prefix", true));
		Assert.assertEquals("prefix.Many", I18nUtils.generateResourceLookupKey("prefix", false));
	}

	@Test
	public void generateResourceLookupKeyManyThreshold() {
		Assert.assertEquals("prefix.0", I18nUtils.generateResourceLookupKeyManyThreshold("prefix", 2, 0));
		Assert.assertEquals("prefix.1", I18nUtils.generateResourceLookupKeyManyThreshold("prefix", 2, 1));
		Assert.assertEquals("prefix.Many", I18nUtils.generateResourceLookupKeyManyThreshold("prefix", 2, 2));
	}
}
