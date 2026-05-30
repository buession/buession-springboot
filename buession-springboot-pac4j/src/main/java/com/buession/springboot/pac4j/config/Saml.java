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
 * | Copyright @ 2013-2026 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.pac4j.config;

import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.encryption.Decrypter;
import org.pac4j.core.profile.converter.AttributeConverter;
import org.pac4j.core.util.generator.ValueGenerator;
import org.pac4j.saml.crypto.CredentialProvider;
import org.pac4j.saml.crypto.SAML2SignatureTrustEngineProvider;
import org.pac4j.saml.crypto.SignatureSigningParametersProvider;
import org.pac4j.saml.logout.impl.SAML2LogoutRequestMessageSender;
import org.pac4j.saml.logout.impl.SAML2LogoutValidator;
import org.pac4j.saml.metadata.SAML2MetadataGenerator;
import org.pac4j.saml.metadata.SAML2MetadataResolver;
import org.pac4j.saml.profile.api.SAML2ObjectBuilder;
import org.pac4j.saml.profile.api.SAML2ResponseValidator;
import org.pac4j.saml.replay.ReplayCacheProvider;
import org.pac4j.saml.sso.artifact.SOAPPipelineProvider;
import org.pac4j.saml.store.SAMLMessageStoreFactory;
import org.springframework.core.io.Resource;

import java.time.Duration;
import java.util.List;
import java.util.Map;

