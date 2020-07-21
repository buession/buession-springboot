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
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.oss.autoconfigure;

import com.buession.oss.AbstractOSSClient;
import com.buession.oss.AliCloudOSSClient;
import com.buession.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfiguration {

	@Autowired
	private OssProperties ossProperties;

	@Bean
	@ConditionalOnClass(name = {"com.aliyun.oss.OSS"})
	@ConditionalOnMissingBean
	public OSSClient aliCloudOSSClient(){
		AbstractOSSClient ossClient = new AliCloudOSSClient(ossProperties.getEndpoint(),
				ossProperties.getAccessKeyId(), ossProperties.getSecretAccessKey());

		if(ossProperties.getBaseUrl() != null){
			ossClient.setBaseUrl(ossProperties.getBaseUrl());
		}

		return ossClient;
	}

}
