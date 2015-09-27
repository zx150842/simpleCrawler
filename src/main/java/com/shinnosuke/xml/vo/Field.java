package com.shinnosuke.xml.vo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("field")
public class Field {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private boolean isArray;
	@XStreamImplicit
	private List<Parser> parsers;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isArray() {
		return isArray;
	}
	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}
	public List<Parser> getParsers() {
		return parsers;
	}
	public void setParsers(List<Parser> parsers) {
		this.parsers = parsers;
	}
	
	@Override
	public String toString() {
		return Base.getString(this);
	}
}
