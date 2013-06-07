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
package com.pushinginertia.commons.core.geo;

import com.pushinginertia.commons.core.validation.ValidateAs;

import java.io.Serializable;
import java.lang.Math;import java.lang.NumberFormatException;import java.lang.Object;import java.lang.Override;import java.lang.String;import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Encapsulates a geographical coordinate (i.e., latitude or longitude).
 */
public class GeoCoordinate implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int PRECISION = 6;

	private final BigDecimal degrees;
	private final double radians;

	private GeoCoordinate(final BigDecimal degrees, final double radians) {
		this.degrees = degrees;
		this.radians = radians;
	}

	public static GeoCoordinate fromDegrees(final BigDecimal degrees) {
		ValidateAs.notNull(degrees, "degrees");
		return new GeoCoordinate(
				degrees,
				Math.toRadians(degrees.doubleValue()));
	}

	public static GeoCoordinate fromDegrees(final double degrees) {
		return new GeoCoordinate(
				new BigDecimal(degrees).setScale(PRECISION, RoundingMode.HALF_UP),
				Math.toRadians(degrees));
	}

	public static GeoCoordinate fromDegrees(final String degrees) throws NumberFormatException {
		ValidateAs.notNull(degrees, "degrees");
		return fromDegrees(new BigDecimal(degrees));
	}

	/**
	 * Retrieves this coordinate in degrees.
	 * @return coordinate in degrees
	 */
	public BigDecimal getDegrees() {
		return degrees;
	}

	/**
	 * Retrieves this coordinate in radians.
	 * @return coordinate in radians
	 */
	public double getRadians() {
		return radians;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final GeoCoordinate that = (GeoCoordinate) o;
		return degrees.equals(that.degrees);
	}

	@Override
	public int hashCode() {
		return degrees.hashCode();
	}

	@Override
	public String toString() {
		return degrees.toString();
	}
}
