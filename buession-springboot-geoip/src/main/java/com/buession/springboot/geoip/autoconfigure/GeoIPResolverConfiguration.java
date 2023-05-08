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
package com.buession.springboot.geoip.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.geoip.Resolver;
import com.buession.geoip.spring.GeoIPResolverFactoryBean;
import com.maxmind.geoip2.DatabaseReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({DatabaseReader.class})
@EnableConfigurationProperties(GeoIPProperties.class)
public class GeoIPResolverConfiguration {

	private final GeoIPProperties properties;

	public GeoIPResolverConfiguration(GeoIPProperties properties){
		this.properties = properties;
	}

	@Bean
	@ConditionalOnMissingBean
	public Resolver geoIPResolver() throws Exception{
		GeoIPResolverFactoryBean factory = new GeoIPResolverFactoryBean();

		if(Validate.hasText(properties.getDbPath())){
			factory.setDbPath(new File(properties.getDbPath()));
		}

		factory.setEnableCache(properties.isEnableCache());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

}
