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

import com.buession.canal.client.CanalClient;
import com.buession.canal.client.DefaultCanalClient;
import com.buession.canal.client.adapter.CanalAdapterClient;
import com.buession.canal.client.adapter.KafkaCanalAdapterClient;
import com.buession.canal.client.adapter.RabbitMqCanalAdapterClient;
import com.buession.canal.client.adapter.TcpCanalAdapterClient;
import com.buession.canal.client.consumer.CanalConsumer;
import com.buession.canal.client.consumer.DefaultCanalConsumer;
import com.buession.canal.core.convert.DefaultMessageTransponder;
import com.buession.canal.core.convert.MessageTransponder;
import com.buession.canal.spring.beans.factory.CanalEventListenerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CanalProperties.class)
@ConditionalOnBean(CanalEventListenerFactory.class)
@Import(ThreadPoolConfiguration.class)
public class CanalClientConfiguration {

	@ConditionalOnMissingBean({MessageTransponder.class})
	@Bean
	public MessageTransponder messageTransponder() {
		return new DefaultMessageTransponder();
	}

	@Bean(name = "canalClient", initMethod = "start", destroyMethod = "stop")
	public CanalClient canalClient(
			@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
		return new DefaultCanalClient(createConsumers(), executorService.getIfAvailable());
	}

	interface AdapterClientConfiguration {

		CanalClient canalClient(ObjectProvider<ExecutorService> executorService);

	}

	static abstract class AbstractCanalAdapterClientConfiguration implements AdapterClientConfiguration {

		protected CanalProperties canalProperties;

		protected MessageTransponder messageTransponder;

		protected CanalEventListenerFactory canalEventListenerFactory;

		public AbstractCanalAdapterClientConfiguration(CanalProperties canalProperties,
													   ObjectProvider<MessageTransponder> messageTransponder,
													   ObjectProvider<CanalEventListenerFactory> canalEventListenerFactory) {
			this.canalProperties = canalProperties;
			this.messageTransponder = messageTransponder.getIfAvailable();
			this.canalEventListenerFactory = canalEventListenerFactory.getIfAvailable();
		}

		protected List<CanalConsumer> createConsumers(final Consumer<List<CanalConsumer>> consumer) {
			final List<CanalConsumer> consumers = new ArrayList<>();
			consumer.accept(consumers);
			return consumers;
		}

		protected void createConsumers(final String destination, final List<CanalConsumer> consumers,
									   final Supplier<CanalAdapterClient> adapterClient) {
			canalEventListenerFactory.getListeners().forEach((listener)->{
				if(Objects.equals(destination, listener.getDestination())){
					listener.getInvokes().forEach((invoke->{
						consumers.add(new DefaultCanalConsumer(adapterClient.get(), messageTransponder,
								invoke.getCallable()));
					}));
				}
			});
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(ClientConfiguredCondition.KafkaCanalAdapterClientConfiguredCondition.class)
	@Import(ThreadPoolConfiguration.class)
	static class Kafka extends AbstractCanalAdapterClientConfiguration {

		public Kafka(CanalProperties canalProperties, ObjectProvider<MessageTransponder> messageTransponder,
					 ObjectProvider<CanalEventListenerFactory> canalEventListenerFactory) {
			super(canalProperties, messageTransponder, canalEventListenerFactory);
		}

		@Bean(name = "canalClient", initMethod = "start", destroyMethod = "stop")
		@Override
		public CanalClient canalClient(
				@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
			return new DefaultCanalClient(createConsumers(), executorService.getIfAvailable());
		}

		private List<CanalConsumer> createConsumers() {
			return createConsumers((consumers)->canalProperties.getKafka().forEach((name, config)->{
				createConsumers(name, consumers, ()->new KafkaCanalAdapterClient(config.getServers(),
						name, config.getGroupId(), config.getPartition(), config.getTimeout().getSeconds(),
						config.getBatchSize()));
			}));
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(ClientConfiguredCondition.RabbitCanalAdapterClientConfiguredCondition.class)
	@Import(ThreadPoolConfiguration.class)
	static class Rabbit extends AbstractCanalAdapterClientConfiguration {

		public Rabbit(CanalProperties canalProperties, ObjectProvider<MessageTransponder> messageTransponder,
					  ObjectProvider<CanalEventListenerFactory> canalEventListenerFactory) {
			super(canalProperties, messageTransponder, canalEventListenerFactory);
		}

		@Bean(name = "canalClient", initMethod = "start", destroyMethod = "stop")
		@Override
		public CanalClient canalClient(
				@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
			return new DefaultCanalClient(createConsumers(), executorService.getIfAvailable());
		}

		private List<CanalConsumer> createConsumers() {
			return createConsumers((consumers)->canalProperties.getRabbit().forEach((name, config)->{
				createConsumers(name, consumers, ()->new RabbitMqCanalAdapterClient(config.getServer(),
						config.getVirtualHost(), config.getUsername(), config.getPassword(), name,
						config.getTimeout().getSeconds(), config.getBatchSize()));
			}));
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@Conditional(ClientConfiguredCondition.TcpCanalAdapterClientConfiguredCondition.class)
	@Import(ThreadPoolConfiguration.class)
	static class Tcp extends AbstractCanalAdapterClientConfiguration {

		public Tcp(CanalProperties canalProperties, ObjectProvider<MessageTransponder> messageTransponder,
				   ObjectProvider<CanalEventListenerFactory> canalEventListenerFactory) {
			super(canalProperties, messageTransponder, canalEventListenerFactory);
		}

		@Bean(name = "canalClient", initMethod = "start", destroyMethod = "stop")
		@Override
		public CanalClient canalClient(
				@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
			return new DefaultCanalClient(createConsumers(), executorService.getIfAvailable());
		}

		private List<CanalConsumer> createConsumers() {
			return createConsumers((consumers)->canalProperties.getTcp().forEach((name, config)->{
				createConsumers(name, consumers, ()->new TcpCanalAdapterClient(config.getServer(),
						config.getZkServers(), name, config.getUsername(), config.getPassword(),
						config.getTimeout().getSeconds(), config.getBatchSize()));
			}));
		}

	}


}
