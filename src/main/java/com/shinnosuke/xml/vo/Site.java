package com.shinnosuke.xml.vo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("site")
public class Site {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String url;
	@XStreamAsAttribute
	private String skipStatusCode;
	@XStreamAsAttribute
	private String userAgent;
	@XStreamAsAttribute
	private boolean includeHttps;
	@XStreamAsAttribute
	private boolean isDupRemovalStrict;
	@XStreamAsAttribute
	private String reqDelay;
	@XStreamAsAttribute
	private boolean enable;
	@XStreamAsAttribute
	private String charset;
	@XStreamAsAttribute
	private String schedule;
	@XStreamAsAttribute
	private int thread;
	@XStreamAsAttribute
	private String waitQueue;
	@XStreamAsAttribute
	private String timeout;
	private List<Seed> seeds;
	private List<Rule> rules;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSkipStatusCode() {
		return skipStatusCode;
	}

	public void setSkipStatusCode(String skipStatusCode) {
		this.skipStatusCode = skipStatusCode;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public boolean isIncludeHttps() {
		return includeHttps;
	}

	public void setIncludeHttps(boolean includeHttps) {
		this.includeHttps = includeHttps;
	}

	public boolean isDupRemovalStrict() {
		return isDupRemovalStrict;
	}

	public void setDupRemovalStrict(boolean isDupRemovalStrict) {
		this.isDupRemovalStrict = isDupRemovalStrict;
	}

	public String getReqDelay() {
		return reqDelay;
	}

	public void setReqDelay(String reqDelay) {
		this.reqDelay = reqDelay;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public int getThread() {
		return thread;
	}

	public void setThread(int thread) {
		this.thread = thread;
	}

	public String getWaitQueue() {
		return waitQueue;
	}

	public void setWaitQueue(String waitQueue) {
		this.waitQueue = waitQueue;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public List<Seed> getSeeds() {
		return seeds;
	}

	public void setSeeds(List<Seed> seeds) {
		this.seeds = seeds;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	@Override
	public String toString() {
		return Base.getString(this);
	}
}
