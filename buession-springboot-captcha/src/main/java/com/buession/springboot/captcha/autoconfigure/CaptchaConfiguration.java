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

import com.buession.core.validator.Validate;
import com.buession.httpclient.HttpClient;
import com.buession.security.captcha.CaptchaClient;
import com.buession.security.captcha.aliyun.AliYunCaptchaClient;
import com.buession.security.captcha.geetest.GeetestCaptchaClient;
import com.buession.security.captcha.netease.NetEaseCaptchaClient;
import com.buession.security.captcha.tencent.TencentCaptchaClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @since 1.2.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfiguration {

	private CaptchaProperties properties;

	private HttpClient httpClient;

	public CaptchaConfiguration(CaptchaProperties properties, ObjectProvider<HttpClient> httpClient){
		this.properties = properties;
		this.httpClient = httpClient.getIfAvailable();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha", name = "aliyun")
	public AliYunCaptchaClient aliYunCaptchaClient(){
		final CaptchaProperties.Aliyun config = properties.getAliyun();
		final AliYunCaptchaClient client = Validate.hasText(config.getRegionId()) ? new AliYunCaptchaClient(
				config.getAccessKeyId(), config.getAccessKeySecret(), config.getRegionId(),
				httpClient) : new AliYunCaptchaClient(config.getAccessKeyId(), config.getAccessKeySecret(), httpClient);

		afterInitialized(client);

		return client;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha", name = "geetest")
	public GeetestCaptchaClient geetestCaptchaClient(){
		final CaptchaProperties.Geetest config = properties.getGeetest();
		final GeetestCaptchaClient client = new GeetestCaptchaClient(config.getAppId(), config.getSecretKey(),
				config.getVersion(), httpClient);

		afterInitialized(client);

		return client;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha", name = "netease")
	public NetEaseCaptchaClient netEaseCaptchaClient(){
		final CaptchaProperties.Netease config = properties.getNetease();
		final NetEaseCaptchaClient client = new NetEaseCaptchaClient(config.getAppId(), config.getSecretKey(),
				httpClient);

		afterInitialized(client);

		return client;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha", name = "tencent")
	public TencentCaptchaClient tencentCaptchaClient(){
		final CaptchaProperties.Tencent config = properties.getTencent();
		final TencentCaptchaClient client = new TencentCaptchaClient(config.getAppId(), config.getSecretKey(),
				httpClient);

		afterInitialized(client);

		return client;
	}

	private void afterInitialized(final CaptchaClient captchaClient){
		captchaClient.setJavaScript(properties.getJavascript());
	}

}
