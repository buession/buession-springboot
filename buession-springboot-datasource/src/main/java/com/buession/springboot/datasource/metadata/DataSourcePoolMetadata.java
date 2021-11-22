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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.datasource.metadata;

import java.util.List;

/**
 * Provides access meta-data that is commonly available from most pooled
 * {@link javax.sql.DataSource} implementations.
 *
 * @author Yong.Teng
 * @since 1.3.2
 */
public class DataSourcePoolMetadata {

	/**
	 * Master 库连接池 Metadata
	 */
	private org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata master;

	/**
	 * Slave 库连接池 Metadata
	 */
	private List<org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata> slaves;

	/**
	 * 获取 Master 库连接池 Metadata
	 *
	 * @return Master 库连接池 Metadata
	 */
	public org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata getMaster(){
		return master;
	}

	/**
	 * 设置 Master 库连接池 Metadata
	 *
	 * @param master
	 * 		Master 库连接池 Metadata
	 */
	public void setMaster(org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata master){
		this.master = master;
	}

	/**
	 * 获取 Slave 库连接池 Metadata
	 *
	 * @return Slave 库连接池 Metadata
	 */
	public List<org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata> getSlaves(){
		return slaves;
	}

	/**
	 * 设置 Slave 库连接池 Metadata
	 *
	 * @param slaves
	 * 		Slave 库连接池 Metadata
	 */
	public void setSlaves(List<org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata> slaves){
		this.slaves = slaves;
	}

}
