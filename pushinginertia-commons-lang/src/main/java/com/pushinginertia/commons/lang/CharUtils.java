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
 * Character utility methods.
 */
public class CharUtils {
	/**
	 * identifies if a given character exists in the character array
	 * @param c character to search for
	 * @param arr array to search
	 * @return index of the character or -1 if not found
	 */
	public static int inCharArray(char c, char[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (c == arr[i])
				return i;
		}
		return -1;
	}
}
