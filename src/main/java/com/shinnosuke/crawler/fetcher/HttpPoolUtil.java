package com.shinnosuke.crawler.fetcher;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import com.shinnosuke.crawler.Configurable;
import com.shinnosuke.crawler.Configuration;

public class HttpPoolUtil extends Configurable {

	private ConnectingIOReactor ioReactor;
	private PoolingNHttpClientConnectionManager cm;
	private CloseableHttpAsyncClient httpClient;
	
	public HttpPoolUtil(Configuration config) {
		super(config);
		try {
			ioReactor = new DefaultConnectingIOReactor();
			cm = new PoolingNHttpClientConnectionManager(ioReactor);
			httpClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
			IdleConnectionEvictor connEvictor = new IdleConnectionEvictor(cm);
			connEvictor.start();
		} catch (IOReactorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CloseableHttpAsyncClient getHttpAsyncClient() {
		return httpClient;
	}
}
