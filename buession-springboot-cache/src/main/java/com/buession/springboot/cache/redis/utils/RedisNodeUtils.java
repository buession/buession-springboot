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
package com.buession.springboot.cache.redis.utils;

import com.buession.core.utils.StringUtils;
import com.buession.redis.core.RedisNode;
import org.springframework.beans.factory.BeanInitializationException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 2.0.0
 */
public class RedisNodeUtils {

	public static RedisNode parse(final String str, final int defaultPort) throws ParseException{
		String[] hostAndPort = StringUtils.split(str, ':');

		if(hostAndPort.length == 1){
			return new RedisNode(hostAndPort[0], defaultPort);
		}else if(hostAndPort.length == 2){
			try{
				return new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1]));
			}catch(Exception e){
				throw new ParseException("Illegal redis host and port: " + str + ".", -1);
			}
		}else{
			throw new ParseException("Illegal redis host and port: " + str + ".", -1);
		}
	}

	public static List<RedisNode> parse(final Collection<String> str, final int defaultPort) throws ParseException{
		List<RedisNode> nodes = new ArrayList<>(str.size());

		for(String s : str){
			nodes.add(parse(s, defaultPort));
		}

		return nodes;
	}

}
