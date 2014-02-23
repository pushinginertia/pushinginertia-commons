/* Copyright (c) 2011-2014 Pushing Inertia
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
package com.pushinginertia.commons.domain.service;

/**
 * Specification for a service that provides encryption and decryption operations.
 */
public interface ICryptService {
	/**
	 * Encrypts a string and returns a base64 representation of the encrypted byte array.
	 * @param s string to encrypt (must not be null)
	 * @return encrypted string as a base64 representation
	 * @throws CryptException if a failure occurs during encryption (this wraps any exception thrown by a crypt library)
	 */
	public String encryptToBase64(String s) throws CryptException;

	/**
	 * Decrypts a base64 encoded string.
	 * @param encryptedBase64 encrypted string as a base64 representation of a byte array (must not be null)
	 * @return decrypted string
	 * @throws CryptException if a failure occurs during encryption (this wraps any exception thrown by a crypt library)
	 */
	public String decryptFromBase64(String encryptedBase64) throws CryptException;

	/**
	 * Encapsulates the various exceptions that might be thrown when attempting a crypt operation
	 */
	public class CryptException extends Exception {
		private static final long serialVersionUID = 1L;

		public CryptException(final String message, final Throwable cause) {
			super(message, cause);
		}
	}
}
