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
	private Nano nano;

	/**
	 * 随机数 ID 生成器配置
	 */
	@NestedConfigurationProperty
	private RandomDigit randomDigit;

	/**
	 * 随机 ID 生成器配置
	 */
	@NestedConfigurationProperty
	private Random random;

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
	public Nano getNano() {
		return nano;
	}

	/**
	 * 设置 Nano ID 生成器配置
	 *
	 * @param nano
	 * 		Nano ID 生成器配置
	 */
	public void setNano(Nano nano) {
		this.nano = nano;
	}

	/**
	 * 返回随机数 ID 生成器配置
	 *
	 * @return 随机数 ID 生成器配置
	 */
	public RandomDigit getRandomDigit() {
		return randomDigit;
	}

	/**
	 * 返回随机 ID 生成器配置
	 *
	 * @return 随机 ID 生成器配置
	 */
	public Random getRandom() {
		return random;
	}

	/**
	 * 设置随机 ID 生成器配置
	 *
	 * @param random
	 * 		随机 ID 生成器配置
	 */
	public void setRandom(Random random) {
		this.random = random;
	}

	/**
	 * 设置随机数 ID 生成器配置
	 *
	 * @param randomDigit
	 * 		随机数 ID 生成器配置
	 */
	public void setRandomDigit(RandomDigit randomDigit) {
		this.randomDigit = randomDigit;
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
	public final static class Nano {

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
	 * 随机数 ID 生成器配置
	 *
	 * @author Yong.Teng
	 * @since 3.0.1
	 */
	public final static class RandomDigit {

		/**
		 * 最小值
		 */
		private long min = Long.MIN_VALUE;

		/**
		 * 最大值
		 */
		private long max = Long.MAX_VALUE;

		/**
		 * 返回最小值
		 *
		 * @return 最小值
		 */
		public long getMin() {
			return min;
		}

		/**
		 * 设置最小值
		 *
		 * @param min
		 * 		最小值
		 */
		public void setMin(long min) {
			this.min = min;
		}

		/**
		 * 返回最大值
		 *
		 * @return 最大值
		 */
		public long getMax() {
			return max;
		}

		/**
		 * 设置最大值
		 *
		 * @param max
		 * 		最大值
		 */
		public void setMax(long max) {
			this.max = max;
		}

	}

	/**
	 * 随机 ID 生成器配置
	 *
	 * @author Yong.Teng
	 * @since 3.0.1
	 */
	public final static class Random {

		/**
		 * 长度
		 */
		private Integer length;

		/**
		 * 随机字符词典
		 */
		private String chars;

		/**
		 * 返回长度
		 *
		 * @return 长度
		 */
		public Integer getLength() {
			return length;
		}

		/**
		 * 设置长度
		 *
		 * @param length
		 * 		长度
		 */
		public void setLength(Integer length) {
			this.length = length;
		}

		/**
		 * 返回随机字符词典
		 *
		 * @return 随机字符词典
		 */
		public String getChars() {
			return chars;
		}

		/**
		 * 设置随机字符词典
		 *
		 * @param chars
		 * 		随机字符词典
		 */
		public void setChars(String chars) {
			this.chars = chars;
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
