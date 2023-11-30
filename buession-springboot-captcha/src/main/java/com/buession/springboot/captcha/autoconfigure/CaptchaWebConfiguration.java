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
package com.buession.springboot.captcha.autoconfigure;

import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.geetest.GeetestParameter;
import com.buession.security.captcha.tencent.TencentCaptchaClient;
import com.buession.security.captcha.validator.CaptchaValidator;
import com.buession.security.captcha.validator.reactive.ReactiveAliYunCaptchaValidator;
import com.buession.security.captcha.validator.reactive.ReactiveGeetestCaptchaValidator;
import com.buession.security.captcha.validator.reactive.ReactiveTencentCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletAliYunCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletGeetestCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletTencentCaptchaValidator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 行为验证码客户端 Web 自动配置类
 *
 * @author Yong.Teng
 * @since 2.3.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
@AutoConfigureAfter({CaptchaConfiguration.class})
public class CaptchaWebConfiguration {

	static abstract class AbstractCaptchaWebConfiguration {

		protected final CaptchaProperties properties;

		public AbstractCaptchaWebConfiguration(CaptchaProperties properties) {
			this.properties = properties;
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	@AutoConfigureAfter(WebMvcAutoConfiguration.class)
	static class CaptchaServletWebConfiguration extends AbstractCaptchaWebConfiguration {

		public CaptchaServletWebConfiguration(CaptchaProperties properties) {
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean({CaptchaValidator.class})
		@ConditionalOnBean({AliYunCaptchaClient.class})
		public ServletAliYunCaptchaValidator aliYunCaptchaValidator(
				ObjectProvider<AliYunCaptchaClient> aliYunCaptchaClient) {
			return new ServletAliYunCaptchaValidator(aliYunCaptchaClient.getIfAvailable(),
					properties.getAliyun().getParameter());
		}

		@Bean
		@ConditionalOnMissingBean({CaptchaValidator.class})
		@ConditionalOnBean({GeetestCaptchaClient.class})
		public ServletGeetestCaptchaValidator geetestCaptchaValidator(
				ObjectProvider<GeetestCaptchaClient> geetestCaptchaClient) {
			GeetestParameter parameter = geetestCaptchaClient.getIfAvailable().isV3() ? properties.getGeetest().getV3()
					.getParameter() : properties.getGeetest().getV4().getParameter();
			return new ServletGeetestCaptchaValidator(geetestCaptchaClient.getIfAvailable(), parameter);
		}

		@Bean
		@ConditionalOnMissingBean({CaptchaValidator.class})
		@ConditionalOnBean({TencentCaptchaClient.class})
		public ServletTencentCaptchaValidator tencentCaptchaValidator(
				ObjectProvider<TencentCaptchaClient> tencentCaptchaClient) {
			return new ServletTencentCaptchaValidator(tencentCaptchaClient.getIfAvailable(),
					properties.getTencent().getParameter());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	@AutoConfigureAfter(WebFluxAutoConfiguration.class)
	static class CaptchaReactiveWebConfiguration extends AbstractCaptchaWebConfiguration {

		public CaptchaReactiveWebConfiguration(CaptchaProperties properties) {
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean({CaptchaValidator.class})
		@ConditionalOnBean({AliYunCaptchaClient.class})
		public ReactiveAliYunCaptchaValidator aliYunCaptchaValidator(
				ObjectProvider<AliYunCaptchaClient> aliYunCaptchaClient) {
			return new ReactiveAliYunCaptchaValidator(aliYunCaptchaClient.getIfAvailable(),
					properties.getAliyun().getParameter());
		}

		@Bean
		@ConditionalOnMissingBean({CaptchaValidator.class})
		@ConditionalOnBean({GeetestCaptchaClient.class})
		public ReactiveGeetestCaptchaValidator geetestCaptchaValidator(
				ObjectProvider<GeetestCaptchaClient> geetestCaptchaClient) {
			GeetestParameter parameter = geetestCaptchaClient.getIfAvailable().isV3() ? properties.getGeetest().getV3()
					.getParameter() : properties.getGeetest().getV4().getParameter();
			return new ReactiveGeetestCaptchaValidator(geetestCaptchaClient.getIfAvailable(), parameter);
		}

		@Bean
		@ConditionalOnMissingBean({CaptchaValidator.class})
		@ConditionalOnBean({TencentCaptchaClient.class})
		public ReactiveTencentCaptchaValidator tencentCaptchaValidator(
				ObjectProvider<TencentCaptchaClient> tencentCaptchaClient) {
			return new ReactiveTencentCaptchaValidator(tencentCaptchaClient.getIfAvailable(),
					properties.getTencent().getParameter());
		}

	}

}
