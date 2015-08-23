package com.shinosuke.crawler.parse;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.Lists;
import com.shinnosuke.crawler.fetcher.Page;

public class Parse {

	public static Page parse(String content) {
		Page page = new Page();
		page.setContent(content);
		page.setOutgoingUrls(getUrls(content));
		return page;
	}
	
	private static List<String> getUrls(String content) {
		Document doc = Jsoup.parse(content);
		Elements links = doc.getElementsByTag("a");
		List<String> outgoingUrls = Lists.newArrayList();
		for (Element link : links) {
			outgoingUrls.add(link.attr("href"));
		}
		return outgoingUrls;
	}
}
