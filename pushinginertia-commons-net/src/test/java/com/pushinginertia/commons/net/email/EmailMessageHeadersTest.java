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
package com.pushinginertia.commons.net.email;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class EmailMessageHeadersTest {
	@Test
	public void builder() {
		final EmailMessageHeaders.Builder builder = new EmailMessageHeaders.Builder();
		builder.add(EmailMessageHeaders.Builder.X_ORIGINATING_IP, "8.8.8.8");
		builder.add(EmailMessageHeaders.Builder.X_ORIGINATING_COUNTRY, "US");
		final EmailMessageHeaders headers = builder.build();
		final Map<String, String> map = headers.getHeaders();
		Assert.assertEquals(2, map.size());
		Assert.assertTrue(map.containsKey(EmailMessageHeaders.Builder.X_ORIGINATING_IP));
		Assert.assertTrue(map.containsKey(EmailMessageHeaders.Builder.X_ORIGINATING_COUNTRY));
		Assert.assertEquals("8.8.8.8", map.get(EmailMessageHeaders.Builder.X_ORIGINATING_IP));
		Assert.assertEquals("US", map.get(EmailMessageHeaders.Builder.X_ORIGINATING_COUNTRY));
	}
}
