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

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
	 *
	 * @since 1.2.0
	 */
	private Class<? extends TypeHandler> defaultEnumTypeHandler;

	/**
	 * MyBatis 的执行器
	 *
	 * @since 1.2.0
	 */
	private ExecutorType executorType;

	/**
	 * 指定外部化 MyBatis Properties 配置，通过该配置可以抽离配置，实现不同环境的配置部署
	 *
	 * @since 1.2.0
	 */
	private Properties configurationProperties;

	/**
	 * 原生 MyBatis 所支持的配置 {@link Configuration}
	 *
	 * @since 1.2.0
	 */
	private Configuration configuration;

	/**
	 * 是否启用快速失败机制
	 *
	 * @since 1.2.0
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

	/**
	 * 返回默认枚举 TypeHandler
	 *
	 * @return 默认枚举 TypeHandler
	 *
	 * @since 1.2.0
	 */
	public Class<? extends TypeHandler> getDefaultEnumTypeHandler(){
		return defaultEnumTypeHandler;
	}

	/**
	 * 设置默认枚举 TypeHandler
	 *
	 * @param defaultEnumTypeHandler
	 * 		默认枚举 TypeHandler
	 *
	 * @since 1.2.0
	 */
	public void setDefaultEnumTypeHandler(Class<? extends TypeHandler> defaultEnumTypeHandler){
		this.defaultEnumTypeHandler = defaultEnumTypeHandler;
	}

	/**
	 * 返回 MyBatis 的执行器
	 *
	 * @return MyBatis 的执行器
	 *
	 * @since 1.2.0
	 */
	public ExecutorType getExecutorType(){
		return executorType;
	}

	/**
	 * 设置 MyBatis 的执行器
	 *
	 * @param executorType
	 * 		MyBatis 的执行器
	 *
	 * @since 1.2.0
	 */
	public void setExecutorType(ExecutorType executorType){
		this.executorType = executorType;
	}

	/**
	 * 返回指定的外部化 MyBatis Properties 配置
	 *
	 * @return 指定的外部化 MyBatis Properties 配置
	 *
	 * @since 1.2.0
	 */
	public Properties getConfigurationProperties(){
		return configurationProperties;
	}

	/**
	 * 指定外部化 MyBatis Properties 配置，通过该配置可以抽离配置，实现不同环境的配置部署
	 *
	 * @param configurationProperties
	 * 		外部化 MyBatis Properties 配置
	 *
	 * @since 1.2.0
	 */
	public void setConfigurationProperties(Properties configurationProperties){
		this.configurationProperties = configurationProperties;
	}

	/**
	 * 返回原生 MyBatis 所支持的配置 {@link Configuration}
	 *
	 * @return 原生 MyBatis 所支持的配置
	 *
	 * @since 1.2.0
	 */
	public Configuration getConfiguration(){
		return configuration;
	}

	/**
	 * 设置原生 MyBatis 所支持的配置 {@link Configuration}
	 *
	 * @param configuration
	 * 		原生 MyBatis 所支持的配置
	 *
	 * @since 1.2.0
	 */
	public void setConfiguration(Configuration configuration){
		this.configuration = configuration;
	}

	/**
	 * 返回是否启用快速失败机制
	 *
	 * @return 是否启用快速失败机制
	 *
	 * @since 1.2.0
	 */
	public boolean getFailFast(){
		return failFast;
	}

	/**
	 * 配置是否启用快速失败机制
	 *
	 * @param failFast
	 * 		是否启用快速失败机制
	 *
	 * @since 1.2.0
	 */
	public void setFailFast(boolean failFast){
		this.failFast = failFast;
	}

}
