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
package com.buession.springboot.datasource.config;

import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.Set;

/**
 * Druid 数据源配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public class DruidDataSourceConfig extends BaseDataSourceConfig {

	/**
	 * 用户名回调 {@link NameCallback} 类名
	 */
	private String userCallbackClassName;

	/**
	 * 密码回调 {@link PasswordCallback} 类名
	 */
	private String passwordCallbackClassName;

	/**
	 * 连接超时
	 */
	private Duration connectTimeout;

	/**
	 * 网络套接字的读超时
	 */
	private Duration socketTimeout;

	/**
	 * 在发生连接错误后，连接池尝试重新获取连接之间的间隔时间
	 */
	private Duration timeBetweenConnectError;

	/**
	 * 数据库连接发生 Socket 读取超时时，决定是否立即销毁该连接
	 */
	private Boolean killWhenSocketReadTimeout;

	/**
	 * 物理连接的超时时间
	 */
	private Duration phyTimeout;

	/**
	 * 物理数据库连接的最大使用次数，设定每个物理连接在被关闭之前最多可以被重复使用的次数
	 */
	private Long phyMaxUseCount;

	/**
	 * 数据源初始化时是否以异步方式进行
	 */
	private Boolean asyncInit;

	/**
	 * 是否启用特定的变体或选项
	 */
	private Boolean initVariants;

	/**
	 * 在连接池初始化时应用的全局变体或选项
	 */
	private Boolean initGlobalVariants;

	/**
	 * 自定义检查数据库连接的有效性类
	 */
	private String validConnectionCheckerClassName;

	/**
	 * 连接出错重试次数
	 */
	private Integer connectionErrorRetryAttempts;

	/**
	 * 数据源初始化过程中出现异常时，是否抛出异常
	 */
	private Boolean initExceptionThrow;

	/**
	 * 异常分类器 {@link com.alibaba.druid.pool.ExceptionSorter} 名称；
	 * 用于检测和分类数据库连接中的异常，当检测到某些特定类型的异常时，可以根据分类器的判断来决定是否将当前连接标记为不可用，并从连接池中移除。
	 */
	private String exceptionSorterClassName;

	/**
	 * 是否启用 Oracle JDBC 驱动的隐式连接缓存
	 */
	private Boolean useOracleImplicitCache;

	/**
	 * 是否异步关闭连接
	 */
	private Boolean asyncCloseConnectionEnable;

	/**
	 * 在事务中执行的 SQL 查询的超时时间
	 */
	private Duration transactionQueryTimeout;

	/**
	 * 事务的阈值时间
	 */
	private Duration transactionThreshold;

	/**
	 * 控制连接池内部锁机制的公平性。
	 * 当为 {@code true} 时，使用公平锁，公平锁保证线程获取锁的顺序是按照线程请求锁的顺序，即先请求锁的线程会先获得锁；适用于对锁获取顺序有严格要求的场景，但可能会导致较高的上下文切换成本，从而影响性能。
	 * 当为 {@code false} 时，使用非公平锁，非公平锁在获取锁时不考虑线程的请求顺序，可能会导致“线程饥饿”现象，但通常能够提供更好的吞吐量和性能。
	 */
	private Boolean fairLock;

	/**
	 * 数据源初始化过程中，如果发生错误，是否立即停止初始化并抛出异常
	 */
	private Boolean failFast;

	/**
	 * 是否监控执行时间
	 */
	private Boolean checkExecuteTime;

	/**
	 * 是否启用全局数据源统计功能
	 */
	private Boolean useGlobalDataSourceStat;

	/**
	 * SQL 监控日志的记录器名称
	 */
	private String statLoggerClassName;

	/**
	 * SQL 监控统计中 SQL 语句的最大数量；当超过这个数量时，Druid 会自动清理掉最早的一些 SQL 监控信息，以保持在设定的最大值以内；
	 * 这个设置有助于防止在高并发或长时间运行的情况下，SQL 监控信息占用过多的内存。
	 */
	private Integer maxSqlSize;

	/**
	 * 是否启用重置统计
	 */
	private Boolean resetStatEnable;

	/**
	 * 过滤器
	 */
	private Set<String> filters;

	/**
	 * 是否跳过加载 SPI 机制下的 {@link com.alibaba.druid.filter.Filter}
	 */
	private Boolean loadSpifilterSkip;

	/**
	 * 是否允许清除过滤器
	 */
	private Boolean clearFiltersEnable;

	private Boolean enable;

	/**
	 * 最大连接数，可以在这个池中同一时刻被分配的有效连接数的最大值，如设置为负数，则不限制
	 */
	private Integer maxActive;

	/**
	 * 数据库连接池中的空闲连接是否保持存活
	 */
	private Boolean keepAlive;

	/**
	 * 连接池中空闲连接的保活间隔时间，这个属性决定了在连接池中，空闲连接每隔多长时间会被检查并进行保活操作，以确保这些连接在空闲时不会被数据库服务器断开
	 */
	private Duration keepAliveBetweenTime;

	/**
	 * 测试连接是否有效时是否使用 PING 方法
	 */
	private Boolean usePingMethod;

	/**
	 * 连接池在将连接返回到连接池时，是否保留连接的原始事务隔离级别
	 */
	private Boolean keepConnectionUnderlyingTransactionIsolation;

	/**
	 * 连接池中创建连接任务的最大数量
	 */
	private Integer maxCreateTaskCount;

	/**
	 * 连接池中等待数据库连接的最大线程数量
	 */
	private Integer maxWaitThreadCount;

	/**
	 * 在发生致命错误（fatal error）时连接池的最大活动连接数
	 */
	private Integer onFatalErrorMaxActive;

	/**
	 * 在获取连接失败后，是否立即断开（标记为不可用）连接池中的所有连接
	 */
	private Boolean breakAfterAcquireFailure;

	/**
	 * 当连接池未满且没有可用连接时，系统在超时前重试获取连接的次数
	 */
	private Integer notFullTimeoutRetryCount;

	/**
	 * 是否使用本地会话状态；这个配置主要用于处理连接池中的数据库连接在会话级别的状态管理。
	 */
	private Boolean useLocalSessionState;

	/**
	 * 是否缓存 {@link PreparedStatement}，也就是PSCache；
	 * PSCache 对支持游标的数据库性能提升巨大，比如说 oracle，在mysql下建议关闭
	 */
	private Boolean poolPreparedStatements;

	/**
	 * 连接池中是否启用 {@link PreparedStatement} 的共享功能
	 */
	private Boolean sharePreparedStatements;

	/**
	 * 每个数据库连接上可以缓存的 {@link PreparedStatement} 对象的最大数量
	 */
	private Integer maxPoolPreparedStatementPerConnectionSize;

	/**
	 * 最大打开 PSCache 数，在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些
	 */
	private Integer maxOpenPreparedStatements;

	/**
	 * 是否移除抛弃的（abandoned）连接，一个连接使用超过了 removeAbandonedTimeout 上限就被视为抛弃的，
	 * 开启该开关可以恢复那些应用没有关闭的连接
	 */
	private Boolean removeAbandoned;

	/**
	 * 日志统计信息的记录间隔时间
	 */
	private Duration timeBetweenLogStats;

	/**
	 * 是否在连接关闭时记录日志
	 */
	private Boolean dupCloseLogEnable;

	/**
	 * 控制在获取连接和关闭连接时是否记录线程不一致的情况
	 */
	private Boolean logDifferentThread;

	/**
	 * 返回用户名回调 {@link NameCallback} 类名
	 *
	 * @return 用户名回调 {@link NameCallback} 类名
	 */
	public String getUserCallbackClassName() {
		return userCallbackClassName;
	}

	/**
	 * 设置用户名回调 {@link NameCallback} 类名
	 *
	 * @param userCallbackClassName
	 * 		用户名回调 {@link NameCallback} 类名
	 */
	public void setUserCallbackClassName(String userCallbackClassName) {
		this.userCallbackClassName = userCallbackClassName;
	}

	/**
	 * 返回密码回调 {@link PasswordCallback} 类名
	 *
	 * @return 密码回调 {@link PasswordCallback} 类名
	 */
	public String getPasswordCallbackClassName() {
		return passwordCallbackClassName;
	}

	/**
	 * 设置密码回调 {@link PasswordCallback} 类名
	 *
	 * @param passwordCallbackClassName
	 * 		密码回调 {@link PasswordCallback} 类名
	 */
	public void setPasswordCallbackClassName(String passwordCallbackClassName) {
		this.passwordCallbackClassName = passwordCallbackClassName;
	}

	/**
	 * 返回连接超时
	 *
	 * @return 连接超时
	 */
	public Duration getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * 设置连接超时
	 *
	 * @param connectTimeout
	 * 		连接超时
	 */
	public void setConnectTimeout(Duration connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * 返回网络套接字的读超时
	 *
	 * @return 网络套接字的读超时
	 */
	public Duration getSocketTimeout() {
		return socketTimeout;
	}

	/**
	 * 设置网络套接字的读超时
	 *
	 * @param socketTimeout
	 * 		网络套接字的读超时
	 */
	public void setSocketTimeout(Duration socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	/**
	 * 返回在发生连接错误后，连接池尝试重新获取连接之间的间隔时间
	 *
	 * @return 在发生连接错误后，连接池尝试重新获取连接之间的间隔时间
	 */
	public Duration getTimeBetweenConnectError() {
		return timeBetweenConnectError;
	}

	/**
	 * 设置在发生连接错误后，连接池尝试重新获取连接之间的间隔时间
	 *
	 * @param timeBetweenConnectError
	 * 		在发生连接错误后，连接池尝试重新获取连接之间的间隔时间
	 */
	public void setTimeBetweenConnectError(Duration timeBetweenConnectError) {
		this.timeBetweenConnectError = timeBetweenConnectError;
	}

	/**
	 * 返回数据库连接发生 Socket 读取超时时，决定是否立即销毁该连接
	 *
	 * @return 数据库连接发生 Socket 读取超时时，决定是否立即销毁该连接
	 */
	public Boolean getKillWhenSocketReadTimeout() {
		return killWhenSocketReadTimeout;
	}

	/**
	 * 设置数据库连接发生 Socket 读取超时时，决定是否立即销毁该连接
	 *
	 * @param killWhenSocketReadTimeout
	 * 		数据库连接发生 Socket 读取超时时，决定是否立即销毁该连接
	 */
	public void setKillWhenSocketReadTimeout(Boolean killWhenSocketReadTimeout) {
		this.killWhenSocketReadTimeout = killWhenSocketReadTimeout;
	}

	/**
	 * 返回物理连接的超时时间
	 *
	 * @return 物理连接的超时时间
	 */
	public Duration getPhyTimeout() {
		return phyTimeout;
	}

	/**
	 * 设置物理连接的超时时间
	 *
	 * @param phyTimeout
	 * 		物理连接的超时时间
	 */
	public void setPhyTimeout(Duration phyTimeout) {
		this.phyTimeout = phyTimeout;
	}

	/**
	 * 返回物理数据库连接的最大使用次数，设定每个物理连接在被关闭之前最多可以被重复使用的次数
	 *
	 * @return 物理数据库连接的最大使用次数
	 */
	public Long getPhyMaxUseCount() {
		return phyMaxUseCount;
	}

	/**
	 * 设置物理数据库连接的最大使用次数，设定每个物理连接在被关闭之前最多可以被重复使用的次数
	 *
	 * @param phyMaxUseCount
	 * 		物理数据库连接的最大使用次数，设定每个物理连接在被关闭之前最多可以被重复使用的次数
	 */
	public void setPhyMaxUseCount(Long phyMaxUseCount) {
		this.phyMaxUseCount = phyMaxUseCount;
	}

	/**
	 * 返回数据源初始化时是否以异步方式进行
	 *
	 * @return 数据源初始化时是否以异步方式进行
	 */
	public Boolean getAsyncInit() {
		return asyncInit;
	}

	/**
	 * 设置数据源初始化时是否以异步方式进行
	 *
	 * @param asyncInit
	 * 		数据源初始化时是否以异步方式进行
	 */
	public void setAsyncInit(Boolean asyncInit) {
		this.asyncInit = asyncInit;
	}

	/**
	 * 返回是否启用特定的变体或选项
	 *
	 * @return 是否启用特定的变体或选项
	 */
	public Boolean getInitVariants() {
		return initVariants;
	}

	/**
	 * 设置是否启用特定的变体或选项
	 *
	 * @param initVariants
	 * 		是否启用特定的变体或选项
	 */
	public void setInitVariants(Boolean initVariants) {
		this.initVariants = initVariants;
	}

	/**
	 * 返回在连接池初始化时应用的全局变体或选项
	 *
	 * @return 在连接池初始化时应用的全局变体或选项
	 */
	public Boolean getInitGlobalVariants() {
		return initGlobalVariants;
	}

	/**
	 * 设置在连接池初始化时应用的全局变体或选项
	 *
	 * @param initGlobalVariants
	 * 		在连接池初始化时应用的全局变体或选项
	 */
	public void setInitGlobalVariants(Boolean initGlobalVariants) {
		this.initGlobalVariants = initGlobalVariants;
	}

	/**
	 * 返回自定义检查数据库连接的有效性类
	 *
	 * @return 自定义检查数据库连接的有效性类
	 */
	public String getValidConnectionCheckerClassName() {
		return validConnectionCheckerClassName;
	}

	/**
	 * 设置自定义检查数据库连接的有效性类
	 *
	 * @param validConnectionCheckerClassName
	 * 		自定义检查数据库连接的有效性类
	 */
	public void setValidConnectionCheckerClassName(String validConnectionCheckerClassName) {
		this.validConnectionCheckerClassName = validConnectionCheckerClassName;
	}

	/**
	 * 返回连接出错重试次数
	 *
	 * @return 连接出错重试次数
	 */
	public Integer getConnectionErrorRetryAttempts() {
		return connectionErrorRetryAttempts;
	}

	/**
	 * 设置连接出错重试次数
	 *
	 * @param connectionErrorRetryAttempts
	 * 		连接出错重试次数
	 */
	public void setConnectionErrorRetryAttempts(Integer connectionErrorRetryAttempts) {
		this.connectionErrorRetryAttempts = connectionErrorRetryAttempts;
	}

	/**
	 * 返回数据源初始化过程中出现异常时，是否抛出异常
	 *
	 * @return 数据源初始化过程中出现异常时，是否抛出异常
	 */
	public Boolean getInitExceptionThrow() {
		return initExceptionThrow;
	}

	/**
	 * 设置数据源初始化过程中出现异常时，是否抛出异常
	 *
	 * @param initExceptionThrow
	 * 		数据源初始化过程中出现异常时，是否抛出异常
	 */
	public void setInitExceptionThrow(Boolean initExceptionThrow) {
		this.initExceptionThrow = initExceptionThrow;
	}

	/**
	 * 异常分类器 {@link com.alibaba.druid.pool.ExceptionSorter} 名称
	 *
	 * @return 异常分类器 {@link com.alibaba.druid.pool.ExceptionSorter} 名称
	 */
	public String getExceptionSorterClassName() {
		return exceptionSorterClassName;
	}

	/**
	 * 设置异常分类器 {@link com.alibaba.druid.pool.ExceptionSorter} 名称
	 *
	 * @param exceptionSorterClassName
	 * 		异常分类器 {@link com.alibaba.druid.pool.ExceptionSorter} 名称
	 */
	public void setExceptionSorterClassName(String exceptionSorterClassName) {
		this.exceptionSorterClassName = exceptionSorterClassName;
	}

	/**
	 * 返回是否启用 Oracle JDBC 驱动的隐式连接缓存
	 *
	 * @return 是否启用 Oracle JDBC 驱动的隐式连接缓存
	 */
	public Boolean getUseOracleImplicitCache() {
		return useOracleImplicitCache;
	}

	/**
	 * 设置是否启用 Oracle JDBC 驱动的隐式连接缓存
	 *
	 * @param useOracleImplicitCache
	 * 		是否启用 Oracle JDBC 驱动的隐式连接缓存
	 */
	public void setUseOracleImplicitCache(Boolean useOracleImplicitCache) {
		this.useOracleImplicitCache = useOracleImplicitCache;
	}

	/**
	 * 返回是否异步关闭连接
	 *
	 * @return 是否异步关闭连接
	 */
	public Boolean getAsyncCloseConnectionEnable() {
		return asyncCloseConnectionEnable;
	}

	/**
	 * 设置是否异步关闭连接
	 *
	 * @param asyncCloseConnectionEnable
	 * 		是否异步关闭连接
	 */
	public void setAsyncCloseConnectionEnable(Boolean asyncCloseConnectionEnable) {
		this.asyncCloseConnectionEnable = asyncCloseConnectionEnable;
	}

	/**
	 * 返回在事务中执行的 SQL 查询的超时时间
	 *
	 * @return 在事务中执行的 SQL 查询的超时时间
	 */
	public Duration getTransactionQueryTimeout() {
		return transactionQueryTimeout;
	}

	/**
	 * 设置在事务中执行的 SQL 查询的超时时间
	 *
	 * @param transactionQueryTimeout
	 * 		在事务中执行的 SQL 查询的超时时间
	 */
	public void setTransactionQueryTimeout(Duration transactionQueryTimeout) {
		this.transactionQueryTimeout = transactionQueryTimeout;
	}

	/**
	 * 返回事务的阈值时间
	 *
	 * @return 事务的阈值时间
	 */
	public Duration getTransactionThreshold() {
		return transactionThreshold;
	}

	/**
	 * 设置事务的阈值时间
	 *
	 * @param transactionThreshold
	 * 		事务的阈值时间
	 */
	public void setTransactionThreshold(Duration transactionThreshold) {
		this.transactionThreshold = transactionThreshold;
	}

	/**
	 * 返回是否使用公平锁
	 *
	 * @return 是否使用公平锁
	 */
	public Boolean getFairLock() {
		return fairLock;
	}

	/**
	 * 设置是否使用公平锁
	 *
	 * @param fairLock
	 * 		是否使用公平锁
	 */
	public void setFairLock(Boolean fairLock) {
		this.fairLock = fairLock;
	}

	/**
	 * 返回数据源初始化过程中，如果发生错误，是否立即停止初始化并抛出异常
	 *
	 * @return 数据源初始化过程中，如果发生错误，是否立即停止初始化并抛出异常
	 */
	public Boolean getFailFast() {
		return failFast;
	}

	/**
	 * 设置数据源初始化过程中，如果发生错误，是否立即停止初始化并抛出异常
	 *
	 * @param failFast
	 * 		数据源初始化过程中，如果发生错误，是否立即停止初始化并抛出异常
	 */
	public void setFailFast(Boolean failFast) {
		this.failFast = failFast;
	}

	/**
	 * 返回是否监控执行时间
	 *
	 * @return 是否监控执行时间
	 */
	public Boolean getCheckExecuteTime() {
		return checkExecuteTime;
	}

	/**
	 * 设置是否监控执行时间
	 *
	 * @param checkExecuteTime
	 * 		是否监控执行时间
	 */
	public void setCheckExecuteTime(Boolean checkExecuteTime) {
		this.checkExecuteTime = checkExecuteTime;
	}

	/**
	 * 返回是否启用全局数据源统计功能
	 *
	 * @return 是否启用全局数据源统计功能
	 */
	public Boolean getUseGlobalDataSourceStat() {
		return useGlobalDataSourceStat;
	}

	/**
	 * 设置是否启用全局数据源统计功能
	 *
	 * @param useGlobalDataSourceStat
	 * 		是否启用全局数据源统计功能
	 */
	public void setUseGlobalDataSourceStat(Boolean useGlobalDataSourceStat) {
		this.useGlobalDataSourceStat = useGlobalDataSourceStat;
	}

	/**
	 * 返回 SQL 监控日志的记录器名称
	 *
	 * @return SQL 监控日志的记录器名称
	 */
	public String getStatLoggerClassName() {
		return statLoggerClassName;
	}

	/**
	 * 设置 SQL 监控日志的记录器名称
	 *
	 * @param statLoggerClassName
	 * 		SQL 监控日志的记录器名称
	 */
	public void setStatLoggerClassName(String statLoggerClassName) {
		this.statLoggerClassName = statLoggerClassName;
	}

	/**
	 * 返回 SQL 监控统计中 SQL 语句的最大数量
	 *
	 * @return SQL 监控统计中 SQL 语句的最大数量
	 */
	public Integer getMaxSqlSize() {
		return maxSqlSize;
	}

	/**
	 * 设置 SQL 监控统计中 SQL 语句的最大数量
	 *
	 * @param maxSqlSize
	 * 		SQL 监控统计中 SQL 语句的最大数量
	 */
	public void setMaxSqlSize(Integer maxSqlSize) {
		this.maxSqlSize = maxSqlSize;
	}

	/**
	 * 返回是否启用重置统计
	 *
	 * @return 是否启用重置统计
	 */
	public Boolean getResetStatEnable() {
		return resetStatEnable;
	}

	/**
	 * 设置是否启用重置统计
	 *
	 * @param resetStatEnable
	 * 		是否启用重置统计
	 */
	public void setResetStatEnable(Boolean resetStatEnable) {
		this.resetStatEnable = resetStatEnable;
	}

	/**
	 * 返回过滤器
	 *
	 * @return 过滤器
	 */
	public Set<String> getFilters() {
		return filters;
	}

	/**
	 * 设置过滤器
	 *
	 * @param filters
	 * 		过滤器
	 */
	public void setFilters(Set<String> filters) {
		this.filters = filters;
	}

	/**
	 * 返回是否跳过加载 SPI 机制下的 {@link com.alibaba.druid.filter.Filter}
	 *
	 * @return 是否跳过加载 SPI 机制下的 {@link com.alibaba.druid.filter.Filter}
	 */
	public Boolean getLoadSpifilterSkip() {
		return loadSpifilterSkip;
	}

	/**
	 * 设置是否跳过加载 SPI 机制下的 {@link com.alibaba.druid.filter.Filter}
	 *
	 * @param loadSpifilterSkip
	 * 		是否跳过加载 SPI 机制下的 {@link com.alibaba.druid.filter.Filter}
	 */
	public void setLoadSpifilterSkip(Boolean loadSpifilterSkip) {
		this.loadSpifilterSkip = loadSpifilterSkip;
	}

	/**
	 * 返回是否允许清除过滤器
	 *
	 * @return 是否允许清除过滤器
	 */
	public Boolean getClearFiltersEnable() {
		return clearFiltersEnable;
	}

	/**
	 * 设置是否允许清除过滤器
	 *
	 * @param clearFiltersEnable
	 * 		是否允许清除过滤器
	 */
	public void setClearFiltersEnable(Boolean clearFiltersEnable) {
		this.clearFiltersEnable = clearFiltersEnable;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	/**
	 * 返回最大连接数
	 *
	 * @return 最大连接数
	 */
	public Integer getMaxActive() {
		return maxActive;
	}

	/**
	 * 设置最大连接数
	 *
	 * @param maxActive
	 * 		最大连接数
	 */
	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
		super.setMaxTotal(maxActive);
	}

	@Override
	public void setMaxTotal(Integer maxTotal) {
		setMaxActive(maxTotal);
	}

	/**
	 * 返回数据库连接池中的空闲连接是否保持存活
	 *
	 * @return 数据库连接池中的空闲连接是否保持存活
	 *
	 * @since 3.0.0
	 */
	public Boolean getKeepAlive() {
		return keepAlive;
	}

	/**
	 * 设置数据库连接池中的空闲连接是否保持存活
	 *
	 * @param keepAlive
	 * 		数据库连接池中的空闲连接是否保持存活
	 *
	 * @since 3.0.0
	 */
	public void setKeepAlive(Boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	/**
	 * 返回连接池中空闲连接的保活间隔时间
	 *
	 * @return 连接池中空闲连接的保活间隔时间
	 */
	public Duration getKeepAliveBetweenTime() {
		return keepAliveBetweenTime;
	}

	/**
	 * 设置连接池中空闲连接的保活间隔时间
	 *
	 * @param keepAliveBetweenTime
	 * 		连接池中空闲连接的保活间隔时间
	 */
	public void setKeepAliveBetweenTime(Duration keepAliveBetweenTime) {
		this.keepAliveBetweenTime = keepAliveBetweenTime;
	}

	/**
	 * 返回测试连接是否有效时是否使用 PING 方法
	 *
	 * @return 测试连接是否有效时是否使用 PING 方法
	 */
	public Boolean getUsePingMethod() {
		return usePingMethod;
	}

	/**
	 * 设置测试连接是否有效时是否使用 PING 方法
	 *
	 * @param usePingMethod
	 * 		测试连接是否有效时是否使用 PING 方法
	 */
	public void setUsePingMethod(Boolean usePingMethod) {
		this.usePingMethod = usePingMethod;
	}

	/**
	 * 返回连接池在将连接返回到连接池时，是否保留连接的原始事务隔离级别
	 *
	 * @return 连接池在将连接返回到连接池时，是否保留连接的原始事务隔离级别
	 */
	public Boolean getKeepConnectionUnderlyingTransactionIsolation() {
		return keepConnectionUnderlyingTransactionIsolation;
	}

	/**
	 * 设置连接池在将连接返回到连接池时，是否保留连接的原始事务隔离级别
	 *
	 * @param keepConnectionUnderlyingTransactionIsolation
	 * 		连接池在将连接返回到连接池时，是否保留连接的原始事务隔离级别
	 */
	public void setKeepConnectionUnderlyingTransactionIsolation(Boolean keepConnectionUnderlyingTransactionIsolation) {
		this.keepConnectionUnderlyingTransactionIsolation = keepConnectionUnderlyingTransactionIsolation;
	}

	/**
	 * 返回连接池中创建连接任务的最大数量
	 *
	 * @return 连接池中创建连接任务的最大数量
	 */
	public Integer getMaxCreateTaskCount() {
		return maxCreateTaskCount;
	}

	/**
	 * 设置连接池中创建连接任务的最大数量
	 *
	 * @param maxCreateTaskCount
	 * 		连接池中创建连接任务的最大数量
	 */
	public void setMaxCreateTaskCount(Integer maxCreateTaskCount) {
		this.maxCreateTaskCount = maxCreateTaskCount;
	}

	/**
	 * 返回连接池中等待数据库连接的最大线程数量
	 *
	 * @return 连接池中等待数据库连接的最大线程数量
	 */
	public Integer getMaxWaitThreadCount() {
		return maxWaitThreadCount;
	}

	/**
	 * 设置连接池中等待数据库连接的最大线程数量
	 *
	 * @param maxWaitThreadCount
	 * 		连接池中等待数据库连接的最大线程数量
	 */
	public void setMaxWaitThreadCount(Integer maxWaitThreadCount) {
		this.maxWaitThreadCount = maxWaitThreadCount;
	}

	/**
	 * 返回在发生致命错误（fatal error）时连接池的最大活动连接数
	 *
	 * @return 在发生致命错误（fatal error）时连接池的最大活动连接数
	 */
	public Integer getOnFatalErrorMaxActive() {
		return onFatalErrorMaxActive;
	}

	/**
	 * 设置在发生致命错误（fatal error）时连接池的最大活动连接数
	 *
	 * @param onFatalErrorMaxActive
	 * 		在发生致命错误（fatal error）时连接池的最大活动连接数
	 */
	public void setOnFatalErrorMaxActive(Integer onFatalErrorMaxActive) {
		this.onFatalErrorMaxActive = onFatalErrorMaxActive;
	}

	/**
	 * 返回在获取连接失败后，是否立即断开（标记为不可用）连接池中的所有连接
	 *
	 * @return 在获取连接失败后，是否立即断开（标记为不可用）连接池中的所有连接
	 */
	public Boolean getBreakAfterAcquireFailure() {
		return breakAfterAcquireFailure;
	}

	/**
	 * 设置在获取连接失败后，是否立即断开（标记为不可用）连接池中的所有连接
	 *
	 * @param breakAfterAcquireFailure
	 * 		在获取连接失败后，是否立即断开（标记为不可用）连接池中的所有连接
	 */
	public void setBreakAfterAcquireFailure(Boolean breakAfterAcquireFailure) {
		this.breakAfterAcquireFailure = breakAfterAcquireFailure;
	}

	/**
	 * 返回当连接池未满且没有可用连接时，系统在超时前重试获取连接的次数
	 *
	 * @return 当连接池未满且没有可用连接时，系统在超时前重试获取连接的次数
	 */
	public Integer getNotFullTimeoutRetryCount() {
		return notFullTimeoutRetryCount;
	}

	/**
	 * 设置当连接池未满且没有可用连接时，系统在超时前重试获取连接的次数
	 *
	 * @param notFullTimeoutRetryCount
	 * 		当连接池未满且没有可用连接时，系统在超时前重试获取连接的次数
	 */
	public void setNotFullTimeoutRetryCount(Integer notFullTimeoutRetryCount) {
		this.notFullTimeoutRetryCount = notFullTimeoutRetryCount;
	}

	/**
	 * 返回是否使用本地会话状态
	 *
	 * @return 是否使用本地会话状态
	 */
	public Boolean getUseLocalSessionState() {
		return useLocalSessionState;
	}

	/**
	 * 设置是否使用本地会话状态
	 *
	 * @param useLocalSessionState
	 * 		是否使用本地会话状态
	 */
	public void setUseLocalSessionState(Boolean useLocalSessionState) {
		this.useLocalSessionState = useLocalSessionState;
	}

	/**
	 * 返回是否缓存 {@link PreparedStatement}，也就是PSCache
	 *
	 * @return 是否缓存 preparedStatement，也就是PSCache
	 */
	public Boolean getPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	/**
	 * 设置是否缓存 {@link PreparedStatement}，也就是PSCache；
	 * PSCache 对支持游标的数据库性能提升巨大，比如说 oracle，在mysql下建议关闭
	 *
	 * @param poolPreparedStatements
	 * 		是否缓存 preparedStatement
	 */
	public void setPoolPreparedStatements(Boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	/**
	 * 返回连接池中是否启用 {@link PreparedStatement} 的共享功能
	 *
	 * @return 连接池中是否启用 {@link PreparedStatement} 的共享功能
	 */
	public Boolean getSharePreparedStatements() {
		return sharePreparedStatements;
	}

	/**
	 * 设置连接池中是否启用 {@link PreparedStatement} 的共享功能
	 *
	 * @param sharePreparedStatements
	 * 		连接池中是否启用 {@link PreparedStatement} 的共享功能
	 */
	public void setSharePreparedStatements(Boolean sharePreparedStatements) {
		this.sharePreparedStatements = sharePreparedStatements;
	}

	/**
	 * 返回每个数据库连接上可以缓存的 {@link PreparedStatement} 对象的最大数量
	 *
	 * @return 每个数据库连接上可以缓存的 {@link PreparedStatement} 对象的最大数量
	 */
	public Integer getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	/**
	 * 设置每个数据库连接上可以缓存的 {@link PreparedStatement} 对象的最大数量
	 *
	 * @param maxPoolPreparedStatementPerConnectionSize
	 * 		每个数据库连接上可以缓存的 {@link PreparedStatement} 对象的最大数量
	 */
	public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	/**
	 * 返回最大打开 PSCache 数
	 *
	 * @return 最大打开 PSCache 数
	 */
	public Integer getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}

	/**
	 * 设置最大打开 PSCache 数，在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些
	 *
	 * @param maxOpenPreparedStatements
	 * 		最大打开 PSCache 数
	 */
	public void setMaxOpenPreparedStatements(Integer maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}

	/**
	 * 返回是否移除抛弃的（abandoned）连接
	 *
	 * @return 是否移除抛弃的（abandoned）连接
	 */
	public Boolean getRemoveAbandoned() {
		return removeAbandoned;
	}

	/**
	 * 设置是否移除抛弃的（abandoned）连接
	 *
	 * @param removeAbandoned
	 * 		是否移除抛弃的（abandoned）连接
	 */
	public void setRemoveAbandoned(Boolean removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	/**
	 * 返回日志统计信息的记录间隔时间
	 *
	 * @return 日志统计信息的记录间隔时间
	 */
	public Duration getTimeBetweenLogStats() {
		return timeBetweenLogStats;
	}

	/**
	 * 设置日志统计信息的记录间隔时间
	 *
	 * @param timeBetweenLogStats
	 * 		日志统计信息的记录间隔时间
	 */
	public void setTimeBetweenLogStats(Duration timeBetweenLogStats) {
		this.timeBetweenLogStats = timeBetweenLogStats;
	}

	/**
	 * 返回是否在连接关闭时记录日志
	 *
	 * @return 是否在连接关闭时记录日志
	 */
	public Boolean getDupCloseLogEnable() {
		return dupCloseLogEnable;
	}

	/**
	 * 设置是否在连接关闭时记录日志
	 *
	 * @param dupCloseLogEnable
	 * 		是否在连接关闭时记录日志
	 */
	public void setDupCloseLogEnable(Boolean dupCloseLogEnable) {
		this.dupCloseLogEnable = dupCloseLogEnable;
	}

	/**
	 * 返回是否在获取连接和关闭连接时是否记录线程不一致的情况
	 *
	 * @return 是否在获取连接和关闭连接时是否记录线程不一致的情况
	 */
	public Boolean getLogDifferentThread() {
		return logDifferentThread;
	}

	/**
	 * 设置在获取连接和关闭连接时是否记录线程不一致的情况
	 *
	 * @param logDifferentThread
	 * 		是否在获取连接和关闭连接时是否记录线程不一致的情况
	 */
	public void setLogDifferentThread(Boolean logDifferentThread) {
		this.logDifferentThread = logDifferentThread;
	}

}
