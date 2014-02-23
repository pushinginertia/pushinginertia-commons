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
package com.pushinginertia.commons.domain.listener;

import com.pushinginertia.commons.domain.base.ICryptEntity;
import com.pushinginertia.commons.domain.service.ICryptService;
import org.hibernate.event.PreLoadEvent;
import org.hibernate.event.PreLoadEventListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Fires when an entity is loaded from the database, before values are injected. The purpose is to inject services into
 * entities that require them to work with specific data, such as crypt operations. This must be configured in the
 * hibernate sessionFactory bean as follows.
 * <pre>
 * &lt;bean id="sessionFactory" class="org.springframework.orm.hibernate3.LocalSessionFactoryBean"&gt;
 *     &lt;property name="eventListeners"&gt;
 *         &lt;map&gt;
 *             &lt;entry key="pre-load"&gt;
 *                 &lt;bean class="com.pushinginertia.commons.domain.listener.HibernatePreLoadListener"/&gt;
 *             &lt;/entry&gt;
 *         &lt;/map&gt;
 *     &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre&gt;
 */
public class HibernatePreLoadListener implements PreLoadEventListener {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICryptService cryptService;

	public HibernatePreLoadListener() {
	}

	public void onPreLoad(final PreLoadEvent event) {
		final Object entity = event.getEntity();
		if (entity instanceof ICryptEntity) {
			((ICryptEntity)entity).setCryptService(cryptService);
		}
	}
}
