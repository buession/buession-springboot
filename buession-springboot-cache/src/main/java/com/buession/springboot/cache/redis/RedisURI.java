/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * =========================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +-------------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										       |
 * | Author: Yong.Teng <webmaster@buession.com> 													       |
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis;

import com.buession.core.builder.SetBuilder;
import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.net.AbstractUserInfoURI;
import com.buession.net.AbstractUserInfoURIBuilder;
import com.buession.redis.core.RedisNode;

import java.net.URI;
import java.util.Map;
import java.util.Set;

/**
 * @author Yong.Teng
 */
public class RedisURI extends AbstractUserInfoURI {

	private final static long serialVersionUID = 7893709100532398408L;

	public final static String REDIS = "redis";

	public final static String REDISS = "rediss";

	public final static String PARAMETER_NAME_DATABASE = "database";

	public final static String PARAMETER_NAME_DATABASE_ALT = "db";

	public final static String PARAMETER_NAME_CLIENT_NAME = "clientName";

	public final static String PARAMETER_NAME_WEIGHT = "weight";

	public final static String PARAMETER_NAME_TIMEOUT = "timeout";

	public final static Set<String> ALLOWED_SCHEMES = SetBuilder.<String>create().add(REDIS).add(REDISS).build();

	public final static int DEFAULT_TIMEOUT = 60;

	private URI uri;

	private int database;

	private String clientName;

	private int weight;

	private int timeout = DEFAULT_TIMEOUT;

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public boolean isSsl() {
		return REDISS.equals(getScheme());
	}

	public boolean isUseSsl() {
		return isSsl();
	}

	public void setUseSsl(boolean useSsl) {
		setUseSsl(useSsl);
	}

	public static RedisURI create(String uri) {
		Assert.isBlank(uri, "URI must not be null or empty.");
		return create(URI.create(uri));
	}

	public static RedisURI create(URI uri) {
		RedisURI redisURI = buildRedisUriFromUri(uri);

		redisURI.uri = uri;

		return redisURI;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append(getClass().getSimpleName());

		sb.append(" [");

		sb.append("host='").append(host).append('\'');
		sb.append(", port=").append(port);

		if(password != null){
			sb.append(", password='").append(password).append('\'');
		}

		sb.append(", database=").append(database);

		if(clientName != null){
			sb.append(", clientName='").append(clientName).append('\'');
		}

		sb.append(", weight=").append(weight);
		sb.append(", timeout=").append(timeout);
		sb.append(", useSsl=").append(isUseSsl());

		sb.append(']');

		return sb.toString();
	}

	@Override
	public URI toURI() {
		return this.uri;
	}

	private static RedisURI buildRedisUriFromUri(URI uri) {
		Assert.isNull(uri, "URI must not be null");

		Assert.isTrue(Validate.hasText(uri.getScheme()) &&
				ALLOWED_SCHEMES.contains(uri.getScheme()), "Scheme " + uri.getScheme() + " not supported.");

		Builder builder = Builder.getInstance();

		String userInfo = uri.getUserInfo();

		if(Validate.hasText(userInfo) == false && Validate.hasText(uri.getAuthority()) &&
				uri.getAuthority().indexOf('@') > 0){
			userInfo = uri.getAuthority().substring(0, uri.getAuthority().indexOf('@'));
		}

		if(Validate.hasText(userInfo)){
			String password = userInfo;
			int semiIndex = password.indexOf(':');

			if(semiIndex == 0){
				password = password.substring(1);
			}else{
				if(semiIndex > 0){
					password = password.substring(semiIndex + 1);
				}
			}

			if(Validate.hasText(password)){
				builder.password(password);
			}
		}

		if(Validate.hasText(uri.getPath())){
			String database = uri.getPath().substring(1);

			if(Validate.hasText(database)){
				builder.database(Integer.parseInt(database));
			}
		}

		if(Validate.hasText(uri.getQuery())){
			builder.queryString(uri.getQuery());
		}

		return builder.build();
	}

	public final static class Builder extends AbstractUserInfoURIBuilder<RedisURI> {

		private int database = RedisNode.DEFAULT_DATABASE;

		private String queryString;

		private Builder() {
			super();
			host(RedisNode.DEFAULT_HOST);
			port(RedisNode.DEFAULT_PORT);
		}

		public static Builder getInstance() {
			return new Builder();
		}

		public Builder database(final int database) {
			Assert.isNegative(database, "Invalid database number: " + database);
			this.database = database;
			return this;
		}

		public Builder queryString(final String queryString) {
			this.queryString = queryString;
			return this;
		}

		@Override
		public RedisURI build() {
			RedisURI redisURI = new RedisURI();

			redisURI.setHost(host);
			redisURI.setPort(port);

			if(password != null){
				redisURI.setPassword(password);
			}

			redisURI.setDatabase(database);

			Map<String, String> parameters = parseParameters(queryString);

			if(Validate.isNotEmpty(parameters)){
				parseDatabase(redisURI, parameters.get(PARAMETER_NAME_DATABASE));
				parseDatabase(redisURI, parameters.get(PARAMETER_NAME_DATABASE_ALT));
				parseClientName(redisURI, parameters.get(PARAMETER_NAME_CLIENT_NAME));
				parseWeight(redisURI, parameters.get(PARAMETER_NAME_WEIGHT));
				parseTimeout(redisURI, parameters.get(PARAMETER_NAME_TIMEOUT));
			}

			redisURI.setUseSsl(REDISS.equalsIgnoreCase(scheme));

			return redisURI;
		}

		private static void parseDatabase(final RedisURI redisURI, final String paramValue) {
			if(Validate.hasText(paramValue)){
				int db = Integer.parseInt(paramValue);

				if(db >= 0){
					redisURI.setDatabase(db);
				}
			}
		}

		private static void parseClientName(final RedisURI redisURI, final String paramValue) {
			if(Validate.hasText(paramValue)){
				redisURI.setClientName(paramValue);
			}
		}

		private static void parseWeight(final RedisURI redisURI, final String paramValue) {
			if(Validate.hasText(paramValue)){
				int weight = Integer.parseInt(paramValue);

				if(weight >= 0){
					redisURI.setWeight(weight);
				}
			}
		}

		private static void parseTimeout(final RedisURI redisURI, final String paramValue) {
			if(Validate.hasText(paramValue)){
				int timeout = Integer.parseInt(paramValue);

				if(timeout >= 0){
					redisURI.setTimeout(timeout);
					return;
				}
			}

			redisURI.setTimeout(DEFAULT_TIMEOUT);
		}

	}

}
