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
 * | Copyright @ 2013-2018 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mongodb.autoconfigure;

import com.buession.core.validator.Validate;
import com.mongodb.MongoClientOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import javax.annotation.PostConstruct;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(MongoDBConfigurationProperties.class)
@AutoConfigureAfter(MongoDataAutoConfiguration.class)
@Import({MongoDataAutoConfiguration.class})
public class MongoDBConfiguration implements EnvironmentAware {

    protected final ApplicationContext applicationContext;

    protected final MongoClientOptions options;

    protected Environment environment;

    @Autowired
    protected MongoDBConfigurationProperties mongoDBConfigurationProperties;

    @Autowired
    protected MongoMappingContext mongoMappingContext;

    @Autowired
    protected MappingMongoConverter mappingMongoConverter;

    private final static Logger logger = LoggerFactory.getLogger(MongoDBConfiguration.class);

    public MongoDBConfiguration(ApplicationContext applicationContext, ObjectProvider<MongoClientOptions> options,
                                Environment environment){
        this.applicationContext = applicationContext;
        this.options = options.getIfAvailable();
        this.environment = environment;
    }

    @PostConstruct
    public void initialize(){
        setTypeMapper();
    }

    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public MongoClientOptions getOptions(){
        return options;
    }

    public Environment getEnvironment(){
        return environment;
    }

    @Override
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    protected void setTypeMapper(){
        if(mongoDBConfigurationProperties.getTypeMapper() != null){
            try{
                MongoTypeMapper mongoTypeMapper = mongoDBConfigurationProperties.getTypeMapper().newInstance();

                mappingMongoConverter.setTypeMapper(mongoTypeMapper);
            }catch(InstantiationException e){
                logger.error("Set MongoTypeMapper failure: {}", e);
            }catch(IllegalAccessException e){
                logger.error("Set MongoTypeMapper failure: {}", e);
            }
        }else if(Validate.hasText(mongoDBConfigurationProperties.getTypeKey())){
            MongoTypeMapper mongoTypeMapper;

            if(mongoDBConfigurationProperties.getTypeKey() == null){
                mongoTypeMapper = new DefaultMongoTypeMapper(null, mongoMappingContext);
            }else{
                mongoTypeMapper = new DefaultMongoTypeMapper(mongoDBConfigurationProperties.getTypeKey(),
                        mongoMappingContext);
            }
            mappingMongoConverter.setTypeMapper(mongoTypeMapper);
        }
    }
}
