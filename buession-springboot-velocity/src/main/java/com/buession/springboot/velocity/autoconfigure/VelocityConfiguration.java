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

import com.buession.velocity.spring.VelocityConfigurer;
import com.buession.velocity.spring.VelocityEngineFactory;
import com.buession.velocity.spring.VelocityEngineFactoryBean;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
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
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;
import javax.servlet.Servlet;
import java.util.Properties;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties({VelocityProperties.class})
@ConditionalOnClass({VelocityEngine.class})
@AutoConfigureAfter({WebMvcAutoConfiguration.class, WebFluxAutoConfiguration.class})
public class VelocityConfiguration {

    private final ApplicationContext applicationContext;

    @Autowired
    private VelocityProperties properties;

    private final static Logger logger = LoggerFactory.getLogger(VelocityConfiguration.class);

    public VelocityConfiguration(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void checkTemplateLocationExists(){
        if(properties.isCheckTemplateLocation()){
            TemplateLocation location = new TemplateLocation(properties.getResourceLoaderPath());
            if(location.exists(applicationContext) == false){
                logger.warn("Cannot find template location: {} (please add some templates, check your Velocity " +
                        "configuration, or set {}.checkTemplateLocation=false)", location, VelocityProperties.class
                        .getName());
            }
        }
    }

    static abstract class AbstractVelocityConfiguration {

        @Autowired
        protected VelocityProperties properties;

        protected VelocityConfigurer velocityConfigurer(){
            VelocityConfigurer configurer = new VelocityConfigurer();

            applyProperties(configurer);

            return configurer;
        }

        protected void applyProperties(VelocityEngineFactory factory){
            Properties velocityProperties = new Properties();

            velocityProperties.setProperty(RuntimeConstants.INPUT_ENCODING, properties.getCharsetName());
            // velocityProperties.setProperty("output.encoding", properties.getCharsetName());
            velocityProperties.setProperty(RuntimeConstants.VM_LIBRARY, properties.getVelocityMacro().getLibrary());
            velocityProperties.putAll(properties.getProperties());

            factory.setResourceLoaderPath(properties.getResourceLoaderPath());
            factory.setPreferFileSystemAccess(properties.isPreferFileSystemAccess());
            factory.setVelocityProperties(velocityProperties);
        }

    }

    @Configuration
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnClass({Servlet.class})
    @AutoConfigureAfter(WebMvcAutoConfiguration.class)
    public class VelocityServletWebConfiguration extends AbstractVelocityConfiguration {

        @Bean
        @ConditionalOnMissingBean
        @Override
        public VelocityConfigurer velocityConfigurer(){
            return super.velocityConfigurer();
        }

        @Bean
        @ConditionalOnMissingBean(name = "velocityViewResolver")
        public com.buession.velocity.servlet.VelocityViewResolver velocityViewResolver(){
            com.buession.velocity.servlet.VelocityViewResolver resolver = new com.buession.velocity.servlet
                    .VelocityViewResolver();

            resolver.setEncoding(properties.getCharset().name());
            resolver.setToolboxConfigLocation(properties.getToolboxConfigLocation());
            resolver.setDateToolAttribute(properties.getDateToolAttribute());
            resolver.setNumberToolAttribute(properties.getNumberToolAttribute());

            properties.applyToMvcViewResolver(resolver);

            return resolver;
        }

        @Bean
        @ConditionalOnEnabledResourceChain
        @ConditionalOnMissingFilterBean(ResourceUrlEncodingFilter.class)
        public FilterRegistrationBean<ResourceUrlEncodingFilter> resourceUrlEncodingFilter(){
            FilterRegistrationBean<ResourceUrlEncodingFilter> registration = new FilterRegistrationBean<>(new
                    ResourceUrlEncodingFilter());
            registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
            return registration;
        }

    }

    @Configuration
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @ConditionalOnClass(WebFluxConfigurer.class)
    @AutoConfigureAfter(WebFluxAutoConfiguration.class)
    public class VelocityReactiveWebConfiguration extends AbstractVelocityConfiguration {

        @PostConstruct
        public void initialize(){
            logger.error("{} cloud not support on {}", VelocityEngine.class.getName(), ConditionalOnWebApplication
                    .Type.REACTIVE.name());
        }

    }

    @Configuration
    @ConditionalOnNotWebApplication
    public class VelocityNonWebConfiguration extends AbstractVelocityConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public VelocityEngineFactoryBean velocityConfiguration(){
            VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();

            applyProperties(velocityEngineFactoryBean);

            return velocityEngineFactoryBean;
        }

    }

}
