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

import java.io.Serializable;

/**
 * Encapsulates an IP address and the 2-character ISO country code the IP address belongs to.
 */
public class IpAddressAndCountry implements Serializable {
	private static final long serialVersionUID = 1L;

	private final IpAddress ipAddress;
	private final String countryCode;

	/**
	 * Encapsulates an IP address and the 2-character ISO country code the IP address belongs to.
	 * @param ipAddress IP address
	 * @param countryCode 2-character country code (can be null, will be transformed to uppercase)
	 */
	public IpAddressAndCountry(final IpAddress ipAddress, final String countryCode) {
		this.ipAddress = ValidateAs.notNull(ipAddress, "ipAddress");
		if (countryCode == null) {
			this.countryCode = null;
		} else {
			ValidateAs.ofLength(countryCode, 2, "countryCode");
			this.countryCode = countryCode.toUpperCase();
		}
	}

	/**
	 * Encapsulates an IP address and the 2-character ISO country code the IP address belongs to.
	 * @param ipAddress IP address
	 * @param countryCode 2-character country code (can be null, will be transformed to uppercase)
	 */
	public IpAddressAndCountry(final String ipAddress, final String countryCode) {
		this(new IpAddress(ipAddress), countryCode);
	}

	public IpAddress getIpAddress() {
		return ipAddress;
	}

	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final IpAddressAndCountry that = (IpAddressAndCountry)o;
		if (countryCode != null ? !countryCode.equals(that.countryCode) : that.countryCode != null) {
			return false;
		}
		return ipAddress.equals(that.ipAddress);
	}

	@Override
	public int hashCode() {
		int result = ipAddress.hashCode();
		result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "IpAddressAndCountry{" + "ipAddress=" + ipAddress + ", countryCode=" + countryCode + '}';
	}
}
