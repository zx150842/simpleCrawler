package com.shinnosuke.crawler.fetcher;

import java.util.concurrent.TimeUnit;

import org.apache.http.nio.conn.NHttpClientConnectionManager;

public class IdleConnectionEvictor extends Thread {
	
	private final NHttpClientConnectionManager cm;
	private volatile boolean shutdown;
	
	public IdleConnectionEvictor(NHttpClientConnectionManager cm) {
		super();
		this.cm = cm;
	}
	
	@Override
	public void run() {
		try {
			while (!shutdown) {
				synchronized (this) {
					wait(5000);
					cm.closeExpiredConnections();
					cm.closeIdleConnections(5, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException e) {
			
		}
	}
	
	public void shutdown() {
		shutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
