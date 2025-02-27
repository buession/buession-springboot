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
 * | Copyright @ 2013-2025 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.id;

import com.buession.core.id.*;
import com.buession.core.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * ID 生成器 {@link IdGenerator} 自动配置类
 *
 * @author Yong.Teng
 * @since 3.0.1
 */
@AutoConfiguration
@EnableConfigurationProperties(IdProperties.class)
@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "enabled", havingValue = "true")
public class IdGeneratorConfiguration {

	private final IdProperties idProperties;

	private final static Logger logger = LoggerFactory.getLogger(IdGeneratorConfiguration.class);

	public IdGeneratorConfiguration(IdProperties idProperties) {
		this.idProperties = idProperties;
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "atomic-simple.enabled", havingValue = "true")
	@ConditionalOnMissingBean({IdGenerator.class})
	public AtomicSimpleIdGenerator atomicSimpleIdGenerator() {
		logger.debug("IdGenerator using AtomicSimpleIdGenerator.");
		return new AtomicSimpleIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "atomic-uuid.enabled", havingValue = "true")
	@ConditionalOnMissingBean({IdGenerator.class})
	public AtomicUUIDIdGenerator atomicUUIDIdGenerator() {
		logger.debug("IdGenerator using AtomicUUIDIdGenerator.");
		return new AtomicUUIDIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "nano.enabled", havingValue = "true")
	@ConditionalOnMissingBean({IdGenerator.class})
	public NanoIDIdGenerator nanoIDIdGenerator() {
		if(idProperties.getNano() != null){
			IdProperties.Nano nano = idProperties.getNano();

			if(Validate.isNotEmpty(nano.getAlphabet())){
				if(nano.getLength() != null){
					if(logger.isDebugEnabled()){
						logger.debug("IdGenerator using NanoIDIdGenerator with length: {}, alphabet: {}.",
								nano.getLength(), nano.getAlphabet());
					}

					return new NanoIDIdGenerator(nano.getAlphabet().toCharArray(), nano.getLength());
				}else{
					if(logger.isDebugEnabled()){
						logger.debug("IdGenerator using NanoIDIdGenerator with alphabet: {}.", nano.getAlphabet());
					}

					return new NanoIDIdGenerator(nano.getAlphabet().toCharArray());
				}
			}else if(nano.getLength() != null){
				if(logger.isDebugEnabled()){
					logger.debug("IdGenerator using NanoIDIdGenerator with length: {}.", nano.getLength());
				}

				return new NanoIDIdGenerator(nano.getLength());
			}
		}

		logger.debug("IdGenerator using NanoIDIdGenerator.");
		return new NanoIDIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "random-digit.enabled", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnMissingBean({IdGenerator.class})
	public RandomDigitIdGenerator randomDigitIdGenerator() {
		if(idProperties.getRandomDigit() != null){
			IdProperties.RandomDigit randomDigit = idProperties.getRandomDigit();

			if(randomDigit.getMax() != null){
				if(randomDigit.getMin() != null){
					if(logger.isDebugEnabled()){
						logger.debug("IdGenerator using RandomDigitIdGenerator with min: {}, max: {}.",
								randomDigit.getMin(), randomDigit.getMax());
					}

					return new RandomDigitIdGenerator(randomDigit.getMin(), randomDigit.getMax());
				}else{
					if(logger.isDebugEnabled()){
						logger.debug("IdGenerator using RandomDigitIdGenerator with max: {}.", randomDigit.getMax());
					}

					return new RandomDigitIdGenerator(randomDigit.getMax());
				}
			}
		}

		logger.debug("IdGenerator using RandomDigitIdGenerator.");
		return new RandomDigitIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "random.enabled", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnMissingBean({IdGenerator.class})
	public RandomIdGenerator randomIdGenerator() {
		if(idProperties.getRandom() != null){
			IdProperties.Random random = idProperties.getRandom();

			if(Validate.isNotEmpty(random.getChars())){
				if(random.getLength() != null){
					if(logger.isDebugEnabled()){
						logger.debug("IdGenerator using RandomIdGenerator with length: {}, chars: {}.",
								random.getLength(), random.getChars());
					}

					return new RandomIdGenerator(random.getChars().toCharArray(), random.getLength());
				}else{
					if(logger.isDebugEnabled()){
						logger.debug("IdGenerator using RandomIdGenerator with chars: {}.", random.getChars());
					}

					return new RandomIdGenerator(random.getChars().toCharArray());
				}
			}else if(random.getLength() != null){
				if(logger.isDebugEnabled()){
					logger.debug("IdGenerator using RandomIdGenerator with length: {}.", random.getLength());
				}

				return new RandomIdGenerator(random.getLength());
			}
		}

		logger.debug("IdGenerator using RandomIdGenerator.");
		return new RandomIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "snowflake.enabled", havingValue = "true")
	@ConditionalOnMissingBean({IdGenerator.class})
	public SnowflakeIdGenerator snowflakeIdGenerator() {
		if(idProperties.getSnowflake() != null && idProperties.getSnowflake().getDatacenterId() != null &&
				idProperties.getSnowflake().getWorkerId() != null){
			IdProperties.Snowflake snowflake = idProperties.getSnowflake();

			if(logger.isDebugEnabled()){
				logger.debug("IdGenerator using SnowflakeIdGenerator with datacenterId: {}, workerId: {}.",
						snowflake.getDatacenterId(), snowflake.getWorkerId());
			}

			return new SnowflakeIdGenerator(snowflake.getDatacenterId(), snowflake.getWorkerId());
		}else{
			logger.debug("IdGenerator using SnowflakeIdGenerator.");
			return new SnowflakeIdGenerator();
		}
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "uuid.enabled", havingValue = "true")
	@ConditionalOnMissingBean({IdGenerator.class})
	public UUIDIdGenerator uuidIdGenerator() {
		logger.debug("IdGenerator using UUIDIdGenerator.");
		return new UUIDIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "simple.enabled", havingValue = "true", matchIfMissing = true)
	@ConditionalOnMissingBean({IdGenerator.class})
	public SimpleIdGenerator simpleIdGenerator() {
		logger.debug("IdGenerator using SimpleIdGenerator.");
		return new SimpleIdGenerator();
	}

}
