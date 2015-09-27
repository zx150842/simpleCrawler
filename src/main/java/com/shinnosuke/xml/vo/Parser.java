package com.shinnosuke.xml.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("parser")
public class Parser {
	@XStreamAsAttribute
	private String xpath;
	@XStreamAsAttribute
	private String attribute;
	@XStreamAsAttribute
	private String regex;
	
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	
	@Override
	public String toString() {
		return Base.getString(this);
	}
}
