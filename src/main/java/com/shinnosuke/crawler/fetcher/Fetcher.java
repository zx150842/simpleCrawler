package com.shinnosuke.crawler.fetcher;

import java.util.concurrent.locks.ReentrantLock;

import com.shinnosuke.crawler.Configurable;
import com.shinnosuke.crawler.Configuration;
import com.shinosuke.crawler.parse.Parse;

public class Fetcher extends Configurable {

	private static HttpPoolUtil httpPoolUtil;
	private static ReentrantLock httpLock = new ReentrantLock();
	
	public Fetcher(Configuration config) {
		if (httpPoolUtil == null) {
			httpLock.lock();
			if (httpPoolUtil == null) {
				httpPoolUtil = new HttpPoolUtil(config);
			}
			httpLock.unlock();
		}
	}
	
	public Page fetchPage(String url) {
		String content = httpPoolUtil.getContent(url);
		Page page = Parse.parse(content);
		return page;
	}
}
