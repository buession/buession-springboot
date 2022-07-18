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
package com.buession.springboot.velocity.autoconfigure;

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Velocity 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.velocity")
public class VelocityProperties extends AbstractTemplateViewResolverProperties {

	public final static String DEFAULT_RESOURCE_LOADER_PATH = "classpath:/Templates/";

	public final static String DEFAULT_PREFIX = "";

	public final static String DEFAULT_SUFFIX = ".vm";

	/**
	 * 日期时间工具属性
	 */
	private String dateToolAttribute;

	/**
	 * 数字工具属性
	 */
	private String numberToolAttribute;

	/**
	 * Velocity 属性
	 */
	private Map<String, String> properties = new HashMap<>(16, 0.8F);

	/**
	 * 模板目录路径
	 */
	private String resourceLoaderPath = DEFAULT_RESOURCE_LOADER_PATH;

	/**
	 * toolbox 配置文件路径
	 */
	private String toolboxConfigLocation;

	/**
	 * 是否预检文件权限
	 */
	private boolean preferFileSystemAccess = true;

	/**
	 * 宏配置
	 */
	@NestedConfigurationProperty
	private VelocityMacro velocityMacro = new VelocityMacro();

	/**
	 * 构造函数
	 */
	public VelocityProperties(){
		super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
	}

	/**
	 * 返回日期时间工具属性
	 *
	 * @return 日期时间工具属性
	 */
	public String getDateToolAttribute(){
		return dateToolAttribute;
	}

	/**
	 * 设置日期时间工具属性
	 *
	 * @param dateToolAttribute
	 * 		日期时间工具属性
	 */
	public void setDateToolAttribute(String dateToolAttribute){
		this.dateToolAttribute = dateToolAttribute;
	}

	/**
	 * 返回数字工具属性
	 *
	 * @return 数字工具属性
	 */
	public String getNumberToolAttribute(){
		return numberToolAttribute;
	}

	/**
	 * 设置数字工具属性
	 *
	 * @param numberToolAttribute
	 * 		数字工具属性
	 */
	public void setNumberToolAttribute(String numberToolAttribute){
		this.numberToolAttribute = numberToolAttribute;
	}

	/**
	 * 返回 Velocity 属性
	 *
	 * @return Velocity 属性
	 */
	public Map<String, String> getProperties(){
		return properties;
	}

	/**
	 * 设置 Velocity 属性
	 *
	 * @param properties
	 * 		Velocity 属性
	 */
	public void setProperties(Map<String, String> properties){
		this.properties = properties;
	}

	/**
	 * 返回模板目录路径
	 *
	 * @return 模板目录路径
	 */
	public String getResourceLoaderPath(){
		return resourceLoaderPath;
	}

	/**
	 * 设置模板目录路径
	 *
	 * @param resourceLoaderPath
	 * 		模板目录路径
	 */
	public void setResourceLoaderPath(String resourceLoaderPath){
		this.resourceLoaderPath = resourceLoaderPath;
	}

	/**
	 * 返回 toolbox 配置文件路径
	 *
	 * @return toolbox 配置文件路径
	 */
	public String getToolboxConfigLocation(){
		return toolboxConfigLocation;
	}

	/**
	 * 设置 toolbox 配置文件路径
	 *
	 * @param toolboxConfigLocation
	 * 		toolbox 配置文件路径
	 */
	public void setToolboxConfigLocation(String toolboxConfigLocation){
		this.toolboxConfigLocation = toolboxConfigLocation;
	}

	/**
	 * 返回是否预检文件权限
	 *
	 * @return 是否预检文件权限
	 */
	public boolean isPreferFileSystemAccess(){
		return getPreferFileSystemAccess();
	}

	/**
	 * 返回是否预检文件权限
	 *
	 * @return 是否预检文件权限
	 */
	public boolean getPreferFileSystemAccess(){
		return preferFileSystemAccess;
	}

	/**
	 * 设置是否预检文件权限
	 *
	 * @param preferFileSystemAccess
	 * 		是否预检文件权限
	 */
	public void setPreferFileSystemAccess(boolean preferFileSystemAccess){
		this.preferFileSystemAccess = preferFileSystemAccess;
	}

	/**
	 * 返回宏配置
	 *
	 * @return 宏配置
	 */
	public VelocityMacro getVelocityMacro(){
		return velocityMacro;
	}

	/**
	 * 设置宏配置
	 *
	 * @param velocityMacro
	 * 		宏配置
	 */
	public void setVelocityMacro(VelocityMacro velocityMacro){
		this.velocityMacro = velocityMacro;
	}

	/**
	 * 宏配置
	 */
	public static class VelocityMacro {

		/**
		 * 宏文件路径
		 */
		private String library;

		/**
		 * 返回宏文件路径
		 *
		 * @return 宏文件路径
		 */
		public String getLibrary(){
			return library;
		}

		/**
		 * 设置宏文件路径
		 *
		 * @param library
		 * 		宏文件路径
		 */
		public void setLibrary(String library){
			this.library = library;
		}

	}

}