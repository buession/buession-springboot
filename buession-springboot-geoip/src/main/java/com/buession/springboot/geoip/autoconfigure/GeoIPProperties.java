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
package com.buession.springboot.geoip.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * GeoIP 配置
 *
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.geoip")
public class GeoIPProperties {

	/**
	 * 数据库文件路径
	 */
	private String dbPath;

	/**
	 * ASN 库文件路径
	 */
	private String asnDbPath;

	/**
	 * 是否开启缓存
	 */
	private boolean enableCache = true;

	public String getDbPath(){
		return dbPath;
	}

	public void setDbPath(String dbPath){
		this.dbPath = dbPath;
	}

	public String getAsnDbPath(){
		return asnDbPath;
	}

	public void setAsnDbPath(String asnDbPath){
		this.asnDbPath = asnDbPath;
	}

	public boolean isEnableCache(){
		return getEnableCache();
	}

	public boolean getEnableCache(){
		return enableCache;
	}

	public void setEnableCache(boolean enableCache){
		this.enableCache = enableCache;
	}

}
