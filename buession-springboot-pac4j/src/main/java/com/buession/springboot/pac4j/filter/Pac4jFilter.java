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
 * | Copyright @ 2013-2022 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.filter;

import com.buession.core.utils.Assert;
import com.buession.core.utils.StringUtils;
import com.buession.core.validator.Validate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Pac4j Filter
 *
 * @author Yong.Teng
 * @since 2.1.0
 */
public class Pac4jFilter {

	/**
	 * pac4j 过滤器
	 */
	private final Map<String, Filter> filters = new HashMap<>(3);

	/**
	 * 添加 pac4j 过滤器
	 *
	 * @param name
	 * 		过滤器名称，如果为 null 或者空字符串时，使用过滤器类名首字母小写后，作为过滤器名称
	 * @param filter
	 * 		过滤器
	 */
	public void addFilter(final String name, final Filter filter) {
		Assert.isNull(filter, "Filter cloud not null.");

		final String filterName = Validate.hasText(name) ? name : StringUtils.uncapitalize(
				filter.getClass().getSimpleName());
		filters.put(filterName, filter);
	}

	/**
	 * 获取所有 pac4j 过滤器
	 *
	 * @return pac4j 过滤器
	 */
	public Map<String, Filter> getFilters() {
		return filters;
	}

	/**
	 * 清除所有 pac4j 过滤器
	 */
	public void clear() {
		filters.clear();
	}

}
