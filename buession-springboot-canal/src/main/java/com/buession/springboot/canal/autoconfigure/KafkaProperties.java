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

import com.buession.springboot.canal.MqBaseInstance;

/**
 * Kafka 适配器配置
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public class KafkaProperties extends AbstractMqAdapterProperties<KafkaProperties.Instance> {

	/**
	 * Kafka 主机地址
	 */
	private String servers;

	/**
	 * 返回 Kafka 主机地址
	 *
	 * @return Kafka 主机地址
	 */
	public String getServers() {
		return servers;
	}

	/**
	 * 设置 Kafka 主机地址
	 *
	 * @param servers
	 * 		Kafka 主机地址
	 */
	public void setServers(String servers) {
		this.servers = servers;
	}

	public final static class Instance extends MqBaseInstance {

		/**
		 * Group Id
		 */
		private String groupId;

		/**
		 * 分区
		 */
		private Integer partition;

		/**
		 * 返回 Group Id
		 *
		 * @return Group Id
		 */
		public String getGroupId() {
			return groupId;
		}

		/**
		 * 设置 Group Id
		 *
		 * @param groupId
		 * 		Group Id
		 */
		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		/**
		 * 返回分区
		 *
		 * @return 分区
		 */
		public Integer getPartition() {
			return partition;
		}

		/**
		 * 设置分区
		 *
		 * @param partition
		 * 		分区
		 */
		public void setPartition(Integer partition) {
			this.partition = partition;
		}

	}

}
