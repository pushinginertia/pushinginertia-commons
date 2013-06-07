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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Encapsulates a geographical latitudinal/longitudinal coordinate and provides some logic to perform distance
 * calculations and comparisons.
 */
public class GeoPoint implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final double EARTH_RADIUS_KM = 6371;

	private final GeoCoordinate lat;
	private final GeoCoordinate lon;

	/**
	 * Instantiates a point with a given latitude and longitude.
	 * @param lat latitude
	 * @param lon longidute
	 */
	public GeoPoint(final BigDecimal lat, final BigDecimal lon) {
		this.lat = GeoCoordinate.fromDegrees(lat);
		this.lon = GeoCoordinate.fromDegrees(lon);
	}

	/**
	 * Instantiates a point with a given latitude and longitude.
	 * @param lat latitude
	 * @param lon longidute
	 */
	public GeoPoint(final double lat, final double lon) {
		this.lat = GeoCoordinate.fromDegrees(lat);
		this.lon = GeoCoordinate.fromDegrees(lon);
	}

	/**
	 * Instantiates a point with a given latitude and longitude.
	 * @param lat latitude
	 * @param lon longidute
	 * @throws NumberFormatException if one of the values is not numerical
	 */
	public GeoPoint(final String lat, final String lon) throws NumberFormatException {
		this.lat = GeoCoordinate.fromDegrees(lat);
		this.lon = GeoCoordinate.fromDegrees(lon);
	}

	/**
	 * Retrieves the latitude of this point.
	 * @return latitude
	 */
	public BigDecimal getLat() {
		return lat.getDegrees();
	}

	/**
	 * Retrieves the longitude of this point.
	 * @return longitude
	 */
	public BigDecimal getLon() {
		return lon.getDegrees();
	}

	/**
	 * Retrieves the latitude of this point in radians.
	 * @return latitude in radians
	 */
	public Double getLatRadians() {
		return lat.getRadians();
	}

	/**
	 * Retrieves the longitude of this point in radians.
	 * @return longitude in radians
	 */
	public Double getLonRadians() {
		return lon.getRadians();
	}

	/**
	 * Returns the latitute separated from the longitude by a comma.
	 * @return never null
	 * @throws NullPointerException if lat or lon is null
	 */
	public String getLatLonString() throws NullPointerException {
		final StringBuilder sb = new StringBuilder();
		return sb.append(lat.getDegrees()).append(',').append(lon.getDegrees()).toString();
	}

	@Override
	public String toString() {
		return "{" + getLatLonString() + "}";
	}

	/**
	 * Calculates the distance between two points in km.
	 * @param point point to compare to this one
	 * @return distance in km
	 * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">http://www.movable-type.co.uk/scripts/latlong.html</a>
	 */
	public BigDecimal distanceTo(final GeoPoint point) {
		ValidateAs.notNull(point, "point");
		final double lat1 = this.getLatRadians();
		final double lon1 = this.getLonRadians();
		final double lat2 = point.getLatRadians();
		final double lon2 = point.getLonRadians();

		final double dLat = lat2 - lat1;
		final double dLon = lon2 - lon1;

		final double a =
				Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(lat1) * Math.cos(lat2) *
				Math.sin(dLon/2) * Math.sin(dLon/2);
		final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		final double d = EARTH_RADIUS_KM * c;

		return BigDecimal.valueOf(d).setScale(GeoCoordinate.PRECISION, RoundingMode.HALF_UP);
	}

	/**
	 * Calculate the delta change in the latitude to go the given distance north or south.
	 * 1 degree latitude is roughly 111km.
	 * @param distanceKm distance in km
	 * @return change in latitude
	 */
	public BigDecimal calculateLatDelta(double distanceKm) {
		final double lat1 = this.getLatRadians();
		final double dist = distanceKm / EARTH_RADIUS_KM;
		final double lat2 =
				Math.asin(
						Math.sin(lat1) * Math.cos(dist) +
						Math.cos(lat1) * Math.sin(dist));   // Math.cos(bearingRad) removed because it equals 1 when bearing north
		return BigDecimal.valueOf(Math.toDegrees(lat2 - lat1)).setScale(GeoCoordinate.PRECISION, RoundingMode.HALF_UP);
	}

	/**
	 * Calculates the delta change in the longitude to go the given distance east or west.
	 * @param distanceKm distance in km
	 * @return change in longitude
	 */
	public BigDecimal calculateLonDelta(double distanceKm) {
		final double lat1 = this.getLatRadians();
		final double lon1 = this.getLonRadians();
		final double dist = distanceKm / EARTH_RADIUS_KM;
		double lon2 =
				lon1 +
				Math.atan2(
						Math.sin(dist) * Math.cos(lat1),    // Math.sin(bearingRad) removed because it equals 1 when bearing east
						Math.cos(dist) - Math.sin(lat1) * Math.sin(lat1));
		lon2 = (lon2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI;  // normalise to -180...+180
		return BigDecimal.valueOf(Math.toDegrees(lon2 - lon1)).setScale(GeoCoordinate.PRECISION, RoundingMode.HALF_UP);
	}

	/**
	 * Returns the destination point from this point having travelled the given distance (in km) on the given initial
	 * bearing (bearing may vary before destination is reached).
	 * @param bearing initial bearing in degrees (-180 to 180)
	 * @param distanceKm distance in km
	 * @return coordinates of destination point
	 * @see <a href="http://www.movable-type.co.uk/scripts/latlong.html">http://www.movable-type.co.uk/scripts/latlong.html</a>
	 */
	public GeoPoint destinationPoint(int bearing, double distanceKm) {
		final double lat1 = this.getLatRadians();
		final double lon1 = this.getLonRadians();
		final double dist = distanceKm / EARTH_RADIUS_KM;
		final double bearingRad = Math.toRadians(bearing);

		final double lat2 =
				Math.asin(
						Math.sin(lat1) * Math.cos(dist) +
						Math.cos(lat1) * Math.sin(dist) * Math.cos(bearingRad));
		double lon2 =
				lon1 +
				Math.atan2(
						Math.sin(bearingRad) * Math.sin(dist) * Math.cos(lat1),
						Math.cos(dist) - Math.sin(lat1) * Math.sin(lat2));
		lon2 = (lon2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI;  // normalise to -180...+180

		return new GeoPoint(Math.toDegrees(lat2), Math.toDegrees(lon2));
	}

	/**
	 * Generates a new geopoint that is a random distance from this one in the given bearing.
	 * @param bearing initial bearing in degrees (-180 to 180)
	 * @param maxKm maximum km from this point
	 * @return randomized geopoint
	 */
	public GeoPoint randGeoPoint(final int bearing, final double maxKm) {
		final double distanceKm = Math.random() * maxKm;
		return destinationPoint(bearing, distanceKm);
	}

	/**
	 * Generates a new geopoint that is a random distance from this one and in a random bearing.
	 * @param maxKm maximum km from this point
	 * @return randomized geopoint
	 */
	public GeoPoint randGeoPoint(final double maxKm) {
		final Random r = new Random();
		final int bearing = r.nextInt(360) - 180; // range -180..179
		return randGeoPoint(bearing, maxKm);
	}
}