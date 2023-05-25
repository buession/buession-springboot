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
 * | Copyright @ 2013-2022 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mybatis.autoconfigure;

import com.buession.core.collect.Arrays;
import com.buession.core.converter.mapper.PropertyMapper;
import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import com.buession.springboot.datasource.autoconfigure.DataSourceConfiguration;
import com.buession.springboot.datasource.core.DataSource;
import com.buession.springboot.mybatis.ConfigurationCustomizer;
import com.buession.springboot.mybatis.SpringBootVFS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MybatisProperties.class)
@ConditionalOnBean({DataSource.class})
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MybatisConfiguration {

	private final MybatisProperties properties;

	private final DataSource dataSource;

	private final Interceptor[] interceptors;

	private final ResourceLoader resourceLoader;

	private final DatabaseIdProvider databaseIdProvider;

	private final List<ConfigurationCustomizer> configurationCustomizers;

	private final static Logger logger = LoggerFactory.getLogger(MybatisConfiguration.class);

	public MybatisConfiguration(MybatisProperties properties, ObjectProvider<DataSource> dataSource,
								ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader,
								ObjectProvider<DatabaseIdProvider> databaseIdProvider,
								ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider){
		this.properties = properties;
		this.dataSource = dataSource.getIfAvailable();
		this.interceptors = interceptorsProvider.getIfAvailable();
		this.resourceLoader = resourceLoader;
		this.databaseIdProvider = databaseIdProvider.getIfAvailable();
		this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();

		checkConfigFileExists();
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionFactory masterSqlSessionFactory() throws Exception{
		return createSqlSessionFactory(dataSource.getMaster());
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionTemplate masterSqlSessionTemplate(ObjectProvider<SqlSessionFactory> masterSqlSessionFactory){
		return createSqlSessionTemplate(masterSqlSessionFactory.getIfAvailable());
	}

	@Bean
	@ConditionalOnMissingBean
	public List<SqlSessionFactory> slaveSqlSessionFactories() throws Exception{
		if(Validate.isEmpty(dataSource.getSlaves())){
			throw new BeanInstantiationException(SqlSessionFactory.class, "slave dataSource is null or empty");
		}

		List<SqlSessionFactory> slaveSqlSessionFactories = new ArrayList<>(dataSource.getSlaves().size());

		for(javax.sql.DataSource ds : dataSource.getSlaves()){
			slaveSqlSessionFactories.add(createSqlSessionFactory(ds));
		}

		return slaveSqlSessionFactories;
	}

	@Bean
	@ConditionalOnMissingBean
	public List<SqlSessionTemplate> slaveSqlSessionTemplates(List<SqlSessionFactory> slaveSqlSessionFactories){
		if(Validate.isEmpty(slaveSqlSessionFactories)){
			throw new BeanInstantiationException(SqlSessionTemplate.class, "slave sqlSessionFactory is null or empty");
		}

		return slaveSqlSessionFactories.stream().map(this::createSqlSessionTemplate).collect(Collectors.toList());
	}

	private SqlSessionFactory createSqlSessionFactory(javax.sql.DataSource dataSource) throws Exception{
		PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenHasText();
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();

		factory.setDataSource(dataSource);
		factory.setVfs(SpringBootVFS.class);

		if(Validate.hasText(properties.getConfigLocation())){
			factory.setConfigLocation(resourceLoader.getResource(properties.getConfigLocation()));
		}

		org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();
		if(configuration == null && Validate.hasText(properties.getConfigLocation()) == false){
			configuration = new org.apache.ibatis.session.Configuration();
		}

		if(configuration != null && Validate.isNotEmpty(configurationCustomizers)){
			for(ConfigurationCustomizer configurationCustomizer : configurationCustomizers){
				configurationCustomizer.customize(configuration);
			}
		}

		factory.setConfiguration(configuration);
		if(properties.getConfigurationProperties() != null){
			factory.setConfigurationProperties(properties.getConfigurationProperties());
		}

		if(Validate.isNotEmpty(interceptors)){
			factory.setPlugins(interceptors);
		}

		if(databaseIdProvider != null){
			factory.setDatabaseIdProvider(databaseIdProvider);
		}

		mapper.from(properties.getTypeAliasesPackage()).to(factory::setTypeAliasesPackage);
		mapper.from(properties.getTypeHandlersPackage()).to(factory::setTypeHandlersPackage);

		if(properties.getTypeAliasesSuperType() != null){
			factory.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
		}

		if(Validate.isNotEmpty(properties.getTypeAliases())){
			factory.setTypeAliases(properties.getTypeAliases());
		}

		if(Validate.isNotEmpty(properties.getTypeHandlers())){
			factory.setTypeHandlers(properties.getTypeHandlers());
		}

		if(properties.getDefaultEnumTypeHandler() != null){
			factory.setDefaultEnumTypeHandler(properties.getDefaultEnumTypeHandler());
		}

		factory.setFailFast(properties.getFailFast());

		Resource[] resolveMapperLocations = resolveMapperLocations();
		if(Validate.isNotEmpty(resolveMapperLocations)){
			factory.setMapperLocations(resolveMapperLocations);
		}

		return factory.getObject();
	}

	private void checkConfigFileExists(){
		if(properties.isCheckConfigLocation() && Validate.hasText(properties.getConfigLocation())){
			Resource resource = resourceLoader.getResource(properties.getConfigLocation());
			Assert.isFalse(resource.exists(), "Cannot find autoconfigure location: " + resource + " (please add " +
					"autoconfigure file or check your Mybatis configuration)");
		}
	}

	private Resource[] resolveMapperLocations(){
		if(Validate.isNotEmpty(properties.getMapperLocations())){
			Resource[] resources = new Resource[]{};
			PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

			for(String mapperLocation : properties.getMapperLocations()){
				try{
					Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources = Arrays.addAll(null, mappers);
				}catch(IOException e){
					if(logger.isErrorEnabled()){
						logger.error("Get mapper resource error: {}.", e.getMessage());
					}
				}
			}

			return resources;
		}

		return null;
	}

	private SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		ExecutorType executorType = properties.getExecutorType();
		return executorType != null ? new SqlSessionTemplate(sqlSessionFactory, executorType) : new SqlSessionTemplate(
				sqlSessionFactory);
	}

	@Configuration
	@ConditionalOnMissingBean({MapperFactoryBean.class})
	@Import({MapperScannerRegistrarAutoConfigured.class})
	static class MapperScannerRegistrarNotFoundConfiguration implements InitializingBean {

		@Override
		public void afterPropertiesSet() throws Exception{
			logger.debug("No {} found.", MapperFactoryBean.class.getName());
		}

	}

	public static class MapperScannerRegistrarAutoConfigured
			implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {

		private BeanFactory beanFactory;

		private ResourceLoader resourceLoader;

		@Override
		public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry){
			logger.debug("Searching for mappers annotated with @Mapper");
			ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);

			if(resourceLoader != null){
				scanner.setResourceLoader(resourceLoader);
			}

			try{
				List<String> ex = AutoConfigurationPackages.get(beanFactory);
				if(logger.isDebugEnabled()){
					for(String s : ex){
						logger.debug("Using auto-configuration base package '{}'", s);
					}
				}

				scanner.setAnnotationClass(Mapper.class);
				scanner.registerFilters();
				scanner.doScan(StringUtils.toStringArray(ex));
			}catch(IllegalStateException e){
				logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.", e);
			}
		}

		@Override
		public void setBeanFactory(BeanFactory beanFactory) throws BeansException{
			this.beanFactory = beanFactory;
		}

		@Override
		public void setResourceLoader(ResourceLoader resourceLoader){
			this.resourceLoader = resourceLoader;
		}

	}

}
