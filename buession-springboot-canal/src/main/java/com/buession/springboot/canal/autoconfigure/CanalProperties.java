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

import com.buession.springboot.canal.ThreadConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Canal 配置
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
@ConfigurationProperties(CanalProperties.PREFIX)
public class CanalProperties {

	public final static String PREFIX = "spring.canal";

	/**
	 * 线程池配置
	 */
	private ThreadConfig thread = new ThreadConfig();

	/**
	 * Kafka 实例配置
	 */
	private KafkaProperties kafka;

	/**
	 * RabbitMQ 实例配置
	 */
	private RabbitProperties rabbit;

	/**
	 * RocketMQ 实例配置
	 */
	private RocketProperties rocket;

	/**
	 * PulsarMQ 实例配置
	 */
	private PulsarProperties pulsar;

	/**
	 * TCP 实例配置
	 */
	private TcpProperties tcp;

	/**
	 * 返回线程池配置
	 *
	 * @return 线程池配置
	 */
	public ThreadConfig getThread() {
		return thread;
	}

	/**
	 * 设置线程池配置
	 *
	 * @param thread
	 * 		线程池配置
	 */
	public void setThread(ThreadConfig thread) {
		this.thread = thread;
	}

	/**
	 * 返回 Kafka 实例配置
	 *
	 * @return Kafka 实例配置
	 */
	public KafkaProperties getKafka() {
		return kafka;
	}

	/**
	 * 设置 Kafka 实例配置
	 *
	 * @param kafka
	 * 		Kafka 实例配置
	 */
	public void setKafka(KafkaProperties kafka) {
		this.kafka = kafka;
	}

	/**
	 * 返回 RabbitMQ 实例配置
	 *
	 * @return RabbitMQ 实例配置
	 */
	public RabbitProperties getRabbit() {
		return rabbit;
	}

	/**
	 * 设置 RabbitMQ 实例配置
	 *
	 * @param rabbit
	 * 		RabbitMQ 实例配置
	 */
	public void setRabbit(RabbitProperties rabbit) {
		this.rabbit = rabbit;
	}

	/**
	 * 返回 RocketMQ 实例配置
	 *
	 * @return RocketMQ 实例配置
	 */
	public RocketProperties getRocket() {
		return rocket;
	}

	/**
	 * 设置 RocketMQ 实例配置
	 *
	 * @param rocket
	 * 		RocketMQ 实例配置
	 */
	public void setRocket(RocketProperties rocket) {
		this.rocket = rocket;
	}

	/**
	 * 返回 PulsarMQ 实例配置
	 *
	 * @return PulsarMQ 实例配置
	 */
	public PulsarProperties getPulsar() {
		return pulsar;
	}

	/**
	 * 设置 PulsarMQ 实例配置
	 *
	 * @param pulsar
	 * 		PulsarMQ 实例配置
	 */
	public void setPulsar(PulsarProperties pulsar) {
		this.pulsar = pulsar;
	}

	/**
	 * 返回 TCP 实例配置
	 *
	 * @return TCP 实例配置
	 */
	public TcpProperties getTcp() {
		return tcp;
	}

	/**
	 * 设置 TCP 实例配置
	 *
	 * @param tcp
	 * 		TCP 实例配置
	 */
	public void setTcp(TcpProperties tcp) {
		this.tcp = tcp;
	}

}
