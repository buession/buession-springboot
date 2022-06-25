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
package com.buession.springboot.boot;

import com.buession.lang.Gender;
import org.junit.Test;
import org.springframework.boot.context.properties.PropertyMapper;

import java.util.StringJoiner;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public class PropertyMapperTest {

	@Test
	public void test(){

		User a = new User();
		a.setUsername("user");

		User b = new User();

		PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();
		propertyMapper.from(a.getUsername()).to(b::setUsername);
		propertyMapper.from(a.getGender()).to(b::setGender);

		System.out.println(b);

	}

	public final static class User {

		private String username;

		private Gender gender;

		public String getUsername(){
			return username;
		}

		public void setUsername(String username){
			this.username = username;
		}

		public Gender getGender(){
			return gender;
		}

		public void setGender(Gender gender){
			this.gender = gender;
		}

		@Override
		public String toString(){
			return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
					.add("username='" + username + "'")
					.add("gender=" + gender)
					.toString();
		}

	}

}
