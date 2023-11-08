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

import com.buession.canal.client.Binder;
import com.buession.canal.client.adapter.CanalAdapterClient;
import com.buession.canal.client.adapter.KafkaCanalAdapterClient;
import com.buession.canal.client.adapter.PulsarMQCanalAdapterClient;
import com.buession.canal.client.adapter.RabbitMQCanalAdapterClient;
import com.buession.canal.client.adapter.RocketMQCanalAdapterClient;
import com.buession.canal.client.adapter.TcpCanalAdapterClient;
import com.buession.canal.core.binding.CanalBinding;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CanalProperties.class)
public class BinderConfiguration {

	@FunctionalInterface
	interface CanalAdapterClientBuilder<IC extends AbstractAdapterProperties.BaseInstanceConfig,
			C extends CanalAdapterClient> {

		C newInstance(final String destination, final IC instance);

	}

	interface ICanalAdapterBinderConfiguration {

		/**
		 * 初始化 {@link Binder} 列表 bean
		 *
		 * @return {@link Binder} 列表 bean
		 */
		List<Binder> createBinders();

	}

	static abstract class AbstractCanalAdapterBinderConfiguration implements ICanalAdapterBinderConfiguration {

		protected CanalProperties canalProperties;

		protected List<CanalBinding<?>> bindings;

		AbstractCanalAdapterBinderConfiguration(final CanalProperties canalProperties,
												final ObjectProvider<List<CanalBinding<?>>> canalBindings) {
			this.canalProperties = canalProperties;
			this.bindings = canalBindings.getIfAvailable();
		}

