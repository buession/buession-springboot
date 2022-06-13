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
 * | Copyright @ 2013-2022 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.application;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * 应用接口
 *
 * @author Yong.Teng
 */
public interface Application {

	/**
	 * 返回可配置的应用上下文类
	 *
	 * @return 可配置的应用上下文类
	 *
	 * @see ConfigurableApplicationContext
	 */
	Class<? extends ConfigurableApplicationContext> getConfigurableApplicationContext();

	/**
	 * 设置可配置的应用上下文类
	 *
	 * @param configurableApplicationContext
	 * 		可配置的应用上下文类
	 *
	 * @see ConfigurableApplicationContext
	 */
	void setConfigurableApplicationContext(Class<? extends ConfigurableApplicationContext>
												   configurableApplicationContext);

	/**
	 * 启动应用程序
	 *
	 * @param args
	 * 		启动参数
	 */
	void startup(final String[] args);

	/**
	 * 启动应用程序
	 *
	 * @param clazz
	 * 		启动应用程序类
	 * @param args
	 * 		启动参数
	 */
	void startup(final Class<? extends Application> clazz, final String[] args);

	/**
	 * 启动应用程序
	 *
	 * @param args
	 * 		启动参数
	 */
	default void start(final String[] args){
		startup(args);
	}

	/**
	 * 启动应用程序
	 *
	 * @param clazz
	 * 		启动应用程序类
	 * @param args
	 * 		启动参数
	 */
	default void start(final Class<? extends Application> clazz, final String[] args){
		startup(clazz, args);
	}

	/**
	 * 启动应用程序
	 *
	 * @param args
	 * 		启动参数
	 */
	default void launch(final String[] args){
		startup(args);
	}

	/**
	 * 启动应用程序
	 *
	 * @param clazz
	 * 		启动应用程序类
	 * @param args
	 * 		启动参数
	 */
	default void launch(final Class<? extends Application> clazz, final String[] args){
		startup(clazz, args);
	}

	/**
	 * 启动应用程序
	 *
	 * @param args
	 * 		启动参数
	 */
	default void run(final String[] args){
		startup(args);
	}

	/**
	 * 启动应用程序
	 *
	 * @param clazz
	 * 		启动应用程序类
	 * @param args
	 * 		启动参数
	 */
	default void run(final Class<? extends Application> clazz, final String[] args){
		startup(clazz, args);
	}

}
