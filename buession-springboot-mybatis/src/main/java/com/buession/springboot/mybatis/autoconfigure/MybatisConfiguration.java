/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2024 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mybatis.autoconfigure;

import com.buession.core.collect.Arrays;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.core.utils.FieldUtils;
import com.buession.core.validator.Validate;
import com.buession.springboot.datasource.autoconfigure.DataSourceConfiguration;
import com.buession.springboot.mybatis.ConfigurationCustomizer;
import com.buession.springboot.mybatis.ConfiguredMapperScannerRegistrar;
import com.buession.springboot.mybatis.SpringBootVFS;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MybatisProperties.class)
@ConditionalOnBean({DataSource.class})
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter({DataSourceConfiguration.class, MybatisLanguageDriverConfiguration.class})
public class MybatisConfiguration {

	private final MybatisProperties properties;

	private final DataSource dataSource;

	private final Interceptor[] interceptors;

	private final ResourceLoader resourceLoader;

	private Resource configLocationResource = null;

	private final DatabaseIdProvider databaseIdProvider;

	private final List<ConfigurationCustomizer> configurationCustomizers;

	private final LanguageDriver[] languageDrivers;

	private final Resource[] mapperLocations;

	private final static Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

	public MybatisConfiguration(MybatisProperties properties, ObjectProvider<DataSource> dataSource,
								ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader,
								ObjectProvider<DatabaseIdProvider> databaseIdProvider,
								ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
								ObjectProvider<LanguageDriver[]> languageDrivers) {
		this.properties = properties;
		this.dataSource = dataSource.getIfAvailable();
		this.interceptors = interceptorsProvider.getIfAvailable();
		this.resourceLoader = resourceLoader;
		this.databaseIdProvider = databaseIdProvider.getIfAvailable();
		this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
		this.languageDrivers = languageDrivers.getIfAvailable();

		checkConfigFileExists();

		if(Validate.hasText(properties.getConfigLocation())){
			configLocationResource = resourceLoader.getResource(properties.getConfigLocation());
		}

		this.mapperLocations = resolveMapperLocations();
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactoryBean sqlSessionFactory() {
		final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		final PropertyMapper hasTextPropertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		final PropertyMapper nullTextPropertyMapper = PropertyMapper.get().alwaysApplyingWhenHasText();

		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setVfs(SpringBootVFS.class);
		sessionFactoryBean.setFailFast(properties.getFailFast());

		nullTextPropertyMapper.from(configLocationResource).to(sessionFactoryBean::setConfigLocation);

		applyConfiguration(sessionFactoryBean);

		nullTextPropertyMapper.from(properties.getConfigurationProperties())
				.to(sessionFactoryBean::setConfigurationProperties);
		nullTextPropertyMapper.from(databaseIdProvider).to(sessionFactoryBean::setDatabaseIdProvider);

		if(Validate.isNotEmpty(interceptors)){
			sessionFactoryBean.setPlugins(interceptors);
		}

		if(Validate.isNotEmpty(properties.getTypeAliases())){
			sessionFactoryBean.setTypeAliases(properties.getTypeAliases());
		}
		hasTextPropertyMapper.from(properties.getTypeAliasesPackage()).to(sessionFactoryBean::setTypeAliasesPackage);
		nullTextPropertyMapper.from(properties.getTypeAliasesSuperType())
				.to(sessionFactoryBean::setTypeAliasesSuperType);
		hasTextPropertyMapper.from(properties.getTypeHandlersPackage()).to(sessionFactoryBean::setTypeHandlersPackage);

		if(Validate.isNotEmpty(properties.getTypeHandlers())){
			sessionFactoryBean.setTypeHandlers(properties.getTypeHandlers());
		}

		nullTextPropertyMapper.from(properties.getDefaultEnumTypeHandler())
				.to(sessionFactoryBean::setDefaultEnumTypeHandler);

		if(Validate.isNotEmpty(this.mapperLocations)){
			sessionFactoryBean.setMapperLocations(this.mapperLocations);
		}

		Class<? extends LanguageDriver> defaultLanguageDriver = properties.getDefaultScriptingLanguageDriver();
		if(Validate.isNotEmpty(languageDrivers)){
			try{
				FieldUtils.writeField(sessionFactoryBean, "scriptingLanguageDrivers", languageDrivers);
			}catch(IllegalAccessException e){
				if(logger.isWarnEnabled()){
					logger.warn("Set field 'scriptingLanguageDrivers' for {} error: {}",
							SqlSessionFactoryBean.class.getName(), e.getMessage());
				}
			}

			if(defaultLanguageDriver == null && languageDrivers.length == 1){
				defaultLanguageDriver = languageDrivers[0].getClass();
			}
		}

		try{
			FieldUtils.writeDeclaredField(sessionFactoryBean, "defaultScriptingLanguageDriver", defaultLanguageDriver,
					true);
		}catch(IllegalAccessException e){
			if(logger.isWarnEnabled()){
				logger.warn("Set field 'defaultScriptingLanguageDriver' for {} error: {}",
						SqlSessionFactoryBean.class.getName(), e.getMessage());
			}
		}

		return sessionFactoryBean;
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionTemplate sqlSessionTemplate(ObjectProvider<SqlSessionFactory> sqlSessionFactory) {
		ExecutorType executorType = properties.getExecutorType();
		return executorType != null ? new SqlSessionTemplate(sqlSessionFactory.getIfAvailable(),
				executorType) : new SqlSessionTemplate(sqlSessionFactory.getIfAvailable());
	}

	private void checkConfigFileExists() {
		if(properties.isCheckConfigLocation() && Validate.hasText(properties.getConfigLocation())){
			Resource resource = resourceLoader.getResource(properties.getConfigLocation());
			Assert.isFalse(resource.exists(), "Cannot find autoconfigure location: " + resource + " (please add " +
					"autoconfigure file or check your Mybatis configuration)");
		}
	}

	private void applyConfiguration(final SqlSessionFactoryBean sessionFactoryBean) {
		org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();

		if(configuration == null && Validate.hasText(properties.getConfigLocation()) == false){
			configuration = new org.apache.ibatis.session.Configuration();
		}

		if(configuration != null && Validate.isNotEmpty(configurationCustomizers)){
			for(ConfigurationCustomizer customizer : configurationCustomizers){
				customizer.customize(configuration);
			}
		}

		sessionFactoryBean.setConfiguration(configuration);
	}

	private Resource[] resolveMapperLocations() {
		if(Validate.isNotEmpty(properties.getMapperLocations())){
			Resource[] resources = new Resource[]{};
			PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

			for(String mapperLocation : properties.getMapperLocations()){
				try{
					resources = Arrays.addAll(resources, resourceResolver.getResources(mapperLocation));
				}catch(IOException e){
					if(logger.isErrorEnabled()){
						logger.error("Load mapper resource error: {}.", e.getMessage());
					}
				}
			}

			return resources;
		}

		return null;
	}

	@Configuration(proxyBeanMethods = false)
	@Import(ConfiguredMapperScannerRegistrar.class)
	@ConditionalOnMissingBean({MapperFactoryBean.class, MapperScannerConfigurer.class})
	@ConditionalOnProperty(prefix = MybatisProperties.PREFIX, name = "scanner.enabled", havingValue = "true", matchIfMissing = true)
	static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {

		@Override
		public void afterPropertiesSet() throws Exception {
			logger.debug(
					"Not found configuration for registering mapper bean using @MapperScan, MapperFactoryBean and MapperScannerConfigurer.");
		}

	}

}
