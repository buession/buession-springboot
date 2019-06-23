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
package com.buession.springboot.velocity.autoconfigure;

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "velocity")
public class VelocityProperties extends AbstractTemplateViewResolverProperties {

    public final static String DEFAULT_RESOURCE_LOADER_PATH = "classpath:/Templates/";

    public final static String DEFAULT_PREFIX = "";

    public final static String DEFAULT_SUFFIX = ".vm";

    private String prefix = DEFAULT_PREFIX;

    private String suffix = DEFAULT_SUFFIX;

    private String dateToolAttribute;

    private String numberToolAttribute;

    private Map<String, String> properties = new HashMap<>();

    private String resourceLoaderPath = DEFAULT_RESOURCE_LOADER_PATH;

    private String toolboxConfigLocation;

    @NestedConfigurationProperty
    private VelocityMacroProperties velocityMacro = new VelocityMacroProperties();

    private boolean preferFileSystemAccess = true;

    public VelocityProperties(){
        super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    @Override
    public String getPrefix(){
        return prefix;
    }

    @Override
    public void setPrefix(String prefix){
        this.prefix = prefix;
    }

    @Override
    public String getSuffix(){
        return suffix;
    }

    @Override
    public void setSuffix(String suffix){
        this.suffix = suffix;
    }

    public String getDateToolAttribute(){
        return dateToolAttribute;
    }

    public void setDateToolAttribute(String dateToolAttribute){
        this.dateToolAttribute = dateToolAttribute;
    }

    public String getNumberToolAttribute(){
        return numberToolAttribute;
    }

    public void setNumberToolAttribute(String numberToolAttribute){
        this.numberToolAttribute = numberToolAttribute;
    }

    public Map<String, String> getProperties(){
        return properties;
    }

    public void setProperties(Map<String, String> properties){
        this.properties = properties;
    }

    public String getResourceLoaderPath(){
        return resourceLoaderPath;
    }

    public void setResourceLoaderPath(String resourceLoaderPath){
        this.resourceLoaderPath = resourceLoaderPath;
    }

    public String getToolboxConfigLocation(){
        return toolboxConfigLocation;
    }

    public void setToolboxConfigLocation(String toolboxConfigLocation){
        this.toolboxConfigLocation = toolboxConfigLocation;
    }

    public VelocityMacroProperties getVelocityMacro(){
        return velocityMacro;
    }

    public void setVelocityMacro(VelocityMacroProperties velocityMacro){
        this.velocityMacro = velocityMacro;
    }

    public boolean isPreferFileSystemAccess(){
        return this.preferFileSystemAccess;
    }

    public void setPreferFileSystemAccess(boolean preferFileSystemAccess){
        this.preferFileSystemAccess = preferFileSystemAccess;
    }

}