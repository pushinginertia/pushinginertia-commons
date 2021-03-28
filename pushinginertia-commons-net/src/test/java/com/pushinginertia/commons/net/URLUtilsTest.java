/* Copyright (c) 2011-2021 Pushing Inertia
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
package com.pushinginertia.commons.net;

import org.junit.Assert;
import org.junit.Test;

public class URLUtilsTest {
	@Test
	public void extractTopLevelDomainFromHost() {
		Assert.assertEquals("x.com", URLUtils.extractTopLevelDomainFromHost("x.com"));
		Assert.assertEquals("x.com", URLUtils.extractTopLevelDomainFromHost("www.x.com"));
		Assert.assertEquals("x.com", URLUtils.extractTopLevelDomainFromHost("subdomainX.subdomainY.x.com"));
		Assert.assertEquals("com", URLUtils.extractTopLevelDomainFromHost("com"));
		Assert.assertEquals(".com", URLUtils.extractTopLevelDomainFromHost("..com"));
	}

	@Test
	public void uriSchemeLength() {
		Assert.assertEquals(-1, URLUtils.uriSchemeLength(""));
		Assert.assertEquals(-1, URLUtils.uriSchemeLength("/absolute/path"));
		Assert.assertEquals(-1, URLUtils.uriSchemeLength("relative/path"));
		Assert.assertEquals(4, URLUtils.uriSchemeLength("http://www.example.com"));
		Assert.assertEquals(16, URLUtils.uriSchemeLength("chrome-extension://www.example.com"));
		Assert.assertEquals(9, URLUtils.uriSchemeLength("soap.beep://www.example.com"));
		Assert.assertEquals(6, URLUtils.uriSchemeLength("tn3270://www.example.com"));
	}

	@Test
	public void isPathRootRelative() {
		Assert.assertTrue(URLUtils.isPathRootRelative("/"));
		Assert.assertTrue(URLUtils.isPathRootRelative("/images/abc.png"));
		Assert.assertFalse(URLUtils.isPathRootRelative(""));
		Assert.assertFalse(URLUtils.isPathRootRelative("./abc.png"));
		Assert.assertFalse(URLUtils.isPathRootRelative("../abc.png"));
		Assert.assertFalse(URLUtils.isPathRootRelative("//host.com/images/abc.png"));
	}
}
