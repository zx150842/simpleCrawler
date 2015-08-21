package com.shinnosuke.crawler.fetcher;

import com.shinnosuke.crawler.Configurable;
import com.shinnosuke.crawler.URLQueue;

public class Fetcher extends Configurable {

	private URLQueue urlQueue;
	
	public Fetcher(URLQueue urlQueue) {
		this.urlQueue = urlQueue;
	}
	
	public Page fetchPage(String url) {
	
		return null;
	}
}
