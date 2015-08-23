package com.shinnosuke.crawler;

public class CrawlControllerTest {

	public static void main(String[] args) {
		Configuration configuration = new Configuration();
		CrawlController controller = new CrawlController(configuration);
		String url = "http://news.sina.com.cn/";
		controller.addSeed(url);
		controller.start(WebCrawler.class, 1);
	}
	
}
