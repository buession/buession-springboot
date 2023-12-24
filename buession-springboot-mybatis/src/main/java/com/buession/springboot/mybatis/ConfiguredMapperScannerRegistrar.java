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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mybatis;

import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;
import com.buession.springboot.mybatis.autoconfigure.MybatisProperties;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
public class ConfiguredMapperScannerRegistrar implements EnvironmentAware, BeanFactoryAware,
		ImportBeanDefinitionRegistrar {

	private final static String PREFIX = MybatisProperties.PREFIX + ".scanner";

	private Environment environment;

	private BeanFactory beanFactory;

	private final static Logger logger = LoggerFactory.getLogger(ConfiguredMapperScannerRegistrar.class);

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void registerBeanDefinitions(@NonNull AnnotationMetadata annotationMetadata,
										@NonNull BeanDefinitionRegistry registry) {
		if(AutoConfigurationPackages.has(beanFactory)){
			registerBeanDefinition(registry);
		}else{
			logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.");
		}
	}

	public void registerBeanDefinition(@NonNull BeanDefinitionRegistry registry) {
		String basePackage = getProperty("base-package", "basePackage", String.class);
		List<String> basePackages = Validate.isBlank(basePackage) ? AutoConfigurationPackages.get(
				beanFactory) : Arrays.asList(com.buession.core.utils.StringUtils.split(basePackage, ','));

		if(logger.isDebugEnabled()){
			basePackages.forEach((pkg)->logger.debug("Using auto-configuration base package '{}'", pkg));
		}

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
		builder.addPropertyValue("processPropertyPlaceHolders", true);

		String annotationClassName = getProperty("annotation-class", "annotationClass", String.class);

		try{
			Class<?> annotationClass = annotationClassName == null ?
					org.apache.ibatis.annotations.Mapper.class : ClassUtils.forName(annotationClassName, null);

			builder.addPropertyValue("annotationClass", annotationClass);

			if(logger.isDebugEnabled()){
				logger.debug("Searching for mappers annotated with @{}", annotationClass.getSimpleName());
			}
		}catch(ClassNotFoundException e){
			throw new BeanInitializationException(e.getMessage());
		}

		builder.addPropertyValue("basePackage", StringUtils.join(basePackages, ','));

		BeanWrapper beanWrapper = new BeanWrapperImpl(MapperScannerConfigurer.class);

		Stream.of(beanWrapper.getPropertyDescriptors()).filter((x)->"lazyInitialization".equals(x.getName())).findAny()
				.ifPresent((x)->builder.addPropertyValue("lazyInitialization",
						"${" + PREFIX + ".lazy-initialization:false}"));
		Stream.of(beanWrapper.getPropertyDescriptors()).filter((x)->"defaultScope".equals(x.getName())).findAny()
				.ifPresent((x)->builder.addPropertyValue("defaultScope", "${" + PREFIX + ".mapper-default-scope:}"));

		builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

		registry.registerBeanDefinition(MapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
	}

	private <T> T getProperty(final String key, final String humpKey, final Class<T> targetType) {
		T value = environment.getProperty(PREFIX + '.' + key, targetType);

		if(value == null){
			value = environment.getProperty(PREFIX + '.' + humpKey, targetType);
		}

		return value;
	}

}
