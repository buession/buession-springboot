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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.cache.redis.core;

import com.buession.core.type.TypeReference;
import com.buession.redis.core.operations.BitMapOperations;
import com.buession.redis.core.operations.GeoOperations;
import com.buession.redis.core.operations.HashOperations;
import com.buession.redis.core.operations.KeyOperations;
import com.buession.redis.core.operations.ListOperations;
import com.buession.redis.core.operations.ScriptingOperations;
import com.buession.redis.core.operations.SetOperations;
import com.buession.redis.core.operations.SortedSetOperations;
import com.buession.redis.core.operations.StringOperations;
import com.buession.springboot.cache.core.CacheManager;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Redis 缓存管理器
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public interface RedisCacheManager extends CacheManager, BitMapOperations, GeoOperations, HashOperations, KeyOperations,
		ListOperations, ScriptingOperations, SetOperations, SortedSetOperations, StringOperations {

	/**
	 * 获取 key 指定偏移量上的位
	 *
	 * @param key
	 * 		Key
	 * @param offset
	 * 		偏移量
	 * @param supplier
	 * 		supplier for return bit source
	 *
	 * @return Key 指定偏移量上的位
	 */
	default Boolean getBitComputeIfAbsent(final String key, final long offset, final Supplier<Boolean> supplier) {
		Boolean value = getBit(key, offset);

		if(value == null){
			value = supplier.get();
			if(value != null){
				setBit(key, offset, value);
			}
		}

		return value;
	}

	/**
	 * 获取 key 指定偏移量上的位
	 *
	 * @param key
	 * 		Key
	 * @param offset
	 * 		偏移量
	 * @param supplier
	 * 		supplier for return bit source
	 *
	 * @return Key 指定偏移量上的位
	 */
	default Boolean getBitComputeIfAbsent(final byte[] key, final long offset, final Supplier<Boolean> supplier) {
		Boolean value = getBit(key, offset);

		if(value == null){
			value = supplier.get();
			if(value != null){
				setBit(key, offset, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 *
	 * @return 哈希表中给定域的值，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 */
	default String hGetComputeIfAbsent(final String key, final String field, final Supplier<String> supplier) {
		String value = hGet(key, field);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 *
	 * @return 哈希表中给定域的值，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 */
	default byte[] hGetComputeIfAbsent(final byte[] key, final byte[] field, final Supplier<byte[]> supplier) {
		byte[] value = hGet(key, field);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值，并将值反序列化为对象
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表中给定域的值反序列化后的对象，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 */
	default <V> V hGetObjectComputeIfAbsent(final String key, final String field, final Supplier<V> supplier) {
		V value = hGetObject(key, field);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值，并将值反序列化为对象
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表中给定域的值反序列化后的对象，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 */
	default <V> V hGetObjectComputeIfAbsent(final byte[] key, final byte[] field, final Supplier<V> supplier) {
		V value = hGetObject(key, field);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值，并将值反序列化为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表中给定域的值反序列化后的对象，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 */
	default <V> V hGetObjectComputeIfAbsent(final String key, final String field, final Class<V> clazz,
											final Supplier<V> supplier) {
		V value = hGetObject(key, field, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值，并将值反序列化为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表中给定域的值反序列化后的对象，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 */
	default <V> V hGetObjectComputeIfAbsent(final byte[] key, final byte[] field, final Class<V> clazz,
											final Supplier<V> supplier) {
		V value = hGetObject(key, field, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值，并将值反序列化为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表中给定域的值反序列化后的对象，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 *
	 * @see TypeReference
	 */
	default <V> V hGetObjectComputeIfAbsent(final String key, final String field, final TypeReference<V> type,
											final Supplier<V> supplier) {
		V value = hGetObject(key, field, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表中给定域的值，并将值反序列化为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param field
	 * 		域
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code HGET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表中给定域的值反序列化后的对象，如果给定域不存在于哈希表中，又或者给定的哈希表并不存在，则返回 null
	 *
	 * @see TypeReference
	 */
	default <V> V hGetObjectComputeIfAbsent(final byte[] key, final byte[] field, final TypeReference<V> type,
											final Supplier<V> supplier) {
		V value = hGetObject(key, field, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hSet(key, field, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 *
	 * @return 哈希表 key 中，所有的域和值
	 */
	default Map<String, String> hGetAllComputeIfAbsent(final String key, final Supplier<Map<String, String>> supplier) {
		Map<String, String> value = hGetAll(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hMSet(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 *
	 * @return 哈希表 key 中，所有的域和值
	 */
	default Map<byte[], byte[]> hGetAllComputeIfAbsent(final byte[] key, final Supplier<Map<byte[], byte[]>> supplier) {
		Map<byte[], byte[]> value = hGetAll(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				hMSet(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值，并将值反序列化为对象
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表 key 中，所有的域和值
	 */
	default <V> Map<String, V> hGetAllObjectComputeIfAbsent(final String key, final Supplier<Map<String, V>> supplier) {
		Map<String, V> value = hGetAllObject(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				value.forEach((k, v)->{
					hSet(key, k, v);
				});
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值，并将值反序列化为对象
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表 key 中，所有的域和值
	 */
	default <V> Map<byte[], V> hGetAllObjectComputeIfAbsent(final byte[] key, final Supplier<Map<byte[], V>> supplier) {
		Map<byte[], V> value = hGetAllObject(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				value.forEach((k, v)->{
					hSet(key, k, v);
				});
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值，并将值反序列化为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表 key 中，所有的域和值
	 */
	default <V> Map<String, V> hGetAllObjectComputeIfAbsent(final String key, final Class<V> clazz,
															final Supplier<Map<String, V>> supplier) {
		Map<String, V> value = hGetAllObject(key, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				value.forEach((k, v)->{
					hSet(key, k, v);
				});
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值，并将值反序列化为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表 key 中，所有的域和值
	 */
	default <V> Map<byte[], V> hGetAllObjectComputeIfAbsent(final byte[] key, final Class<V> clazz,
															final Supplier<Map<byte[], V>> supplier) {
		Map<byte[], V> value = hGetAllObject(key, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				value.forEach((k, v)->{
					hSet(key, k, v);
				});
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值，并将值反序列化为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表 key 中，所有的域和值
	 *
	 * @see TypeReference
	 */
	default <V> Map<String, V> hGetAllObjectComputeIfAbsent(final String key, final TypeReference<V> type,
															final Supplier<Map<String, V>> supplier) {
		Map<String, V> value = hGetAllObject(key, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				value.forEach((k, v)->hSet(key, k, v));
			}
		}

		return value;
	}

	/**
	 * 获取哈希表 key 中，所有的域和值，并将值反序列化为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code HGETALL} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 哈希表 key 中，所有的域和值
	 *
	 * @see TypeReference
	 */
	default <V> Map<byte[], V> hGetAllObjectComputeIfAbsent(final byte[] key, final TypeReference<V> type,
															final Supplier<Map<byte[], V>> supplier) {
		Map<byte[], V> value = hGetAllObject(key, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				value.forEach((k, v)->hSet(key, k, v));
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 *
	 * @return 下标为 index 的元素；如果 index 参数的值不在列表的区间范围内，返回 null
	 */
	default String lIndexComputeIfAbsent(final String key, final long index, final Supplier<String> supplier) {
		String value = lIndex(key, index);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 *
	 * @return 下标为 index 的元素；如果 index 参数的值不在列表的区间范围内，返回 null
	 */
	default byte[] lIndexComputeIfAbsent(final byte[] key, final long index, final Supplier<byte[]> supplier) {
		byte[] value = lIndex(key, index);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素，并反序列为对象
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 下标为 index 的元素反序列化后的值；如果 index 参数的值不在列表的区间范围内，返回 null
	 */
	default <V> V lIndexObjectComputeIfAbsent(final String key, final long index, final Supplier<V> supplier) {
		V value = lIndexObject(key, index);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素，并反序列为对象
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 下标为 index 的元素反序列化后的值；如果 index 参数的值不在列表的区间范围内，返回 null
	 */
	default <V> V lIndexObjectComputeIfAbsent(final byte[] key, final long index, final Supplier<V> supplier) {
		V value = lIndexObject(key, index);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素，并反序列为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 下标为 index 的元素反序列化后的值；如果 index 参数的值不在列表的区间范围内，返回 null
	 *
	 * @see java.lang.Class
	 */
	default <V> V lIndexObjectComputeIfAbsent(final String key, final long index, final Class<V> clazz,
											  final Supplier<V> supplier) {
		V value = lIndexObject(key, index, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素，并反序列为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 下标为 index 的元素反序列化后的值；如果 index 参数的值不在列表的区间范围内，返回 null
	 *
	 * @see java.lang.Class
	 */
	default <V> V lIndexObjectComputeIfAbsent(final byte[] key, final long index, final Class<V> clazz,
											  final Supplier<V> supplier) {
		V value = lIndexObject(key, index, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素，并反序列为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 下标为 index 的元素反序列化后的值；如果 index 参数的值不在列表的区间范围内，返回 null
	 *
	 * @see TypeReference
	 */
	default <V> V lIndexObjectComputeIfAbsent(final String key, final long index, final TypeReference<V> type,
											  final Supplier<V> supplier) {
		V value = lIndexObject(key, index, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取列表 key 中，下标为 index 的元素，并反序列为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param index
	 * 		下标
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code LINDEX} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 下标为 index 的元素反序列化后的值；如果 index 参数的值不在列表的区间范围内，返回 null
	 *
	 * @see TypeReference
	 */
	default <V> V lIndexObjectComputeIfAbsent(final byte[] key, final long index, final TypeReference<V> type,
											  final Supplier<V> supplier) {
		V value = lIndexObject(key, index, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				lSet(key, index, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 */
	default String getComputeIfAbsent(final String key, final Supplier<String> supplier) {
		String value = get(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 */
	default byte[] getComputeIfAbsent(final byte[] key, final Supplier<byte[]> supplier) {
		byte[] value = get(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值，并将值反序列化为对象
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 */
	default <V> V getObjectComputeIfAbsent(final String key, final Supplier<V> supplier) {
		V value = getObject(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值，并将值反序列化为对象
	 *
	 * @param key
	 * 		Key
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 */
	default <V> V getObjectComputeIfAbsent(final byte[] key, final Supplier<V> supplier) {
		V value = getObject(key);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值，并将值反序列化为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 */
	default <V> V getObjectComputeIfAbsent(final String key, final Class<V> clazz, final Supplier<V> supplier) {
		V value = getObject(key, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值，并将值反序列化为 clazz 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param clazz
	 * 		值对象类
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 */
	default <V> V getObjectComputeIfAbsent(final byte[] key, final Class<V> clazz, final Supplier<V> supplier) {
		V value = getObject(key, clazz);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值，并将值反序列化为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 *
	 * @see TypeReference
	 */
	default <V> V getObjectComputeIfAbsent(final String key, final TypeReference<V> type, final Supplier<V> supplier) {
		V value = getObject(key, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

	/**
	 * 获取键 key 相关联的字符串值，并将值反序列化为 type 指定的对象
	 *
	 * @param key
	 * 		Key
	 * @param type
	 * 		值类型引用
	 * @param supplier
	 * 		supplier for return {@code GET} source
	 * @param <V>
	 * 		值类型
	 *
	 * @return 如果键 key 不存在，那么返回特殊值 null ；否则，返回键 key 的值；
	 * 如果键 key 的值并非字符串类型，那么抛出异常
	 *
	 * @see TypeReference
	 */
	default <V> V getObjectComputeIfAbsent(final byte[] key, final TypeReference<V> type, final Supplier<V> supplier) {
		V value = getObject(key, type);

		if(value == null){
			value = supplier.get();
			if(value != null){
				set(key, value);
			}
		}

		return value;
	}

}
