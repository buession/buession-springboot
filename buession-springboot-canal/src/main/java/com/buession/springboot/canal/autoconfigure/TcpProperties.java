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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.canal.autoconfigure;

/**
 * Kafka 适配器配置
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public class TcpProperties extends AbstractAdapterProperties<TcpProperties.Instance> {

	/**
	 * 主机地址
	 */
	private String server;

	/**
	 * Zookeeper 主机地址
	 */
	private String zkServers;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 返回主机地址
	 *
	 * @return 主机地址
	 */
	public String getServer() {
		return server;
	}

	/**
	 * 设置主机地址
	 *
	 * @param server
	 * 		主机地址
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * 返回 Zookeeper 主机地址
	 *
	 * @return Zookeeper 主机地址
	 */
	public String getZkServers() {
		return zkServers;
	}

	/**
	 * 设置 Zookeeper 主机地址
	 *
	 * @param zkServers
	 * 		Zookeeper 主机地址
	 */
	public void setZkServers(String zkServers) {
		this.zkServers = zkServers;
	}

	/**
	 * 返回用户名
	 *
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 *
	 * @param username
	 * 		用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 返回密码
	 *
	 * @return 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 *
	 * @param password
	 * 		密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public final static class Instance extends AbstractAdapterProperties.BaseInstanceConfiguration {

		public Instance() {
		}

	}

}
