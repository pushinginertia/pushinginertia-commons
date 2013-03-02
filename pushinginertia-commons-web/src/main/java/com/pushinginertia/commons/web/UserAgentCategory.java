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

import java.util.HashSet;
import java.util.Set;

/**
 * Categories that a user agent fall into. This can be used to identify what kind of user agent is making a request,
 * but it's not foolproof, as user agent strings are easily spoofed. However, this can be used to return an HTTP status
 * code 403 to known abusive bots or to restrict access to IE6 and earlier users.
 */
public enum UserAgentCategory {
	/** IE6 or earlier (usually these are just abuse bots) */
	IE6,
	/** Googlebot, Baidu spider, Yandex, etc. */
	SEARCHBOT,
	/** default if no other user agent is identified */
	USER,
	/** email harvesters, spambots, content scrapers, etc. */
	ABUSE;

	/**
	 * Regex to identify if a user agent is IE6 or earlier.
	 * See discussion for background on how this is constructed:
	 * <a href="http://pushinginertia.com/2012/09/regex-to-identify-an-ie-6-user-agent-without-blocking-msie-8-or-9-users/">http://pushinginertia.com/2012/09/regex-to-identify-an-ie-6-user-agent-without-blocking-msie-8-or-9-users/</a>
	 */
	private static final String IE6_UA = "^Mozilla\\/\\d+\\.\\d+ \\([^(]*MSIE [456]\\.[0-9][^)]*\\).*";

	/**
	 * A collection of known search engine web crawler user agents.
	 */
	private static final Set<String> SEARCH_BOT_SET = new HashSet<String>();
	/**
	 * A collection of regular expressions that match known and undesirable bots, scrapers, crawlers, etc.
	 */
	private static final Set<String> ABUSE_BOT_SET = new HashSet<String>();
	static {
		// this is baidu's v1.0 crawler user agent: Baiduspider+(+http://www.baidu.com/search/spider.htm)
		SEARCH_BOT_SET.add("^Mozilla/5\\.0 \\(compatible; Baiduspider/[0-9.]+; \\+http://www\\.baidu\\.com/search/spider\\.html\\)$");
		SEARCH_BOT_SET.add("^Baiduspider-image\\+\\(\\+http://www\\.baidu\\.com/search/spider\\.htm\\)$");
		SEARCH_BOT_SET.add("^Mozilla/5\\.0 \\(compatible; bingbot/[0-9.]+; \\+http://www\\.bing\\.com/bingbot\\.htm\\)$");
		SEARCH_BOT_SET.add("^Mozilla/5\\.0 \\(compatible; Googlebot/[0-9.]+; \\+http://www\\.google\\.com/bot\\.html\\)$");
		SEARCH_BOT_SET.add("^Mozilla/5\\.0 \\(compatible; MSIE or Firefox mutant; not on Windows server; \\+ ?http://(tab\\.search|ws).daum.net/aboutWebSearch.html\\) Daumoa/[0-9.]+$");
		SEARCH_BOT_SET.add("^Mozilla/5\\.0 \\(compatible; YandexBot/[0-9.]+; \\+http://yandex\\.com/bots\\)$");
		SEARCH_BOT_SET.add("^msnbot(/2\\.0b|-media/1\\.1) \\(\\+http://search\\.msn\\.com/msnbot\\.htm\\)$");
		SEARCH_BOT_SET.add("^Yeti/1\\.0 \\(NHN Corp.; http://help\\.naver\\.com/robots/\\)$");
		SEARCH_BOT_SET.add("^Mozilla/4\\.0 \\(compatible; NaverBot/1.0; http://help.naver.com/[a-z0-9_]+.[aj]sp\\)");

		ABUSE_BOT_SET.add("^Java.*");
		ABUSE_BOT_SET.add("^$");
		ABUSE_BOT_SET.add("^Jakarta.*");
		ABUSE_BOT_SET.add("^User-Agent.*");
		ABUSE_BOT_SET.add(".*compatible ;.*");
		ABUSE_BOT_SET.add("^Mozilla(/[0-9]\\.0)?( \\(\\))?$");
		ABUSE_BOT_SET.add(".*(libwww|lwp-trivial|curl|PHP/|urllib|GT::WWW|Snoopy|MFC_Tear_Sample|HTTP::Lite|PHPCrawl|URI::Fetch|Zend_Http_Client|http client|PECL::HTTP|IBM EVV|Bork-edition|Fetch API Request|WEP Search|Wells Search II|Missigua Locator|ISC Systems iRc Search 2.1|Microsoft URL Control|Indy Library|SiteSucker|LWP::|larbin|Nutch|WBSearchBot|intelium_bot|CPython).*");
		ABUSE_BOT_SET.add("^[A-Z][a-z]{3,} [a-z]{4,} [a-z]{4,}.*");
		ABUSE_BOT_SET.add(".*;1813.*"); // http://www.the-art-of-web.com/system/logs-avg/
		ABUSE_BOT_SET.add(".*LYCOSA;.*"); // http://www.forumpostersunion.com/showthread.php?t=20497
		ABUSE_BOT_SET.add(".*MSIE 5\\.5; Windows NT 4\\.0\\) Opera 7\\.0.*"); // http://www.webmasterworld.com/search_engine_spiders/4288915.htm
		ABUSE_BOT_SET.add("^Internet Explorer 6\\.0$");
		ABUSE_BOT_SET.add("^Mozilla/5\\.0 \\(Windows; U; Windows NT 5\\.0; en-US; rv:0\\.9\\.4\\) Gecko/20011128 Netscape6/6\\.2\\.1$");
		ABUSE_BOT_SET.add("^Mozilla/5\\.0 \\(Windows NT 5\\.1; rv:8\\.0\\.1\\) Gecko/20100101 Firefox/8\\.0\\.1$");
		ABUSE_BOT_SET.add("^Mozilla/4\\.0 \\(compatible; MSIE 5\\.5; Windows 95; BCD2000\\)$");  // Xrumer forum spam bot: http://www.forumpostersunion.com/showthread.php?p=83742
		ABUSE_BOT_SET.add("^[a-zA-Z0-9-.]+\\.[a-z]{2,4}$"); // matches user agents that point to a domain
	}

	/**
	 * Parses a user agent string into an enumeration.
	 * @param userAgent user agent string received in the HTTP request
	 * @return best guess as to what category the user agent falls into
	 */
	public static UserAgentCategory parseUserAgent(final String userAgent) {
		if (userAgent == null)
			return ABUSE;

		// 1. first check for known abuse bots
		for (String regex: ABUSE_BOT_SET) {
			if (userAgent.matches(regex))
				return ABUSE;
		}

		// 2. now check for IE6 or earlier (these are most likely bots)
		if (userAgent.matches(IE6_UA))
			return IE6;

		for (String regex: SEARCH_BOT_SET) {
			if (userAgent.matches(regex))
				return SEARCHBOT;
		}

		return USER;
	}
}
