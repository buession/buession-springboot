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
package com.buession.springboot.velocity;

import org.springframework.boot.autoconfigure.template.PathBasedTemplateAvailabilityProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yong.Teng
 */
public class VelocityTemplateAvailabilityProvider extends PathBasedTemplateAvailabilityProvider {

    public VelocityTemplateAvailabilityProvider(){
        super(Constant.CLASS_NAME, VelocityTemplateAvailabilityProperties.class, "spring.velocity");
    }

    static class VelocityTemplateAvailabilityProperties extends TemplateAvailabilityProperties {

        private List<String> resourceLoaderPath = new ArrayList<>(Arrays.asList(Constant.DEFAULT_RESOURCE_LOADER_PATH));

        VelocityTemplateAvailabilityProperties(){
            super(Constant.DEFAULT_PREFIX, Constant.DEFAULT_SUFFIX);
        }

        @Override
        protected List<String> getLoaderPath(){
            return this.resourceLoaderPath;
        }

        public List<String> getResourceLoaderPath(){
            return this.resourceLoaderPath;
        }

        public void setResourceLoaderPath(List<String> resourceLoaderPath){
            this.resourceLoaderPath = resourceLoaderPath;
        }

    }

}