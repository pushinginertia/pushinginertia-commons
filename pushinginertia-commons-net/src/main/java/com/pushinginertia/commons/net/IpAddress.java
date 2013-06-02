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
 * An object representation of an IP address that allows it to be represented in different formats.
 * Not thread-safe.
 */
public class IpAddress implements Serializable {
	private static final long serialVersionUID = -3005794221601450268L;

	private long ipNumber;
	private String ipAddress;

	/**
	 * Constructs the instance from a string representation of the IP address.
	 * @param ipAddress IPv4 address in n.n.n.n format
	 * @throws IllegalArgumentException if the input is not a valid IPv4 address
	 */
	public IpAddress(final String ipAddress) throws IllegalArgumentException {
		this.ipAddress = ValidateAs.notNull(ipAddress, "ipAddress");
		this.ipNumber = IpAddressUtils.toIpNumber(ipAddress);
	}

	public IpAddress(final long ipNumber) {
		this.ipNumber = ipNumber;
		this.ipAddress = null;
	}

	/**
	 * True if this IP address is lower (less than) than the given IP address.
	 * @param otherIpAddress other IP address in string n.n.n.n notation
	 * @return comparison result
	 */
	public boolean isLower(final String otherIpAddress) {
		return isLower(IpAddressUtils.toIpNumber(otherIpAddress));
	}

	/**
	 * True if this IP address is lower than the given IP address.
	 * @param other other IP address
	 * @return comparison result
	 */
	public boolean isLower(final IpAddress other) {
		return isLower(other.getIpNumber());
	}

	/**
	 * True if this IP address is lower than the given IP address.
	 * @param otherIpNumber IP number representation of the IP address
	 * @return comparison result
	 * @see IpAddressUtils#toIpNumber(String)
	 */
	public boolean isLower(final long otherIpNumber) {
		return ipNumber < otherIpNumber;
	}

	/**
	 * True if this IP address is higher than the given IP address.
	 * @param otherIpAddress other IP address in string n.n.n.n notation
	 * @return comparison result
	 */
	public boolean isHigher(final String otherIpAddress) {
		return isHigher(IpAddressUtils.toIpNumber(otherIpAddress));
	}

	/**
	 * True if this IP address is higher than the given IP address.
	 * @param other other IP address
	 * @return comparison result
	 */
	public boolean isHigher(final IpAddress other) {
		return isLower(other.getIpNumber());
	}

	/**
	 * True if this IP address is higher than the given IP address.
	 * @param otherIpNumber IP number representation of the IP address
	 * @return comparison result
	 * @see IpAddressUtils#toIpNumber(String)
	 */
	public boolean isHigher(final long otherIpNumber) {
		return ipNumber > otherIpNumber;
	}

	/**
	 * True if this IP address is between (inclusive) the given IP addresses, equal to the expression lo &lt;= this &lt;= hi.
	 * @param lo minimun
	 * @param hi maximum
	 * @return comparison result
	 */
	public boolean isBetween(final String lo, final String hi) {
		return isBetween(new IpAddress(lo), new IpAddress(hi));
	}

	/**
	 * True if this IP address is between (inclusive) the given IP addresses, equal to the expression lo &lt;= this &lt;= hi.
	 * @param lo minimun
	 * @param hi maximum
	 * @return comparison result
	 */
	public boolean isBetween(final IpAddress lo, final IpAddress hi) {
		ValidateAs.notNull(lo, "lo");
		ValidateAs.notNull(hi, "hi");
		return (lo.ipNumber <= this.ipNumber && this.ipNumber <= hi.ipNumber);
	}

	/**
	 * True if this IP address is equal to the given IP address.
	 * @param otherIpAddress other IP address in string n.n.n.n notation
	 * @return comparison result
	 */
	public boolean isEqual(final String otherIpAddress) {
		return isEqual(IpAddressUtils.toIpNumber(otherIpAddress));
	}

	/**
	 * True if this IP address is equal to the given IP address.
	 * @param other other IP address
	 * @return comparison result
	 */
	public boolean isEqual(final IpAddress other) {
		return isEqual(other.getIpNumber());
	}

	/**
	 * True if this IP address is equal to the given IP address.
	 * @param otherIpNumber IP number representation of the IP address
	 * @return comparison result
	 * @see IpAddressUtils#toIpNumber(String)
	 */
	public boolean isEqual(final long otherIpNumber) {
		return ipNumber == otherIpNumber;
	}

	public long getIpNumber() {
		return ipNumber;
	}

	public String getIpAddress() {
		if (ipAddress == null) {
			ipAddress = IpAddressUtils.toIpAddress(ipNumber);
		}
		return ipAddress;
	}

	@Override
	public String toString() {
		return "IpAddress{ipNumber=" + getIpNumber() + ", ipAddress=" + getIpAddress() + '}';
	}
}
