package com.shinnosuke.crawler;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class URLQueue {
	
	private LinkedBlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();
	private ReentrantLock mutex = new ReentrantLock();
	
	public LinkedBlockingQueue<String> getUrlQueue() {
		return urlQueue;
	}
	
	public ReentrantLock getLock() {
		return mutex;
	}
	
	public void addAll(List<String> urls) {
		urlQueue.addAll(urls);
	}
	
	public void add(String url) {
		urlQueue.add(url);
	}
	
	public void remove(String url) {
		urlQueue.remove(url);
	}
	
}
