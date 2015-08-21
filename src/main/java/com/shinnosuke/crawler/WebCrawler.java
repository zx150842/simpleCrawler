package com.shinnosuke.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.shinnosuke.crawler.fetcher.Fetcher;

public class WebCrawler extends Configurable implements Runnable {

	private boolean isCrawlling = false;
	private URLQueue urlQuene;
	private Fetcher fetcher;
	private boolean isShutDown;
	private static final int assignSize = 50;
	
	public WebCrawler(CrawlController controller, Configuration configuration) {
		super(configuration);
		this.urlQuene = controller.getUrlQuene();
		this.fetcher = new Fetcher(this.urlQuene);
	}
	
	@Override
	public void run() {
		while (!isShutDown) {
			List<String> assignUrls = new ArrayList<>(assignSize);
			ReentrantLock mutex = urlQuene.getLock();
			try {
				mutex.lock();
				int count = 0;
				for (String url : urlQuene.getUrlQueue()) {
					if (count++ == assignSize) {
						break;
					}
					assignUrls.add(url);
				}
			} finally {
				mutex.unlock();
			}
			if (assignUrls.size() == 0) {
				isCrawlling = false;
				return;
			}
			isCrawlling = true;
			for (String url : assignUrls) {
				fetcher.fetchPage(url);
			}
		}
		
	}
	
	public boolean isCrawlling() {
		return isCrawlling;
	}

}
