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

import org.pac4j.core.config.properties.JwksProperties;
import org.pac4j.core.store.Store;
import org.pac4j.core.util.generator.ValueGenerator;
import org.pac4j.oidc.config.method.ClientSecretJwtClientAuthnMethodConfig;
import org.pac4j.oidc.federation.config.OidcFederationProperties;
import org.pac4j.oidc.metadata.IOidcOpMetadataResolver;
import org.pac4j.oidc.util.ValueRetriever;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

/**
 * OIDC 配置
 *
 * @author Yong.Teng
 * @since 4.0.0
 */
public class Oidc extends BaseClientConfig {

	public final static String PREFIX = BaseClientConfig.PROPERTIES_PREFIX + ".oidc";

	/**
	 * Apple OIDC 配置
	 */
	@NestedConfigurationProperty
	private Apple apple = new Apple();

	/**
	 * Azure AD2 OIDC 配置
	 */
	@NestedConfigurationProperty
	private AzureAd2 azureAd2 = new AzureAd2();

	/**
	 * Google OIDC 配置
	 */
	@NestedConfigurationProperty
	private Google google = new Google();

	/**
	 * Keycloak OIDC 配置
	 */
	@NestedConfigurationProperty
	private Keycloak keycloak = new Keycloak();

	/**
	 * 构造函数
	 */
	public Oidc() {
		super(null);
	}

	/**
	 * 返回 Apple OIDC 配置
	 *
	 * @return Apple OIDC 配置
	 */
	public Apple getApple() {
		return apple;
	}

	/**
	 * 设置 Apple OIDC 配置
	 *
	 * @param apple
	 * 		Apple OIDC 配置
	 */
	public void setApple(Apple apple) {
		this.apple = apple;
	}

	/**
	 * 返回 Azure AD2 OIDC 配置
	 *
	 * @return Azure AD2 OIDC 配置
	 */
	public AzureAd2 getAzureAd2() {
		return azureAd2;
	}

	/**
	 * 设置 Azure AD2 OIDC 配置
	 *
	 * @param azureAd2
	 * 		Azure AD2 OIDC 配置
	 */
	public void setAzureAd2(AzureAd2 azureAd2) {
		this.azureAd2 = azureAd2;
	}

	/**
	 * 返回 Google OIDC 配置
	 *
	 * @return Google OIDC 配置
	 */
	public Google getGoogle() {
		return google;
	}

	/**
	 * 设置 Google OIDC 配置
	 *
	 * @param google
	 * 		Google OIDC 配置
	 */
	public void setGoogle(Google google) {
		this.google = google;
	}

	/**
	 * 返回 Keycloak OIDC 配置
	 *
	 * @return Keycloak OIDC 配置
	 */
	public Keycloak getKeycloak() {
		return keycloak;
	}

	/**
	 * 设置 Keycloak OIDC 配置
	 *
	 * @param keycloak
	 * 		Keycloak OIDC 配置
	 */
	public void setKeycloak(Keycloak keycloak) {
		this.keycloak = keycloak;
	}

	public static abstract class BaseOidcClientConfig extends IndirectClientConfig {

		private OidcFederationProperties federation;

		private JwksProperties rpJwks;

		private String clientId;

		private String secret;

		private String logoutUrl;

		private String loginHint;

		private Duration connectTimeout;

		private Duration readTimeout;

		private ClientAuthenticationMethod clientAuthenticationMethod;

		private Set<ClientAuthenticationMethod> supportedClientAuthenticationMethods;

		private ClientSecretJwtClientAuthnMethodConfig clientSecretJwtClientAuthnMethodConfig;

		private CodeChallengeMethod pkceMethod;

		private JWSAlgorithm requestObjectSigningAlgorithm;

		private String responseMode;

		private Map<String, String> mappedClaims;

		private String scope;

		private Class<? extends ValueGenerator> stateGenerator;

		private Class<? extends ValueGenerator> codeVerifierGenerator;

		private Class<? extends ValueRetriever> valueRetriever;

		private Class<? extends IOidcOpMetadataResolver> opMetadataResolver;

		private Boolean withState;

