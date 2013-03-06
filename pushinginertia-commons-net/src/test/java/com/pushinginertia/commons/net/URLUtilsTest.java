package com.pushinginertia.commons.net;

import org.junit.Assert;
import org.junit.Test;

public class URLUtilsTest {
	@Test
	public void extractTopLevelDomainFromHost() {
		Assert.assertEquals("x.com", URLUtils.extractTopLevelDomainFromHost("x.com"));
		Assert.assertEquals("x.com", URLUtils.extractTopLevelDomainFromHost("www.x.com"));
		Assert.assertEquals("x.com", URLUtils.extractTopLevelDomainFromHost("subdomainX.subdomainY.x.com"));
		Assert.assertEquals("com", URLUtils.extractTopLevelDomainFromHost("com"));
		Assert.assertEquals(".com", URLUtils.extractTopLevelDomainFromHost("..com"));
	}
}
