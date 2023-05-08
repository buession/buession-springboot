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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cli.application;

import com.buession.springboot.boot.application.AbstractApplication;
import com.buession.springboot.boot.application.Application;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 命令行应用抽象类
 *
 * @author Yong.Teng
 */
public abstract class AbstractCliApplication extends AbstractApplication implements CliApplication, CommandLineRunner {

	/**
	 * 是否添加 Command Line Properties
	 *
	 * @since 2.3.0
	 */
	private Boolean addCommandLineProperties;

	/**
	 * 构造函数
	 */
	protected AbstractCliApplication(){
		super();
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
	protected AbstractCliApplication(final Class<? extends Banner> banner) throws InstantiationException,
			IllegalAccessException{
		super(banner);
	}

	/**
	 * 构造函数
	 *
	 * @param banner
	 *        {@link Banner} 实例
	 *
	 * @since 1.3.1
	 */
	protected AbstractCliApplication(final Banner banner){
		super(banner);
	}

	/**
	 * 构造函数
	 *
	 * @param addCommandLineProperties
	 * 		是否添加 Command Line Properties
	 *
	 * @since 2.3.0
	 */
	protected AbstractCliApplication(final boolean addCommandLineProperties){
		super();
		this.addCommandLineProperties = addCommandLineProperties;
	}

	/**
	 * 构造函数
	 *
	 * @param banner
	 *        {@link Banner} 类
	 * @param addCommandLineProperties
	 * 		是否添加 Command Line Properties
	 *
	 * @throws InstantiationException
	 * 		反射异常
	 * @throws IllegalAccessException
	 * 		没有访问权限的异常
	 * @since 2.3.0
	 */
	protected AbstractCliApplication(final Class<? extends Banner> banner, final boolean addCommandLineProperties)
			throws InstantiationException, IllegalAccessException{
		super(banner);
		this.addCommandLineProperties = addCommandLineProperties;
	}

	/**
	 * 构造函数
	 *
	 * @param banner
	 *        {@link Banner} 实例
	 * @param addCommandLineProperties
	 * 		是否添加 Command Line Properties
	 *
	 * @since 2.3.0
	 */
	protected AbstractCliApplication(final Banner banner, final boolean addCommandLineProperties){
		super(banner);
		this.addCommandLineProperties = addCommandLineProperties;
	}

	@Override
	public Boolean getAddCommandLineProperties(){
		return addCommandLineProperties;
	}

	@Override
	public void setAddCommandLineProperties(Boolean addCommandLineProperties){
		this.addCommandLineProperties = addCommandLineProperties;
	}

	@Override
	public void startup(Class<? extends Application> clazz, String[] args){
		doStartup(clazz, WebApplicationType.NONE, args);
	}

	@Override
	public final void run(final String[] args){
	}

	@Override
	protected SpringApplicationBuilder springApplicationBuilder(final Class<? extends Application> clazz,
																final WebApplicationType webApplicationType){
		SpringApplicationBuilder springApplicationBuilder = super.springApplicationBuilder(clazz, webApplicationType);

		if(getAddCommandLineProperties() != null){
			springApplicationBuilder.addCommandLineProperties(getAddCommandLineProperties());
		}

		return springApplicationBuilder;
	}

}