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
package com.pushinginertia.commons.net;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class IpIntervalMappingsTest {
	@Test
	public void initializer() throws IOException {
		final IpIntervalMappings.CsvInitializer initializer = new IpIntervalMappings.CsvInitializer(IpIntervalMappings.class);
		final IpIntervalMappings<String> f = new IpIntervalMappings<>(initializer);

		Assert.assertEquals("Google", f.get(new IpAddress("66.249.64.0")));
		Assert.assertEquals("Google", f.get(new IpAddress("66.249.95.255")));
		Assert.assertNull(f.get(new IpAddress("66.249.96.0")));
		Assert.assertEquals("Baidu", f.get(new IpAddress("180.76.15.143")));
		Assert.assertNull(f.get(new IpAddress("1.1.1.1")));
	}
}
