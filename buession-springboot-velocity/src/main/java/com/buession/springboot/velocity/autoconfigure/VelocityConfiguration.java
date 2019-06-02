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
package com.buession.springboot.velocity.autoconfigure;

import com.buession.springboot.velocity.Constant;
import com.buession.velocity.VelocityConfig;
import com.buession.velocity.VelocityConfigurer;
import com.buession.velocity.spring.VelocityEngineFactory;
import com.buession.velocity.spring.VelocityEngineFactoryBean;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties({VelocityProperties.class})
@ConditionalOnClass({VelocityEngine.class})
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
public class VelocityConfiguration {

    private final ApplicationContext applicationContext;

    private final VelocityProperties properties;

    private final static Logger logger = LoggerFactory.getLogger(VelocityConfiguration.class);

    public VelocityConfiguration(ApplicationContext applicationContext, VelocityProperties properties){
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    @PostConstruct
    public void checkTemplateLocationExists(){
        if(properties.isCheckTemplateLocation() == false){
            return;
        }

        TemplateLocation location = new TemplateLocation(properties.getResourceLoaderPath());
        if(location.exists(applicationContext) == false){
            logger.warn("Cannot find template location: {} (please add some templates, check your Velocity " +
                    "configuration, or set {}.checkTemplateLocation=false)", location, VelocityProperties.class
                    .getName());
        }
    }

    @Configuration
    @ConditionalOnClass({Servlet.class})
    @ConditionalOnWebApplication
    public static class VelocityWebConfiguration extends AbstractVelocityConfig {

        @Bean(name = "velocityConfigurer")
        @ConditionalOnMissingBean({VelocityConfig.class})
        public VelocityConfigurer velocityConfigurer(){
            VelocityConfigurer configurer = new VelocityConfigurer();

            applyProperties(configurer);

            return configurer;
        }

        @Bean(name = "velocityEngine")
        public VelocityEngine velocityEngine(VelocityConfigurer configurer) throws VelocityException, IOException{
            return configurer.getVelocityEngine();
        }

        @Bean(name = "resourceUrlEncodingFilter")
        @ConditionalOnMissingBean
        @ConditionalOnEnabledResourceChain
        public ResourceUrlEncodingFilter resourceUrlEncodingFilter(){
            return new ResourceUrlEncodingFilter();
        }
    }

    @Configuration
    @ConditionalOnNotWebApplication
    public static class VelocityNonWebConfiguration extends AbstractVelocityConfig {

        @Bean(name = "velocityConfiguration")
        @ConditionalOnMissingBean
        public VelocityEngineFactoryBean velocityConfiguration(){
            VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();

            applyProperties(velocityEngineFactoryBean);

            return velocityEngineFactoryBean;
        }

    }

    protected static abstract class AbstractVelocityConfig {

        @Autowired
        protected VelocityProperties properties;

        private final static Logger logger = LoggerFactory.getLogger(AbstractVelocityConfig.class);

        protected void applyProperties(VelocityEngineFactory factory){
            factory.setResourceLoaderPath(properties.getResourceLoaderPath());
            factory.setPreferFileSystemAccess(properties.isPreferFileSystemAccess());

            if(properties.getToolboxConfigLocation() != null && properties.getToolboxConfigLocation().startsWith
                    (Constant.CLASSPATH)){
                Resource toolBoxConfigLocation = new ClassPathResource(properties.getToolboxConfigLocation().replace
                        (Constant.CLASSPATH, ""));
                try{
                    properties.setToolboxConfigLocation(toolBoxConfigLocation.getFile().getPath());
                }catch(IOException e){
                    logger.error("Set toolbox location error.", e);
                }
            }

            Properties velocityProperties = new Properties();

            velocityProperties.setProperty("input.encoding", properties.getCharsetName());
            velocityProperties.setProperty("input.encoding", properties.getCharsetName());
            velocityProperties.setProperty(Constant.VELOCITY_MACRO_LIBRARY, properties.getVelocimacro().getLibrary());
            velocityProperties.putAll(properties.getProperties());

            factory.setVelocityProperties(velocityProperties);
        }

    }

}
