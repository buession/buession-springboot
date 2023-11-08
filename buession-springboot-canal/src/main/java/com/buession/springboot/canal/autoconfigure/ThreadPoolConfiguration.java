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

import com.buession.canal.core.concurrent.DefaultCanalThreadPoolExecutor;
import com.buession.springboot.canal.ThreadConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({CanalProperties.class})
public class ThreadPoolConfiguration {

	private final ThreadConfig thread;

	public ThreadPoolConfiguration(CanalProperties canalProperties) {
		this.thread = canalProperties.getThread();
	}

	@Bean(name = "canalExecutorService", destroyMethod = "shutdown")
	public ExecutorService executorService() {
		RejectedExecutionHandler rejectedExecutionHandler;

		switch(thread.getPolicy()){
			case ABORT:
				rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
				break;
			case CALLER_RUNS:
				rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
				break;
			case DISCARD:
				rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
				break;
			case DISCARD_OLDEST:
				rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
				break;
			default:
				rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
				break;
		}

		Integer coreSize = null;
		Integer corePoolSize = thread.getCorePoolSize();
		Integer maximumPoolSize = thread.getMaximumPoolSize();

		if(corePoolSize == null || maximumPoolSize == null){
			coreSize = Runtime.getRuntime().availableProcessors();
			if(corePoolSize == null){
				corePoolSize = coreSize * 2;
			}
			if(maximumPoolSize == null){
				maximumPoolSize = coreSize * 2;
			}
		}

		return new DefaultCanalThreadPoolExecutor(thread.getNamePrefix(), corePoolSize, maximumPoolSize,
				thread.getKeepAliveTime().toMillis(), rejectedExecutionHandler);
	}

}