		private Boolean expireSessionWithToken;

		private Duration tokenExpirationAdvance;

		private Boolean allowUnsignedIdTokens;

		private Boolean includeAccessTokenClaimsInProfile;

		private Boolean useNonce;

		private Boolean useNonceOnRefresh;

		private Boolean callUserInfoEndpoint;

		private Boolean disablePkce;

		private Boolean logoutValidation;

		private Boolean pushedAuthorizationRequest;

		private Integer maxAge;

		private Integer maxClockSkew;

		public BaseOidcClientConfig(String name) {
			super(name);
		}

		public OidcFederationProperties getFederation() {
			return federation;
		}

		public void setFederation(OidcFederationProperties federation) {
			this.federation = federation;
		}

		public JwksProperties getRpJwks() {
			return rpJwks;
		}

		public void setRpJwks(JwksProperties rpJwks) {
			this.rpJwks = rpJwks;
		}

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public String getLogoutUrl() {
			return logoutUrl;
		}

		public void setLogoutUrl(String logoutUrl) {
			this.logoutUrl = logoutUrl;
		}

		public String getLoginHint() {
			return loginHint;
		}

		public void setLoginHint(String loginHint) {
			this.loginHint = loginHint;
		}

		public Duration getConnectTimeout() {
			return connectTimeout;
		}

		public void setConnectTimeout(Duration connectTimeout) {
			this.connectTimeout = connectTimeout;
		}

		public Duration getReadTimeout() {
			return readTimeout;
		}

		public void setReadTimeout(Duration readTimeout) {
			this.readTimeout = readTimeout;
		}

		public ClientAuthenticationMethod getClientAuthenticationMethod() {
			return clientAuthenticationMethod;
		}

		public void setClientAuthenticationMethod(ClientAuthenticationMethod clientAuthenticationMethod) {
			this.clientAuthenticationMethod = clientAuthenticationMethod;
		}

		public Set<ClientAuthenticationMethod> getSupportedClientAuthenticationMethods() {
			return supportedClientAuthenticationMethods;
		}

		public void setSupportedClientAuthenticationMethods(
				Set<ClientAuthenticationMethod> supportedClientAuthenticationMethods) {
			this.supportedClientAuthenticationMethods = supportedClientAuthenticationMethods;
		}

		public ClientSecretJwtClientAuthnMethodConfig getClientSecretJwtClientAuthnMethodConfig() {
			return clientSecretJwtClientAuthnMethodConfig;
		}

		public void setClientSecretJwtClientAuthnMethodConfig(
				ClientSecretJwtClientAuthnMethodConfig clientSecretJwtClientAuthnMethodConfig) {
			this.clientSecretJwtClientAuthnMethodConfig = clientSecretJwtClientAuthnMethodConfig;
		}

		public CodeChallengeMethod getPkceMethod() {
			return pkceMethod;
		}

		public void setPkceMethod(CodeChallengeMethod pkceMethod) {
			this.pkceMethod = pkceMethod;
		}

		public JWSAlgorithm getRequestObjectSigningAlgorithm() {
			return requestObjectSigningAlgorithm;
		}

		public void setRequestObjectSigningAlgorithm(JWSAlgorithm requestObjectSigningAlgorithm) {
			this.requestObjectSigningAlgorithm = requestObjectSigningAlgorithm;
		}

		public String getResponseMode() {
			return responseMode;
		}

		public void setResponseMode(String responseMode) {
			this.responseMode = responseMode;
		}

		public Map<String, String> getMappedClaims() {
			return mappedClaims;
		}

