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
package com.buession.springboot.datasource.autoconfigure;

import com.buession.springboot.datasource.core.DataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean({DataSourceConfiguration.class, DataSource.class})
@Import({DataSourceConfiguration.class})
public class DataSourceTransactionConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DataSourceTransactionManager transactionManager(ObjectProvider<DataSource> dataSource,
														   ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers){
		final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(
				dataSource.getIfAvailable().getMaster());

		transactionManagerCustomizers.ifAvailable((customizers)->customizers.customize(transactionManager));

		return transactionManager;
	}

}
