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

import com.buession.canal.client.CanalContext;
import com.buession.canal.client.DefaultCanalContext;
import com.buession.canal.client.adapter.AdapterClient;
import com.buession.canal.client.dispatcher.Dispatcher;
import com.buession.canal.spring.client.factory.CanalClientFactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CanalProperties.class)
@Import({ThreadPoolConfiguration.class, AdapterClientConfiguration.class})
public class CanalConfiguration {

	@Bean(destroyMethod = "destroy")
	public CanalClientFactoryBean createCanalClientFactoryBean(
			ObjectProvider<Set<AdapterClient>> canalAdapterClients, ObjectProvider<Dispatcher> dispatcher,
			@Qualifier("canalExecutorService") ObjectProvider<ExecutorService> executorService) {
		final CanalClientFactoryBean canalClientFactoryBean = new CanalClientFactoryBean();

		CanalContext context = new DefaultCanalContext(canalAdapterClients.getIfAvailable());
		canalClientFactoryBean.setContext(context);
		canalClientFactoryBean.setDispatcher(dispatcher.getIfAvailable());
		canalClientFactoryBean.setExecutor(executorService.getIfAvailable());

		return canalClientFactoryBean;
	}

}
