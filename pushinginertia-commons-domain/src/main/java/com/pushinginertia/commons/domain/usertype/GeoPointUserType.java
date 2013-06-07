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

import com.pushinginertia.commons.core.geo.GeoPoint;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Custom hibernate user type to easily map two latitude/longitude columns to an instance of {@link GeoPoint}.
 * This can be defined as follows in your hibernate mapping:
 * <pre>
 * &lt;property name="point" type="com.pushinginertia.commons.domain.usertype.GeoPointUserType"&gt;
 *     &lt;column name="lat"/&gt;
 *     &lt;column name="lon"/&gt;
 * &lt;/property&gt;
 * </pre>
 */
public class GeoPointUserType implements CompositeUserType {
	private static final String[] PROPERTY_NAMES = new String[]{"lat", "lon"};
	private static final Type[] TYPES = new Type[]{StandardBasicTypes.BIG_DECIMAL, StandardBasicTypes.BIG_DECIMAL};

	public Object assemble(final Serializable cached, final SessionImplementor session, final Object owner) throws HibernateException {
		return cached;
	}

	public Object deepCopy(final Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(final Object value, final SessionImplementor session) throws HibernateException {
		return (Serializable) value;
	}

	public boolean equals(final Object x, final Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		return x.equals(y);
	}

	public String[] getPropertyNames() {
		return PROPERTY_NAMES;
	}

	public Type[] getPropertyTypes() {
		return TYPES;
	}

	public Object getPropertyValue(final Object component, final int property) throws HibernateException {
		final GeoPoint point = (GeoPoint)component;
		if (property == 0) {
			return point.getLat();
		}
		return point.getLon();
	}

	public int hashCode(final Object x) throws HibernateException {
		return x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}

	public GeoPoint nullSafeGet(final ResultSet resultSet, final String[] names, final SessionImplementor session, final Object owner)
	throws HibernateException, SQLException {
		if (resultSet == null) {
			return null;
		}

		final String latColumnName = names[0];
		final String lonColumnName = names[1];

		final BigDecimal lat = resultSet.getBigDecimal(latColumnName);
		final BigDecimal lon = resultSet.getBigDecimal(lonColumnName);
		if (lat == null || lon == null) {
			return null;
		}
		return new GeoPoint(lat, lon);
	}

	public void nullSafeSet(final PreparedStatement statement, final Object value, final int index, final SessionImplementor session)
	throws HibernateException, SQLException {
		if (value == null) {
			statement.setNull(index, StandardBasicTypes.BIG_DECIMAL.sqlType());
			statement.setNull(index + 1, StandardBasicTypes.BIG_DECIMAL.sqlType());
			return;
		}
		final GeoPoint point = (GeoPoint)value;
		statement.setBigDecimal(index, point.getLat());
		statement.setBigDecimal(index + 1, point.getLon());
	}

	public Object replace(final Object original, final Object target, final SessionImplementor session, final Object owner)
	throws HibernateException {
		return original;
	}

	public Class returnedClass() {
		return GeoPoint.class;
	}

	public void setPropertyValue(final Object component, final int property, final Object value) throws HibernateException {
		throw new UnsupportedOperationException("Immutable " + GeoPoint.class.getName());
	}
}
