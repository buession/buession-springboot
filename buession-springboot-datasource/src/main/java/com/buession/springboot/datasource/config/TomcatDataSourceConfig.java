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

import java.beans.Statement;
import java.time.Duration;

/**
 * Tomcat 数据源配置
 *
 * @author Yong.Teng
 * @since 3.0.0
 */
public class TomcatDataSourceConfig extends BaseDataSourceConfig {

	/**
	 * JNDI 数据源的名称
	 */
	private String jndiName;

	/**
	 * 是否允许在同一个数据库连接中使用不同的用户名进行身份验证
	 */
	private Boolean alternateUsernameAllowed;

	/**
	 * 连接归还到池时，设置为自动提交
	 */
	private Boolean commitOnReturn;

	/**
	 * 连接归还到池时，是否回滚所有操作
	 */
	private Boolean rollbackOnReturn;

	/**
	 * 实现 {@link org.apache.tomcat.jdbc.pool.Validator} 接口并提供无参构造方法的实现类，用来替代连接验证SQL，对连接进行验证
	 */
	private String validatorClassName;

	/**
	 * 验证连接时间间隔
	 */
	private Duration validationInterval;

	/**
	 * JDBC 拦截器，{@link org.apache.tomcat.jdbc.pool.JdbcInterceptor} 的实现
	 */
	private String jdbcInterceptors;

	/**
	 * 最大连接数，可以在这个池中同一时刻被分配的有效连接数的最大值，如设置为负数，则不限制
	 */
	private Integer maxActive;

	/**
	 * 连接在连接池中最大存在的时间
	 */
	private Duration maxAge;

	/**
	 * 创建连接时测试连接的有效性
	 */
	private Boolean testOnConnect;

	/**
	 * 是否使用一个包装的连接 facade 来管理和处理连接池中的连接
	 */
	private Boolean useDisposableConnectionFacade;

	/**
	 * 标记初始化池时是否忽略连接创建错误
	 */
	private Boolean ignoreExceptionOnPreLoad;

	/**
	 * 连接池的请求排队策略，当为 {@code true} 时，为使用公平的排队策略；当为 {@code false} 时，为不使用公平的排队策略
	 */
	private Boolean fairQueue;

	/**
	 * 是否使用包装的 {@link Statement} 对象
	 */
	private Boolean useStatementFacade;

	/**
	 * 是否移除抛弃的（abandoned）连接，一个连接使用超过了 removeAbandonedTimeout 上限就被视为抛弃的，
	 * 开启该开关可以恢复那些应用没有关闭的连接
	 */
	private Boolean removeAbandoned;

	/**
	 * 连接被认为是可疑的超时时间
	 */
	private Duration suspectTimeout;

	/**
	 * 除非正在使用的连接数超过定义的百分比，
	 * 否则已放弃（超时）的连接不会关闭并报告 abandonWhenPercentageFull；
	 * 该值应介于0到100之间。默认值为0，这表示一旦 removeAbandonedTimeout 达到连接就可以关闭连接
	 */
	private Integer abandonWhenPercentageFull;

	/**
	 * 将此值设置为true可以传播已被中断的线程的中断状态（不清除中断状态）
	 */
	private Boolean propagateInterruptState;

	/**
	 * 将此属性设置为true可以在验证阶段将错误记录到日志文件中
	 */
	private Boolean logValidationErrors;

	/**
	 * 是否使用锁机制来管理连接池中的连接
	 */
	private Boolean useLock;

	/**
	 * 是否使用 {@link #equals()} 方法来比较连接池中的连接
	 */
	private Boolean useEquals;

	/**
	 * 返回 JNDI 数据源的名称
	 *
	 * @return JNDI 数据源的名称
	 */
	public String getJndiName() {
		return jndiName;
	}

	/**
	 * 设置 JNDI 数据源的名称
	 *
	 * @param jndiName
	 * 		JNDI 数据源的名称
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	/**
	 * 返回是否允许在同一个数据库连接中使用不同的用户名进行身份验证
	 *
	 * @return 是否允许在同一个数据库连接中使用不同的用户名进行身份验证
	 */
	public Boolean getAlternateUsernameAllowed() {
		return alternateUsernameAllowed;
	}

	/**
	 * 设置是否允许在同一个数据库连接中使用不同的用户名进行身份验证
	 *
	 * @param alternateUsernameAllowed
	 * 		是否允许在同一个数据库连接中使用不同的用户名进行身份验证
	 */
	public void setAlternateUsernameAllowed(Boolean alternateUsernameAllowed) {
		this.alternateUsernameAllowed = alternateUsernameAllowed;
	}

	/**
	 * 返回连接归还到池时，设置为自动提交
	 *
	 * @return 连接归还到池时，设置为自动提交
	 */
	public Boolean getCommitOnReturn() {
		return commitOnReturn;
	}

