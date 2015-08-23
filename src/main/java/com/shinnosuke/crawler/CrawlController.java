package com.shinnosuke.crawler;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.Lists;
import com.shinnosuke.crawler.exception.BusinessException;

public class CrawlController {
	
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition finishCondition = lock.newCondition();
	private final AtomicBoolean isFinish = new AtomicBoolean(false);
	private static final long TEN_SECONDS = 10000;
	private final AtomicBoolean shutDown = new AtomicBoolean(false);
	private static final int DEFAULT_CRAWLERS = Runtime.getRuntime().availableProcessors();
	
	private URLQueue urlQuene;
	private Configuration configuration;
	
	public CrawlController(Configuration configuration) {
		this.urlQuene = new URLQueue();
		this.configuration = configuration;
	}
	
	public <T extends WebCrawler> void start(Class<T> _c) {
		start(_c, DEFAULT_CRAWLERS);
	}
	
	public <T extends WebCrawler> void start(Class<T> _c, int numberOfCrawler) {
		final List<Thread> threads = Lists.newArrayList();
		final List<WebCrawler> crawlers = Lists.newArrayList();
		for (int i = 1; i <= numberOfCrawler; ++i) {
			T crawler;
			try {
				crawler = _c.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new BusinessException(e);
			} 
			crawler.init(this, configuration);
			Thread thread = new Thread(crawler);
			thread.start();
			threads.add(thread);
			crawlers.add(crawler);
		}
		
		Thread monitorThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					sleep(TEN_SECONDS);
					boolean someoneWorking = false;
					for (int index = 0; index < threads.size(); ++index) {
						Thread thread = threads.get(index);
						if (!thread.isAlive()) {
							thread.start();
							threads.remove(index);
							threads.add(index, thread);
						} 
						WebCrawler crawler = crawlers.get(index);
						if (crawler.isCrawlling()) {
							someoneWorking = true;
						}
					}
					
					if (!someoneWorking) {
						sleep(TEN_SECONDS);
						for (int index = 0; index < threads.size(); ++index) {
							Thread thread = threads.get(index);
							WebCrawler crawler = crawlers.get(index);
							if (thread.isAlive() && crawler.isCrawlling()) {
								someoneWorking = true;
								break;
							}
						}
						if (!someoneWorking) {
							if (urlQuene.getUrlQueue().size() == 0) {
								sleep(TEN_SECONDS);
								if (urlQuene.getUrlQueue().size() == 0) {
									break;
								}
							}
						}
					}
				}
				try {
					lock.lock();
					finishCondition.notifyAll();
				} finally {
					lock.unlock();
				}
			}
		});
		monitorThread.start();
		if (!isFinish.get()) {
			try {
				lock.lock();
				finishCondition.await();
				isFinish.compareAndSet(false, true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	public boolean isFinish() {
		return isFinish.get();
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			
		}
	}
	
	public boolean shutDown() {
		return this.shutDown.compareAndSet(false, true);
	}
	
	public boolean isShutDown() {
		return this.shutDown.get();
	}
	
	public URLQueue getUrlQuene() {
		return urlQuene;
	}
	
	public void addSeed(String url) {
		urlQuene.add(url);
	}
	
	public void addSeed(List<String> urls) {
		urlQuene.addAll(urls);
	}
	
}
