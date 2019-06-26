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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.datasource.autoconfigure;

import com.buession.springboot.datasource.core.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yong.Teng
 */
@Configuration
@ConditionalOnBean({DataSourceConfiguration.class, javax.sql.DataSource.class})
@Import({DataSourceConfiguration.class})
@EnableTransactionManagement
public class DataSourceTransactionConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "masterTransactionManager")
    @ConditionalOnBean(name = "masterDataSource")
    @ConditionalOnMissingBean
    public DataSourceTransactionManager masterTransactionManager(){
        return new DataSourceTransactionManager(dataSource.getMaster());
    }

    @Bean(name = "slaveTransactionManagers")
    @ConditionalOnBean(name = "slaveDataSources")
    @ConditionalOnMissingBean
    public List<DataSourceTransactionManager> slaveTransactionManagers(){
        List<DataSourceTransactionManager> slaveTransactionManagers = new ArrayList<>(dataSource.getSlaves().size());

        for(javax.sql.DataSource dataSource : dataSource.getSlaves()){
            slaveTransactionManagers.add(new DataSourceTransactionManager(dataSource));
        }

        return slaveTransactionManagers;
    }

}
