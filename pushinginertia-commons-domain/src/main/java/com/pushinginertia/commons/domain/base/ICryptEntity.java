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
package com.pushinginertia.commons.domain.base;

import com.pushinginertia.commons.domain.service.ICryptService;

/**
 * Any entity implementing this interface will be injected with an instance of
 * {@link com.pushinginertia.commons.domain.service.ICryptService} when loaded, so
 * that values can be encrypted and decrypted.
 */
public interface ICryptEntity {
	/**
	 * Injects an {@link com.pushinginertia.commons.domain.service.ICryptService}
	 * bean into the class implementing this interface.
	 * @param cryptService bean to inject
	 */
	public void setCryptService(ICryptService cryptService);
}
