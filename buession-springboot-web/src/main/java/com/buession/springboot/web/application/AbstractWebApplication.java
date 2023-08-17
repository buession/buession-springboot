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
package com.buession.springboot.web.application;

import com.buession.springboot.boot.application.AbstractApplication;
import com.buession.springboot.boot.application.Application;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Web 应用抽象类
 *
 * @author Yong.Teng
 */
public abstract class AbstractWebApplication extends AbstractApplication implements WebApplication {

	/**
	 * WEB 应用类型
	 */
	private WebApplicationType webApplicationType;

	/**
	 * 构造函数
	 */
	protected AbstractWebApplication(){
		super();
	}

	/**
	 * 构造函数
	 *
	 * @param webApplicationType
	 * 		Web 应用类型
	 *
	 * @see WebApplicationType
	 */
	protected AbstractWebApplication(final WebApplicationType webApplicationType){
		if(webApplicationType != null){
			this.webApplicationType = webApplicationType;
		}
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
	protected AbstractWebApplication(final Class<? extends Banner> banner) throws InstantiationException,
			IllegalAccessException{
		super(banner);
	}

	/**
	 * 构造函数
	 *
	 * @param webApplicationType
	 * 		Web 应用类型
	 * @param banner
	 *        {@link Banner} 类
	 *
	 * @throws InstantiationException
	 * 		反射异常
	 * @throws IllegalAccessException
	 * 		没有访问权限的异常
	 * @see WebApplicationType
	 * @since 1.3.1
	 */
	protected AbstractWebApplication(final WebApplicationType webApplicationType,
									 final Class<? extends Banner> banner) throws InstantiationException,
			IllegalAccessException{
		super(banner);
		if(webApplicationType != null){
			this.webApplicationType = webApplicationType;
		}
	}

	/**
	 * 构造函数
	 *
	 * @param banner
	 *        {@link Banner} 实例
	 *
	 * @since 1.3.1
	 */
	public AbstractWebApplication(final Banner banner){
		super(banner);
	}

	/**
	 * 构造函数
	 *
	 * @param webApplicationType
	 * 		Web 应用类型
	 * @param banner
	 *        {@link Banner} 实例
	 *
	 * @see WebApplicationType
	 * @since 1.3.1
	 */
	public AbstractWebApplication(final WebApplicationType webApplicationType, final Banner banner){
		super(banner);
		if(webApplicationType != null){
			this.webApplicationType = webApplicationType;
		}
	}

	@Override
	public WebApplicationType getWebApplicationType(){
		return webApplicationType;
	}

	@Override
	protected SpringApplicationBuilder springApplicationBuilder(final Class<? extends Application> clazz){
		final SpringApplicationBuilder springApplicationBuilder = super.springApplicationBuilder(clazz);

		if(getWebApplicationType() != null){
			springApplicationBuilder.web(getWebApplicationType());
		}

		return springApplicationBuilder;
	}

}
