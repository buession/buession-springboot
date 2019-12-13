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
package com.buession.springboot.shiro.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "shiro")
public class ShiroProperties {

    private String loginUrl;

    private String successUrl;

    private String unauthorizedUrl;

    private String sessionPrefix;

    private int sessionExpire;

    private Set<String> clients;

    private boolean multiProfile;

    private String[] authorizers;

    private LinkedHashMap<String, String> filterChainDefinitions;

    public String getLoginUrl(){
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl){
        this.loginUrl = loginUrl;
    }

    public String getSuccessUrl(){
        return successUrl;
    }

    public void setSuccessUrl(String successUrl){
        this.successUrl = successUrl;
    }

    public String getUnauthorizedUrl(){
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl){
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getSessionPrefix(){
        return sessionPrefix;
    }

    public void setSessionPrefix(String sessionPrefix){
        this.sessionPrefix = sessionPrefix;
    }

    public int getSessionExpire(){
        return sessionExpire;
    }

    public void setSessionExpire(int sessionExpire){
        this.sessionExpire = sessionExpire;
    }

    public Set<String> getClients(){
        return clients;
    }

    public void setClients(Set<String> clients){
        this.clients = clients;
    }

    public boolean isMultiProfile(){
        return getMultiProfile();
    }

    public boolean getMultiProfile(){
        return multiProfile;
    }

    public void setMultiProfile(boolean multiProfile){
        this.multiProfile = multiProfile;
    }

    public String[] getAuthorizers(){
        return authorizers;
    }

    public void setAuthorizers(String[] authorizers){
        this.authorizers = authorizers;
    }

    public LinkedHashMap<String, String> getFilterChainDefinitions(){
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(LinkedHashMap<String, String> filterChainDefinitions){
        this.filterChainDefinitions = filterChainDefinitions;
    }

}
