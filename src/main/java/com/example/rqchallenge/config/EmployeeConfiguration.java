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

/**
 * A configuration class used for creating HttpClient Connection Pool and
 * objectMapper instances.
 *
 */
@Configuration
public class EmployeeConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeConfiguration.class);

	@Value("${max_connetion:10}")
	private String maxConnectionProp;

	@Value("${max_connetions_per_route:3}")
	private String maxConnectionPerRouteProp;

	@Value("${socket_timeout:10000}")
	private String socketTimeoutProp;

	@Value("${connect_timeout:6000}")
	private String connectTimeoutProp;

	@Value("${connection_request_timeout:10000}")
	private String connectionRequestTimeoutProp;

	/**
	 * 
	 * Creates a CloseableHttpClient from PoolingConnectionManager. <br>
	 * Note: PoolingConnectionManager is created with below default values. <br>
	 * <b> MaxConnection</b>: Maximum number of connections in a pool. <br>
	 * <b> maxConnectionPerRoute</b>: Maximum number of concurrent connection to
	 * same endpoint. <br>
	 * <b> socketTimeout</b>: Idle time before closing connection with server<br>
	 * <b> connectTimeout</b>: Timeout until a connection with the server is
	 * established. <br>
	 * <b> connectionRequestTimeout</b>: Max timeout to fetch a connection from the
	 * connection pool when all connections are busy.
	 * 
	 * @return CloseableHttpClient
	 */
	@Bean
	public CloseableHttpClient httpClient() {

		int maxConnection = Integer.parseInt(maxConnectionProp);
		int maxConnectionPerRoute = Integer.parseInt(maxConnectionPerRouteProp);
		int socketTimeout = Integer.parseInt(socketTimeoutProp);
		int connectTimeout = Integer.parseInt(connectTimeoutProp);
		int connectionRequestTimeout = Integer.parseInt(connectionRequestTimeoutProp);

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(maxConnection);
		connectionManager.setDefaultMaxPerRoute(maxConnectionPerRoute);

		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();

		return HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
				.build();
	}

	/**
	 * @return ObjectMapper
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper;
	}
}
