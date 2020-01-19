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
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Yong.Teng
 */
public abstract class AbstractCliApplication extends AbstractApplication implements CliApplication, CommandLineRunner {

	protected AbstractCliApplication(){

	}

	public static void run(final AbstractCliApplication instance, final String[] args){
		final Banner banner = instance.getBanner();
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(instance.getClass());

		if(banner != null){
			springApplicationBuilder.banner(banner);
		}

		springApplicationBuilder.web(WebApplicationType.NONE).properties(instance.createRuntimeProperties())
				.logStartupInfo(true).run(args);
	}

	@Override
	public void run(final String[] args){
		run(false, args);
	}

	protected abstract void run(final boolean logStartupInfo, final String[] args);

}