/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2018 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mybatis.autoconfigure;

import com.buession.core.validator.Validate;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "mybatis")
public class MybatisProperties {

    private String configLocation;

    private String[] mapperLocations;

    private String typeAliasesPackage;

    private String typeHandlersPackage;

    private boolean checkConfigLocation = false;

    private ExecutorType executorType;

    private Properties configurationProperties;

    @NestedConfigurationProperty
    private Configuration configuration;

    /**
     * 获取配置文件路径
     *
     * @return 配置文件路径
     */
    public String getConfigLocation(){
        return configLocation;
    }

    /**
     * 设置配置文件路径
     *
     * @param configLocation
     *         配置文件路径
     */
    public void setConfigLocation(String configLocation){
        this.configLocation = configLocation;
    }

    public String[] getMapperLocations(){
        return mapperLocations;
    }

    public void setMapperLocations(String[] mapperLocations){
        this.mapperLocations = mapperLocations;
    }

    public String getTypeHandlersPackage(){
        return typeHandlersPackage;
    }

    public void setTypeHandlersPackage(String typeHandlersPackage){
        this.typeHandlersPackage = typeHandlersPackage;
    }

    public String getTypeAliasesPackage(){
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage){
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public boolean isCheckConfigLocation(){
        return checkConfigLocation;
    }

    public void setCheckConfigLocation(boolean checkConfigLocation){
        this.checkConfigLocation = checkConfigLocation;
    }

    public ExecutorType getExecutorType(){
        return executorType;
    }

    public void setExecutorType(ExecutorType executorType){
        this.executorType = executorType;
    }

    public Properties getConfigurationProperties(){
        return configurationProperties;
    }

    public void setConfigurationProperties(Properties configurationProperties){
        this.configurationProperties = configurationProperties;
    }

    public Configuration getConfiguration(){
        return configuration;
    }

    public void setConfiguration(Configuration configuration){
        this.configuration = configuration;
    }

    public Resource[] resolveMapperLocations(){
        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        ArrayList<Resource> resources = new ArrayList<>();

        if(Validate.isEmpty(mapperLocations) == false){
            for(String mapperLocation : mapperLocations){
                try{
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                }catch(IOException e){
                }
            }
        }

        return resources.toArray(new Resource[resources.size()]);
    }

}
