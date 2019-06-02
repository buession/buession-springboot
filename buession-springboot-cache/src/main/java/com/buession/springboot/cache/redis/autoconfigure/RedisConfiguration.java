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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.autoconfigure;

import com.buession.core.validator.Validate;
import com.buession.redis.RedisClientTemplate;
import com.buession.redis.RedisTemplate;
import com.buession.redis.client.connection.RedisConnection;
import com.buession.redis.spring.JedisRedisConnectionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(RedisConfigProperties.class)
@ConditionalOnClass({RedisClientTemplate.class})
public class RedisConfiguration {

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    @Autowired
    private RedisConnection redisConnection;

    private final static Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    @Bean(name = "jedisConnection")
    @ConditionalOnClass({Jedis.class})
    @ConditionalOnMissingBean
    public RedisConnection jedisConnection() throws Exception{
        JedisRedisConnectionFactoryBean connectionFactory = new JedisRedisConnectionFactoryBean();

        connectionFactory.setPoolConfig(jedisPoolConfig());
        connectionFactory.setHost(redisConfigProperties.getHost());
        connectionFactory.setPort(redisConfigProperties.getPort());
        connectionFactory.setDatabase(redisConfigProperties.getDatabase());

        if(Validate.hasText(redisConfigProperties.getPassword())){
            connectionFactory.setPassword(redisConfigProperties.getPassword());
        }

        try{
            connectionFactory.afterPropertiesSet();
        }catch(Exception e){
            logger.error("RedisConnection initialize failure: {}", e);
        }

        return connectionFactory.getObject();
    }

    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean
    public RedisTemplate redisTemplate(){
        RedisTemplate template = new RedisTemplate(redisConnection);

        logger.info("RedisTemplate bean init success.");

        return template;
    }

    private redis.clients.jedis.JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = redisConfigProperties.getPool();
        redis.clients.jedis.JedisPoolConfig config = new redis.clients.jedis.JedisPoolConfig();

        config.setLifo(jedisPoolConfig.getLifo());
        config.setMaxWaitMillis(jedisPoolConfig.getMaxWaitMillis());
        config.setMinEvictableIdleTimeMillis(jedisPoolConfig.getMinEvictableIdleTimeMillis());
        config.setSoftMinEvictableIdleTimeMillis(jedisPoolConfig.getSoftMinEvictableIdleTimeMillis());
        config.setNumTestsPerEvictionRun(jedisPoolConfig.getNumTestsPerEvictionRun());
        config.setEvictionPolicyClassName(jedisPoolConfig.getEvictionPolicyClassName());
        config.setTestOnBorrow(jedisPoolConfig.getTestOnBorrow());
        config.setTestOnReturn(jedisPoolConfig.getTestOnReturn());
        config.setTestWhileIdle(jedisPoolConfig.getTestWhileIdle());
        config.setTimeBetweenEvictionRunsMillis(jedisPoolConfig.getTimeBetweenEvictionRunsMillis());
        config.setBlockWhenExhausted(jedisPoolConfig.getBlockWhenExhausted());
        config.setJmxEnabled(jedisPoolConfig.getJmxEnabled());
        config.setJmxNamePrefix(jedisPoolConfig.getJmxNamePrefix());
        config.setMaxTotal(jedisPoolConfig.getMaxTotal());
        config.setMinIdle(jedisPoolConfig.getMinIdle());
        config.setMaxIdle(jedisPoolConfig.getMaxIdle());

        logger.info("JedisPoolConfig bean init success.");

        return config;
    }

}
