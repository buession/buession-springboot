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
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cli.application;

import com.buession.springboot.boot.application.AbstractApplication;
import com.buession.springboot.boot.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Date;

/**
 * @author Yong.Teng
 */
public abstract class AbstractCliApplication extends AbstractApplication implements CliApplication, CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected AbstractCliApplication(){

	}

	@Override
	public void run(final String[] args){
		run(getClass(), args);
	}

	@Override
	public void run(final Class<? extends Application> clazz, final String[] args){
		final Banner banner = getBanner();
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(clazz);

		if(banner != null){
			springApplicationBuilder.banner(banner);
		}

		if(getConfigurableApplicationContext() != null){
			springApplicationBuilder.contextClass(getConfigurableApplicationContext());
		}

		springApplicationBuilder.web(WebApplicationType.NONE).properties(createRuntimeProperties()).logStartupInfo
				(true).run(args);
		logger.info("Application startup at {}", new Date());
	}

}