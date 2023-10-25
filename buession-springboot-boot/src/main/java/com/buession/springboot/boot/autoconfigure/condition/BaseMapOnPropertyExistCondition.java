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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.autoconfigure.condition;

import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * {@link Condition} that checks if properties are defined in environment base class.
 *
 * @param <V>
 *        {@link Map} 值类型
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public abstract class BaseMapOnPropertyExistCondition<V> extends BaseOnPropertyExistCondition<Map<String, V>> {

	/**
	 * 构造函数
	 *
	 * @param property
	 * 		属性名称
	 * @param item
	 * 		The item
	 * @param registration
	 * 		Source that can be bound by a {@link Binder}.
	 * @param condition
	 * 		The condition
	 */
	public BaseMapOnPropertyExistCondition(final String property, final String item,
										   final Bindable<Map<String, V>> registration, final String condition) {
		super(property, item, registration, condition);
	}

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		final ConditionMessage.Builder message = ConditionMessage.forCondition(getCondition());
		final Map<String, V> registrations = getRegistrations(context.getEnvironment());

		if(registrations.isEmpty()){
			return ConditionOutcome.noMatch(message.notAvailable(getItem()));
		}

		return ConditionOutcome.match(message.foundExactly(getItem()));
	}

}
