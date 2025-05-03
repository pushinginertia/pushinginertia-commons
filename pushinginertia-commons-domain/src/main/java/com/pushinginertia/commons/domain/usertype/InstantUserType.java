/* Copyright (c) 2011-2016 Pushing Inertia
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
package com.pushinginertia.commons.domain.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;

/**
 * Provides database serialization of Java 8 Instant.
 */
public class InstantUserType implements UserType {
	private static final int[] TYPES = new int[]{Types.TIMESTAMP};

	@Override
	public int[] sqlTypes() {
		return TYPES;
	}

	@Override
	public Class<Instant> returnedClass() {
		return Instant.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y || (x != null && y != null && x.equals(y));
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x == null ? 0 : x.hashCode();
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, final String[] names, SessionImplementor session, final Object owner)
	throws HibernateException, SQLException {
		final Object object = rs.getObject(names[0]);

		// must call get above for this null check to work
		if (rs.wasNull()) {
			return null;
		}

		if (!(object instanceof Timestamp)) {
			throw new IllegalArgumentException("Expected timestamp value but got " + object.getClass().getName());
		}

		return assemble((Timestamp)object, owner);
	}

	@Override
	public void nullSafeSet(final PreparedStatement st, final Object value, final int index, SessionImplementor session)
	throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.TIMESTAMP);
		} else {
			final Instant i = (Instant)value;
			st.setObject(index, disassemble(i), Types.TIMESTAMP);
		}
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(@Nullable final Object value) throws HibernateException {
		if (value == null) {
			return null;
		}
		return Timestamp.from((Instant) value);
	}

	@Override
	public Object assemble(@Nullable final Serializable cached, final Object owner) throws HibernateException {
		if (cached == null) {
			return null;
		}
		final Timestamp timestamp = (Timestamp) cached;
		return timestamp.toInstant();
	}

	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}
}
