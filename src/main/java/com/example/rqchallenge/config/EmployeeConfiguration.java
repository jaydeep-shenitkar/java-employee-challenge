package com.example.rqchallenge.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class EmployeeConfiguration {

	@Value("${max_connetion:10}")
	String maxConnectionProp;

	@Value("${socket_timeout:60}")
	String socketTimeoutProp;

	@Value("${connect_timeout:10}")
	String connectTimeoutProp;

	@Value("${connection_request_timeout:10}")
	String connectionRequestTimeoutProp;

	@Bean
	public CloseableHttpClient httpClient() {

		int maxConnection = Integer.parseInt(maxConnectionProp);
		int socketTimeout = Integer.parseInt(socketTimeoutProp);
		int connectTimeout = Integer.parseInt(connectTimeoutProp);
		int connectionRequestTimeout = Integer.parseInt(connectionRequestTimeoutProp);

		Logger logger = LoggerFactory.getLogger(EmployeeConfiguration.class);
		logger.info("MAX CONNECTION " + maxConnection);

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxConnection);
		connectionManager.setDefaultMaxPerRoute(maxConnection);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

		return HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
				.build();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}
}