		public void setMappedClaims(Map<String, String> mappedClaims) {
			this.mappedClaims = mappedClaims;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public Class<? extends ValueGenerator> getStateGenerator() {
			return stateGenerator;
		}

		public void setStateGenerator(Class<? extends ValueGenerator> stateGenerator) {
			this.stateGenerator = stateGenerator;
		}

		public Class<? extends ValueGenerator> getCodeVerifierGenerator() {
			return codeVerifierGenerator;
		}

		public void setCodeVerifierGenerator(Class<? extends ValueGenerator> codeVerifierGenerator) {
			this.codeVerifierGenerator = codeVerifierGenerator;
		}

		public Class<? extends ValueRetriever> getValueRetriever() {
			return valueRetriever;
		}

		public void setValueRetriever(Class<? extends ValueRetriever> valueRetriever) {
			this.valueRetriever = valueRetriever;
		}

		public Class<? extends IOidcOpMetadataResolver> getOpMetadataResolver() {
			return opMetadataResolver;
		}

		public void setOpMetadataResolver(Class<? extends IOidcOpMetadataResolver> opMetadataResolver) {
			this.opMetadataResolver = opMetadataResolver;
		}

		public Boolean isWithState() {
			return getWithState();
		}

		public Boolean getWithState() {
			return withState;
		}

		public void setWithState(Boolean withState) {
			this.withState = withState;
		}

		public Boolean isExpireSessionWithToken() {
			return getExpireSessionWithToken();
		}

		public Boolean getExpireSessionWithToken() {
			return expireSessionWithToken;
		}

		public void setExpireSessionWithToken(Boolean expireSessionWithToken) {
			this.expireSessionWithToken = expireSessionWithToken;
		}

		public Duration isTokenExpirationAdvance() {
			return getTokenExpirationAdvance();
		}

		public Duration getTokenExpirationAdvance() {
			return tokenExpirationAdvance;
		}

		public void setTokenExpirationAdvance(Duration tokenExpirationAdvance) {
			this.tokenExpirationAdvance = tokenExpirationAdvance;
		}

		public Boolean isAllowUnsignedIdTokens() {
			return getAllowUnsignedIdTokens();
		}

		public Boolean getAllowUnsignedIdTokens() {
			return allowUnsignedIdTokens;
		}

		public void setAllowUnsignedIdTokens(Boolean allowUnsignedIdTokens) {
			this.allowUnsignedIdTokens = allowUnsignedIdTokens;
		}

		public Boolean isIncludeAccessTokenClaimsInProfile() {
			return getIncludeAccessTokenClaimsInProfile();
		}

		public Boolean getIncludeAccessTokenClaimsInProfile() {
			return includeAccessTokenClaimsInProfile;
		}

		public void setIncludeAccessTokenClaimsInProfile(Boolean includeAccessTokenClaimsInProfile) {
			this.includeAccessTokenClaimsInProfile = includeAccessTokenClaimsInProfile;
		}

		public Boolean isUseNonce() {
			return getUseNonce();
		}

		public Boolean getUseNonce() {
			return useNonce;
		}

		public void setUseNonce(Boolean useNonce) {
			this.useNonce = useNonce;
		}

		public Boolean isUseNonceOnRefresh() {
			return getUseNonceOnRefresh();
		}

		public Boolean getUseNonceOnRefresh() {
			return useNonceOnRefresh;
		}

		public void setUseNonceOnRefresh(Boolean useNonceOnRefresh) {
			this.useNonceOnRefresh = useNonceOnRefresh;
		}

		public Boolean isCallUserInfoEndpoint() {
			return getCallUserInfoEndpoint();
		}

		public Boolean getCallUserInfoEndpoint() {
			return callUserInfoEndpoint;
		}

		public void setCallUserInfoEndpoint(Boolean callUserInfoEndpoint) {
			this.callUserInfoEndpoint = callUserInfoEndpoint;
		}

		public Boolean isDisablePkce() {
			return getDisablePkce();
		}

		public Boolean getDisablePkce() {
			return disablePkce;
		}

		public void setDisablePkce(Boolean disablePkce) {
			this.disablePkce = disablePkce;
		}

		public Boolean isLogoutValidation() {
			return getLogoutValidation();
		}

		public Boolean getLogoutValidation() {
			return logoutValidation;
		}

		public void setLogoutValidation(Boolean logoutValidation) {
			this.logoutValidation = logoutValidation;
		}

		public Boolean isPushedAuthorizationRequest() {
			return getPushedAuthorizationRequest();
		}

		public Boolean getPushedAuthorizationRequest() {
			return pushedAuthorizationRequest;
		}

		public void setPushedAuthorizationRequest(Boolean pushedAuthorizationRequest) {
			this.pushedAuthorizationRequest = pushedAuthorizationRequest;
		}

		public Integer getMaxAge() {
			return maxAge;
		}

		public void setMaxAge(Integer maxAge) {
			this.maxAge = maxAge;
		}

		public Integer getMaxClockSkew() {
			return maxClockSkew;
		}

		public void setMaxClockSkew(Integer maxClockSkew) {
			this.maxClockSkew = maxClockSkew;
		}

	}

