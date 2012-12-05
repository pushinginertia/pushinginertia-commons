package com.pushinginertia.commons.lang;

/**
 * Methods that deal with byte manipulation.
 */
public class ByteUtils {
	/**
	 * Converts a long to a byte array of length 8
	 * @param v
	 * @return a byte array of length 8
	 */
	public static byte[] longToByteArray(long v) {
		// http://stackoverflow.com/questions/2641150/how-to-convert-a-java-long-to-byte-for-cassandra
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
		// http://stackoverflow.com/questions/1026761/how-to-convert-a-byte-array-to-its-numeric-value-java
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
