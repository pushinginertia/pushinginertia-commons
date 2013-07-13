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
package com.pushinginertia.commons.domain.usertype;

import com.pushinginertia.commons.net.IpAddressAndCountry;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Custom hibernate user type to easily map two columns containing an IP address and country ID to an instance of
 * {@link com.pushinginertia.commons.net.IpAddressAndCountry}. This saves the string representation of the IP address,
 * which is the output of {@link com.pushinginertia.commons.net.IpAddress#getIpAddress()}.
 * This can be defined as follows in your hibernate mapping:
 * <pre>
 * &lt;property name="ip" type="com.pushinginertia.commons.domain.usertype.IpAddressAndCountryUserType"&gt;
 *     &lt;column name="ip_addr"/&gt;
 *     &lt;column name="ip_num_country_code"/&gt;
 * &lt;/property&gt;
 * </pre>
 */
public class IpAddressAndCountryUserType implements CompositeUserType {
	private static final String[] PROPERTY_NAMES = new String[]{"ipAddress", "ipAddressCountryCode"};
	private static final Type[] TYPES = new Type[]{StandardBasicTypes.STRING, StandardBasicTypes.STRING};

	@Override
	public Object assemble(final Serializable cached, final SessionImplementor session, final Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(final Object value, final SessionImplementor session) throws HibernateException {
		return (Serializable) value;
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
	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}

	@Override
	public Type[] getPropertyTypes() {
		return TYPES;
	}

	/**
	 * Get the value of a property.
	 * @param component an instance of class mapped by this "type"
	 * @param property an index corresponding to {@link #PROPERTY_NAMES}
	 * @return the property value
	 * @throws HibernateException
	 */
	@Override
	public Object getPropertyValue(final Object component, final int property) throws HibernateException {
		final IpAddressAndCountry entity = (IpAddressAndCountry)component;
		if (property == 0) {
			return entity.getIpAddress().getIpAddress();
		}
		return entity.getCountryCode();
	}

	@Override
	public int hashCode(final Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public IpAddressAndCountry nullSafeGet(final ResultSet resultSet, final String[] names, final SessionImplementor session, final Object owner)
	throws HibernateException, SQLException {
		if (resultSet == null) {
			return null;
		}

		final String ipAddressColumnName = names[0];
		final String countryCodeColumnName = names[1];

		final String ipAddress = resultSet.getString(ipAddressColumnName);
		final String countryCode = resultSet.getString(countryCodeColumnName);
		if (ipAddress == null) {
			return null;
		}
		return new IpAddressAndCountry(ipAddress, countryCode);
	}

	@Override
	public void nullSafeSet(final PreparedStatement statement, final Object value, final int index, final SessionImplementor session)
	throws HibernateException, SQLException {
		if (value == null) {
			statement.setNull(index, StandardBasicTypes.STRING.sqlType());
			statement.setNull(index + 1, StandardBasicTypes.STRING.sqlType());
			return;
		}
		final IpAddressAndCountry entity = (IpAddressAndCountry)value;
		statement.setString(index, entity.getIpAddress().getIpAddress());
		statement.setString(index + 1, entity.getCountryCode());
	}

	@Override
	public Object replace(final Object original, final Object target, final SessionImplementor session, final Object owner)
	throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return IpAddressAndCountry.class;
	}

	@Override
	public void setPropertyValue(final Object component, final int property, final Object value) throws HibernateException {
		throw new UnsupportedOperationException("Immutable " + IpAddressAndCountry.class.getName());
	}
}
