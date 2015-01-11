package com.pushinginertia.commons.domain.usertype;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapAsJsonUserTypeTest {
	@Test
	public void serialize() {
		final Map<String, Object> map = new LinkedHashMap<>();
		map.put("a", "b");
		map.put("x", "y");
		Assert.assertEquals("{\"a\":\"b\",\"x\":\"y\"}", MapAsJsonUserType.serialize(map));
	}

	@Test
	public void deserializeFromArrayMap() {
		final String json = "[{\"x\":\"y\",\"a\":\"b\"}]";
		final Map<String, Object> map = MapAsJsonUserType.deserialize(json);
		Assert.assertEquals(2, map.size());
		Assert.assertTrue(map.containsKey("a"));
		Assert.assertTrue(map.containsKey("x"));
	}

	@Test
	public void deserializeFromMap() {
		final String json = "{\"x\":\"y\",\"a\":\"b\"}";
		final Map<String, Object> map = MapAsJsonUserType.deserialize(json);
		Assert.assertEquals(2, map.size());
		Assert.assertTrue(map.containsKey("a"));
		Assert.assertTrue(map.containsKey("x"));
	}
}