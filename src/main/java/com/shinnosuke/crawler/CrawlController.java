package com.shinnosuke.crawler;

import java.util.List;

import com.google.common.collect.Lists;

public class CrawlController {
	
	public CrawlController() {
		
	}
	
	public <T extends WebCrawler> void start(Class<T> _c, int numberOfCrawler) {
		final List<T> crawlers = Lists.newArrayList();
		for (int i = 1; i <= numberOfCrawler; ++i) {
			T c;
			try {
				c = _c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			} 
			Thread thread = new Thread(c);
			thread.start();
			crawlers.add(c);
		}
		
		Thread monitorThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for (T c : crawlers) {
					
				}
				
			}
		});
	}
	
}
