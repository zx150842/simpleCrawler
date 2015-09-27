package com.shinnosuke.xml.parser;

import java.io.File;

import com.shinnosuke.xml.vo.Site;
import com.thoughtworks.xstream.XStream;

public class XmlParser {

	private static final XStream xstream = new XStream();
	
	static {
		xstream.autodetectAnnotations(true);
		xstream.processAnnotations(Site.class);
	}
	
	public static Site parser(String configPath) {
		return (Site)xstream.fromXML(new File(configPath));
	}
	
	public static void main(String[] args) {
		String path = XmlParser.class.getClassLoader().getResource("websiteconfig/test.xml").getPath();
		String configPath = path;
		Site site = parser(configPath);
		System.out.println(site);
	}
}
