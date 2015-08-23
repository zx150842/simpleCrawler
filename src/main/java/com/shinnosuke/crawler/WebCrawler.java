package com.shinnosuke.crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.shinnosuke.crawler.fetcher.Fetcher;
import com.shinnosuke.crawler.fetcher.Page;

public class WebCrawler extends Configurable implements Runnable {

	private boolean isCrawlling = false;
	private URLQueue urlQuene;
	private Fetcher fetcher;
	private boolean isShutDown;
	private Configuration configuration;
	private static final int assignSize = 50;

	public WebCrawler() {
		
	}
	
	public void init(CrawlController controller, Configuration configuration) {
		this.urlQuene = controller.getUrlQuene();
		this.fetcher = new Fetcher(configuration);
		this.configuration = configuration;
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
				Page page = fetcher.fetchPage(url);
				if (page != null && page.getOutgoingUrls() != null
						&& page.getOutgoingUrls().size() > 0) {
					urlQuene.addAll(page.getOutgoingUrls());
				}
				urlQuene.remove(url);
			}
		}

	}

	public boolean isCrawlling() {
		return isCrawlling;
	}

}
