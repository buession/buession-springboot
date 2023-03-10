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
package com.buession.springboot.captcha.autoconfigure;

import com.buession.httpclient.HttpClient;
import com.buession.security.captcha.CaptchaClient;
import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.geetest.GeetestParameter;
import com.buession.security.captcha.tencent.TencentCaptchaClient;
import com.buession.security.captcha.validator.CaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletAliYunCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletGeetestCaptchaValidator;
import com.buession.security.captcha.validator.servlet.ServletTencentCaptchaValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 行为验证码客户端自动配置类
 *
 * @since 1.2.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfiguration {

	protected CaptchaProperties properties;

	public CaptchaConfiguration(CaptchaProperties properties){
		this.properties = properties;
	}

	protected void afterInitialized(final CaptchaClient captchaClient){
		captchaClient.setJavaScript(properties.getJavascript());
	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "aliyun.enabled", havingValue = "true")
	static class AliYunCaptchaConfiguration extends CaptchaConfiguration {

		public AliYunCaptchaConfiguration(CaptchaProperties properties){
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public AliYunCaptchaClient captchaClient(HttpClient httpClient){
			final CaptchaProperties.Aliyun config = properties.getAliyun();
			final AliYunCaptchaClient client = new AliYunCaptchaClient(config.getAccessKeyId(),
					config.getAccessKeySecret(), config.getAppKey(), config.getRegionId(), httpClient);

			afterInitialized(client);

			return client;
		}

		@Bean
		@ConditionalOnMissingBean
		public ServletAliYunCaptchaValidator captchaValidator(AliYunCaptchaClient aliYunCaptchaClient){
			return new ServletAliYunCaptchaValidator(aliYunCaptchaClient, properties.getAliyun().getParameter());
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "geetest.enabled", havingValue = "true")
	static class GeetestCaptchaConfiguration extends CaptchaConfiguration {

		public GeetestCaptchaConfiguration(CaptchaProperties properties){
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public GeetestCaptchaClient captchaClient(HttpClient httpClient){
			final CaptchaProperties.Geetest config = properties.getGeetest();
			final GeetestCaptchaClient client = new GeetestCaptchaClient(config.getAppId(), config.getSecretKey(),
					config.getVersion(), httpClient);

			afterInitialized(client);

			return client;
		}

		@Bean
		@ConditionalOnMissingBean
		public ServletGeetestCaptchaValidator captchaValidator(GeetestCaptchaClient geetestCaptchaClient){
			GeetestParameter parameter = "v3".equalsIgnoreCase(
					geetestCaptchaClient.getVersion()) ? properties.getGeetest().getV3()
					.getParameter() : properties.getGeetest().getV4().getParameter();
			return new ServletGeetestCaptchaValidator(geetestCaptchaClient, parameter);
		}

	}

	@Configuration(proxyBeanMethods = false)
	@EnableConfigurationProperties(CaptchaProperties.class)
	@ConditionalOnMissingBean({CaptchaValidator.class})
	@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "tencent.enabled", havingValue = "true")
	static class TencentCaptchaConfiguration extends CaptchaConfiguration {

		public TencentCaptchaConfiguration(CaptchaProperties properties){
			super(properties);
		}

		@Bean
		@ConditionalOnMissingBean
		public TencentCaptchaClient captchaClient(HttpClient httpClient){
			final CaptchaProperties.Tencent config = properties.getTencent();
			final TencentCaptchaClient client = new TencentCaptchaClient(config.getAppId(), config.getSecretKey(),
					httpClient);

			afterInitialized(client);

			return client;
		}

		@Bean
		@ConditionalOnMissingBean
		public ServletTencentCaptchaValidator captchaValidator(TencentCaptchaClient tencentCaptchaClient){
			return new ServletTencentCaptchaValidator(tencentCaptchaClient, properties.getTencent().getParameter());
		}

	}

}
