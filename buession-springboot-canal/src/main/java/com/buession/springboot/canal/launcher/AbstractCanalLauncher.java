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
package com.buession.springboot.canal.launcher;

import com.buession.canal.client.CanalClient;
import com.buession.core.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Canal 客户端启动器抽象类
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public abstract class AbstractCanalLauncher implements CanalLauncher {

	/**
	 * Canal 客户端
	 */
	private final CanalClient canalClient;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 构造函数
	 *
	 * @param canalClient
	 * 		Canal 客户端
	 */
	public AbstractCanalLauncher(final CanalClient canalClient) {
		Assert.isNull(canalClient, "CanalClient cloud not be null.");
		this.canalClient = canalClient;
	}

	@Override
	public void startup() {
		logger.info("CanalClient startup.");
		canalClient.start();
	}

	@Override
	public void close() throws Exception {
		canalClient.stop();
		logger.info("CanalClient close.");
	}

}
