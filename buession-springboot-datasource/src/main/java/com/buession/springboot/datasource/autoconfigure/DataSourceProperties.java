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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

    /**
     * Fully qualified name of the connection pool implementation to use. By default, it
     * is auto-detected from the classpath.
     */
    private Class<? extends DataSource> type;

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private String driverClassName;

    @NestedConfigurationProperty
    private org.springframework.boot.autoconfigure.jdbc.DataSourceProperties master = new org.springframework.boot
            .autoconfigure.jdbc.DataSourceProperties();

    private List<org.springframework.boot.autoconfigure.jdbc.DataSourceProperties> slaves = new ArrayList<>();

    public Class<? extends DataSource> getType(){
        return type;
    }

    public void setType(Class<? extends DataSource> type){
        this.type = type;
    }

    /**
     * Return the configured driver or {@code null} if none was configured.
     *
     * @return the configured driver
     */
    public String getDriverClassName(){
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName){
        this.driverClassName = driverClassName;
    }

    public org.springframework.boot.autoconfigure.jdbc.DataSourceProperties getMaster(){
        return master;
    }

    public void setMaster(org.springframework.boot.autoconfigure.jdbc.DataSourceProperties master){
        this.master = master;
    }

    public List<org.springframework.boot.autoconfigure.jdbc.DataSourceProperties> getSlaves(){
        return slaves;
    }

    public void setSlaves(List<org.springframework.boot.autoconfigure.jdbc.DataSourceProperties> slaves){
        this.slaves = slaves;
    }

}