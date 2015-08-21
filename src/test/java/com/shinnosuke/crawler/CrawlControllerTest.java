package com.shinnosuke.crawler;

public class CrawlControllerTest {

	public static void main(String[] args) {
		try {
			new CrawlControllerTest().method1();;
		} catch (RuntimeException e) {
			System.out.println("catch exception in main");
		}
	}
	
	private void method1() {
		try {
			method2();
		} catch (RuntimeException e) {
			System.out.println("catch exception in method1");
		}
	}
	
	private void method2() {
		throw new RuntimeException();
	}
}
