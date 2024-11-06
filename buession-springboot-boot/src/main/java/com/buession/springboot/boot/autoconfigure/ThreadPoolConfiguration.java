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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.autoconfigure;

import com.buession.core.concurrent.DefaultThreadFactory;
import com.buession.core.concurrent.DefaultThreadPoolExecutor;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.validator.Validate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池自动配置
 *
 * @author Yong.Teng
 * @since 2.3.3
 */
@AutoConfiguration
@EnableConfigurationProperties(ThreadPoolProperties.class)
@ConditionalOnProperty(prefix = ThreadPoolProperties.PREFIX, name = "enabled", havingValue = "true")
public class ThreadPoolConfiguration {

	private final ThreadPoolProperties threadPoolProperties;

	public ThreadPoolConfiguration(ThreadPoolProperties threadPoolProperties) {
		this.threadPoolProperties = threadPoolProperties;
	}

	@Bean(name = "threadPoolWorkQueue")
	@ConditionalOnMissingBean(name = "threadPoolWorkQueue")
	public BlockingQueue<Runnable> workQueue() {
		return new LinkedBlockingDeque<>();
	}

	@Bean(name = "threadPoolThreadFactory")
	@ConditionalOnMissingBean(name = "threadPoolThreadFactory")
	public ThreadFactory threadPoolFactory() {
		final DefaultThreadFactory threadFactory = Validate.hasText(
				threadPoolProperties.getNamePrefix()) ? new DefaultThreadFactory(
				threadPoolProperties.getNamePrefix()) : new DefaultThreadFactory();
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(threadPoolProperties.getPriority()).to(threadFactory::setPriority);
		propertyMapper.from(threadPoolProperties.getDaemon()).to(threadFactory::setDaemon);

		return threadFactory;
	}

	@Bean(name = "threadPoolRejectedHandler")
	@ConditionalOnMissingBean(name = "threadPoolRejectedHandler")
	public RejectedExecutionHandler rejectedHandler() {
		return new ThreadPoolExecutor.AbortPolicy();
	}

	@Bean(name = "threadPoolExecutor")
	@ConditionalOnMissingBean(name = "threadPoolExecutor")
	public ThreadPoolExecutor threadPoolExecutor(
			@Qualifier("threadPoolWorkQueue") ObjectProvider<BlockingQueue<Runnable>> workQueue,
			@Qualifier("threadPoolThreadFactory") ObjectProvider<ThreadFactory> threadFactory,
			@Qualifier("threadPoolRejectedHandler") ObjectProvider<RejectedExecutionHandler> rejectedHandler) {
		final com.buession.core.concurrent.ThreadPoolConfiguration threadPoolConfiguration =
				new com.buession.core.concurrent.ThreadPoolConfiguration();
		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(threadPoolProperties.getNamePrefix()).to(threadPoolConfiguration::setNamePrefix);
		propertyMapper.from(threadPoolProperties.getAllowCoreThreadTimeOut())
				.to(threadPoolConfiguration::setAllowCoreThreadTimeOut);
		propertyMapper.from(threadPoolProperties.getDaemon()).to(threadPoolConfiguration::setDaemon);
		propertyMapper.from(threadPoolProperties.getPriority()).to(threadPoolConfiguration::setPriority);

		threadPoolConfiguration.setCorePoolSize(Optional.ofNullable(threadPoolProperties.getCorePoolSize()).orElse(-1));
		threadPoolConfiguration.setMaximumPoolSize(
				Optional.ofNullable(threadPoolProperties.getMaximumPoolSize()).orElse(-1));

		if(threadPoolProperties.getKeepAliveTime() != null){
			threadPoolConfiguration.setKeepAliveTime(threadPoolProperties.getKeepAliveTime().toMillis());
			threadPoolConfiguration.setKeepAliveTimeTimeUnit(TimeUnit.MILLISECONDS);
		}

		workQueue.ifAvailable(threadPoolConfiguration::setWorkQueue);
		threadFactory.ifAvailable(threadPoolConfiguration::setThreadFactory);
		rejectedHandler.ifAvailable(threadPoolConfiguration::setRejectedHandler);

		return new DefaultThreadPoolExecutor(threadPoolConfiguration);
	}

}
