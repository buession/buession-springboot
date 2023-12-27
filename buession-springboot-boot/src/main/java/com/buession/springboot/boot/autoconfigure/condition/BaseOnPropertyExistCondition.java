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
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@link Condition} that checks if properties are defined in environment base class.
 *
 * @param <T>
 * 		The bound type
 *
 * @author Yong.Teng
 * @since 2.3.1
 */
public abstract class BaseOnPropertyExistCondition<T> extends SpringBootCondition {

	/**
	 * 属性名称
	 */
	private final String property;

	/**
	 * The item
	 */
	private final String item;

	/**
	 * Source that can be bound by a {@link Binder}.
	 */
	private final Bindable<T> registration;

	/**
	 * The condition
	 */
	private final String condition;

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
	public BaseOnPropertyExistCondition(final String property, final String item, final Bindable<T> registration,
										final String condition) {
		this.property = property;
		this.item = item;
		this.registration = registration;
		this.condition = condition;
	}

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		final ConditionMessage.Builder message = ConditionMessage.forCondition(condition);
		final T registrations = getRegistrations(context.getEnvironment());

		if(registrations == null){
			return ConditionOutcome.noMatch(message.notAvailable(item));
		}

		return ConditionOutcome.match(message.foundExactly(item));
	}

	protected String getProperty() {
		return property;
	}

	protected String getItem() {
		return item;
	}

	protected Bindable<T> getRegistration() {
		return registration;
	}

	protected String getCondition() {
		return condition;
	}

	protected T getRegistrations(final Environment environment) {
		return getRegistrations(environment, null);
	}

	protected T getRegistrations(final Environment environment, final T defaultValue) {
		return Binder.get(environment).bind(property, registration).orElse(defaultValue);
	}

}
