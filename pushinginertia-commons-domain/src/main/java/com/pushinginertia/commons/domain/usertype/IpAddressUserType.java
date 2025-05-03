/* Copyright (c) 2011-2015 Pushing Inertia
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

import com.pushinginertia.commons.net.IpAddress;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Custom hibernate user type to map an {@link com.pushinginertia.commons.net.IpAddress} to a numeric representation.
 */
public class IpAddressUserType implements EnhancedUserType, Serializable {
	private static final long serialVersionUID = 1L;
	private static final int[] SQL_TYPES = new int[]{Types.INTEGER};
	private static final Logger LOG = LoggerFactory.getLogger(IpAddressUserType.class);

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class returnedClass() {
		return IpAddress.class;
	}

	@Override
	public boolean equals(final Object x, final Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(final Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(final ResultSet rs, String[] names, SessionImplementor session, Object owner)
	throws HibernateException, SQLException {
		if (rs == null) {
			return null;
		}

		final String columnName = names[0];
		final long columnValue = rs.getLong(columnName);
		if (rs.wasNull()) {
			LOG.debug("Returning null for column: {}", columnName);
			return null;
		}

		// and then each time, advance to opening START_OBJECT
		return deserialize(columnValue);
	}

	@Override
	public void nullSafeSet(final PreparedStatement statement, Object value, int index, SessionImplementor session)
	throws HibernateException, SQLException {
		if (value == null) {
			statement.setNull(index, StandardBasicTypes.INTEGER.sqlType());
			return;
		}

		final long ipNumber = serialize((IpAddress)value);
		statement.setLong(index, ipNumber);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return serialize((IpAddress)value);
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
		return deserialize((Long)cached);
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}

	@Override
	public String objectToSQLString(final Object value) {
		return Long.toString(serialize((IpAddress) value));
	}

	@Override
	public String toXMLString(Object value) {
		return Long.toString(serialize((IpAddress) value));
	}

	@Override
	public Object fromXMLString(final String xmlValue) {
		try {
			return deserialize(Long.valueOf(xmlValue));
		} catch (final NumberFormatException e) {
			throw new HibernateException("Failed to parse IP number: " + xmlValue, e);
		}
	}

	static long serialize(final IpAddress ipAddress) throws HibernateException{
		return ipAddress.getIpNumber();
	}

	static IpAddress deserialize(final long columnValue) throws HibernateException {
		return new IpAddress(columnValue);
	}
}
