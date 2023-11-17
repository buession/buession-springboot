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

import com.buession.springboot.mybatis.autoconfigure.MybatisProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
		if(AutoConfigurationPackages.has(beanFactory) == false){
			logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.");
			return;
		}

		List<String> packages = AutoConfigurationPackages.get(beanFactory);
		if(logger.isDebugEnabled()){
			packages.forEach(pkg->logger.debug("Using auto-configuration base package '{}'", pkg));
		}

		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
		builder.addPropertyValue("processPropertyPlaceHolders", true);

		Class<?> annotationClass = environment.getProperty(PREFIX + ".annotation-class",
				Class.class, org.apache.ibatis.annotations.Mapper.class);

		if(logger.isDebugEnabled()){
			logger.debug("Searching for mappers annotated with @{}", annotationClass.getName());
		}

		builder.addPropertyValue("annotationClass", annotationClass);
		builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));

		BeanWrapper beanWrapper = new BeanWrapperImpl(MapperScannerConfigurer.class);
		Set<String> propertyNames = Stream.of(beanWrapper.getPropertyDescriptors()).map(PropertyDescriptor::getName)
				.collect(Collectors.toSet());

		if(propertyNames.contains("lazyInitialization")){
			// Need to mybatis-spring 2.0.2+
			builder.addPropertyValue("lazyInitialization", "${" + PREFIX + ".lazy-initialization:false}");
		}
		if(propertyNames.contains("defaultScope")){
			// Need to mybatis-spring 2.0.6+
			builder.addPropertyValue("defaultScope", "${" + PREFIX + ".mapper-default-scope:}");
		}

		// for spring-native
		//boolean injectSqlSession = environment.getProperty("spring.mybatis.inject-sql-session-on-mapper-scan",
		//	Boolean.class, Boolean.TRUE);
		boolean injectSqlSession = environment.getProperty(PREFIX + ".inject-sql-session-on-mapper-scan", Boolean.class,
				Boolean.TRUE);
		if(injectSqlSession && beanFactory instanceof ListableBeanFactory){
			ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
			Optional<String> sqlSessionTemplateBeanName = Optional
					.ofNullable(getBeanNameForType(SqlSessionTemplate.class, listableBeanFactory));
			Optional<String> sqlSessionFactoryBeanName = Optional
					.ofNullable(getBeanNameForType(SqlSessionFactory.class, listableBeanFactory));
			if(sqlSessionTemplateBeanName.isPresent() || !sqlSessionFactoryBeanName.isPresent()){
				builder.addPropertyValue("sqlSessionTemplateBeanName",
						sqlSessionTemplateBeanName.orElse("sqlSessionTemplate"));
			}else{
				builder.addPropertyValue("sqlSessionFactoryBeanName", sqlSessionFactoryBeanName.get());
			}
		}

		builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

		registry.registerBeanDefinition(MapperScannerConfigurer.class.getName(), builder.getBeanDefinition());
	}

	private String getBeanNameForType(Class<?> type, ListableBeanFactory factory) {
		String[] beanNames = factory.getBeanNamesForType(type);
		return beanNames.length > 0 ? beanNames[0] : null;
	}

}
