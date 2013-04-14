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
package com.pushinginertia.commons.lang;

/**
 * Methods that deal with byte manipulation.
 */
public class ByteUtils {
	/**
	 * Converts an int to a byte array of length 4
	 * @param v int value to convert
	 * @return a byte array of length 4
	 */
	public static byte[] intToByteArray(int v) {
		final byte[] writeBuffer = new byte[4];
		writeBuffer[0] = (byte)(v >>> 24);
		writeBuffer[1] = (byte)(v >>> 16);
		writeBuffer[2] = (byte)(v >>>  8);
		writeBuffer[3] = (byte)(v >>>  0);
		return writeBuffer;
	}

	/**
	 * Converts a long to a byte array of length 8
	 * @param v long value to convert
	 * @return a byte array of length 8
	 */
	public static byte[] longToByteArray(long v) {
		final byte[] writeBuffer = new byte[8];
		writeBuffer[0] = (byte)(v >>> 56);
		writeBuffer[1] = (byte)(v >>> 48);
		writeBuffer[2] = (byte)(v >>> 40);
		writeBuffer[3] = (byte)(v >>> 32);
		writeBuffer[4] = (byte)(v >>> 24);
		writeBuffer[5] = (byte)(v >>> 16);
		writeBuffer[6] = (byte)(v >>>  8);
		writeBuffer[7] = (byte)(v >>>  0);
		return writeBuffer;
	}

	/**
	 * Converts 8 bytes in a byte array to a long.
	 * @param by
	 * @param offset offset to start at in the byte array
	 * @return
	 */
	public static long byteArrayToLong(byte[] by, int offset) {
		int length = by.length - offset;
		if (length > 8)
			length = 8;
		long value = 0;
		for (int i = 0; i < length; i++) {
		   value = (value << 8) + (by[offset + i] & 0xff);
		}
		return value;
	}

	public static long byteArrayToLong(byte[] by) {
		return byteArrayToLong(by, 0);
	}
}
