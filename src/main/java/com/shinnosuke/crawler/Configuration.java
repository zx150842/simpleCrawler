package com.shinnosuke.crawler;

public class Configuration {
	
	private boolean resumable = false;	
	private long requestDelay = 1000;

	public boolean isResumable() {
		return resumable;
	}

	public void setResumable(boolean resumable) {
		this.resumable = resumable;
	}

	public long getRequestDelay() {
		return requestDelay;
	}

	public void setRequestDelay(long requestDelay) {
		this.requestDelay = requestDelay;
	}
}
