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
package com.buession.springboot.canal.autoconfigure;

import com.buession.canal.core.CanalMode;
import com.buession.springboot.boot.autoconfigure.condition.BaseOnPropertyExistCondition;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
interface ClientConfiguredCondition {

	ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata);

	abstract class AbstractCanalAdapterClientConfiguredCondition<P extends AdapterProperties>
			extends BaseOnPropertyExistCondition<P> implements ClientConfiguredCondition {

		public AbstractCanalAdapterClientConfiguredCondition(final Bindable<Map<String, P>> registrationMap,
															 final CanalMode mode) {
			super(CanalProperties.PREFIX + '.' + mode.getName(), registrationMap);
		}

		@Override
		public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
			ConditionMessage.Builder message = ConditionMessage.forCondition(
					"Canal adapter client Configured Condition");
			Map<String, P> registrations = getRegistrations(context.getEnvironment());

			if(registrations.isEmpty()){
				return ConditionOutcome.noMatch(message.notAvailable("registered canal adapter clients"));
			}

			return ConditionOutcome.match(message.foundExactly("registered canal adapter clients " +
					Arrays.toString(registrations.entrySet().toArray())));
		}

	}

	class KafkaCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<KafkaProperties> {

		private static final Bindable<Map<String, KafkaProperties>> REGISTRATION_MAP = Bindable
				.mapOf(String.class, KafkaProperties.class);

		KafkaCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION_MAP, CanalMode.KAFKA);
		}

	}

	class RabbitCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<RabbitProperties> {

		private static final Bindable<Map<String, RabbitProperties>> REGISTRATION_MAP = Bindable
				.mapOf(String.class, RabbitProperties.class);

		RabbitCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION_MAP, CanalMode.RABBIT_MQ);
		}

	}

	class TcpCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<TcpProperties> {

		private static final Bindable<Map<String, TcpProperties>> REGISTRATION_MAP = Bindable
				.mapOf(String.class, TcpProperties.class);

		TcpCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION_MAP, CanalMode.TCP);
		}

	}

}
