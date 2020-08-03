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
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mybatis.autoconfigure;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Properties;

/**
 * MyBatis 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.mybatis")
public class MybatisProperties {

	/**
	 * 配置文件路径
	 */
	private String configLocation;

	/**
	 * 启动时是否检查 MyBatis XML 文件的存在
	 */
	private boolean checkConfigLocation = false;

	/**
	 * Mapper 路径
	 */
	private String[] mapperLocations;

	/**
	 * MyBatis 映射类型别名包，通过该属性可以给包中的类注册别名，注册后在 Mapper 对应的 XML 文件中可以直接使用类名，而不用使用全限定的类名，即： XML 中调用的时候不用包含包名
	 */
	private String typeAliasesPackage;

	/**
	 * 该配置请和 typeAliasesPackage 一起使用，如果配置了该属性，则仅仅会扫描路径下以该类作为父类的域对象
	 */
	private Class<?> typeAliasesSuperType;

	/**
	 * 类型别名包
	 */
	private Class<?>[] typeAliases;

	/**
	 * TypeHandler 扫描路径，如果配置了该属性，SqlSessionFactoryBean 会把该包下面的类注册为对应的 TypeHandler
	 */
	private String typeHandlersPackage;

	/**
	 * TypeHandler
	 */
	private TypeHandler<?>[] typeHandlers;

	/**
	 * 默认枚举 TypeHandler
	 */
	private Class<? extends TypeHandler> defaultEnumTypeHandler;

	/**
	 * MyBatis 的执行器
	 */
	private ExecutorType executorType;

	/**
	 * 指定外部化 MyBatis Properties 配置，通过该配置可以抽离配置，实现不同环境的配置部署
	 */
	private Properties configurationProperties;

	/**
	 * 原生 MyBatis 所支持的配置
	 * {@link Configuration}
	 */
	@NestedConfigurationProperty
	private Configuration configuration = new Configuration();

	/**
	 * 是否启用快速失败机制
	 */
	private boolean failFast;

	/**
	 * 获取配置文件路径
	 *
	 * @return 配置文件路径
	 */
	public String getConfigLocation(){
		return configLocation;
	}

	/**
	 * 设置配置文件路径
	 *
	 * @param configLocation
	 * 		配置文件路径
	 */
	public void setConfigLocation(String configLocation){
		this.configLocation = configLocation;
	}

	public boolean isCheckConfigLocation(){
		return getCheckConfigLocation();
	}

	public boolean getCheckConfigLocation(){
		return checkConfigLocation;
	}

	public void setCheckConfigLocation(boolean checkConfigLocation){
		this.checkConfigLocation = checkConfigLocation;
	}

	public String[] getMapperLocations(){
		return mapperLocations;
	}

	public void setMapperLocations(String[] mapperLocations){
		this.mapperLocations = mapperLocations;
	}

	public String getTypeAliasesPackage(){
		return typeAliasesPackage;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage){
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public Class<?> getTypeAliasesSuperType(){
		return typeAliasesSuperType;
	}

	public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType){
		this.typeAliasesSuperType = typeAliasesSuperType;
	}

	public Class<?>[] getTypeAliases(){
		return typeAliases;
	}

	public void setTypeAliases(Class<?>[] typeAliases){
		this.typeAliases = typeAliases;
	}

	public String getTypeHandlersPackage(){
		return typeHandlersPackage;
	}

	public void setTypeHandlersPackage(String typeHandlersPackage){
		this.typeHandlersPackage = typeHandlersPackage;
	}

	public TypeHandler<?>[] getTypeHandlers(){
		return typeHandlers;
	}

	public void setTypeHandlers(TypeHandler<?>[] typeHandlers){
		this.typeHandlers = typeHandlers;
	}

	public Class<? extends TypeHandler> getDefaultEnumTypeHandler(){
		return defaultEnumTypeHandler;
	}

	public void setDefaultEnumTypeHandler(Class<? extends TypeHandler> defaultEnumTypeHandler){
		this.defaultEnumTypeHandler = defaultEnumTypeHandler;
	}

	public ExecutorType getExecutorType(){
		return executorType;
	}

	public void setExecutorType(ExecutorType executorType){
		this.executorType = executorType;
	}

	public Properties getConfigurationProperties(){
		return configurationProperties;
	}

	public void setConfigurationProperties(Properties configurationProperties){
		this.configurationProperties = configurationProperties;
	}

	public Configuration getConfiguration(){
		return configuration;
	}

	public void setConfiguration(Configuration configuration){
		this.configuration = configuration;
	}

	public boolean getFailFast(){
		return failFast;
	}

	public void setFailFast(boolean failFast){
		this.failFast = failFast;
	}

	@ConfigurationProperties(prefix = "mybatis")
	@Deprecated
	public final static class DeprecatedMybatisProperties extends MybatisProperties {

		/**
		 * 配置文件路径
		 */
		@Deprecated
		private String configLocation;

		/**
		 * 启动时是否检查 MyBatis XML 文件的存在
		 */
		@Deprecated
		private boolean checkConfigLocation = false;

		/**
		 * Mapper 路径
		 */
		@Deprecated
		private String[] mapperLocations;

		/**
		 * MyBatis 映射类型别名包，通过该属性可以给包中的类注册别名，注册后在 Mapper 对应的 XML 文件中可以直接使用类名，而不用使用全限定的类名，即： XML 中调用的时候不用包含包名
		 */
		@Deprecated
		private String typeAliasesPackage;

		/**
		 * 该配置请和 typeAliasesPackage 一起使用，如果配置了该属性，则仅仅会扫描路径下以该类作为父类的域对象
		 */
		@Deprecated
		private Class<?> typeAliasesSuperType;

		/**
		 * 类型别名包
		 */
		@Deprecated
		private Class<?>[] typeAliases;

		/**
		 * TypeHandler 扫描路径，如果配置了该属性，SqlSessionFactoryBean 会把该包下面的类注册为对应的 TypeHandler
		 */
		@Deprecated
		private String typeHandlersPackage;

		/**
		 * TypeHandler
		 */
		@Deprecated
		private TypeHandler<?>[] typeHandlers;

		/**
		 * 默认枚举 TypeHandler
		 */
		@Deprecated
		private Class<? extends TypeHandler> defaultEnumTypeHandler;

		/**
		 * MyBatis 的执行器
		 */
		@Deprecated
		private ExecutorType executorType;

		/**
		 * 指定外部化 MyBatis Properties 配置，通过该配置可以抽离配置，实现不同环境的配置部署
		 */
		@Deprecated
		private Properties configurationProperties;

		/**
		 * 原生 MyBatis 所支持的配置
		 * {@link Configuration}
		 */
		@Deprecated
		@NestedConfigurationProperty
		private Configuration configuration = new Configuration();

		/**
		 * 是否启用快速失败机制
		 */
		@Deprecated
		private boolean failFast;

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.config-location")
		@Override
		public void setConfigLocation(String configLocation){
			super.setConfigLocation(configLocation);
			this.configLocation = configLocation;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.check-config-location")
		@Override
		public void setCheckConfigLocation(boolean checkConfigLocation){
			super.setCheckConfigLocation(checkConfigLocation);
			this.checkConfigLocation = checkConfigLocation;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.mapper-locations")
		@Override
		public void setMapperLocations(String[] mapperLocations){
			super.setMapperLocations(mapperLocations);
			this.mapperLocations = mapperLocations;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.type-aliases-package")
		@Override
		public void setTypeAliasesPackage(String typeAliasesPackage){
			super.setTypeAliasesPackage(typeAliasesPackage);
			this.typeAliasesPackage = typeAliasesPackage;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.type-aliases-super-type")
		@Override
		public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType){
			super.setTypeAliasesSuperType(typeAliasesSuperType);
			this.typeAliasesSuperType = typeAliasesSuperType;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.type-aliases")
		@Override
		public void setTypeAliases(Class<?>[] typeAliases){
			super.setTypeAliases(typeAliases);
			this.typeAliases = typeAliases;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.type-handlers-package")
		@Override
		public void setTypeHandlersPackage(String typeHandlersPackage){
			super.setTypeHandlersPackage(typeHandlersPackage);
			this.typeHandlersPackage = typeHandlersPackage;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.type-handlers")
		@Override
		public void setTypeHandlers(TypeHandler<?>[] typeHandlers){
			super.setTypeHandlers(typeHandlers);
			this.typeHandlers = typeHandlers;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.default-enum-type-handler")
		@Override
		public void setDefaultEnumTypeHandler(Class<? extends TypeHandler> defaultEnumTypeHandler){
			super.setDefaultEnumTypeHandler(defaultEnumTypeHandler);
			this.defaultEnumTypeHandler = defaultEnumTypeHandler;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.executor-type")
		@Override
		public void setExecutorType(ExecutorType executorType){
			super.setExecutorType(executorType);
			this.executorType = executorType;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.configuration-properties")
		@Override
		public void setConfigurationProperties(Properties configurationProperties){
			super.setConfigurationProperties(configurationProperties);
			this.configurationProperties = configurationProperties;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.configuration")
		@Override
		public void setConfiguration(Configuration configuration){
			super.setConfiguration(configuration);
			this.configuration = configuration;
		}

		@Deprecated
		@DeprecatedConfigurationProperty(reason = "规范命名", replacement = "spring.mybatis.fail-fast")
		@Override
		public void setFailFast(boolean failFast){
			super.setFailFast(failFast);
			this.failFast = failFast;
		}

	}

}
