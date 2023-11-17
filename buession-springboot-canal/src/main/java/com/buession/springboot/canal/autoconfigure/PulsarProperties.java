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

import com.buession.canal.client.adapter.PulsarMQAdapterClient;

/**
 * PulsarMQ 适配器配置
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public class PulsarProperties extends AbstractMqAdapterProperties<PulsarProperties.Instance> {

	/**
	 * PulsarMQ 服务地址
	 */
	private String serviceUrl;

	/**
	 * Role Token
	 */
	private String roleToken;

	/**
	 * -
	 */
	private int getBatchTimeout = PulsarMQAdapterClient.DEFAULT_GET_BATCH_TIMEOUT;

	/**
	 * -
	 */
	private int batchProcessTimeout = PulsarMQAdapterClient.DEFAULT_BATCH_PROCESS_TIMEOUT;

	/**
	 * -
	 */
	private int redeliveryDelay = PulsarMQAdapterClient.DEFAULT_REDELIVERY_DELAY;

	/**
	 * -
	 */
	private int ackTimeout = PulsarMQAdapterClient.DEFAULT_ACK_TIMEOUT;

	/**
	 * 是否重试
	 */
	private boolean retry = true;

	/**
	 * -
	 */
	private boolean retryDLQUpperCase = true;

	/**
	 * -
	 */
	private Integer maxRedeliveryCount;

	/**
	 * 返回 PulsarMQ 服务地址
	 *
	 * @return PulsarMQ 服务地址
	 */
	public String getServiceUrl() {
		return serviceUrl;
	}

	/**
	 * 设置 PulsarMQ 服务地址
	 *
	 * @param serviceUrl
	 * 		PulsarMQ 服务地址
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	/**
	 * 返回 Role Token
	 *
	 * @return Role Token
	 */
	public String getRoleToken() {
		return roleToken;
	}

	/**
	 * 设置 Role Token
	 *
	 * @param roleToken
	 * 		Role Token
	 */
	public void setRoleToken(String roleToken) {
		this.roleToken = roleToken;
	}

	public int getGetBatchTimeout() {
		return getBatchTimeout;
	}

	public void setGetBatchTimeout(int getBatchTimeout) {
		this.getBatchTimeout = getBatchTimeout;
	}

	public int getBatchProcessTimeout() {
		return batchProcessTimeout;
	}

	public void setBatchProcessTimeout(int batchProcessTimeout) {
		this.batchProcessTimeout = batchProcessTimeout;
	}

	public int getRedeliveryDelay() {
		return redeliveryDelay;
	}

	public void setRedeliveryDelay(int redeliveryDelay) {
		this.redeliveryDelay = redeliveryDelay;
	}

	public int getAckTimeout() {
		return ackTimeout;
	}

	public void setAckTimeout(int ackTimeout) {
		this.ackTimeout = ackTimeout;
	}

	/**
	 * 返回是否重试
	 *
	 * @return true / false
	 */
	public boolean isRetry() {
		return retry;
	}

	/**
	 * 设置是否重试
	 *
	 * @param retry
	 * 		true / false
	 */
	public void setRetry(boolean retry) {
		this.retry = retry;
	}

	public boolean isRetryDLQUpperCase() {
		return retryDLQUpperCase;
	}

	public void setRetryDLQUpperCase(boolean retryDLQUpperCase) {
		this.retryDLQUpperCase = retryDLQUpperCase;
	}

	public Integer getMaxRedeliveryCount() {
		return maxRedeliveryCount;
	}

	public void setMaxRedeliveryCount(Integer maxRedeliveryCount) {
		this.maxRedeliveryCount = maxRedeliveryCount;
	}

	public final static class Instance extends AbstractMqAdapterProperties.MqBaseInstanceConfiguration {

		/**
		 * 订阅名称
		 */
		private String subscriptName;

		public Instance() {
			super();
		}

		/**
		 * 返回订阅名称
		 *
		 * @return 订阅名称
		 */
		public String getSubscriptName() {
			return subscriptName;
		}

		/**
		 * 设置订阅名称
		 *
		 * @param subscriptName
		 * 		订阅名称
		 */
		public void setSubscriptName(String subscriptName) {
			this.subscriptName = subscriptName;
		}

	}

}
