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

import com.buession.core.utils.Assert;
import com.buession.core.validator.Validate;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Yong.Teng
 * @since 2.3.1
 */
@Order(Ordered.HIGHEST_PRECEDENCE + 40)
class OnExistPropertyCondition extends SpringBootCondition {

	@Override
	public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
		final List<AnnotationAttributes> allAnnotationAttributes = annotationAttributesFromMultiValueMap(
				metadata.getAllAnnotationAttributes(ConditionalOnExistProperty.class.getName()));
		final List<ConditionMessage> noMatch = new ArrayList<>();
		final List<ConditionMessage> match = new ArrayList<>();

		for(AnnotationAttributes annotationAttributes : allAnnotationAttributes){
			ConditionOutcome outcome = determineOutcome(annotationAttributes, context.getEnvironment());
			(outcome.isMatch() ? match : noMatch).add(outcome.getConditionMessage());
		}

		return noMatch.isEmpty() ? ConditionOutcome.match(ConditionMessage.of(match)) : ConditionOutcome.noMatch(
				ConditionMessage.of(noMatch));
	}

	private List<AnnotationAttributes> annotationAttributesFromMultiValueMap(
			final MultiValueMap<String, Object> multiValueMap) {
		final List<Map<String, Object>> maps = new ArrayList<>();

		multiValueMap.forEach((key, value)->{
			for(int i = 0, j = value.size(); i < j; i++){
				Map<String, Object> map;
				if(i < maps.size()){
					map = maps.get(i);
				}else{
					map = new HashMap<>(1);
					maps.add(map);
				}
				map.put(key, value.get(i));
			}
		});

		return maps.stream().map(AnnotationAttributes::fromMap).collect(Collectors.toList());
	}

	private static ConditionOutcome determineOutcome(final AnnotationAttributes annotationAttributes,
													 final PropertyResolver resolver) {
		final OnExistPropertyCondition.Spec spec = new OnExistPropertyCondition.Spec(annotationAttributes);
		final List<String> missingProperties = new ArrayList<>();
		final List<String> nonMatchingProperties = new ArrayList<>();

		spec.collectProperties(resolver, missingProperties, nonMatchingProperties);

		ConditionMessage.Builder conditionMessageBuilder =
				ConditionMessage.forCondition(ConditionalOnExistProperty.class, spec);

		if(missingProperties.isEmpty() == false){
			return ConditionOutcome.noMatch(conditionMessageBuilder.didNotFind("property", "properties")
					.items(ConditionMessage.Style.QUOTE, missingProperties));
		}

		if(nonMatchingProperties.isEmpty() == false){
			return ConditionOutcome.noMatch(
					conditionMessageBuilder.found("different value in property", "different value in properties")
							.items(ConditionMessage.Style.QUOTE, nonMatchingProperties));
		}

		return ConditionOutcome.match(conditionMessageBuilder.because("matched"));
	}

	private static class Spec {

		private final String prefix;

		private final String havingValue = "";

		private final String[] names;

		private final boolean matchIfMissing;

		Spec(AnnotationAttributes annotationAttributes) {
			String prefix = annotationAttributes.getString("prefix").trim();

			if(Validate.hasText(prefix) && prefix.endsWith(".") == false){
				prefix = prefix + '.';
			}

			this.prefix = prefix;
			this.names = getNames(annotationAttributes);
			this.matchIfMissing = annotationAttributes.getBoolean("matchIfMissing");
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();

			sb.append('(').append(prefix);
			if(names.length == 1){
				sb.append(names[0]);
			}else{
				sb.append('[').append(StringUtils.arrayToCommaDelimitedString(names)).append(']');
			}
			sb.append(')');

			return sb.toString();
		}

		private static String[] getNames(final AnnotationAttributes annotationAttributes) {
			String[] value = (String[]) annotationAttributes.get("value");
			String[] name = (String[]) annotationAttributes.get("name");

			Assert.isFalse(value.length > 0 || name.length > 0,
					"The name or value attribute of @ConditionalOnExistProperty must be specified");
			Assert.isFalse(value.length == 0 || name.length == 0,
					"The name and value attributes of @ConditionalOnExistProperty are exclusive");

			return value.length > 0 ? value : name;
		}

		private void collectProperties(final PropertyResolver resolver, final List<String> missing,
									   final List<String> nonMatching) {
			for(String name : names){
				String key = prefix + name;
				if(resolver.containsProperty(key)){
					if(isMatch(resolver.getProperty(key), havingValue) == false){
						nonMatching.add(name);
					}
				}else{
					if(matchIfMissing == false){
						missing.add(name);
					}
				}
			}
		}

		private static boolean isMatch(final String value, final String requiredValue) {
			return Validate.isNotBlank(requiredValue) ? requiredValue.equalsIgnoreCase(value) :
					"false".equalsIgnoreCase(value) == false;
		}

	}

}
