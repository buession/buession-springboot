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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mongodb.autoconfigure;

import com.buession.core.validator.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author Yong.Teng
 */
@AutoConfiguration
@EnableConfigurationProperties(MongoDBProperties.class)
@AutoConfigureAfter(MongoDataAutoConfiguration.class)
@Import({MongoDataAutoConfiguration.class})
public class MongoDBConfiguration {

	public MongoDBConfiguration(MongoMappingContext mongoMappingContext, MongoDBProperties properties,
								ObjectProvider<MappingMongoConverter> mappingMongoConverter) {
		MongoTypeMapper mongoTypeMapper;

		if(properties.getTypeMapper() != null){
			mongoTypeMapper = BeanUtils.instantiateClass(properties.getTypeMapper());
		}else{
			mongoTypeMapper = new DefaultMongoTypeMapper(
					Validate.hasText(properties.getTypeKey()) ? properties.getTypeKey() : null, mongoMappingContext);
		}

		mappingMongoConverter.ifAvailable((mongoConverter)->mongoConverter.setTypeMapper(mongoTypeMapper));
	}

}
