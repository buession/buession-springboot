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
package com.buession.springboot.canal;

import java.time.Duration;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
public abstract class BaseInstance {

	/**
	 * 过滤规则
	 */
	private String filter;

	/**
	 * 超时时间
	 */
	private Duration timeout = Duration.ofSeconds(10);

	/**
	 * 批处理大小
	 */
	private int batchSize = 10;

	/**
	 * 返回过滤规则
	 *
	 * @return 过滤规则
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * 设置过滤规则
	 *
	 * @param filter
	 * 		过滤规则
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * 返回超时时间
	 *
	 * @return 超时时间
	 */
	public Duration getTimeout() {
		return timeout;
	}

	/**
	 * 设置超时时间
	 *
	 * @param timeout
	 * 		超时时间
	 */
	public void setTimeout(Duration timeout) {
		this.timeout = timeout;
	}

	/**
	 * 返回批处理大小
	 *
	 * @return 批处理大小
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * 设置批处理大小
	 *
	 * @param batchSize
	 * 		批处理大小
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
