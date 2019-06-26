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
package com.buession.springboot.jwt.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String parameterName;

    private String prefixHeader;

    private String encryptionKey;

    public String getParameterName(){
        return parameterName;
    }

    public void setParameterName(String parameterName){
        this.parameterName = parameterName;
    }

    public String getPrefixHeader(){
        return prefixHeader;
    }

    public void setPrefixHeader(String prefixHeader){
        this.prefixHeader = prefixHeader;
    }

    public String getEncryptionKey(){
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey){
        this.encryptionKey = encryptionKey;
    }


}
