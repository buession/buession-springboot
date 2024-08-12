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
 * | Copyright @ 2013-2024 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.application;

import org.springframework.boot.Banner;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.env.Environment;

/**
 * 应用接口
 *
 * @author Yong.Teng
 */
public interface Application extends Launcher, Daemon {

	/**
	 * 获取 {@link Banner}
	 *
	 * @return {@link Banner}
	 */
	Banner getBanner();

	/**
	 * 设置 {@link Banner}
	 *
	 * @param banner
	 *        {@link Banner}
	 *
	 * @since 2.3.0
	 */
	void setBanner(Banner banner);

	/**
	 * 获取 {@link Banner} 模式
	 *
	 * @return {@link Banner} 模式
	 *
	 * @since 2.3.0
	 */
	Banner.Mode getBannerMode();

	/**
	 * 设置 {@link Banner} 模式
	 *
	 * @param bannerMode
	 *        {@link Banner} 模式
	 *
	 * @since 2.3.0
	 */
	void setBannerMode(Banner.Mode bannerMode);

	/**
	 * Return if the application is headless and should not instantiate AWT.
	 *
	 * @return if the application is headless and should not instantiate AWT.
	 *
	 * @since 3.0.0
	 */
	Boolean getHeadless();

	/**
	 * Sets if the application is headless and should not instantiate AWT.
	 *
	 * @param headless
	 * 		if the application is headless and should not instantiate AWT.
	 *
	 * @since 3.0.0
	 */
	void setHeadless(Boolean headless);

	/**
	 * Return flag to indicate if the {@link ApplicationConversionService} should be added to the application context's
	 * {@link Environment}.
	 *
	 * @return Flag to indicate if the {@link ApplicationConversionService} should be added to the application context's
	 * {@link Environment}.
	 *
	 * @since 3.0.0
	 */
	Boolean getAddConversionService();

	/**
	 * Sets Flag to indicate if the {@link ApplicationConversionService} should be added to the application context's
	 * {@link Environment}.
	 *
	 * @param addConversionService
	 * 		Flag to indicate if the {@link ApplicationConversionService} should be added to the application context's
	 *        {@link Environment}.
	 *
	 * @since 3.0.0
	 */
	void setAddConversionService(Boolean addConversionService);

	/**
	 * 返回是否延迟初始化
	 *
	 * @return 是否延迟初始化
	 *
	 * @since 2.3.0
	 */
	Boolean getLazyInitialization();

	/**
	 * 设置是否延迟初始化
	 *
	 * @param lazyInitialization
	 * 		是否延迟初始化
	 *
	 * @since 2.3.0
	 */
	void setLazyInitialization(Boolean lazyInitialization);

	/**
	 * 启动应用程序
	 *
	 * @param args
	 * 		启动参数
	 */
	default void run(final String[] args) {
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
	default void run(final Class<? extends Application> clazz, final String[] args) {
		startup(clazz, args);
	}

}
