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
 * RocketMQ 适配器配置
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public class RocketProperties extends AbstractMqAdapterProperties<RocketProperties.Instance> {

	/**
	 * RocketMQ NameServer 地址
	 */
	private String nameServer;

	/**
	 * 是否启用消息跟踪
	 */
	private Boolean enableMessageTrace;

	/**
	 * 消息轨迹数据 Topic
	 */
	private String customizedTraceTopic;

	/**
	 * -
	 */
	private String accessChannel;

	/**
	 * 返回 RocketMQ NameServer 地址
	 *
	 * @return RocketMQ NameServer 地址
	 */
	public String getNameServer() {
		return nameServer;
	}

	/**
	 * 设置 RocketMQ NameServer 地址
	 *
	 * @param nameServer
	 * 		RocketMQ NameServer 地址
	 */
	public void setNameServer(String nameServer) {
		this.nameServer = nameServer;
	}

	/**
	 * 返回是否启用消息跟踪
	 *
	 * @return true / false
	 */
	public Boolean getEnableMessageTrace() {
		return enableMessageTrace;
	}

	/**
	 * 设置是否启用消息跟踪
	 *
	 * @param enableMessageTrace
	 * 		true / false
	 */
	public void setEnableMessageTrace(Boolean enableMessageTrace) {
		this.enableMessageTrace = enableMessageTrace;
	}

	/**
	 * 返回消息轨迹数据 Topic
	 *
	 * @return 消息轨迹数据 Topic
	 */
	public String getCustomizedTraceTopic() {
		return customizedTraceTopic;
	}

	/**
	 * 设置消息轨迹数据 Topic
	 *
	 * @param customizedTraceTopic
	 * 		消息轨迹数据 Topic
	 */
	public void setCustomizedTraceTopic(String customizedTraceTopic) {
		this.customizedTraceTopic = customizedTraceTopic;
	}

	public String getAccessChannel() {
		return accessChannel;
	}

	public void setAccessChannel(String accessChannel) {
		this.accessChannel = accessChannel;
	}

	public final static class Instance extends AbstractMqAdapterProperties.MqBaseInstanceConfiguration {

		/**
		 * Group ID
		 */
		private String groupId;

		/**
		 * 名称空间
		 */
		private String namespace;

		public Instance() {
			super();
		}

		/**
		 * 返回 Group ID
		 *
		 * @return Group ID
		 */
		public String getGroupId() {
			return groupId;
		}

		/**
		 * 设置 Group ID
		 *
		 * @param groupId
		 * 		Group ID
		 */
		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		/**
		 * 返回名称空间
		 *
		 * @return 名称空间
		 */
		public String getNamespace() {
			return namespace;
		}

		/**
		 * 设置名称空间
		 *
		 * @param namespace
		 * 		名称空间
		 */
		public void setNamespace(String namespace) {
			this.namespace = namespace;
		}

	}

}
