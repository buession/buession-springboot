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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yong.Teng
 */
@Configuration
@EnableConfigurationProperties(DataSourceConfigurationProperties.class)
@ConditionalOnClass({javax.sql.DataSource.class, EmbeddedDatabaseType.class})
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

    @Bean(name = "dataSource")
    @ConditionalOnMissingBean
    public DataSource dataSource(){
        logger.info("Create master datasource: by driver {}, type {}", dataSourceConfigurationProperties
                .getDriverClassName(), dataSourceConfigurationProperties.getType().getName());
        javax.sql.DataSource masterDataSource = createDataSource(dataSourceConfigurationProperties.getMaster(),
                dataSourceConfigurationProperties.getType());

        List<DataSourceProperties> slavesDatasourceProperties = dataSourceConfigurationProperties.getSlaves();
        List<javax.sql.DataSource> slavesDtaSources = new ArrayList<>(slavesDatasourceProperties == null ? 1 :
                slavesDatasourceProperties.size());

        if(slavesDatasourceProperties == null){
            javax.sql.DataSource slaveDataSource = createDataSource(dataSourceConfigurationProperties.getMaster(),
                    dataSourceConfigurationProperties.getType());

            slavesDtaSources.add(slaveDataSource);
        }else{
            for(DataSourceProperties dataSourceProperties : slavesDatasourceProperties){
                javax.sql.DataSource slaveDataSource = createDataSource(dataSourceProperties,
                        dataSourceConfigurationProperties.getType());

                slavesDtaSources.add(slaveDataSource);
            }
        }

        logger.info("Create {} size slave datasource: by driver {}, type {}", slavesDtaSources.size(),
                dataSourceConfigurationProperties.getDriverClassName(), dataSourceConfigurationProperties.getType()
                        .getName());

        return new DataSource(masterDataSource, slavesDtaSources);
    }

    protected final static javax.sql.DataSource createDataSource(DataSourceProperties properties, Class<? extends
            javax.sql.DataSource> type){
        return properties.initializeDataSourceBuilder().type(type).build();
    }

}
