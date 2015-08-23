package com.shinnosuke.crawler.fetcher;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

import com.shinnosuke.crawler.Configurable;
import com.shinnosuke.crawler.Configuration;
import com.shinnosuke.crawler.exception.BusinessException;

public class HttpPoolUtil extends Configurable {

	private static final int DEFAULT_SO_TIMEOUT = 30000;
	private static final int DEFAULT_CONNECT_TIMEOUT = 30000;
	private static final String DEFAULT_CHARSET = "UTF-8";

	private ConnectingIOReactor ioReactor;
	private PoolingNHttpClientConnectionManager connManager;
	private CloseableHttpAsyncClient httpClient;

	public HttpPoolUtil(Configuration config) {
		super(config);
		init();
	}

	private void init() {
		try {
			IOReactorConfig ioReactorConfig = IOReactorConfig
					.custom()
					.setIoThreadCount(
							Runtime.getRuntime().availableProcessors())
					.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
					.setSoTimeout(DEFAULT_SO_TIMEOUT).build();
			ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
			connManager = new PoolingNHttpClientConnectionManager(ioReactor);
			MessageConstraints messageConstraints = MessageConstraints.custom()
					.setMaxHeaderCount(200).setMaxLineLength(2000).build();
			ConnectionConfig connConfig = ConnectionConfig.custom()
					.setMalformedInputAction(CodingErrorAction.IGNORE)
					.setUnmappableInputAction(CodingErrorAction.IGNORE)
					.setCharset(Consts.UTF_8)
					.setMessageConstraints(messageConstraints).build();
			connManager.setDefaultConnectionConfig(connConfig);
			httpClient = HttpAsyncClients.custom()
					.setConnectionManager(connManager).build();
			httpClient.start();
			IdleConnectionEvictor connEvictor = new IdleConnectionEvictor(
					connManager);
			connEvictor.start();
		} catch (IOReactorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void build() {
		if (connManager != null) {
			try {
				connManager.shutdown();
			} catch (IOException e) {
				throw new BusinessException(e);
			}
		}
		init();
	}

	public Future<HttpResponse> getFuture(String url) {
		HttpGet httpGet = new HttpGet(url);
		if (!httpClient.isRunning()) {
			build();
		}
		return httpClient.execute(httpGet, null);
	}

	public String getContent(String url) {
		Future<HttpResponse> future = getFuture(url);
		HttpResponse response = null;
		String content = null;
		try {
			response = future.get();
			content = getContent(response);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
	public String getContent(HttpResponse response) {
		String content = null;
		Header header = response.getFirstHeader("Content-type");
		String charsetPrefix = "charset=";
		String charset = DEFAULT_CHARSET;
		if (header != null && header.getValue() != null && header.getValue().indexOf(charsetPrefix) >= 0) {
			charset = header.getValue().substring(header.getValue().indexOf(charset) + charset.length());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			try {
				content = EntityUtils.toString(entity, charset);
			} catch (Exception e) {
				
			} finally {
				try {
					EntityUtils.consume(entity);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return content;
	}
}
