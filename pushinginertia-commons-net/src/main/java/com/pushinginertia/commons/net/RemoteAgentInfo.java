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

import com.pushinginertia.commons.core.validation.ValidateAs;
import com.pushinginertia.commons.net.util.HttpServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * An bean that represents data found in an {@link javax.servlet.http.HttpServletRequest}.
 */
public class RemoteAgentInfo implements Serializable {
	private IpAddress ipAddress;
	private String userAgent;

	public RemoteAgentInfo(final HttpServletRequest req) {
		ValidateAs.notNull(req, "req");
		this.ipAddress = new IpAddress(HttpServletRequestUtils.getRemoteIpAddress(req));
		this.userAgent = HttpServletRequestUtils.getUserAgent(req);
	}

	public RemoteAgentInfo(final IpAddress ipAddress, final String userAgent) {
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
	}

	public IpAddress getIpAddress() {
		return ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}
}
