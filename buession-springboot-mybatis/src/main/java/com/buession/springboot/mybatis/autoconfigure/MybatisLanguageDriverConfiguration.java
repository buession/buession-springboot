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
package com.buession.springboot.mybatis.autoconfigure;

import org.apache.ibatis.scripting.LanguageDriver;
import org.mybatis.scripting.freemarker.FreeMarkerLanguageDriver;
import org.mybatis.scripting.freemarker.FreeMarkerLanguageDriverConfig;
import org.mybatis.scripting.thymeleaf.ThymeleafLanguageDriver;
import org.mybatis.scripting.thymeleaf.ThymeleafLanguageDriverConfig;
import org.mybatis.scripting.velocity.VelocityLanguageDriver;
import org.mybatis.scripting.velocity.VelocityLanguageDriverConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(LanguageDriver.class)
public class MybatisLanguageDriverConfiguration {

	private static final String CONFIGURATION_PROPERTY_PREFIX = MybatisProperties.PREFIX +
			"scripting-language-driver";

	/**
	 * Configuration class for mybatis-freemarker 1.1.x or under.
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(FreeMarkerLanguageDriver.class)
	@ConditionalOnMissingClass("org.mybatis.scripting.freemarker.FreeMarkerLanguageDriverConfig")
	public static class LegacyFreeMarkerConfiguration {

		@Bean
		@ConditionalOnMissingBean
		FreeMarkerLanguageDriver freeMarkerLanguageDriver() {
			return new FreeMarkerLanguageDriver();
		}

	}

	/**
	 * Configuration class for mybatis-freemarker 1.2.x or above.
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({FreeMarkerLanguageDriver.class, FreeMarkerLanguageDriverConfig.class})
	static class FreeMarkerConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@ConfigurationProperties(CONFIGURATION_PROPERTY_PREFIX + ".freemarker")
		public FreeMarkerLanguageDriverConfig freeMarkerLanguageDriverConfig() {
			return FreeMarkerLanguageDriverConfig.newInstance();
		}

		@Bean
		@ConditionalOnMissingBean
		public FreeMarkerLanguageDriver freeMarkerLanguageDriver(FreeMarkerLanguageDriverConfig config) {
			return new FreeMarkerLanguageDriver(config);
		}

	}

	/**
	 * Configuration class for mybatis-velocity 2.0 or under.
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(org.mybatis.scripting.velocity.Driver.class)
	@ConditionalOnMissingClass("org.mybatis.scripting.velocity.VelocityLanguageDriverConfig")
	public static class LegacyVelocityConfiguration {

		@Bean
		@ConditionalOnMissingBean
		public org.mybatis.scripting.velocity.Driver velocityLanguageDriver() {
			return new org.mybatis.scripting.velocity.Driver();
		}

	}

	/**
	 * Configuration class for mybatis-velocity 2.1.x or above.
	 */
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass({VelocityLanguageDriver.class, VelocityLanguageDriverConfig.class})
	public static class VelocityConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@ConfigurationProperties(CONFIGURATION_PROPERTY_PREFIX + ".velocity")
		public VelocityLanguageDriverConfig velocityLanguageDriverConfig() {
			return VelocityLanguageDriverConfig.newInstance();
		}

		@Bean
		@ConditionalOnMissingBean
		public VelocityLanguageDriver velocityLanguageDriver(VelocityLanguageDriverConfig config) {
			return new VelocityLanguageDriver(config);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@ConditionalOnClass(ThymeleafLanguageDriver.class)
	public static class ThymeleafConfiguration {

		@Bean
		@ConditionalOnMissingBean
		@ConfigurationProperties(CONFIGURATION_PROPERTY_PREFIX + ".thymeleaf")
		public ThymeleafLanguageDriverConfig thymeleafLanguageDriverConfig() {
			return ThymeleafLanguageDriverConfig.newInstance();
		}

		@Bean
		@ConditionalOnMissingBean
		public ThymeleafLanguageDriver thymeleafLanguageDriver(ThymeleafLanguageDriverConfig config) {
			return new ThymeleafLanguageDriver(config);
		}

		// This class provides to avoid the https://github.com/spring-projects/spring-boot/issues/21626 as workaround.
		@SuppressWarnings("unused")
		private final static class MetadataThymeleafLanguageDriverConfig extends ThymeleafLanguageDriverConfig {

			@ConfigurationProperties(CONFIGURATION_PROPERTY_PREFIX + ".thymeleaf.dialect")
			@Override
			public DialectConfig getDialect() {
				return super.getDialect();
			}

			@ConfigurationProperties(CONFIGURATION_PROPERTY_PREFIX + ".thymeleaf.template-file")
			@Override
			public TemplateFileConfig getTemplateFile() {
				return super.getTemplateFile();
			}

		}

	}

}
