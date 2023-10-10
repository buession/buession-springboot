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

import com.alibaba.otter.canal.protocol.FlatMessage;
import com.buession.canal.client.CanalClient;
import com.buession.canal.client.DefaultCanalClient;
import com.buession.canal.client.Instance;
import com.buession.canal.client.adapter.CanalAdapterClient;
import com.buession.canal.client.adapter.KafkaCanalAdapterClient;
import com.buession.canal.client.adapter.RabbitMqCanalAdapterClient;
import com.buession.canal.client.adapter.TcpCanalAdapterClient;
import com.buession.canal.client.handler.AsyncFlatMessageHandler;
import com.buession.canal.client.handler.MessageHandler;
import com.buession.canal.client.handler.SyncFlatMessageHandler;
import com.buession.canal.core.CanalMode;
import com.buession.springboot.boot.autoconfigure.condition.ConditionalOnExistProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CanalProperties.class)
public class CanalClientConfiguration {

	@ConditionalOnProperty(prefix = CanalProperties.PREFIX, name = "async.enable", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnMissingBean({MessageHandler.class})
	@Bean
	public MessageHandler<FlatMessage> asyncMessageHandler() {
		return new AsyncFlatMessageHandler();
	}

	@ConditionalOnMissingBean({MessageHandler.class})
	@Bean
	public MessageHandler<FlatMessage> syncMessageHandler() {
		return new SyncFlatMessageHandler();
	}

	static abstract class AbstractCanalAdapterClientConfiguration {

		protected CanalProperties canalProperties;

		protected MessageHandler<FlatMessage> messageHandler;

		protected final Logger logger = LoggerFactory.getLogger(getClass());

		public AbstractCanalAdapterClientConfiguration(CanalProperties canalProperties,
													   ObjectProvider<MessageHandler<FlatMessage>> messageHandler) {
			this.canalProperties = canalProperties;
			this.messageHandler = messageHandler.getIfAvailable();
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@ConditionalOnExistProperty(prefix = CanalProperties.PREFIX, name = "kafka")
	@Import(ThreadPoolConfiguration.class)
	static class Kafka extends AbstractCanalAdapterClientConfiguration {

		public Kafka(CanalProperties canalProperties, ObjectProvider<MessageHandler<FlatMessage>> messageHandler) {
			super(canalProperties, messageHandler);
		}

		@ConditionalOnProperty(prefix = CanalProperties.PREFIX, name = "async.enable", havingValue = "true", matchIfMissing = true)
		@Bean(name = "canalClient")
		public CanalClient canalClient(
				@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
			logger.info("Initialize CanalClient with async.");
			return new DefaultCanalClient(createInstances(messageHandler), executorService.getIfAvailable());
		}

		@ConditionalOnMissingBean(name = "canalClient", value = CanalClient.class)
		@Bean(name = "canalClient")
		public CanalClient canalClient() {
			logger.info("Initialize CanalClient not with async.");
			return new DefaultCanalClient(createInstances(messageHandler));
		}

		private List<Instance> createInstances(final MessageHandler<FlatMessage> messageHandler) {
			final List<Instance> instances = new ArrayList<>();

			canalProperties.getKafka().forEach((name, config)->{
				final CanalAdapterClient adapterClient = new KafkaCanalAdapterClient(config.getServers(),
						config.getTopic(), config.getGroupId(), config.getPartition(), messageHandler,
						config.getTimeout().getSeconds(), config.getBatchSize());

				instances.add(new Instance(CanalMode.KAFKA, adapterClient));
			});

			return instances;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@ConditionalOnExistProperty(prefix = CanalProperties.PREFIX, name = "rabbit")
	@Import(ThreadPoolConfiguration.class)
	static class Rabbit extends AbstractCanalAdapterClientConfiguration {

		public Rabbit(CanalProperties canalProperties, ObjectProvider<MessageHandler<FlatMessage>> messageHandler) {
			super(canalProperties, messageHandler);
		}

		@ConditionalOnProperty(prefix = CanalProperties.PREFIX, name = "async.enable", havingValue = "true", matchIfMissing = true)
		@Bean(name = "canalClient")
		public CanalClient canalClient(
				@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
			logger.info("Initialize CanalClient with async.");
			return new DefaultCanalClient(createInstances(messageHandler), executorService.getIfAvailable());
		}

		@ConditionalOnMissingBean(name = "canalClient", value = CanalClient.class)
		@Bean(name = "canalClient")
		public CanalClient canalClient() {
			logger.info("Initialize CanalClient not with async.");
			return new DefaultCanalClient(createInstances(messageHandler));
		}

		private List<Instance> createInstances(final MessageHandler<FlatMessage> messageHandler) {
			final List<Instance> instances = new ArrayList<>();

			canalProperties.getRabbit().forEach((name, config)->{
				final CanalAdapterClient adapterClient = new RabbitMqCanalAdapterClient(config.getServer(),
						config.getVirtualHost(), config.getUsername(), config.getPassword(), config.getQueueName(),
						messageHandler, config.getTimeout().getSeconds(), config.getBatchSize());

				instances.add(new Instance(CanalMode.RABBIT_MQ, adapterClient));
			});

			return instances;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CanalProperties.class)
	@ConditionalOnExistProperty(prefix = CanalProperties.PREFIX, name = "tcp")
	@Import(ThreadPoolConfiguration.class)
	static class Tcp extends AbstractCanalAdapterClientConfiguration {

		public Tcp(CanalProperties canalProperties, ObjectProvider<MessageHandler<FlatMessage>> messageHandler) {
			super(canalProperties, messageHandler);
		}

		@ConditionalOnProperty(prefix = CanalProperties.PREFIX, name = "async.enable", havingValue = "true", matchIfMissing = true)
		@Bean(name = "canalClient")
		public CanalClient canalClient(
				@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
			logger.info("Initialize CanalClient with async.");
			return new DefaultCanalClient(createInstances(messageHandler), executorService.getIfAvailable());
		}

		@ConditionalOnMissingBean(name = "canalClient", value = CanalClient.class)
		@Bean(name = "canalClient")
		public CanalClient canalClient() {
			logger.info("Initialize CanalClient not with async.");
			return new DefaultCanalClient(createInstances(messageHandler));
		}

		private List<Instance> createInstances(final MessageHandler<FlatMessage> messageHandler) {
			final List<Instance> instances = new ArrayList<>();

			canalProperties.getTcp().forEach((name, config)->{
				final CanalAdapterClient adapterClient = new TcpCanalAdapterClient(config.getServer(),
						config.getZkServers(), config.getDestination(), config.getUsername(), config.getPassword(),
						messageHandler, config.getTimeout().getSeconds(), config.getBatchSize());

				instances.add(new Instance(CanalMode.TCP, adapterClient));
			});

			return instances;
		}

	}

}
