/* Copyright (c) 2011-2012 Pushing Inertia
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

import org.apache.commons.net.util.SubnetUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a range of IP addresses from a low to a high address, with the ability to convert to/from CIDR notation.
 */
public class IpAddressRange implements Serializable {
	private static final long serialVersionUID = 1L;

	private IpAddress lowAddress;
	private IpAddress highAddress;
	private List<String> cidrNotationList;

	protected IpAddressRange(final IpAddress lowAddress, final IpAddress highAddress) {
		this.lowAddress = lowAddress;
		this.highAddress = highAddress;
		this.cidrNotationList = null;
	}

	public static IpAddressRange fromIpAddressRange(final IpAddress lowAddress, final IpAddress highAddress) {
		return new IpAddressRange(lowAddress, highAddress);
	}

	public static IpAddressRange fromIpAddressRange(final String lowAddress, final String highAddress) {
		final IpAddress lo = new IpAddress(lowAddress);
		final IpAddress hi = new IpAddress(highAddress);
		return new IpAddressRange(lo, hi);
	}

	/**
	 * Parses an input string into an IP range. The input string must be of one of three forms:
	 * <ul>
	 *     <li>&lt;IPlo&gt;-&lt;IPhi&gt;</li>
	 *     <li>CIDR notation</li>
	 *     <li>single IP</li>
	 * </ul>
	 * @param rangeAsString string representation to parse
	 * @return parsed IP address range
	 * @throws IllegalArgumentException if the input cannot be parsed
	 */
	public static IpAddressRange parse(final String rangeAsString) throws IllegalArgumentException {
		final int hyphenIndex = rangeAsString.indexOf('-');
		if (hyphenIndex >= 0) {
			// expect two IP addresses separated by a hyphen
			final String ipLo = rangeAsString.substring(0, hyphenIndex).trim();
			final String ipHi = rangeAsString.substring(hyphenIndex + 1).trim();
			return IpAddressRange.fromIpAddressRange(ipLo, ipHi);
		}

		if (rangeAsString.contains("/")) {
			// expect CIDR notation
			return IpAddressRange.fromCidrNotation(rangeAsString);
		}

		// expect single IP address
		final IpAddress ip = new IpAddress(rangeAsString);
		return IpAddressRange.fromIpAddressRange(ip, ip);
	}

	protected IpAddressRange(final String cidrNotation) throws IllegalArgumentException {
		// 1. compute lo/hi addresses from given cidr block
		final SubnetUtils su = new SubnetUtils(cidrNotation);
		su.setInclusiveHostCount(true);
		final SubnetUtils.SubnetInfo info = su.getInfo();
		final String loa = info.getLowAddress();
		final String hia = info.getHighAddress();
		info.getCidrSignature();

		// 2. assign values
		this.cidrNotationList = Arrays.asList(cidrNotation);
		this.lowAddress = new IpAddress(loa);
		this.highAddress = new IpAddress(hia);
	}

	public static IpAddressRange fromCidrNotation(final String cidrNotation) {
		return new IpAddressRange(cidrNotation.trim());
	}

	/**
	 * Is the given IP address within this range?
	 * @param ipAddress address to compare in string n.n.n.n notation
	 * @return true if the given IP address is within this range
	 */
	public boolean isInRange(final String ipAddress) {
		return isInRange(IpAddressUtils.toIpNumber(ipAddress));
	}

	/**
	 * Is the given IP address within this range?
	 * @param ipAddress address to compare
	 * @return true if the given IP address is within this range
	 */
	public boolean isInRange(final IpAddress ipAddress) {
		return isInRange(ipAddress.getIpNumber());
	}

	/**
	 * Is the given IP address within this range?
	 * @param ipNumber IP number notation of the IP address
	 * @return true if the given IP address is within this range
	 */
	public boolean isInRange(final long ipNumber) {
		return lowAddress.getIpNumber() <= ipNumber && ipNumber <= highAddress.getIpNumber();
	}

	public IpAddress getLowAddress() {
		return lowAddress;
	}

	public IpAddress getHighAddress() {
		return highAddress;
	}

	public List<String> getCidrNotationList() {
		if (cidrNotationList == null) {
			cidrNotationList = IpAddressUtils.toCidrNotationList(lowAddress, highAddress);
		}
		return cidrNotationList;
	}

	@Override
	public String toString() {
		return "IpAddressRange{" +
				"lowAddress=" + lowAddress +
				", highAddress=" + highAddress +
				", cidrNotationList=" + cidrNotationList +
				'}';
	}
}
