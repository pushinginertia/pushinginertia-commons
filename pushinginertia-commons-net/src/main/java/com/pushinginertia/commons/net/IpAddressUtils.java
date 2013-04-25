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

import com.pushinginertia.commons.lang.Tuple2;
import com.pushinginertia.commons.lang.ValidateAs;

import java.util.ArrayList;
import java.util.List;

/**
 * Static utility methods for IP address handling.
 */
public class IpAddressUtils {
	private static final int[] CIDR2MASK =
			new int[]{
					0x00000000, 0x80000000, 0xC0000000, 0xE0000000, 0xF0000000, 0xF8000000, 0xFC000000, 0xFE000000,
					0xFF000000, 0xFF800000, 0xFFC00000, 0xFFE00000, 0xFFF00000, 0xFFF80000, 0xFFFC0000, 0xFFFE0000,
					0xFFFF0000, 0xFFFF8000, 0xFFFFC000, 0xFFFFE000, 0xFFFFF000, 0xFFFFF800, 0xFFFFFC00, 0xFFFFFE00,
					0xFFFFFF00, 0xFFFFFF80, 0xFFFFFFC0, 0xFFFFFFE0, 0xFFFFFFF0, 0xFFFFFFF8, 0xFFFFFFFC, 0xFFFFFFFE,
					0xFFFFFFFF};

	/**
	 * Defines the ranges of non-routable IP addresses.
	 */
	private static final List<Tuple2<IpAddress, IpAddress>> NON_ROUTABLE_IPS = new ArrayList<Tuple2<IpAddress, IpAddress>>();
	static {
		NON_ROUTABLE_IPS.add(new Tuple2<IpAddress, IpAddress>(new IpAddress("10.0.0.0"), new IpAddress("10.255.255.255")));
		NON_ROUTABLE_IPS.add(new Tuple2<IpAddress, IpAddress>(new IpAddress("172.16.0.0"), new IpAddress("172.31.255.255")));
		NON_ROUTABLE_IPS.add(new Tuple2<IpAddress, IpAddress>(new IpAddress("192.168.0.0"), new IpAddress("192.168.255.255")));
	}


	/**
	 * Converts an IP address to an IP number for efficient database lookups.
	 * @param ipAddress IPv4 address with four octets separated by dots
	 * @return IP number representation
	 * @throws IllegalArgumentException if the input is not a valid IPv4 address or is null
	 */
	public static long toIpNumber(final String ipAddress) throws IllegalArgumentException {
		if (ipAddress == null) {
			throw new IllegalArgumentException("ipAddress is null");
		}

		// 1. split string into octets
		final String[] s = ipAddress.split("\\.");
		if (s.length != 4) {
			throw new IllegalArgumentException("Not an IP address: " + ipAddress);
		}

		// 2. convert to number
		long out = 0;
		try {
			out += Long.parseLong(s[0]) << 24;
			out += Long.parseLong(s[1]) << 16;
			out += Long.parseLong(s[2]) << 8;
			out += Long.parseLong(s[3]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Not an IP address: " + ipAddress);
		}
		return out;
	}

	/**
	 * Converts an IP number to an IP address.
	 * @param ipNumber numeric representation of the IP address
	 * @return IPv4 address
	 */
	public static String toIpAddress(final long ipNumber) {
		final StringBuilder sb = new StringBuilder();
		sb.append((ipNumber >> 24) & 0xff);
		sb.append('.');
		sb.append((ipNumber >> 16) & 0xff);
		sb.append('.');
		sb.append((ipNumber >> 8) & 0xff);
		sb.append('.');
		sb.append(ipNumber & 0xff);
		return sb.toString();
	}

//	/**
//	 * Converts an IP address in CIDR notation (n.n.n.n/n) into an IP number range.
//	 * @param cidr
//	 */
//	public static void cidrToIpNumberRange(final String cidr) {
//		// TODO
//	}

	/**
	 * Converts a range of IP addresses to a list of CIDR blocks in n.n.n.n/n notation.
	 * @param lowIpAddress lowest inclusive IP address in the range
	 * @param highIpAddress highest inclusive IP address in the range
	 * @return generated list
	 */
	public static List<String> toCidrNotationList(final String lowIpAddress, final String highIpAddress) {
		return toCidrNotationList(toIpNumber(lowIpAddress), toIpNumber(highIpAddress));
	}

	/**
	 * Converts a range of IP addresses to a list of CIDR blocks in n.n.n.n/n notation.
	 * @param lowIpAddress lowest inclusive IP address in the range
	 * @param highIpAddress highest inclusive IP address in the range
	 * @return generated list
	 */
	public static List<String> toCidrNotationList(final IpAddress lowIpAddress, final IpAddress highIpAddress) {
		return toCidrNotationList(lowIpAddress.getIpNumber(), highIpAddress.getIpNumber());
	}

	/**
	 * Converts a range of IP numbers to a list of CIDR blocks in n.n.n.n/n notation. Loosely based on the code sample
	 * found at: http://stackoverflow.com/questions/5020317/in-java-given-an-ip-address-range-return-the-minimum-list-of-cidr-blocks-that
	 *
	 * @param lowIpNumber lowest inclusive IP number in the range
	 * @param highIpNumber highest inclusive IP number in the range
	 * @return generated list
	 */
	public static List<String> toCidrNotationList(final long lowIpNumber, final long highIpNumber) {
		long curIpNumber = lowIpNumber;
		final List<String> pairs = new ArrayList<String>();
		while (highIpNumber >= curIpNumber) {
			byte maxsize = 32;
			while (maxsize > 0) {
				final long mask = CIDR2MASK[maxsize - 1];
				final long maskedBase = curIpNumber & mask;
				if (maskedBase != curIpNumber) {
					break;
				}

				maxsize--;
			}
			final double x = Math.log(highIpNumber - curIpNumber + 1) / Math.log(2);
			final byte maxdiff = (byte) (32 - Math.floor(x));
			if (maxsize < maxdiff) {
				maxsize = maxdiff;
			}
			final String ip = toIpAddress(curIpNumber);
			pairs.add(ip + "/" + maxsize);
			curIpNumber += Math.pow(2, (32 - maxsize));
		}
		return pairs;
	}

	/**
	 * Identifies if a given IP address is from the local host (127.0.0.1).
	 * @param ipAddress IP address to examine
	 * @return true iff the given IP address maps to localhost
	 */
	public static boolean isLocalhost(final IpAddress ipAddress) {
		ValidateAs.notNull(ipAddress, "ipAddress");
		return isLocalhost(ipAddress.getIpAddress());
	}

	/**
	 * Identifies if a given IP address is from the local host (127.0.0.1).
	 * @param ipAddress IP address to examine
	 * @return true iff the given IP address maps to localhost
	 */
	public static boolean isLocalhost(final String ipAddress) {
		return "127.0.0.1".equals(ipAddress);
	}

	/**
	 * Identifies if a given IP address is non-routable.
	 * @param ipAddress IP address to test
	 * @return true if non-routable
	 */
	public static boolean isNonRoutable(final IpAddress ipAddress) {
		for (final Tuple2<IpAddress, IpAddress> range: NON_ROUTABLE_IPS) {
			final IpAddress lo = range.getV1();
			final IpAddress hi = range.getV2();
			if (ipAddress.isBetween(lo, hi)) {
				return true;
			}
		}
		return false;
	}
}
