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
import org.springframework.boot.context.properties.bind.Bindable;

/**
 * @author Yong.Teng
 * @since 0.0.1
 */
interface AdapterClientConfiguredCondition {

	abstract class AbstractCanalAdapterClientConfiguredCondition<P extends AdapterProperties<?
			extends AbstractAdapterProperties.BaseInstanceConfig>>
			extends BaseOnPropertyExistCondition<P> implements AdapterClientConfiguredCondition {

		public AbstractCanalAdapterClientConfiguredCondition(final Bindable<P> registration, final CanalMode mode) {
			super(CanalProperties.PREFIX + '.' + mode.getName(), "registered canal adapter clients", registration,
					"Canal " + mode.getName() + "adapter client configured condition");
		}

	}

	class KafkaCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<KafkaProperties> {

		private final static Bindable<KafkaProperties> REGISTRATION = Bindable.of(KafkaProperties.class);

		KafkaCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION, CanalMode.KAFKA);
		}

	}

	class RabbitCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<RabbitProperties> {

		private final static Bindable<RabbitProperties> REGISTRATION = Bindable.of(RabbitProperties.class);

		RabbitCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION, CanalMode.RABBIT_MQ);
		}

	}

	class PulsarCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<PulsarProperties> {

		private final static Bindable<PulsarProperties> REGISTRATION = Bindable.of(PulsarProperties.class);

		PulsarCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION, CanalMode.PULSAR_MQ);
		}

	}

	class RocketCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<RocketProperties> {

		private final static Bindable<RocketProperties> REGISTRATION = Bindable.of(RocketProperties.class);

		RocketCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION, CanalMode.ROCKET_MQ);
		}

	}

	class TcpCanalAdapterClientConfiguredCondition
			extends AbstractCanalAdapterClientConfiguredCondition<TcpProperties> {

		private final static Bindable<TcpProperties> REGISTRATION = Bindable.of(TcpProperties.class);

		TcpCanalAdapterClientConfiguredCondition() {
			super(REGISTRATION, CanalMode.TCP);
		}

	}

}
