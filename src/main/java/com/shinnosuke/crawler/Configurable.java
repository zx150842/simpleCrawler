package com.shinnosuke.crawler;

public class Configurable {

	private Configuration configuration;
	
	public Configurable() {}
	
	public Configurable(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public Configuration get() {
		return configuration;
	}
}
