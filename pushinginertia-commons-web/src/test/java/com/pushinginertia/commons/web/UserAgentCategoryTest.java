/* Copyright (c) 2011-2012 Pushing Inertia
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
package com.pushinginertia.commons.web;

import org.junit.Assert;
import org.junit.Test;

public class UserAgentCategoryTest {
	@Test
	public void abuse() {
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent(null));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent(""));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Java 6"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Java"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Jakarta"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("User-Agent: abcd"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible ; )"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/4.0"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("libwww"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("lwp-trivial"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("curl"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("PHP/"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("urllib"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("GT::WWW"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Snoopy"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("MFC_Tear_Sample"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("HTTP::Lite"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("PHPCrawl"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("URI::Fetch"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Zend_Http_Client"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("http client"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("PECL::HTTP"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("IBM EVV")); // this might be legitimate for Opera
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Bork-edition"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Fetch API Request"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("WEP Search"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Wells Search II"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Missigua Locator"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("ISC Systems iRc Search 2.1"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Microsoft URL Control"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Indy Library"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;1813)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Zobv zkjgws pzjngq s"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("SiteSucker/2.3.3"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("LWP::Simple/5.810"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("larbin_2.6.3 (larbin2.6.3@unspecified.mail)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Internet Explorer 6.0"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Internet Explorer"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("SS/Nutch-1.5 (SS)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; WBSearchBot/1.1; +http://www.warebay.com/bot.html)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("intelium_bot"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.0; en-US; rv:0.9.4) Gecko/20011128 Netscape6/6.2.1"));
//		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (Windows NT 5.1; rv:8.0.1) Gecko/20100101 Firefox/8.0.1"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 5.5; Windows 95; BCD2000)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Python-urllib/2.7"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 ()"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("python-requests/1.1.0 CPython/2.7.3 Linux/3.2.0-31-virtual"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("ip-search-bot.com"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("CatchBot/2.0; +http://www.catchbot.com"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.8.0.11)  Firefox/1.5.0.11; 360Spider"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; MJ12bot/v1.4.3; http://www.majestic12.co.uk/bot.php?+)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.110 Safari/537.36 Squider/0.01"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("WhatWeb/0.4.7"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("YisouSpider"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; BLEXBot/1.0; +http://webmeup-crawler.com/)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Apache-HttpClient/4.3 (java 1.5)"));
		Assert.assertEquals(UserAgentCategory.ABUSE, UserAgentCategory.parseUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36 AppEngine-Google; (+http://code.google.com/appengine; appid: s~abcd)"));
	}

	@Test
	public void ie6() {
		// various IE 6 stings
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 5.5; Windows 98; DigExt)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/5.0 (Windows; U; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4325)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/45.0 (compatible; MSIE 6.0; Windows NT 5.1)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.08 (compatible; MSIE 6.0; Windows NT 5.1)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.01 (compatible; MSIE 6.0; Windows NT 5.1)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (X11; MSIE 6.0; i686; .NET CLR 1.1.4322; .NET CLR 2.0.50727; FDM)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (Windows; MSIE 6.0; Windows NT 6.0)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (Windows; MSIE 6.0; Windows NT 5.2)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (Windows; MSIE 6.0; Windows NT 5.0)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (Windows; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (MSIE 6.0; Windows NT 5.1)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (MSIE 6.0; Windows NT 5.0)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible;MSIE 6.0;Windows 98;Q312461)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (Compatible; Windows NT 5.1; MSIE 6.0) (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; U; MSIE 6.0; Windows NT 5.1) (Compatible; ; ; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; U; MSIE 6.0; Windows NT 5.1)"));
		Assert.assertEquals(UserAgentCategory.IE6, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; Windows NT 6.1; Trident/5.0)"));

		// IE 8 strings that look like IE 6 strings
		Assert.assertEquals(UserAgentCategory.USER, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"));
		Assert.assertEquals(UserAgentCategory.USER, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; Tablet PC 2.0; .NET4.0C; BRI/2)"));
		Assert.assertEquals(UserAgentCategory.USER, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; IPMS/5101ACA8-14FA1F2F282-00000016668C; User-agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; http://bsalsa.com) ; InfoPath.2; .NET4.0C)"));
		Assert.assertEquals(UserAgentCategory.USER, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ;  Embedded Web Browser from: http://bsalsa.com/; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; InfoPath.2)"));
		Assert.assertEquals(UserAgentCategory.USER, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; GTB6.5; QQDownload 534; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; SLCC2; .NET CLR 2.0.50727; Media Center PC 6.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729)"));
		Assert.assertEquals(UserAgentCategory.USER, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; InfoPath.3; Tablet PC 2.0)"));
	}

	@Test
	public void searchBot() {
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Baiduspider-image+(+http://www.baidu.com/search/spider.htm)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; bingbot/2.0; +http://www.bing.com/bingbot.htm)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534+ (KHTML, like Gecko) BingPreview/1.0b"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; MSIE or Firefox mutant; not on Windows server; + http://tab.search.daum.net/aboutWebSearch.html) Daumoa/3.0"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; MSIE or Firefox mutant; not on Windows server; +http://ws.daum.net/aboutWebSearch.html) Daumoa/2.0"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/5.0 (compatible; YandexBot/3.0; +http://yandex.com/bots)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("msnbot/2.0b (+http://search.msn.com/msnbot.htm)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("msnbot-media/1.1 (+http://search.msn.com/msnbot.htm)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Yeti/1.0 (NHN Corp.; http://help.naver.com/robots/)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; NaverBot/1.0; http://help.naver.com/delete_main.asp)"));
		Assert.assertEquals(UserAgentCategory.SEARCHBOT, UserAgentCategory.parseUserAgent("Mozilla/4.0 (compatible; NaverBot/1.0; http://help.naver.com/customer_webtxt_02.jsp)"));
		Assert.assertEquals(UserAgentCategory.USER, UserAgentCategory.parseUserAgent("Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.2.13) Gecko/20101206 Ubuntu/9.10 (karmic) Firefox/3.6.13 GTB7.1"));
	}
}
