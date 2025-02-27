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
 * | Copyright @ 2013-2025 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.id;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * ID 生成器配置
 *
 * @author Yong.Teng
 * @since 3.0.1
 */
@ConfigurationProperties(prefix = IdProperties.PREFIX)
public class IdProperties {

	public final static String PREFIX = "spring.id";

	/**
	 * Nano ID 生成器配置
	 */
	@NestedConfigurationProperty
	private NanoID nanoID;

	/**
	 * 返回雪花算法 ID 生成器配置
	 */
	@NestedConfigurationProperty
	private Snowflake snowflake;

	/**
	 * 返回 Nano ID 生成器配置
	 *
	 * @return Nano ID 生成器配置
	 */
	public NanoID getNanoID() {
		return nanoID;
	}

	/**
	 * 设置 Nano ID 生成器配置
	 *
	 * @param nanoID
	 * 		Nano ID 生成器配置
	 */
	public void setNanoID(NanoID nanoID) {
		this.nanoID = nanoID;
	}

	/**
	 * 返回雪花算法 ID 生成器配置
	 *
	 * @return 雪花算法 ID 生成器配置
	 */
	public Snowflake getSnowflake() {
		return snowflake;
	}

	/**
	 * 设置雪花算法 ID 生成器配置
	 *
	 * @param snowflake
	 * 		雪花算法 ID 生成器配置
	 */
	public void setSnowflake(Snowflake snowflake) {
		this.snowflake = snowflake;
	}

	/**
	 * Nano ID 生成器配置
	 *
	 * @author Yong.Teng
	 * @since 3.0.1
	 */
	public final static class NanoID {

		/**
		 * 生成长度
		 */
		private Integer length;

		/**
		 * 返回生成长度
		 *
		 * @return 生成长度
		 */
		public Integer getLength() {
			return length;
		}

		/**
		 * 设置生成长度
		 *
		 * @param length
		 * 		生成长度
		 */
		public void setLength(Integer length) {
			this.length = length;
		}

	}

	/**
	 * 雪花算法 ID 生成器配置
	 *
	 * @author Yong.Teng
	 * @since 3.0.1
	 */
	public final static class Snowflake {

		/**
		 * 数据中心 ID
		 */
		private Long datacenterId;

		/**
		 * 工作机器 ID
		 */
		private Long workerId;

		/**
		 * 返回数据中心 ID
		 *
		 * @return 数据中心 ID
		 */
		public Long getDatacenterId() {
			return datacenterId;
		}

		/**
		 * 设置数据中心 ID
		 *
		 * @param datacenterId
		 * 		数据中心 ID
		 */
		public void setDatacenterId(Long datacenterId) {
			this.datacenterId = datacenterId;
		}

		/**
		 * 返回工作机器 ID
		 *
		 * @return 工作机器 ID
		 */
		public Long getWorkerId() {
			return workerId;
		}

		/**
		 * 设置工作机器 ID
		 *
		 * @param workerId
		 * 		工作机器 ID
		 */
		public void setWorkerId(Long workerId) {
			this.workerId = workerId;
		}

	}

}
