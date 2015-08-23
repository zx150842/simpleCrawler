package com.shinnosuke.crawler.fetcher;

import java.util.List;

public class Page {

	private String url;
	private List<String> outgoingUrls; 
	private String content;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<String> getOutgoingUrls() {
		return outgoingUrls;
	}
	public void setOutgoingUrls(List<String> outgoingUrls) {
		this.outgoingUrls = outgoingUrls;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
