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

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.EnhancedUserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

/**
 * Custom hibernate user type to map a JSON-encoded map within a column into a Java Map&lt;String, Object&gt; instance.
 */
public class MapAsJsonUserType implements EnhancedUserType, Serializable {
	private static final long serialVersionUID = 1L;
	private static final int[] SQL_TYPES = new int[]{Types.VARCHAR};
	private static final Logger LOG = LoggerFactory.getLogger(MapAsJsonUserType.class);

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	@Override
	public Class returnedClass() {
		return Map.class;
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
	public Object nullSafeGet(final ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		if (rs == null) {
			return null;
		}

		final String columnName = names[0];
		final String columnValue = rs.getString(columnName);
		if (columnValue == null) {
			LOG.debug("Returning null for column: {}", columnName);
			return null;
		}

		// and then each time, advance to opening START_OBJECT
		return deserialize(columnValue);
	}

	@Override
	public void nullSafeSet(final PreparedStatement statement, Object value, int index)
	throws HibernateException, SQLException {
		if (value == null) {
			statement.setNull(index, StandardBasicTypes.STRING.sqlType());
			return;
		}

		final String jsonEncoded = serialize((Map<String, Object>)value);
		statement.setString(index, jsonEncoded);
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
		return serialize((Map<String, Object>)value);
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
		return deserialize((String)cached);
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return original;
	}

	@Override
	public String objectToSQLString(final Object value) {
		return '\'' + serialize((Map<String, Object>)value) + '\'';
	}

	@Override
	public String toXMLString(Object value) {
		return serialize((Map<String, Object>)value);
	}

	@Override
	public Object fromXMLString(final String xmlValue) {
		return deserialize(xmlValue);
	}

	static String serialize(final Map<String, Object> map) throws HibernateException{
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(map);
		} catch (final IOException e) {
			throw new HibernateException("Failed to encode map as a JSON-encoded string.");
		}
	}

	static Map<String, Object> deserialize(final String columnValue) throws HibernateException {
		final ObjectMapper mapper = new ObjectMapper();
		final JsonFactory f = new JsonFactory();
		try {
			final JsonParser jp = f.createJsonParser(columnValue);
			// advance stream to first token
			jp.nextToken();
			if (jp.isExpectedStartArrayToken()) {
				// if the token is START_ARRAY, advance once more to the object within it
				// this handles encoded strings with a single map inside an array
				jp.nextToken();
			}
			return mapper.readValue(jp, Map.class);
		} catch (final IOException e) {
			throw new HibernateException(
					"Failed to parse JSON-encoded map: " + columnValue, e);
		}
	}
}