/**
 * SAML 配置
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
public class Saml extends BaseClientConfig {

	private String providerName;

	private Duration identityProviderMetadataConnectTimeout;

	private Duration identityProviderMetadataReadTimeout;

	private Resource identityProviderMetadata;

	private Resource serviceProviderMetadata;

	private Class<? extends SAML2MetadataResolver> identityProviderMetadataResolver;

	private List<String> supportedProtocols;

	private String attributeAsId;

	private String nameIdAttribute;

	private Map<String, String> mappedAttributes;

	private String singleSignOutServiceUrl;

	private String requestInitiatorUrl;

	private String assertionConsumerServiceUrl;

	private String identityProviderEntityId;

	private String serviceProviderEntityId;

	private Long maximumAuthenticationLifetime;

	private Long acceptedSkew;

	private Boolean forceAuth;

	private Boolean passive;

	private Boolean partialLogoutTreatedAsSuccess;

	private String comparisonType;

	private String authnRequestBindingType;

	private String authnRequestSubjectNameId;

	private String authnRequestSubjectNameIdFormat;

	private List<String> authnContextClassRefs;

	private Boolean authnRequestSigned;

	private Class<? extends SAML2ObjectBuilder<AuthnRequest>> authnRequestBuilder;

	private String responseBindingType;

	private String spLogoutRequestBindingType;

	private String spLogoutResponseBindingType;

	private Boolean spLogoutRequestSigned;

	private String nameIdPolicyFormat;

	private Boolean useNameQualifier;

	private Boolean signMetadata;

	private Boolean forceServiceProviderMetadataGeneration;

	private Boolean nameIdPolicyAllowCreate;

	private Class<? extends AttributeConverter> attributeConverter;

	private Class<? extends SAMLMessageStoreFactory> messageStoreFactory;

	private Class<? extends SAML2MetadataGenerator> metadataGenerator;

	private Class<? extends CredentialProvider> credentialProvider;

	private List<String> blackListedSignatureSigningAlgorithms;

	private List<String> signatureAlgorithms;

	private List<String> signatureReferenceDigestMethods;

	private String signatureCanonicalizationAlgorithm;

	private Class<? extends SignatureSigningParametersProvider> signatureSigningParametersProvider;

	private Class<? extends SAML2ResponseValidator> authnResponseValidator;

	private Class<? extends SAML2LogoutValidator> logoutValidator;

	private Class<? extends SAML2SignatureTrustEngineProvider> signatureTrustEngineProvider;

	private Class<? extends SAML2MetadataResolver> serviceProviderMetadataResolver;

	private Class<? extends SAML2MetadataResolver> defaultIdentityProviderMetadataResolver;

	private Boolean wantsAssertionsSigned;

	private Boolean wantsResponsesSigned;

	private Boolean allSignatureValidationDisabled;

	private Boolean responseDestinationAttributeMandatory;

	private int assertionConsumerServiceIndex;

	private int attributeConsumingServiceIndex;

	private String postLogoutUrl;

	private String issuerFormat;

	private Class<? extends Decrypter> decrypter;

	private Class<? extends ValueGenerator> stateGenerator;

	private Class<? extends ReplayCacheProvider> replayCache;

	private Class<? extends SOAPPipelineProvider> soapPipelineProvider;

	private Class<? extends SAML2LogoutRequestMessageSender> logoutRequestMessageSender;

	public Saml() {
		super("saml");
	}

	/**
	 * 返回 Provider 名称
	 *
	 * @return Provider 名称
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * 设置 Provider 名称
	 *
	 * @param providerName
	 * 		Provider 名称
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * 返回 Identity Provider 元数据连接超时时间
	 *
	 * @return Identity Provider 元数据连接超时时间
	 */
	public Duration getIdentityProviderMetadataConnectTimeout() {
		return identityProviderMetadataConnectTimeout;
	}

	/**
	 * 设置 Identity Provider 元数据连接超时时间
	 *
	 * @param identityProviderMetadataConnectTimeout
	 * 		Identity Provider 元数据连接超时时间
	 */
	public void setIdentityProviderMetadataConnectTimeout(Duration identityProviderMetadataConnectTimeout) {
		this.identityProviderMetadataConnectTimeout = identityProviderMetadataConnectTimeout;
	}

	/**
	 * 返回 Identity Provider 元数据读取超时时间
	 *
	 * @return Identity Provider 元数据读取超时时间
	 */
	public Duration getIdentityProviderMetadataReadTimeout() {
		return identityProviderMetadataReadTimeout;
	}

	/**
	 * 设置 Identity Provider 元数据读取超时时间
	 *
	 * @param identityProviderMetadataReadTimeout
	 * 		Identity Provider 元数据读取超时时间
	 */
	public void setIdentityProviderMetadataReadTimeout(Duration identityProviderMetadataReadTimeout) {
		this.identityProviderMetadataReadTimeout = identityProviderMetadataReadTimeout;
	}

	/**
	 * 返回 Identity Provider 元数据路径
	 *
	 * @return Identity Provider 元数据路径
	 */
	public Resource getIdentityProviderMetadata() {
		return identityProviderMetadata;
	}

	/**
	 * 设置 Identity Provider 元数据路径
	 *
	 * @param identityProviderMetadata
	 * 		Identity Provider 元数据路径
	 */
	public void setIdentityProviderMetadata(Resource identityProviderMetadata) {
		this.identityProviderMetadata = identityProviderMetadata;
	}

	/**
	 * 返回 Service Provider 元数据资源
	 *
	 * @return Service Provider 元数据资源
	 */
	public Resource getServiceProviderMetadata() {
		return serviceProviderMetadata;
	}

	/**
	 * 设置 Service Provider 元数据资源
	 *
	 * @param serviceProviderMetadata
	 * 		Service Provider 元数据资源
	 */
	public void setServiceProviderMetadata(Resource serviceProviderMetadata) {
		this.serviceProviderMetadata = serviceProviderMetadata;
	}

	/**
	 * 返回 Identity Provider 元数据解析器
	 *
	 * @return Identity Provider 元数据解析器
	 */
	public Class<? extends SAML2MetadataResolver> getIdentityProviderMetadataResolver() {
		return identityProviderMetadataResolver;
	}

	/**
	 * 设置 Identity Provider 元数据解析器
	 *
	 * @param identityProviderMetadataResolver
	 * 		Identity Provider 元数据解析器
	 */
	public void setIdentityProviderMetadataResolver(
			Class<? extends SAML2MetadataResolver> identityProviderMetadataResolver) {
		this.identityProviderMetadataResolver = identityProviderMetadataResolver;
	}

	/**
	 * 返回支持的协议列表
	 *
	 * @return 支持的协议列表
	 */
	public List<String> getSupportedProtocols() {
		return supportedProtocols;
	}

	/**
	 * 设置支持的协议列表
	 *
	 * @param supportedProtocols
	 * 		支持的协议列表
	 */
	public void setSupportedProtocols(List<String> supportedProtocols) {
		this.supportedProtocols = supportedProtocols;
	}

	/**
	 * 返回作为 ID 的属性
	 *
	 * @return 作为 ID 的属性
	 */
	public String getAttributeAsId() {
		return attributeAsId;
	}

	/**
	 * 设置作为 ID 的属性
	 *
	 * @param attributeAsId
	 * 		作为 ID 的属性
	 */
	public void setAttributeAsId(String attributeAsId) {
		this.attributeAsId = attributeAsId;
	}

	/**
	 * 返回 Name ID 属性
	 *
	 * @return Name ID 属性
	 */
	public String getNameIdAttribute() {
		return nameIdAttribute;
	}

	/**
	 * 设置 Name ID 属性
	 *
	 * @param nameIdAttribute
	 * 		Name ID 属性
	 */
	public void setNameIdAttribute(String nameIdAttribute) {
		this.nameIdAttribute = nameIdAttribute;
	}

	/**
	 * 返回映射属性
	 *
	 * @return 映射属性
	 */
	public Map<String, String> getMappedAttributes() {
		return mappedAttributes;
	}

	/**
	 * 设置映射属性
	 *
	 * @param mappedAttributes
	 * 		映射属性
	 */
	public void setMappedAttributes(Map<String, String> mappedAttributes) {
		this.mappedAttributes = mappedAttributes;
	}

	/**
	 * 返回 Single Sign Out 服务地址
	 *
	 * @return Single Sign Out 服务地址
	 */
	public String getSingleSignOutServiceUrl() {
		return singleSignOutServiceUrl;
	}

	/**
	 * 设置 Single Sign Out 服务地址
	 *
	 * @param singleSignOutServiceUrl
	 * 		Single Sign Out 服务地址
	 */
	public void setSingleSignOutServiceUrl(String singleSignOutServiceUrl) {
		this.singleSignOutServiceUrl = singleSignOutServiceUrl;
	}

	/**
	 * 返回请求发起者地址
	 *
	 * @return 请求发起者地址
	 */
	public String getRequestInitiatorUrl() {
		return requestInitiatorUrl;
	}

	/**
	 * 设置请求发起者地址
	 *
	 * @param requestInitiatorUrl
	 * 		请求发起者地址
	 */
	public void setRequestInitiatorUrl(String requestInitiatorUrl) {
		this.requestInitiatorUrl = requestInitiatorUrl;
	}

	/**
	 * 返回断言消费者服务地址
	 *
	 * @return 断言消费者服务地址
	 */
	public String getAssertionConsumerServiceUrl() {
		return assertionConsumerServiceUrl;
	}

	/**
	 * 设置断言消费者服务地址
	 *
	 * @param assertionConsumerServiceUrl
	 * 		断言消费者服务地址
	 */
	public void setAssertionConsumerServiceUrl(String assertionConsumerServiceUrl) {
		this.assertionConsumerServiceUrl = assertionConsumerServiceUrl;
	}

	/**
	 * 返回 Identity Provider Entity ID
	 *
	 * @return Identity Provider Entity ID
	 */
	public String getIdentityProviderEntityId() {
		return identityProviderEntityId;
	}

	/**
	 * 设置 Identity Provider Entity ID
	 *
	 * @param identityProviderEntityId
	 * 		Identity Provider Entity ID
	 */
	public void setIdentityProviderEntityId(String identityProviderEntityId) {
		this.identityProviderEntityId = identityProviderEntityId;
	}

	/**
	 * 返回 Service Provider Entity ID
	 *
	 * @return Service Provider Entity ID
	 */
	public String getServiceProviderEntityId() {
		return serviceProviderEntityId;
	}

	/**
	 * 设置 Service Provider Entity ID
	 *
	 * @param serviceProviderEntityId
	 * 		Service Provider Entity ID
	 */
	public void setServiceProviderEntityId(String serviceProviderEntityId) {
		this.serviceProviderEntityId = serviceProviderEntityId;
	}

	/**
	 * 返回最大认证生命周期
	 *
	 * @return 最大认证生命周期
	 */
	public Long getMaximumAuthenticationLifetime() {
		return maximumAuthenticationLifetime;
	}

	/**
	 * 设置最大认证生命周期
	 *
	 * @param maximumAuthenticationLifetime
	 * 		最大认证生命周期
	 */
	public void setMaximumAuthenticationLifetime(Long maximumAuthenticationLifetime) {
		this.maximumAuthenticationLifetime = maximumAuthenticationLifetime;
	}

	/**
	 * 返回可接受的时间偏差
	 *
	 * @return 可接受的时间偏差
	 */
	public Long getAcceptedSkew() {
		return acceptedSkew;
	}

	/**
	 * 设置可接受的时间偏差
	 *
	 * @param acceptedSkew
	 * 		可接受的时间偏差
	 */
	public void setAcceptedSkew(Long acceptedSkew) {
		this.acceptedSkew = acceptedSkew;
	}

	/**
	 * 返回是否强制认证
	 *
	 * @return 是否强制认证
	 */
	public Boolean isForceAuth() {
		return getForceAuth();
	}

	/**
	 * 返回是否强制认证
	 *
	 * @return 是否强制认证
	 */
	public Boolean getForceAuth() {
		return forceAuth;
	}

	/**
	 * 设置是否强制认证
	 *
	 * @param forceAuth
	 * 		是否强制认证
	 */
	public void setForceAuth(Boolean forceAuth) {
		this.forceAuth = forceAuth;
	}

	/**
	 * 返回是否被动认证
	 *
	 * @return 是否被动认证
	 */
	public Boolean isPassive() {
		return getPassive();
	}

	/**
	 * 返回是否被动认证
	 *
	 * @return 是否被动认证
	 */
	public Boolean getPassive() {
		return passive;
	}

	/**
	 * 设置是否被动认证
	 *
	 * @param passive
	 * 		是否被动认证
	 */
	public void setPassive(Boolean passive) {
		this.passive = passive;
	}

	/**
	 * 返回部分退出登录是否视为成功
	 *
	 * @return 部分退出登录是否视为成功
	 */
	public Boolean isPartialLogoutTreatedAsSuccess() {
		return getPartialLogoutTreatedAsSuccess();
	}

	/**
	 * 返回部分退出登录是否视为成功
	 *
	 * @return 部分退出登录是否视为成功
	 */
	public Boolean getPartialLogoutTreatedAsSuccess() {
		return partialLogoutTreatedAsSuccess;
	}

	/**
	 * 设置部分退出登录是否视为成功
	 *
	 * @param partialLogoutTreatedAsSuccess
	 * 		部分退出登录是否视为成功
	 */
	public void setPartialLogoutTreatedAsSuccess(Boolean partialLogoutTreatedAsSuccess) {
		this.partialLogoutTreatedAsSuccess = partialLogoutTreatedAsSuccess;
	}

	/**
	 * 返回比较类型
	 *
	 * @return 比较类型
	 */
	public String getComparisonType() {
		return comparisonType;
	}

	/**
	 * 设置比较类型
	 *
	 * @param comparisonType
	 * 		比较类型
	 */
	public void setComparisonType(String comparisonType) {
		this.comparisonType = comparisonType;
	}

	/**
	 * 返回认证请求绑定类型
	 *
	 * @return 认证请求绑定类型
	 */
	public String getAuthnRequestBindingType() {
		return authnRequestBindingType;
	}

	/**
	 * 设置认证请求绑定类型
	 *
	 * @param authnRequestBindingType
	 * 		认证请求绑定类型
	 */
	public void setAuthnRequestBindingType(String authnRequestBindingType) {
		this.authnRequestBindingType = authnRequestBindingType;
	}

	/**
	 * 返回认证请求 Subject Name ID
	 *
	 * @return 认证请求 Subject Name ID
	 */
	public String getAuthnRequestSubjectNameId() {
		return authnRequestSubjectNameId;
	}

	/**
	 * 设置认证请求 Subject Name ID
	 *
	 * @param authnRequestSubjectNameId
	 * 		认证请求 Subject Name ID
	 */
	public void setAuthnRequestSubjectNameId(String authnRequestSubjectNameId) {
		this.authnRequestSubjectNameId = authnRequestSubjectNameId;
	}

	/**
	 * 返回认证请求 Subject Name ID 格式
	 *
	 * @return 认证请求 Subject Name ID 格式
	 */
	public String getAuthnRequestSubjectNameIdFormat() {
		return authnRequestSubjectNameIdFormat;
	}

	/**
	 * 设置认证请求 Subject Name ID 格式
	 *
	 * @param authnRequestSubjectNameIdFormat
	 * 		认证请求 Subject Name ID 格式
	 */
	public void setAuthnRequestSubjectNameIdFormat(String authnRequestSubjectNameIdFormat) {
		this.authnRequestSubjectNameIdFormat = authnRequestSubjectNameIdFormat;
	}

	/**
	 * 返回认证上下文类引用列表
	 *
	 * @return 认证上下文类引用列表
	 */
	public List<String> getAuthnContextClassRefs() {
		return authnContextClassRefs;
	}

	/**
	 * 设置认证上下文类引用列表
	 *
	 * @param authnContextClassRefs
	 * 		认证上下文类引用列表
	 */
	public void setAuthnContextClassRefs(List<String> authnContextClassRefs) {
		this.authnContextClassRefs = authnContextClassRefs;
	}

	/**
	 * 返回认证请求是否签名
	 *
	 * @return 认证请求是否签名
	 */
	public Boolean isAuthnRequestSigned() {
		return getAuthnRequestSigned();
	}

	/**
	 * 返回认证请求是否签名
	 *
	 * @return 认证请求是否签名
	 */
	public Boolean getAuthnRequestSigned() {
		return authnRequestSigned;
	}

	/**
	 * 设置认证请求是否签名
	 *
	 * @param authnRequestSigned
	 * 		认证请求是否签名
	 */
	public void setAuthnRequestSigned(Boolean authnRequestSigned) {
		this.authnRequestSigned = authnRequestSigned;
	}

	/**
	 * 返回认证请求构建器
	 *
	 * @return 认证请求构建器
	 */
	public Class<? extends SAML2ObjectBuilder<AuthnRequest>> getAuthnRequestBuilder() {
		return authnRequestBuilder;
	}

	/**
	 * 设置认证请求构建器
	 *
	 * @param authnRequestBuilder
	 * 		认证请求构建器
	 */
	public void setAuthnRequestBuilder(
			Class<? extends SAML2ObjectBuilder<AuthnRequest>> authnRequestBuilder) {
		this.authnRequestBuilder = authnRequestBuilder;
	}

	/**
	 * 返回响应绑定类型
	 *
	 * @return 响应绑定类型
	 */
	public String getResponseBindingType() {
		return responseBindingType;
	}

	/**
	 * 设置响应绑定类型
	 *
	 * @param responseBindingType
	 * 		响应绑定类型
	 */
	public void setResponseBindingType(String responseBindingType) {
		this.responseBindingType = responseBindingType;
	}

	/**
	 * 返回 SP 退出登录请求绑定类型
	 *
	 * @return SP 退出登录请求绑定类型
	 */
	public String getSpLogoutRequestBindingType() {
		return spLogoutRequestBindingType;
	}

	/**
	 * 设置 SP 退出登录请求绑定类型
	 *
	 * @param spLogoutRequestBindingType
	 * 		SP 退出登录请求绑定类型
	 */
	public void setSpLogoutRequestBindingType(String spLogoutRequestBindingType) {
		this.spLogoutRequestBindingType = spLogoutRequestBindingType;
	}

	/**
	 * 返回 SP 退出登录响应绑定类型
	 *
	 * @return SP 退出登录响应绑定类型
	 */
	public String getSpLogoutResponseBindingType() {
		return spLogoutResponseBindingType;
	}

	/**
	 * 设置 SP 退出登录响应绑定类型
	 *
	 * @param spLogoutResponseBindingType
	 * 		SP 退出登录响应绑定类型
	 */
	public void setSpLogoutResponseBindingType(String spLogoutResponseBindingType) {
		this.spLogoutResponseBindingType = spLogoutResponseBindingType;
	}

	/**
	 * 返回 SP 退出登录请求是否签名
	 *
	 * @return SP 退出登录请求是否签名
	 */
	public Boolean isSpLogoutRequestSigned() {
		return getSpLogoutRequestSigned();
	}

	/**
	 * 返回 SP 退出登录请求是否签名
	 *
	 * @return SP 退出登录请求是否签名
	 */
	public Boolean getSpLogoutRequestSigned() {
		return spLogoutRequestSigned;
	}

	/**
	 * 设置 SP 退出登录请求是否签名
	 *
	 * @param spLogoutRequestSigned
	 * 		SP 退出登录请求是否签名
	 */
	public void setSpLogoutRequestSigned(Boolean spLogoutRequestSigned) {
		this.spLogoutRequestSigned = spLogoutRequestSigned;
	}

	/**
	 * 返回 Name ID 策略格式
	 *
	 * @return Name ID 策略格式
	 */
	public String getNameIdPolicyFormat() {
		return nameIdPolicyFormat;
	}

	/**
	 * 设置 Name ID 策略格式
	 *
	 * @param nameIdPolicyFormat
	 * 		Name ID 策略格式
	 */
	public void setNameIdPolicyFormat(String nameIdPolicyFormat) {
		this.nameIdPolicyFormat = nameIdPolicyFormat;
	}

	/**
	 * 返回是否使用 Name Qualifier
	 *
	 * @return 是否使用 Name Qualifier
	 */
	public Boolean isUseNameQualifier() {
		return getUseNameQualifier();
	}

	/**
	 * 返回是否使用 Name Qualifier
	 *
	 * @return 是否使用 Name Qualifier
	 */
	public Boolean getUseNameQualifier() {
		return useNameQualifier;
	}

	/**
	 * 设置是否使用 Name Qualifier
	 *
	 * @param useNameQualifier
	 * 		是否使用 Name Qualifier
	 */
	public void setUseNameQualifier(Boolean useNameQualifier) {
		this.useNameQualifier = useNameQualifier;
	}

	/**
	 * 返回是否签名元数据
	 *
	 * @return 是否签名元数据
	 */
	public Boolean isSignMetadata() {
		return getSignMetadata();
	}

	/**
	 * 返回是否签名元数据
	 *
	 * @return 是否签名元数据
	 */
	public Boolean getSignMetadata() {
		return signMetadata;
	}

	/**
	 * 设置是否签名元数据
	 *
	 * @param signMetadata
	 * 		是否签名元数据
	 */
	public void setSignMetadata(Boolean signMetadata) {
		this.signMetadata = signMetadata;
	}

	/**
	 * 返回是否强制生成 Service Provider 元数据
	 *
	 * @return 是否强制生成 Service Provider 元数据
	 */
	public Boolean isForceServiceProviderMetadataGeneration() {
		return getForceServiceProviderMetadataGeneration();
	}

	/**
	 * 返回是否强制生成 Service Provider 元数据
	 *
	 * @return 是否强制生成 Service Provider 元数据
	 */
	public Boolean getForceServiceProviderMetadataGeneration() {
		return forceServiceProviderMetadataGeneration;
	}

	/**
	 * 设置是否强制生成 Service Provider 元数据
	 *
	 * @param forceServiceProviderMetadataGeneration
	 * 		是否强制生成 Service Provider 元数据
	 */
	public void setForceServiceProviderMetadataGeneration(Boolean forceServiceProviderMetadataGeneration) {
		this.forceServiceProviderMetadataGeneration = forceServiceProviderMetadataGeneration;
	}

	/**
	 * 返回 Name ID 策略是否允许创建
	 *
	 * @return Name ID 策略是否允许创建
	 */
	public Boolean isNameIdPolicyAllowCreate() {
		return getNameIdPolicyAllowCreate();
	}

	/**
	 * 返回 Name ID 策略是否允许创建
	 *
	 * @return Name ID 策略是否允许创建
	 */
	public Boolean getNameIdPolicyAllowCreate() {
		return nameIdPolicyAllowCreate;
	}

	/**
	 * 设置 Name ID 策略是否允许创建
	 *
	 * @param nameIdPolicyAllowCreate
	 * 		Name ID 策略是否允许创建
	 */
	public void setNameIdPolicyAllowCreate(Boolean nameIdPolicyAllowCreate) {
		this.nameIdPolicyAllowCreate = nameIdPolicyAllowCreate;
	}

	/**
	 * 返回属性转换器
	 *
	 * @return 属性转换器
	 */
	public Class<? extends AttributeConverter> getAttributeConverter() {
		return attributeConverter;
	}

	/**
	 * 设置属性转换器
	 *
	 * @param attributeConverter
	 * 		属性转换器
	 */
	public void setAttributeConverter(Class<? extends AttributeConverter> attributeConverter) {
		this.attributeConverter = attributeConverter;
	}

	/**
	 * 返回消息存储工厂
	 *
	 * @return 消息存储工厂
	 */
	public Class<? extends SAMLMessageStoreFactory> getMessageStoreFactory() {
		return messageStoreFactory;
	}

	/**
	 * 设置消息存储工厂
	 *
	 * @param messageStoreFactory
	 * 		消息存储工厂
	 */
	public void setMessageStoreFactory(Class<? extends SAMLMessageStoreFactory> messageStoreFactory) {
		this.messageStoreFactory = messageStoreFactory;
	}

	/**
	 * 返回元数据生成器
	 *
	 * @return 元数据生成器
	 */
	public Class<? extends SAML2MetadataGenerator> getMetadataGenerator() {
		return metadataGenerator;
	}

	/**
	 * 设置元数据生成器
	 *
	 * @param metadataGenerator
	 * 		元数据生成器
	 */
	public void setMetadataGenerator(Class<? extends SAML2MetadataGenerator> metadataGenerator) {
		this.metadataGenerator = metadataGenerator;
	}

	/**
	 * 返回凭据提供者
	 *
	 * @return 凭据提供者
	 */
	public Class<? extends CredentialProvider> getCredentialProvider() {
		return credentialProvider;
	}

	/**
	 * 设置凭据提供者
	 *
	 * @param credentialProvider
	 * 		凭据提供者
	 */
	public void setCredentialProvider(Class<? extends CredentialProvider> credentialProvider) {
		this.credentialProvider = credentialProvider;
	}

	/**
	 * 返回签名算法黑名单列表
	 *
	 * @return 签名算法黑名单列表
	 */
	public List<String> getBlackListedSignatureSigningAlgorithms() {
		return blackListedSignatureSigningAlgorithms;
	}

	/**
	 * 设置签名算法黑名单列表
	 *
	 * @param blackListedSignatureSigningAlgorithms
	 * 		签名算法黑名单列表
	 */
	public void setBlackListedSignatureSigningAlgorithms(List<String> blackListedSignatureSigningAlgorithms) {
		this.blackListedSignatureSigningAlgorithms = blackListedSignatureSigningAlgorithms;
	}

	/**
	 * 返回签名算法列表
	 *
	 * @return 签名算法列表
	 */
	public List<String> getSignatureAlgorithms() {
		return signatureAlgorithms;
	}

	/**
	 * 设置签名算法列表
	 *
	 * @param signatureAlgorithms
	 * 		签名算法列表
	 */
	public void setSignatureAlgorithms(List<String> signatureAlgorithms) {
		this.signatureAlgorithms = signatureAlgorithms;
	}

	/**
	 * 返回签名引用摘要方法列表
	 *
	 * @return 签名引用摘要方法列表
	 */
	public List<String> getSignatureReferenceDigestMethods() {
		return signatureReferenceDigestMethods;
	}

	/**
	 * 设置签名引用摘要方法列表
	 *
	 * @param signatureReferenceDigestMethods
	 * 		签名引用摘要方法列表
	 */
	public void setSignatureReferenceDigestMethods(List<String> signatureReferenceDigestMethods) {
		this.signatureReferenceDigestMethods = signatureReferenceDigestMethods;
	}

	/**
	 * 返回签名规范化算法
	 *
	 * @return 签名规范化算法
	 */
	public String getSignatureCanonicalizationAlgorithm() {
		return signatureCanonicalizationAlgorithm;
	}

	/**
	 * 设置签名规范化算法
	 *
	 * @param signatureCanonicalizationAlgorithm
	 * 		签名规范化算法
	 */
	public void setSignatureCanonicalizationAlgorithm(String signatureCanonicalizationAlgorithm) {
		this.signatureCanonicalizationAlgorithm = signatureCanonicalizationAlgorithm;
	}

	/**
	 * 返回签名参数提供者
	 *
	 * @return 签名参数提供者
	 */
	public Class<? extends SignatureSigningParametersProvider> getSignatureSigningParametersProvider() {
		return signatureSigningParametersProvider;
	}

	/**
	 * 设置签名参数提供者
	 *
	 * @param signatureSigningParametersProvider
	 * 		签名参数提供者
	 */
	public void setSignatureSigningParametersProvider(
			Class<? extends SignatureSigningParametersProvider> signatureSigningParametersProvider) {
		this.signatureSigningParametersProvider = signatureSigningParametersProvider;
	}

	/**
	 * 返回认证响应验证器
	 *
	 * @return 认证响应验证器
	 */
	public Class<? extends SAML2ResponseValidator> getAuthnResponseValidator() {
		return authnResponseValidator;
	}

	/**
	 * 设置认证响应验证器
	 *
	 * @param authnResponseValidator
	 * 		认证响应验证器
	 */
	public void setAuthnResponseValidator(Class<? extends SAML2ResponseValidator> authnResponseValidator) {
		this.authnResponseValidator = authnResponseValidator;
	}

	/**
	 * 返回退出登录验证器
	 *
	 * @return 退出登录验证器
	 */
	public Class<? extends SAML2LogoutValidator> getLogoutValidator() {
		return logoutValidator;
	}

	/**
	 * 设置退出登录验证器
	 *
	 * @param logoutValidator
	 * 		退出登录验证器
	 */
	public void setLogoutValidator(Class<? extends SAML2LogoutValidator> logoutValidator) {
		this.logoutValidator = logoutValidator;
	}

	/**
	 * 返回签名信任引擎提供者
	 *
	 * @return 签名信任引擎提供者
	 */
	public Class<? extends SAML2SignatureTrustEngineProvider> getSignatureTrustEngineProvider() {
		return signatureTrustEngineProvider;
	}

	/**
	 * 设置签名信任引擎提供者
	 *
	 * @param signatureTrustEngineProvider
	 * 		签名信任引擎提供者
	 */
	public void setSignatureTrustEngineProvider(
			Class<? extends SAML2SignatureTrustEngineProvider> signatureTrustEngineProvider) {
		this.signatureTrustEngineProvider = signatureTrustEngineProvider;
	}

	/**
	 * 返回 Service Provider 元数据解析器
	 *
	 * @return Service Provider 元数据解析器
	 */
	public Class<? extends SAML2MetadataResolver> getServiceProviderMetadataResolver() {
		return serviceProviderMetadataResolver;
	}

	/**
	 * 设置 Service Provider 元数据解析器
	 *
	 * @param serviceProviderMetadataResolver
	 * 		Service Provider 元数据解析器
	 */
	public void setServiceProviderMetadataResolver(
			Class<? extends SAML2MetadataResolver> serviceProviderMetadataResolver) {
		this.serviceProviderMetadataResolver = serviceProviderMetadataResolver;
	}

	/**
	 * 返回默认 Identity Provider 元数据解析器
	 *
	 * @return 默认 Identity Provider 元数据解析器
	 */
	public Class<? extends SAML2MetadataResolver> getDefaultIdentityProviderMetadataResolver() {
		return defaultIdentityProviderMetadataResolver;
	}

	/**
	 * 设置默认 Identity Provider 元数据解析器
	 *
	 * @param defaultIdentityProviderMetadataResolver
	 * 		默认 Identity Provider 元数据解析器
	 */
	public void setDefaultIdentityProviderMetadataResolver(
			Class<? extends SAML2MetadataResolver> defaultIdentityProviderMetadataResolver) {
		this.defaultIdentityProviderMetadataResolver = defaultIdentityProviderMetadataResolver;
	}

	/**
	 * 返回是否需要断言签名
	 *
	 * @return 是否需要断言签名
	 */
	public Boolean isWantsAssertionsSigned() {
		return getWantsAssertionsSigned();
	}

	/**
	 * 返回是否需要断言签名
	 *
	 * @return 是否需要断言签名
	 */
	public Boolean getWantsAssertionsSigned() {
		return wantsAssertionsSigned;
	}

	/**
	 * 设置是否需要断言签名
	 *
	 * @param wantsAssertionsSigned
	 * 		是否需要断言签名
	 */
	public void setWantsAssertionsSigned(Boolean wantsAssertionsSigned) {
		this.wantsAssertionsSigned = wantsAssertionsSigned;
	}

	/**
	 * 返回是否需要响应签名
	 *
	 * @return 是否需要响应签名
	 */
	public Boolean isWantsResponsesSigned() {
		return getWantsResponsesSigned();
	}

	/**
	 * 返回是否需要响应签名
	 *
	 * @return 是否需要响应签名
	 */
	public Boolean getWantsResponsesSigned() {
		return wantsResponsesSigned;
	}

	/**
	 * 设置是否需要响应签名
	 *
	 * @param wantsResponsesSigned
	 * 		是否需要响应签名
	 */
	public void setWantsResponsesSigned(Boolean wantsResponsesSigned) {
		this.wantsResponsesSigned = wantsResponsesSigned;
	}

	/**
	 * 返回是否禁用所有签名验证
	 *
	 * @return 是否禁用所有签名验证
	 */
	public Boolean isAllSignatureValidationDisabled() {
		return getAllSignatureValidationDisabled();
	}

	/**
	 * 返回是否禁用所有签名验证
	 *
	 * @return 是否禁用所有签名验证
	 */
	public Boolean getAllSignatureValidationDisabled() {
		return allSignatureValidationDisabled;
	}

	/**
	 * 设置是否禁用所有签名验证
	 *
	 * @param allSignatureValidationDisabled
	 * 		是否禁用所有签名验证
	 */
	public void setAllSignatureValidationDisabled(Boolean allSignatureValidationDisabled) {
		this.allSignatureValidationDisabled = allSignatureValidationDisabled;
	}

	/**
	 * 返回响应目标属性是否必需
	 *
	 * @return 响应目标属性是否必需
	 */
	public Boolean isResponseDestinationAttributeMandatory() {
		return getResponseDestinationAttributeMandatory();
	}

	/**
	 * 返回响应目标属性是否必需
	 *
	 * @return 响应目标属性是否必需
	 */
	public Boolean getResponseDestinationAttributeMandatory() {
		return responseDestinationAttributeMandatory;
	}

	/**
	 * 设置响应目标属性是否必需
	 *
	 * @param responseDestinationAttributeMandatory
	 * 		响应目标属性是否必需
	 */
	public void setResponseDestinationAttributeMandatory(Boolean responseDestinationAttributeMandatory) {
		this.responseDestinationAttributeMandatory = responseDestinationAttributeMandatory;
	}

	/**
	 * 返回断言消费者服务索引
	 *
	 * @return 断言消费者服务索引
	 */
	public int getAssertionConsumerServiceIndex() {
		return assertionConsumerServiceIndex;
	}

	/**
	 * 设置断言消费者服务索引
	 *
	 * @param assertionConsumerServiceIndex
	 * 		断言消费者服务索引
	 */
	public void setAssertionConsumerServiceIndex(int assertionConsumerServiceIndex) {
		this.assertionConsumerServiceIndex = assertionConsumerServiceIndex;
	}

	/**
	 * 返回属性消费者服务索引
	 *
	 * @return 属性消费者服务索引
	 */
	public int getAttributeConsumingServiceIndex() {
		return attributeConsumingServiceIndex;
	}

	/**
	 * 设置属性消费者服务索引
	 *
	 * @param attributeConsumingServiceIndex
	 * 		属性消费者服务索引
	 */
	public void setAttributeConsumingServiceIndex(int attributeConsumingServiceIndex) {
		this.attributeConsumingServiceIndex = attributeConsumingServiceIndex;
	}

	/**
	 * 返回退出登录后跳转地址
	 *
	 * @return 退出登录后跳转地址
	 */
	public String getPostLogoutUrl() {
		return postLogoutUrl;
	}

	/**
	 * 设置退出登录后跳转地址
	 *
	 * @param postLogoutUrl
	 * 		退出登录后跳转地址
	 */
	public void setPostLogoutUrl(String postLogoutUrl) {
		this.postLogoutUrl = postLogoutUrl;
	}

	/**
	 * 返回发行者格式
	 *
	 * @return 发行者格式
	 */
	public String getIssuerFormat() {
		return issuerFormat;
	}

	/**
	 * 设置发行者格式
	 *
	 * @param issuerFormat
	 * 		发行者格式
	 */
	public void setIssuerFormat(String issuerFormat) {
		this.issuerFormat = issuerFormat;
	}

	/**
	 * 返回解密器
	 *
	 * @return 解密器
	 */
	public Class<? extends Decrypter> getDecrypter() {
		return decrypter;
	}

	/**
	 * 设置解密器
	 *
	 * @param decrypter
	 * 		解密器
	 */
	public void setDecrypter(Class<? extends Decrypter> decrypter) {
		this.decrypter = decrypter;
	}

	/**
	 * 返回状态生成器
	 *
	 * @return 状态生成器
	 */
	public Class<? extends ValueGenerator> getStateGenerator() {
		return stateGenerator;
	}

	/**
	 * 设置状态生成器
	 *
	 * @param stateGenerator
	 * 		状态生成器
	 */
	public void setStateGenerator(Class<? extends ValueGenerator> stateGenerator) {
		this.stateGenerator = stateGenerator;
	}

	/**
	 * 返回重放缓存提供者
	 *
	 * @return 重放缓存提供者
	 */
	public Class<? extends ReplayCacheProvider> getReplayCache() {
		return replayCache;
	}

	/**
	 * 设置重放缓存提供者
	 *
	 * @param replayCache
	 * 		重放缓存提供者
	 */
	public void setReplayCache(Class<? extends ReplayCacheProvider> replayCache) {
		this.replayCache = replayCache;
	}

	/**
	 * 返回 SOAP 管道提供者
	 *
	 * @return SOAP 管道提供者
	 */
	public Class<? extends SOAPPipelineProvider> getSoapPipelineProvider() {
		return soapPipelineProvider;
	}

	/**
	 * 设置 SOAP 管道提供者
	 *
	 * @param soapPipelineProvider
	 * 		SOAP 管道提供者
	 */
	public void setSoapPipelineProvider(Class<? extends SOAPPipelineProvider> soapPipelineProvider) {
		this.soapPipelineProvider = soapPipelineProvider;
	}

	/**
	 * 返回退出登录请求消息发送器
	 *
	 * @return 退出登录请求消息发送器
	 */
	public Class<? extends SAML2LogoutRequestMessageSender> getLogoutRequestMessageSender() {
		return logoutRequestMessageSender;
	}

	/**
	 * 设置退出登录请求消息发送器
	 *
	 * @param logoutRequestMessageSender
	 * 		退出登录请求消息发送器
	 */
	public void setLogoutRequestMessageSender(
			Class<? extends SAML2LogoutRequestMessageSender> logoutRequestMessageSender) {
		this.logoutRequestMessageSender = logoutRequestMessageSender;
	}

}