		protected <IC extends AbstractAdapterProperties.BaseInstanceConfig, C extends CanalAdapterClient> List<Binder> createBinders(
				final AdapterProperties<IC> properties, final CanalAdapterClientBuilder<IC, C> builder) {
			final List<Binder> binders = new ArrayList<>(properties.getInstances().size());

			properties.getInstances().forEach((name, instanceConfig)->{
				Binder binder = new Binder();

				for(CanalBinding<?> binding : bindings){
					if(Objects.equals(binding.getDestination(), name)){
						binder.setBinding(binding);
					}
				}

				if(binder.getBinding() != null){
					binder.setAdapterClient(builder.newInstance(name, instanceConfig));
					binder.setTimeout(instanceConfig.getTimeout().getSeconds());
					binders.add(binder);
				}
			});

			return binders;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.KafkaCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(ICanalAdapterBinderConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".kafka")
	static class KafkaCanalAdapterBinderConfiguration extends AbstractCanalAdapterBinderConfiguration {

		public KafkaCanalAdapterBinderConfiguration(final CanalProperties canalProperties,
													final ObjectProvider<List<CanalBinding<?>>> canalBindings) {
			super(canalProperties, canalBindings);
		}

		@Bean
		@Override
		public List<Binder> createBinders() {
			final KafkaProperties kafka = canalProperties.getKafka();
			return createBinders(kafka, (topic, instance)->{
				final KafkaCanalAdapterClient canalAdapterClient = new KafkaCanalAdapterClient(kafka.getServers(),
						topic, instance.getGroupId(), instance.getPartition(), instance.getBatchSize(),
						instance.isFlatMessage());
				canalAdapterClient.setFilter(instance.getFilter());
				return canalAdapterClient;
			});
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.PulsarCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(ICanalAdapterBinderConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".pulsar")
	static class PulsarCanalAdapterBinderConfiguration extends AbstractCanalAdapterBinderConfiguration {

		public PulsarCanalAdapterBinderConfiguration(final CanalProperties canalProperties,
													 final ObjectProvider<List<CanalBinding<?>>> canalBindings) {
			super(canalProperties, canalBindings);
		}

		@Bean
		@Override
		public List<Binder> createBinders() {
			final PulsarProperties pulsar = canalProperties.getPulsar();
			return createBinders(pulsar, (topic, instance)->{
				final PulsarMQCanalAdapterClient canalAdapterClient = new PulsarMQCanalAdapterClient(
						pulsar.getServiceUrl(), pulsar.getRoleToken(), topic, instance.getSubscriptName(),
						pulsar.getGetBatchTimeout(), pulsar.getBatchProcessTimeout(), pulsar.getRedeliveryDelay(),
						pulsar.getAckTimeout(), pulsar.isRetry(), pulsar.isRetryDLQUpperCase(),
						pulsar.getMaxRedeliveryCount(), instance.getBatchSize(), instance.isFlatMessage());
				canalAdapterClient.setFilter(instance.getFilter());
				return canalAdapterClient;
			});
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.RabbitCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(ICanalAdapterBinderConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".rabbit")
	static class RabbitCanalAdapterBinderConfiguration extends AbstractCanalAdapterBinderConfiguration {

		public RabbitCanalAdapterBinderConfiguration(final CanalProperties canalProperties,
													 final ObjectProvider<List<CanalBinding<?>>> canalBindings) {
			super(canalProperties, canalBindings);
		}

		@Bean
		@Override
		public List<Binder> createBinders() {
			final RabbitProperties rabbit = canalProperties.getRabbit();
			return createBinders(rabbit, (queueName, instance)->{
				final RabbitMQCanalAdapterClient canalAdapterClient = new RabbitMQCanalAdapterClient(rabbit.getServer(),
						rabbit.getVirtualHost(), rabbit.getUsername(), rabbit.getPassword(), queueName,
						instance.getBatchSize(), instance.isFlatMessage());
				canalAdapterClient.setFilter(instance.getFilter());
				return canalAdapterClient;
			});
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.RocketCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(ICanalAdapterBinderConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".rocket")
	static class RocketCanalAdapterBinderConfiguration extends AbstractCanalAdapterBinderConfiguration {

		public RocketCanalAdapterBinderConfiguration(final CanalProperties canalProperties,
													 final ObjectProvider<List<CanalBinding<?>>> canalBindings) {
			super(canalProperties, canalBindings);
		}

		@Bean
		@Override
		public List<Binder> createBinders() {
			final RocketProperties rocket = canalProperties.getRocket();
			return createBinders(rocket, (topic, instance)->{
				final RocketMQCanalAdapterClient canalAdapterClient = new RocketMQCanalAdapterClient(
						rocket.getNameServer(), topic, instance.getGroupId(), rocket.getEnableMessageTrace(),
						rocket.getCustomizedTraceTopic(), rocket.getAccessChannel(), instance.getBatchSize(),
						instance.isFlatMessage());
				canalAdapterClient.setFilter(instance.getFilter());
				return canalAdapterClient;
			});
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(AdapterClientConfiguredCondition.TcpCanalAdapterClientConfiguredCondition.class)
	@ConditionalOnMissingBean(ICanalAdapterBinderConfiguration.class)
	@ConfigurationProperties(prefix = CanalProperties.PREFIX + ".tcp")
	static class TcpCanalAdapterBinderConfiguration extends AbstractCanalAdapterBinderConfiguration {

		public TcpCanalAdapterBinderConfiguration(final CanalProperties canalProperties,
												  final ObjectProvider<List<CanalBinding<?>>> canalBindings) {
			super(canalProperties, canalBindings);
		}

		@Bean
		@Override
		public List<Binder> createBinders() {
			final TcpProperties tcp = canalProperties.getTcp();
			return createBinders(tcp, (destination, instance)->{
				final TcpCanalAdapterClient canalAdapterClient = new TcpCanalAdapterClient(tcp.getServer(),
						tcp.getZkServers(), destination, tcp.getUsername(), tcp.getPassword(), instance.getBatchSize());
				canalAdapterClient.setFilter(instance.getFilter());
				return canalAdapterClient;
			});
		}

	}

}
