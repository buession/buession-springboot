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
package com.buession.springboot.security.shiro;

import com.buession.core.validator.Validate;
import com.buession.springboot.security.shiro.autoconfigure.ShiroProperties;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;

/**
 * @author Yong.Teng
 */
public abstract class AbstractShiroWebConfiguration extends org.apache.shiro.spring.web.config
        .AbstractShiroWebConfiguration {

    @Autowired
    protected ShiroProperties shiroProperties;

    @Bean(name = "shiroFilterChainDefinition")
    @ConditionalOnResource(resources = {"classpath:shiro.conf"})
    @Override
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        Ini ini = Ini.fromResourcePath("classpath:shiro.conf");

        Ini.Section urls = ini.getSection("urls");

        DefaultShiroFilterChainDefinition shiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();

        if(Validate.isEmpty(shiroProperties.getFilterChainDefinitions()) == false){
            shiroProperties.getFilterChainDefinitions().forEach((antPath, definition)->{
                shiroFilterChainDefinition.addPathDefinition(antPath, definition);
            });
        }

        if(Validate.isEmpty(urls) == false){
            urls.forEach((antPath, definition)->{
                shiroFilterChainDefinition.addPathDefinition(antPath, definition);
            });
        }

        return shiroFilterChainDefinition;
    }

}