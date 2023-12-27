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

import com.buession.canal.client.adapter.AdapterClient;
import com.buession.canal.client.adapter.KafkaAdapterClient;
import com.buession.canal.client.adapter.PulsarMQAdapterClient;
import com.buession.canal.client.adapter.RabbitMQAdapterClient;
import com.buession.canal.client.adapter.RocketMQAdapterClient;
import com.buession.canal.client.adapter.TcpAdapterClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CanalProperties.class)
public class AdapterClientConfiguration {

	@FunctionalInterface
	interface CanalAdapterClientBuilder<IC extends com.buession.canal.core.Configuration,
			C extends AdapterClient> {

		C newInstance(final String destination, final IC instance);

	}

	interface IAdapterClientConfiguration {

		/**
		 * 初始化 {@link AdapterClient} 列表 bean
		 *
		 * @return {@link AdapterClient} 列表 bean
		 */
		Set<AdapterClient> createAdapterClients();

	}

	static abstract class AbstractAdapterClientConfiguration implements IAdapterClientConfiguration {

		protected CanalProperties canalProperties;

		AbstractAdapterClientConfiguration(final CanalProperties canalProperties) {
			this.canalProperties = canalProperties;
		}

		protected <IC extends com.buession.canal.core.Configuration, C extends AdapterClient> Set<AdapterClient> createCanalAdapterClients(
				final AdapterProperties<IC> properties, final CanalAdapterClientBuilder<IC, C> builder) {
			return properties.getInstances().entrySet().stream().map((e)->builder.newInstance(e.getKey(), e.getValue()))
					.collect(Collectors.toSet());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.KafkaCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(IAdapterClientConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".kafka")
	static class KafkaAdapterClientConfiguration extends AbstractAdapterClientConfiguration {

		public KafkaAdapterClientConfiguration(final CanalProperties canalProperties) {
			super(canalProperties);
		}

		@Bean
		@Override
		public Set<AdapterClient> createAdapterClients() {
			final KafkaProperties kafka = canalProperties.getKafka();
			return createCanalAdapterClients(kafka, (topic, instance)->new KafkaAdapterClient(kafka.getServers(), topic,
					instance.getGroupId(), instance.getPartition(), instance, instance.isFlatMessage()));
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.PulsarCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(IAdapterClientConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".pulsar")
	static class PulsarAdapterClientConfiguration extends AbstractAdapterClientConfiguration {

		public PulsarAdapterClientConfiguration(final CanalProperties canalProperties) {
			super(canalProperties);
		}

		@Bean
		@Override
		public Set<AdapterClient> createAdapterClients() {
			final PulsarProperties pulsar = canalProperties.getPulsar();
			return createCanalAdapterClients(pulsar, (topic, instance)->new PulsarMQAdapterClient(
					pulsar.getServiceUrl(), pulsar.getRoleToken(), topic, instance.getSubscriptName(),
					pulsar.getGetBatchTimeout(), pulsar.getBatchProcessTimeout(), pulsar.getRedeliveryDelay(),
					pulsar.getAckTimeout(), pulsar.isRetry(), pulsar.isRetryDLQUpperCase(),
					pulsar.getMaxRedeliveryCount(), instance, instance.isFlatMessage()));
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.RabbitCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(IAdapterClientConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".rabbit")
	static class RabbitAdapterClientConfiguration extends AbstractAdapterClientConfiguration {

		public RabbitAdapterClientConfiguration(final CanalProperties canalProperties) {
			super(canalProperties);
		}

		@Bean
		@Override
		public Set<AdapterClient> createAdapterClients() {
			final RabbitProperties rabbit = canalProperties.getRabbit();
			return createCanalAdapterClients(rabbit,
					(queueName, instance)->new RabbitMQAdapterClient(rabbit.getServer(),
							rabbit.getVirtualHost(), rabbit.getUsername(), rabbit.getPassword(), queueName,
							instance, instance.isFlatMessage()));
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.RocketCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(IAdapterClientConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".rocket")
	static class RocketAdapterClientConfiguration extends AbstractAdapterClientConfiguration {

		public RocketAdapterClientConfiguration(final CanalProperties canalProperties) {
			super(canalProperties);
		}

		@Bean
		@Override
		public Set<AdapterClient> createAdapterClients() {
			final RocketProperties rocket = canalProperties.getRocket();
			return createCanalAdapterClients(rocket, (topic, instance)->new RocketMQAdapterClient(
					rocket.getNameServer(), topic, instance.getGroupId(), rocket.getEnableMessageTrace(),
					rocket.getCustomizedTraceTopic(), rocket.getAccessChannel(), instance,
					instance.isFlatMessage()));
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.TcpCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(IAdapterClientConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".tcp")
	static class TcpAdapterClientConfiguration extends AbstractAdapterClientConfiguration {

		public TcpAdapterClientConfiguration(final CanalProperties canalProperties) {
			super(canalProperties);
		}

		@Bean
		@Override
		public Set<AdapterClient> createAdapterClients() {
			final TcpProperties tcp = canalProperties.getTcp();
			return createCanalAdapterClients(tcp,
					(destination, instance)->new TcpAdapterClient(tcp.getServer(),
							tcp.getZkServers(), destination, tcp.getUsername(), tcp.getPassword(), instance));
		}

	}

}
