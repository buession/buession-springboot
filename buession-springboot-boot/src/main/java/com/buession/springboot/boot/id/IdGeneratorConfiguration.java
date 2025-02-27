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

import com.buession.core.id.AtomicSimpleIdGenerator;
import com.buession.core.id.AtomicUUIDIdGenerator;
import com.buession.core.id.NanoIDIdGenerator;
import com.buession.core.id.RandomDigitIdGenerator;
import com.buession.core.id.RandomIdGenerator;
import com.buession.core.id.SimpleIdGenerator;
import com.buession.core.id.SnowflakeIdGenerator;
import com.buession.core.id.UUIDIdGenerator;
import com.buession.core.validator.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Yong.Teng
 * @since 0.0.1
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
	@ConditionalOnMissingBean
	public AtomicSimpleIdGenerator atomicSimpleIdGenerator() {
		logger.debug("IdGenerator use AtomicSimpleIdGenerator.");
		return new AtomicSimpleIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "atomic-uuid.enabled", havingValue = "true")
	@ConditionalOnMissingBean
	public AtomicUUIDIdGenerator atomicUUIDIdGenerator() {
		logger.debug("IdGenerator use AtomicUUIDIdGenerator.");
		return new AtomicUUIDIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "nano.enabled", havingValue = "true")
	@ConditionalOnMissingBean
	public NanoIDIdGenerator nanoIDIdGenerator() {
		if(idProperties.getNano() != null && idProperties.getNano().getLength() != null){
			IdProperties.Nano nano = idProperties.getNano();

			if(Validate.isEmpty(nano.getAlphabet())){
				if(logger.isDebugEnabled()){
					logger.debug("IdGenerator use NanoIDIdGenerator with length: {}, alphabet: {}.", nano.getLength(),
							nano.getAlphabet());
				}

				return new NanoIDIdGenerator(nano.getAlphabet().toCharArray(), nano.getLength());
			}else{
				if(logger.isDebugEnabled()){
					logger.debug("IdGenerator use NanoIDIdGenerator with length: {}.", nano.getLength());
				}

				return new NanoIDIdGenerator(nano.getLength());
			}
		}else{
			logger.debug("IdGenerator use NanoIDIdGenerator.");
			return new NanoIDIdGenerator();
		}
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "random-digit.enabled", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnMissingBean
	public RandomDigitIdGenerator randomDigitIdGenerator() {
		if(idProperties.getRandomDigit() != null){
			IdProperties.RandomDigit randomDigit = idProperties.getRandomDigit();

			if(logger.isDebugEnabled()){
				logger.debug("IdGenerator use RandomDigitIdGenerator with min: {}, max: {}.", randomDigit.getMin(),
						randomDigit.getMax());
			}

			return new RandomDigitIdGenerator(randomDigit.getMin(), randomDigit.getMax());
		}else{
			return new RandomDigitIdGenerator();
		}
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "random.enabled", havingValue = "true",
			matchIfMissing = true)
	@ConditionalOnMissingBean
	public RandomIdGenerator randomIdGenerator() {
		if(idProperties.getRandom() != null){
			IdProperties.Random random = idProperties.getRandom();

			if(logger.isDebugEnabled()){
				logger.debug("IdGenerator use RandomIdGenerator with length: {}, chars: {}.", random.getLength(),
						random.getChars());
			}

			return new RandomIdGenerator(random.getChars().toCharArray(), random.getLength());
		}else{
			return new RandomIdGenerator();
		}
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "snowflake.enabled", havingValue = "true", matchIfMissing = true)
	@ConditionalOnMissingBean
	public SnowflakeIdGenerator snowflakeIdGenerator() {
		if(idProperties.getSnowflake() != null && idProperties.getSnowflake().getDatacenterId() != null &&
				idProperties.getSnowflake().getWorkerId() != null){
			if(logger.isDebugEnabled()){
				logger.debug("IdGenerator use SnowflakeIdGenerator with datacenterId: {}, workerId: {}.",
						idProperties.getSnowflake().getDatacenterId(), idProperties.getSnowflake().getWorkerId());
			}

			return new SnowflakeIdGenerator(idProperties.getSnowflake().getDatacenterId(),
					idProperties.getSnowflake().getWorkerId());
		}else{
			logger.debug("IdGenerator use SnowflakeIdGenerator.");
			return new SnowflakeIdGenerator();
		}
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "uuid.enabled", havingValue = "true")
	@ConditionalOnMissingBean
	public UUIDIdGenerator uuidIdGenerator() {
		logger.debug("IdGenerator use UUIDIdGenerator.");
		return new UUIDIdGenerator();
	}

	@Bean
	@ConditionalOnProperty(prefix = IdProperties.PREFIX, name = "simple.enabled")
	@ConditionalOnMissingBean
	public SimpleIdGenerator simpleIdGenerator() {
		logger.debug("IdGenerator use SimpleIdGenerator.");
		return new SimpleIdGenerator();
	}

}
