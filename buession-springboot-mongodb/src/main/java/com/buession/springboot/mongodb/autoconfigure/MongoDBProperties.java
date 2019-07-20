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
 * | Copyright @ 2013-2018 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.mongodb.autoconfigure;

import com.mongodb.ReadPreference;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;

/**
 * @author Yong.Teng
 */
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoDBProperties {

    private Class<? extends MongoTypeMapper> typeMapper;

    private String typeKey;

    private Class<ReadPreference> readPreference;

    public Class<? extends MongoTypeMapper> getTypeMapper(){
        return typeMapper;
    }

    public void setTypeMapper(Class<? extends MongoTypeMapper> typeMapper){
        this.typeMapper = typeMapper;
    }

    public String getTypeKey(){
        return typeKey;
    }

    public void setTypeKey(String typeKey){
        this.typeKey = typeKey;
    }

    public Class<ReadPreference> getReadPreference(){
        return readPreference;
    }

    public void setReadPreference(Class<ReadPreference> readPreference){
        this.readPreference = readPreference;
    }
}