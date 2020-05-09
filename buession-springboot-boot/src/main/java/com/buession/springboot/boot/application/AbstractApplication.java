/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.application;

import com.buession.springboot.boot.config.RuntimeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Yong.Teng
 */
public abstract class AbstractApplication implements Application {

	private Class<? extends ConfigurableApplicationContext> configurableApplicationContext;

	private final static Logger logger = LoggerFactory.getLogger(AbstractApplication.class);

	@Override
	public Class<? extends ConfigurableApplicationContext> getConfigurableApplicationContext(){
		return configurableApplicationContext;
	}

	@Override
	public void setConfigurableApplicationContext(Class<? extends ConfigurableApplicationContext>
															  configurableApplicationContext){
		this.configurableApplicationContext = configurableApplicationContext;
	}

	@Override
	public void startup(final String[] args){
		startup(getClass(), args);

		if(logger.isInfoEnabled()){
			logger.info("Startup {} width arguments: {}", getClass().getSimpleName(), args);
		}
	}

	/**
	 * 获取 Banner
	 *
	 * @return Banner
	 */
	protected abstract Banner getBanner();

	protected RuntimeProperties createRuntimeProperties(){
		return new RuntimeProperties();
	}

}
