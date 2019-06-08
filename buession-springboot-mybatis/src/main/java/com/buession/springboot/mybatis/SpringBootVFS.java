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
package com.buession.springboot.mybatis;

import org.apache.ibatis.io.VFS;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yong.Teng
 */
public class SpringBootVFS extends VFS {

    private final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver(getClass()
            .getClassLoader());

    public SpringBootVFS(){
    }

    @Override
    public boolean isValid(){
        return true;
    }

    @Override
    protected List<String> list(URL url, String path) throws IOException{
        Resource[] resources = resourceResolver.getResources("classpath*:" + path + "/**/*.class");
        ArrayList<String> resourcePaths = new ArrayList<>(resources.length);

        for(Resource resource : resources){
            resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
        }

        return resourcePaths;
    }

    private static String preserveSubpackageName(URI uri, String rootPath){
        String uriStr = uri.toString();
        int i = uriStr.indexOf(rootPath);
        return uriStr.substring(i);
    }

}
