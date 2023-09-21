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
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

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
								ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
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
	public SqlSessionFactoryBean masterSqlSessionFactory() {
		return createSqlSessionFactory(dataSource.getMaster());
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionTemplate masterSqlSessionTemplate(ObjectProvider<SqlSessionFactory> masterSqlSessionFactory) {
		return createSqlSessionTemplate(masterSqlSessionFactory.getIfAvailable());
	}

	@Bean
	@ConditionalOnMissingBean
	public List<SqlSessionFactoryBean> slaveSqlSessionFactories() {
		if(Validate.isEmpty(dataSource.getSlaves())){
			throw new BeanInstantiationException(SqlSessionFactory.class, "slave dataSource is null or empty");
		}

		return dataSource.getSlaves().parallelStream().map(this::createSqlSessionFactory).collect(Collectors.toList());
	}

	@Bean
	@ConditionalOnMissingBean
	public List<SqlSessionTemplate> slaveSqlSessionTemplates(List<SqlSessionFactory> slaveSqlSessionFactories) {
		if(Validate.isEmpty(slaveSqlSessionFactories)){
			throw new BeanInstantiationException(SqlSessionTemplate.class, "slave sqlSessionFactory is null or empty");
		}

		return slaveSqlSessionFactories.stream().map(this::createSqlSessionTemplate).collect(Collectors.toList());
	}

	private SqlSessionFactoryBean createSqlSessionFactory(javax.sql.DataSource dataSource) {
		PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenHasText();
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

		factoryBean.setDataSource(dataSource);
		factoryBean.setVfs(SpringBootVFS.class);

		mapper.from(properties::getConfigLocation).as(resourceLoader::getResource).to(factoryBean::setConfigLocation);

		org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();
		if(configuration == null && Validate.hasText(properties.getConfigLocation()) == false){
			configuration = new org.apache.ibatis.session.Configuration();
		}

		if(configuration != null && Validate.isNotEmpty(configurationCustomizers)){
			for(ConfigurationCustomizer configurationCustomizer : configurationCustomizers){
				configurationCustomizer.customize(configuration);
			}
		}

		factoryBean.setConfiguration(configuration);
		if(properties.getConfigurationProperties() != null){
			factoryBean.setConfigurationProperties(properties.getConfigurationProperties());
		}

		if(Validate.isNotEmpty(interceptors)){
			factoryBean.setPlugins(interceptors);
		}

		if(databaseIdProvider != null){
			factoryBean.setDatabaseIdProvider(databaseIdProvider);
		}

		mapper.from(properties.getTypeAliasesPackage()).to(factoryBean::setTypeAliasesPackage);
		mapper.from(properties.getTypeHandlersPackage()).to(factoryBean::setTypeHandlersPackage);

		if(properties.getTypeAliasesSuperType() != null){
			factoryBean.setTypeAliasesSuperType(properties.getTypeAliasesSuperType());
		}

		if(Validate.isNotEmpty(properties.getTypeAliases())){
			factoryBean.setTypeAliases(properties.getTypeAliases());
		}

		if(Validate.isNotEmpty(properties.getTypeHandlers())){
			factoryBean.setTypeHandlers(properties.getTypeHandlers());
		}

		if(properties.getDefaultEnumTypeHandler() != null){
			factoryBean.setDefaultEnumTypeHandler(properties.getDefaultEnumTypeHandler());
		}

		factoryBean.setFailFast(properties.getFailFast());

		Resource[] resolveMapperLocations = resolveMapperLocations();
		if(Validate.isNotEmpty(resolveMapperLocations)){
			factoryBean.setMapperLocations(resolveMapperLocations);
		}

		return factoryBean;
	}

	private void checkConfigFileExists() {
		if(properties.isCheckConfigLocation() && Validate.hasText(properties.getConfigLocation())){
			Resource resource = resourceLoader.getResource(properties.getConfigLocation());
			Assert.isFalse(resource.exists(), "Cannot find autoconfigure location: " + resource + " (please add " +
					"autoconfigure file or check your Mybatis configuration)");
		}
	}

	private Resource[] resolveMapperLocations() {
		if(Validate.isNotEmpty(properties.getMapperLocations())){
			Resource[] resources = new Resource[]{};
			PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

			for(String mapperLocation : properties.getMapperLocations()){
				try{
					Resource[] mappers = resourceResolver.getResources(mapperLocation);
					resources = Arrays.addAll(resources, mappers);
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

	private SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		ExecutorType executorType = properties.getExecutorType();
		return executorType != null ? new SqlSessionTemplate(sqlSessionFactory, executorType) : new SqlSessionTemplate(
				sqlSessionFactory);
	}

	@Configuration
	@EnableConfigurationProperties(MybatisProperties.class)
	@ConditionalOnProperty(prefix = MybatisProperties.PREFIX, name = "scanner")
	static class MapperScannerConfiguration {

		private final MybatisProperties.Scanner scanner;

		public MapperScannerConfiguration(MybatisProperties properties) {
			this.scanner = properties.getScanner();
		}

		@Bean
		public MapperScannerConfigurer mapperScannerConfigurer() {
			MapperScannerConfigurer configurer = new MapperScannerConfigurer();
			PropertyMapper mapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

			mapper.from(scanner::getBasePackage).to(configurer::setBasePackage);
			mapper.from(scanner::getAddToConfig).to(configurer::setAddToConfig);
			mapper.from(scanner::getLazyInitialization).to(configurer::setLazyInitialization);
			mapper.from(scanner::getMarkerInterface).to(configurer::setMarkerInterface);
			mapper.from(scanner::getMapperFactoryBeanClass).to(configurer::setMapperFactoryBeanClass);
			mapper.from(scanner::getBeanName).to(configurer::setBeanName);
			mapper.from(scanner::getProcessPropertyPlaceHolders).to(configurer::setProcessPropertyPlaceHolders);
			mapper.from(scanner::getDefaultScope).to(configurer::setDefaultScope);

			return configurer;
		}

	}

	/*
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

	 */

}
