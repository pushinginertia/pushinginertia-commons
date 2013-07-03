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
package com.pushinginertia.commons.ui.util;

import com.pushinginertia.commons.core.validation.ValidateAs;

/**
 * Abstracts common logic when dealing with pagination.
 */
public final class PaginationUtils {
	/**
	 * Calculates the item number of the first item on a page of items.
	 * @param page page number (first page is 1, not 0)
	 * @param itemsPerPage number of items shown on each page
	 * @return item number of the first item on the page (where the first item is numbered 1, not 0)
	 */
	public static int calcFirstItemOnPage(final int page, final int itemsPerPage) {
		ValidateAs.positive(page, "page");
		return ((page - 1) * itemsPerPage) + 1;
	}

	private PaginationUtils() {}
}
