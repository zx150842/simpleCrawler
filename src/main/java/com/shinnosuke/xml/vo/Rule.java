package com.shinnosuke.xml.vo;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("rule")
public class Rule {
	@XStreamAsAttribute
	private String type;
	@XStreamAsAttribute
	private String value;
	@XStreamAsAttribute
	private boolean isListPage;
	@XStreamImplicit
	private List<Field> fields;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public boolean isListPage() {
		return isListPage;
	}
	public void setListPage(boolean isListPage) {
		this.isListPage = isListPage;
	}
	
	@Override
	public String toString() {
		return Base.getString(this);
	}
}
