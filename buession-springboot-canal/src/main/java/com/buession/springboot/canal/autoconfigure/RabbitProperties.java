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
 * RabbitMQ 适配器配置
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public class RabbitProperties extends BaseAdapterProperties {

	/**
	 * RabbitMQ 主机地址
	 */
	private String server;

	/**
	 * Virtual Host
	 */
	private String virtualHost;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 队列名称
	 */
	private String queueName;

	/**
	 * 返回 RabbitMQ 主机地址
	 *
	 * @return RabbitMQ 主机地址
	 */
	public String getServer() {
		return server;
	}

	/**
	 * 设置 RabbitMQ 主机地址
	 *
	 * @param server
	 * 		RabbitMQ 主机地址
	 */
	public void setServer(String server) {
		this.server = server;
	}

	/**
	 * 返回 Virtual Host
	 *
	 * @return Virtual Host
	 */
	public String getVirtualHost() {
		return virtualHost;
	}

	/**
	 * 设置 Virtual Host
	 *
	 * @param virtualHost
	 * 		Virtual Host
	 */
	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
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

	/**
	 * 返回队列名称
	 *
	 * @return 队列名称
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * 设置队列名称
	 *
	 * @param queueName
	 * 		队列名称
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}
