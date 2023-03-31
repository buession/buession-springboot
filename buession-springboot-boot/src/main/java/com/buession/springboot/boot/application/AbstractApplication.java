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
 * | Copyright @ 2013-2023 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.application;

import com.buession.core.utils.Assert;
import com.buession.springboot.boot.config.RuntimeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Date;

/**
 * 应用抽象类
 *
 * @author Yong.Teng
 */
public abstract class AbstractApplication implements Application {

	/**
	 * 可配置的应用上下文类
	 */
	private Class<? extends ConfigurableApplicationContext> configurableApplicationContext;

	/**
	 * Banner
	 *
	 * @since 1.3.1
	 */
	private Banner banner;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 构造函数
	 */
	protected AbstractApplication(){
	}

	/**
	 * 构造函数
	 *
	 * @param banner
	 *        {@link Banner} 类
	 *
	 * @throws InstantiationException
	 * 		反射异常
	 * @throws IllegalAccessException
	 * 		没有访问权限的异常
	 * @since 1.3.1
	 */
	protected AbstractApplication(final Class<? extends Banner> banner) throws InstantiationException,
			IllegalAccessException{
		Assert.isNull(banner, "Banner class cloud not be null.");
		this.banner = banner.newInstance();
	}

	/**
	 * 构造函数
	 *
	 * @param banner
	 *        {@link Banner} 实例
	 *
	 * @since 1.3.1
	 */
	protected AbstractApplication(final Banner banner){
		Assert.isNull(banner, "Banner cloud not be null.");
		this.banner = banner;
	}

	@Override
	public Class<? extends ConfigurableApplicationContext> getConfigurableApplicationContext(){
		return configurableApplicationContext;
	}

	@Override
	public void setConfigurableApplicationContext(
			Class<? extends ConfigurableApplicationContext> configurableApplicationContext){
		this.configurableApplicationContext = configurableApplicationContext;
	}

	@Override
	public void startup(final String[] args){
		startup(getClass(), args);

		if(logger.isInfoEnabled()){
			logger.info("Startup {} with arguments: {} at {}", getClass().getSimpleName(), args, new Date());
		}
	}

	public void doStartup(final Class<? extends Application> clazz, final WebApplicationType webApplicationType,
						  final String[] args){
		final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(clazz);

		final PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(getBanner()).to(springApplicationBuilder::banner);
		propertyMapper.from(getConfigurableApplicationContext()).to(springApplicationBuilder::contextClass);

		springApplicationBuilder.web(webApplicationType).properties(createRuntimeProperties()).logStartupInfo(true)
				.run(args);
	}

	/**
	 * 获取 Banner
	 *
	 * @return Banner
	 */
	protected Banner getBanner(){
		return banner;
	}

	protected RuntimeProperties createRuntimeProperties(){
		return new RuntimeProperties();
	}

}
