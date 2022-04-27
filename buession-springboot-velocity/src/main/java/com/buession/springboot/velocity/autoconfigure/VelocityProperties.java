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
	 * 模板前缀
	 */
	private String prefix = DEFAULT_PREFIX;

	/**
	 * 模板后缀
	 */
	private String suffix = DEFAULT_SUFFIX;

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
	 * 宏配置
	 */
	@NestedConfigurationProperty
	private VelocityMacro velocityMacro = new VelocityMacro();

	private boolean preferFileSystemAccess = true;

	public VelocityProperties(){
		super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
	}

	@Override
	public String getPrefix(){
		return prefix;
	}

	@Override
	public void setPrefix(String prefix){
		this.prefix = prefix;
	}

	@Override
	public String getSuffix(){
		return suffix;
	}

	@Override
	public void setSuffix(String suffix){
		this.suffix = suffix;
	}

	public String getDateToolAttribute(){
		return dateToolAttribute;
	}

	public void setDateToolAttribute(String dateToolAttribute){
		this.dateToolAttribute = dateToolAttribute;
	}

	public String getNumberToolAttribute(){
		return numberToolAttribute;
	}

	public void setNumberToolAttribute(String numberToolAttribute){
		this.numberToolAttribute = numberToolAttribute;
	}

	public Map<String, String> getProperties(){
		return properties;
	}

	public void setProperties(Map<String, String> properties){
		this.properties = properties;
	}

	public String getResourceLoaderPath(){
		return resourceLoaderPath;
	}

	public void setResourceLoaderPath(String resourceLoaderPath){
		this.resourceLoaderPath = resourceLoaderPath;
	}

	public String getToolboxConfigLocation(){
		return toolboxConfigLocation;
	}

	public void setToolboxConfigLocation(String toolboxConfigLocation){
		this.toolboxConfigLocation = toolboxConfigLocation;
	}

	public VelocityMacro getVelocityMacro(){
		return velocityMacro;
	}

	public void setVelocityMacro(VelocityMacro velocityMacro){
		this.velocityMacro = velocityMacro;
	}

	public boolean isPreferFileSystemAccess(){
		return getPreferFileSystemAccess();
	}

	public boolean getPreferFileSystemAccess(){
		return preferFileSystemAccess;
	}

	public void setPreferFileSystemAccess(boolean preferFileSystemAccess){
		this.preferFileSystemAccess = preferFileSystemAccess;
	}

	public static class VelocityMacro {

		private String library;

		public String getLibrary(){
			return library;
		}

		public void setLibrary(String library){
			this.library = library;
		}

	}

}