	public enum ClientAuthenticationMethod {

		/**
		 * Clients that have received a client secret from the authorisation
		 * server authenticate with the authorisation server in accordance with
		 * section 3.2.1 of OAuth 2.0 using HTTP Basic authentication. This is
		 * the default if no method has been registered for the client.
		 */
		CLIENT_SECRET_BASIC("client_secret_basic"),

		/**
		 * Clients that have received a client secret from the authorisation
		 * server authenticate with the authorisation server in accordance with
		 * section 3.2.1 of OAuth 2.0 by including the client credentials in
		 * the request body.
		 */
		CLIENT_SECRET_POST("client_secret_post"),

		/**
		 * Clients that have received a client secret from the authorisation
		 * server, create a JWT using an HMAC SHA algorithm, such as HMAC
		 * SHA-256. The HMAC (Hash-based Message Authentication Code) is
		 * calculated using the value of client secret as the shared key. The
		 * client authenticates in accordance with section 2.2 of (JWT) Bearer
		 * Token Profiles and OAuth 2.0 Assertion Profile.
		 */
		CLIENT_SECRET_JWT("client_secret_jwt"),

		/**
		 * Clients that have registered a public key sign a JWT using the RSA
		 * algorithm if a RSA key was registered or the ECDSA algorithm if an
		 * Elliptic Curve key was registered (see JWA for the algorithm
		 * identifiers). The client authenticates in accordance with section
		 * 2.2 of (JWT) Bearer Token Profiles and OAuth 2.0 Assertion Profile.
		 */
		PRIVATE_KEY_JWT("private_key_jwt"),

		/**
		 * PKI mutual TLS OAuth client authentication. See OAuth 2.0 Mutual TLS
		 * Client Authentication and Certificate Bound Access Tokens (RFC
		 * 8705), section 2.1.
		 */
		TLS_CLIENT_AUTH("tls_client_auth"),

		/**
		 * Self-signed certificate mutual TLS OAuth client authentication. See
		 * OAuth 2.0 Mutual TLS Client Authentication and Certificate Bound
		 * Access Tokens (RFC 8705), section 2.2.
		 */
		SELF_SIGNED_TLS_CLIENT_AUTH("self_signed_tls_client_auth"),

		/**
		 * Client verification by means of a request object at the
		 * authorisation or PAR endpoints. Intended for OpenID Connect
		 * Federation 1.0 clients undertaking automatic registration. See
		 * OpenID Connect Federation 1.0.
		 */
		REQUEST_OBJECT("request_object"),

		/**
		 * The client is a public client as defined in OAuth 2.0 and does not
		 * have a client secret.
		 */
		NONE("none");

		private final String value;

		ClientAuthenticationMethod(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return getValue();
		}

	}

	public enum CodeChallengeMethod {

		/**
		 * Plain code challenge method.
		 */
		PLAIN("plain"),

		/**
		 * SHA-256 code challenge method.
		 */
		S256("S256");

		private final String value;

		CodeChallengeMethod(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return getValue();
		}

	}

	public enum JWSAlgorithm {

		/**
		 * HMAC using SHA-256 hash algorithm (required).
		 */
		HS256("HS256"),

		/**
		 * HMAC using SHA-384 hash algorithm (optional).
		 */
		HS384("HS384"),

		/**
		 * HMAC using SHA-512 hash algorithm (optional).
		 */
		HS512("HS512"),

		/**
		 * RSASSA-PKCS-v1_5 using SHA-256 hash algorithm (recommended).
		 */
		RS256("RS256"),

		/**
		 * RSASSA-PKCS-v1_5 using SHA-384 hash algorithm (optional).
		 */
		RS384("RS384"),

