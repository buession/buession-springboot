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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(DataSourceConfigurationProperties.class)
@ConditionalOnClass({DataSource.class, EmbeddedDatabaseType.class})
@EnableTransactionManagement
public class DataSourceConfiguration implements BeanClassLoaderAware, EnvironmentAware {

    private ClassLoader classLoader;

    private Environment environment;

    @Autowired
    private DataSourceConfigurationProperties dataSourceConfigurationProperties;

    private final static Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

    public ClassLoader getClassLoader(){
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader){
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader){
        setClassLoader(classLoader);
    }

    public Environment getEnvironment(){
        return environment;
    }

    @Override
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    @Bean(name = "masterDataSource")
    @ConditionalOnMissingBean
    public DataSource masterDataSource(){
        logger.info("Create master datasource: by driver {}, type {}", dataSourceConfigurationProperties
                .getDriverClassName(), dataSourceConfigurationProperties.getType().getName());
        return createDataSource(dataSourceConfigurationProperties.getMaster(), dataSourceConfigurationProperties
                .getType());
    }

    @Bean(name = "slaveDataSources")
    @ConfigurationProperties(prefix = "spring.datasource.slaves")
    @ConditionalOnMissingBean
    public List<DataSource> slaveDataSources(){
        List<DataSourceProperties> slavesDatasourceProperties = dataSourceConfigurationProperties.getSlaves();
        List<DataSource> dataSources = new ArrayList<>(slavesDatasourceProperties == null ? 1 :
                slavesDatasourceProperties.size());

        if(slavesDatasourceProperties == null){
            DataSource dataSource = createDataSource(dataSourceConfigurationProperties.getMaster(),
                    dataSourceConfigurationProperties.getType());

            dataSources.add(dataSource);
        }else{
            for(DataSourceProperties dataSourceProperties : slavesDatasourceProperties){
                DataSource dataSource = createDataSource(dataSourceProperties, dataSourceConfigurationProperties
                        .getType());

                dataSources.add(dataSource);
            }
        }

        logger.info("Create {} size slave datasource: by driver {}, type {}", dataSources.size(),
                dataSourceConfigurationProperties.getDriverClassName(), dataSourceConfigurationProperties.getType()
                        .getName());

        return dataSources;
    }

    protected final static DataSource createDataSource(DataSourceProperties properties, Class<? extends DataSource>
            type){
        return properties.initializeDataSourceBuilder().type(type).build();
    }

}