	/**
	 * 设置连接归还到池时，是否自动提交
	 *
	 * @param commitOnReturn
	 * 		连接归还到池时，是否自动提交
	 */
	public void setCommitOnReturn(Boolean commitOnReturn) {
		this.commitOnReturn = commitOnReturn;
	}

	/**
	 * 返回连接归还到池时，是否回滚所有操作
	 *
	 * @return 连接归还到池时，是否回滚所有操作
	 */
	public Boolean getRollbackOnReturn() {
		return rollbackOnReturn;
	}

	/**
	 * 设置连接归还到池时，是否回滚所有操作
	 *
	 * @param rollbackOnReturn
	 * 		连接归还到池时，是否回滚所有操作
	 */
	public void setRollbackOnReturn(Boolean rollbackOnReturn) {
		this.rollbackOnReturn = rollbackOnReturn;
	}

	/**
	 * 返回实现 {@link org.apache.tomcat.jdbc.pool.Validator} 接口并提供无参构造方法的实现类，用来替代连接验证SQL，对连接进行验证的类名
	 *
	 * @return 实现 {@link org.apache.tomcat.jdbc.pool.Validator} 接口并提供无参构造方法的实现类，用来替代连接验证SQL，对连接进行验证的类名
	 */
	public String getValidatorClassName() {
		return validatorClassName;
	}

	/**
	 * 设置实现 {@link org.apache.tomcat.jdbc.pool.Validator} 接口并提供无参构造方法的实现类，用来替代连接验证SQL，对连接进行验证的类名
	 *
	 * @param validatorClassName
	 * 		实现 {@link org.apache.tomcat.jdbc.pool.Validator} 接口并提供无参构造方法的实现类，用来替代连接验证SQL，对连接进行验证的类名
	 */
	public void setValidatorClassName(String validatorClassName) {
		this.validatorClassName = validatorClassName;
	}

	/**
	 * 返回验证连接时间间隔
	 *
	 * @return 验证连接时间间隔
	 */
	public Duration getValidationInterval() {
		return validationInterval;
	}

	/**
	 * 设置验证连接时间间隔
	 *
	 * @param validationInterval
	 * 		验证连接时间间隔
	 */
	public void setValidationInterval(Duration validationInterval) {
		this.validationInterval = validationInterval;
	}

	/**
	 * 返回 JDBC 拦截器，{@link org.apache.tomcat.jdbc.pool.JdbcInterceptor} 的实现
	 *
	 * @return JDBC 拦截器
	 */
	public String getJdbcInterceptors() {
		return jdbcInterceptors;
	}

