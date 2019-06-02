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
package com.buession.springboot.cas.autoconfigure;

import org.pac4j.cas.config.CasProtocol;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "cas")
public class CasProperties {

    private CasProtocol protocol = CasProtocol.CAS30;

    private String loginUrl;

    private String prefixUrl;

    private String callbackUrl;

    public CasProtocol getProtocol(){
        return protocol;
    }

    public void setProtocol(final CasProtocol protocol){
        this.protocol = protocol;
    }

    public String getLoginUrl(){
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl){
        this.loginUrl = loginUrl;
    }

    public String getPrefixUrl(){
        return prefixUrl;
    }

    public void setPrefixUrl(String prefixUrl){
        this.prefixUrl = prefixUrl;
    }

    public String getCallbackUrl(){
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl){
        this.callbackUrl = callbackUrl;
    }
}