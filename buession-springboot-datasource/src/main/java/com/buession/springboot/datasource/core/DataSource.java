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
package com.buession.springboot.datasource.core;

import java.io.Serializable;
import java.util.List;

/**
 * 主从数据源
 *
 * @author Yong.Teng
 */
public class DataSource implements Serializable {

	private final static long serialVersionUID = 1181748178611062130L;

	/**
	 * Master 库数据源
	 */
	private javax.sql.DataSource master;

	/**
	 * Slave 库数据源列表
	 */
	private List<javax.sql.DataSource> slaves;

	/**
	 * 构造函数
	 */
	public DataSource() {
	}

	/**
	 * 构造函数
	 *
	 * @param master
	 * 		Master 库数据源
	 * @param slaves
	 * 		Slave 库数据源列表
	 */
	public DataSource(final javax.sql.DataSource master, final List<javax.sql.DataSource> slaves) {
		this.master = master;
		this.slaves = slaves;
	}

	/**
	 * 返回 Master 库数据源
	 *
	 * @return Master 库数据源
	 */
	public javax.sql.DataSource getMaster() {
		return master;
	}

	/**
	 * 设置 Master 库数据源
	 *
	 * @param master
	 * 		Master 库数据源
	 */
	public void setMaster(javax.sql.DataSource master) {
		this.master = master;
	}

	/**
	 * 返回 Slave 库数据源列表
	 *
	 * @return Slave 库数据源列表
	 */
	public List<javax.sql.DataSource> getSlaves() {
		return slaves;
	}

	/**
	 * 设置 Slave 库数据源列表
	 *
	 * @param slaves
	 * 		Slave 库数据源列表
	 */
	public void setSlaves(List<javax.sql.DataSource> slaves) {
		this.slaves = slaves;
	}

}