	/**
	 * 设置 JDBC 拦截器，{@link org.apache.tomcat.jdbc.pool.JdbcInterceptor} 的实现
	 *
	 * @param jdbcInterceptors
	 * 		JDBC 拦截器
	 */
	public void setJdbcInterceptors(String jdbcInterceptors) {
		this.jdbcInterceptors = jdbcInterceptors;
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
	 *
	 * @since 3.0.0
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
	 * 返回连接在连接池中最大存在的时间
	 *
	 * @return 连接在连接池中最大存在的时间
	 */
	public Duration getMaxAge() {
		return maxAge;
	}

	/**
	 * 设置连接在连接池中最大存在的时间
	 *
	 * @param maxAge
	 * 		连接在连接池中最大存在的时间
	 */
	public void setMaxAge(Duration maxAge) {
		this.maxAge = maxAge;
	}

	/**
	 * 返回创建连接时是否测试连接的有效性
	 *
	 * @return 创建连接时是否测试连接的有效性
	 */
	public Boolean getTestOnConnect() {
		return testOnConnect;
	}

	/**
	 * 设置创建连接时是否测试连接的有效性
	 *
	 * @param testOnConnect
	 * 		创建连接时是否测试连接的有效性
	 */
	public void setTestOnConnect(Boolean testOnConnect) {
		this.testOnConnect = testOnConnect;
		super.setTestOnCreate(testOnConnect);
	}

	@Override
	public void setTestOnCreate(Boolean testOnCreate) {
		setTestOnConnect(testOnCreate);
	}

	/**
	 * 返回是否使用一个包装的连接 facade 来管理和处理连接池中的连接
	 *
	 * @return 是否使用一个包装的连接 facade 来管理和处理连接池中的连接
	 */
	public Boolean getUseDisposableConnectionFacade() {
		return useDisposableConnectionFacade;
	}

	/**
	 * 设置是否使用一个包装的连接 facade 来管理和处理连接池中的连接
	 *
	 * @param useDisposableConnectionFacade
	 * 		是否使用一个包装的连接 facade 来管理和处理连接池中的连接
	 */
	public void setUseDisposableConnectionFacade(Boolean useDisposableConnectionFacade) {
		this.useDisposableConnectionFacade = useDisposableConnectionFacade;
	}

	/**
	 * 返回标记初始化池时是否忽略连接创建错误
	 *
	 * @return 初始化池时是否忽略连接创建错误
	 */
	public Boolean getIgnoreExceptionOnPreLoad() {
		return ignoreExceptionOnPreLoad;
	}

	/**
	 * 设置标记初始化池时是否忽略连接创建错误；如果要在初始化池时忽略连接创建错误，请设置为true
	 *
	 * @param ignoreExceptionOnPreLoad
	 * 		初始化池时是否忽略连接创建错误
	 */
	public void setIgnoreExceptionOnPreLoad(Boolean ignoreExceptionOnPreLoad) {
		this.ignoreExceptionOnPreLoad = ignoreExceptionOnPreLoad;
	}

	/**
	 * 返回连接池的请求排队策略，当为 {@code true} 时，为使用公平的排队策略；当为 {@code false} 时，为不使用公平的排队策略
	 *
	 * @return 连接池的请求排队策略
	 */
	public Boolean getFairQueue() {
		return fairQueue;
	}

	/**
	 * 设置连接池的请求排队策略，当为 {@code true} 时，为使用公平的排队策略；当为 {@code false} 时，为不使用公平的排队策略
	 *
	 * @param fairQueue
	 * 		连接池的请求排队策略
	 */
	public void setFairQueue(Boolean fairQueue) {
		this.fairQueue = fairQueue;
	}

	/**
	 * 返回使用包装的 {@link Statement} 对象
	 *
	 * @return 使用包装的 {@link Statement} 对象
	 */
	public Boolean getUseStatementFacade() {
		return useStatementFacade;
	}

	/**
	 * 设置是使用包装的 {@link Statement} 对象
	 *
	 * @param useStatementFacade
	 * 		使用包装的 {@link Statement} 对象
	 */
	public void setUseStatementFacade(Boolean useStatementFacade) {
		this.useStatementFacade = useStatementFacade;
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
	 * 连接被认为是可疑的超时时间
	 *
	 * @return 连接被认为是可疑的超时时间
	 */
	public Duration getSuspectTimeout() {
		return suspectTimeout;
	}

	/**
	 * 连接被认为是可疑的超时时间
	 *
	 * @param suspectTimeout
	 * 		连接被认为是可疑的超时时间
	 */
	public void setSuspectTimeout(Duration suspectTimeout) {
		this.suspectTimeout = suspectTimeout;
	}

	/**
	 * 返回正在使用的连接数超过定义的百分比
	 *
	 * @return 正在使用的连接数超过定义的百分比
	 */
	public Integer getAbandonWhenPercentageFull() {
		return abandonWhenPercentageFull;
	}

	/**
	 * 设置正在使用的连接数超过定义的百分比
	 *
	 * @param abandonWhenPercentageFull
	 * 		正在使用的连接数超过定义的百分比
	 */
	public void setAbandonWhenPercentageFull(Integer abandonWhenPercentageFull) {
		this.abandonWhenPercentageFull = abandonWhenPercentageFull;
	}

	/**
	 * 返回是否传播已被中断的线程的中断状态
	 *
	 * @return 是否传播已被中断的线程的中断状态
	 */
	public Boolean getPropagateInterruptState() {
		return propagateInterruptState;
	}

	/**
	 * 设置是否传播已被中断的线程的中断状态
	 *
	 * @param propagateInterruptState
	 * 		是否传播已被中断的线程的中断状态
	 */
	public void setPropagateInterruptState(Boolean propagateInterruptState) {
		this.propagateInterruptState = propagateInterruptState;
	}

	/**
	 * 返回验证阶段将错误记录到日志文件中
	 *
	 * @return 验证阶段将错误记录到日志文件中
	 */
	public Boolean getLogValidationErrors() {
		return logValidationErrors;
	}

	/**
	 * 设置验证阶段将错误记录到日志文件中
	 *
	 * @param logValidationErrors
	 * 		验证阶段将错误记录到日志文件中
	 */
	public void setLogValidationErrors(Boolean logValidationErrors) {
		this.logValidationErrors = logValidationErrors;
	}

	/**
	 * 返回是否使用锁机制来管理连接池中的连接
	 *
	 * @return 是否使用锁机制来管理连接池中的连接
	 */
	public Boolean getUseLock() {
		return useLock;
	}

	/**
	 * 设置是否使用锁机制来管理连接池中的连接
	 *
	 * @param useLock
	 * 		是否使用锁机制来管理连接池中的连接
	 */
	public void setUseLock(Boolean useLock) {
		this.useLock = useLock;
	}

	/**
	 * 返回是否使用 {@link #equals(Object obj)} 方法来比较连接池中的连接
	 *
	 * @return 是否使用 {@link #equals(Object obj)} 方法来比较连接池中的连接
	 */
	public Boolean getUseEquals() {
		return useEquals;
	}

	/**
	 * 设置是否使用 {@link #equals(Object obj)} 方法来比较连接池中的连接
	 *
	 * @param useEquals
	 * 		是否使用 {@link #equals(Object obj)} 方法来比较连接池中的连接
	 */
	public void setUseEquals(Boolean useEquals) {
		this.useEquals = useEquals;
	}

}