		/**
		 * RSASSA-PKCS-v1_5 using SHA-512 hash algorithm (optional).
		 */
		RS512("RS512"),

		/**
		 * ECDSA using P-256 (secp256r1) curve and SHA-256 hash algorithm
		 * (recommended).
		 */
		ES256("ES256"),

		/**
		 * ECDSA using P-256K (secp256k1) curve and SHA-256 hash algorithm
		 * (optional).
		 */
		ES256K("ES256K"),

		/**
		 * ECDSA using P-384 curve and SHA-384 hash algorithm (optional).
		 */
		ES384("ES384"),

		/**
		 * ECDSA using P-521 curve and SHA-512 hash algorithm (optional).
		 */
		ES512("ES512"),

		/**
		 * RSASSA-PSS using SHA-256 hash algorithm and MGF1 mask generation
		 * function with SHA-256 (optional).
		 */
		PS256("PS256"),

		/**
		 * RSASSA-PSS using SHA-384 hash algorithm and MGF1 mask generation
		 * function with SHA-384 (optional).
		 */
		PS384("PS384"),

		/**
		 * RSASSA-PSS using SHA-512 hash algorithm and MGF1 mask generation
		 * function with SHA-512 (optional).
		 */
		PS512("PS512"),

		/**
		 * EdDSA signature algorithms (optional).
		 */
		EdDSA("EdDSA"),

		/**
		 * EdDSA signature algorithms using Ed25519 curve (optional).
		 */
		Ed25519("Ed25519"),

		/**
		 * EdDSA signature algorithms using Ed448 curve (optional).
		 */
		Ed448("Ed448");

		private final String value;

		JWSAlgorithm(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return getValue();
		}
	}

	/**
	 * Apple OIDC 配置
	 */
	public final static class Apple extends BaseOidcClientConfig {

		private String discoveryURI;

		/**
		 * Apple Auth Key ID
		 */
		private String privateKeyID;

		/**
		 * Apple Team ID
		 */
		private String teamId;

		/**
		 * Client secret cache store
		 */
		private Store<String, String> store;

		/**
		 * Client secret expiration timeout
		 */
		private Duration timeout;

		public Apple() {
			super("apple");
		}

		public String getDiscoveryURI() {
			return discoveryURI;
		}

		public void setDiscoveryURI(String discoveryURI) {
			this.discoveryURI = discoveryURI;
		}

		public String getPrivateKeyID() {
			return privateKeyID;
		}

		public void setPrivateKeyID(String privateKeyID) {
			this.privateKeyID = privateKeyID;
		}

		public String getTeamId() {
			return teamId;
		}

		public void setTeamId(String teamId) {
			this.teamId = teamId;
		}

		public Store<String, String> getStore() {
			return store;
		}

		public void setStore(Store<String, String> store) {
			this.store = store;
		}

		public Duration getTimeout() {
			return timeout;
		}

		public void setTimeout(Duration timeout) {
			this.timeout = timeout;
		}

	}

	/**
	 * Azure AD2 OIDC 配置
	 */
	public final static class AzureAd2 extends BaseOidcClientConfig {

		/**
		 * AzureAd tenant
		 **/
		private String tenant;

		public AzureAd2() {
			super("azuread2");
		}

		public String getTenant() {
			return tenant;
		}

		public void setTenant(String tenant) {
			this.tenant = tenant;
		}

	}

	/**
	 * Google OIDC 配置
	 */
	public final static class Google extends BaseOidcClientConfig {

		public Google() {
			super("google");
		}

	}

	/**
	 * Keycloak OIDC 配置
	 */
	public final static class Keycloak extends BaseOidcClientConfig {

		/**
		 * Keycloak auth realm
		 **/
		private String realm;

		/**
		 * Keycloak server base uri
		 **/
		private String baseUri;

		public Keycloak() {
			super("keycloak");
		}

		public String getRealm() {
			return realm;
		}

		public void setRealm(String realm) {
			this.realm = realm;
		}

		public String getBaseUri() {
			return baseUri;
		}

		public void setBaseUri(String baseUri) {
			this.baseUri = baseUri;
		}

	}

}
