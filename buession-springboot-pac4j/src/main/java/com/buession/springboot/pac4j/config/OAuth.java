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
package com.buession.springboot.pac4j.config;

import com.github.scribejava.core.model.Verb;
import org.pac4j.oauth.client.QQClient;
import org.pac4j.oauth.client.WechatClient;
import org.pac4j.oauth.client.WeiboClient;
import org.pac4j.oauth.config.OAuthConfiguration;
import org.pac4j.oauth.profile.facebook.FacebookProfileDefinition;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * OAuth 配置
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
public class OAuth extends BaseConfig {

	public final static String PREFIX = PROPERTIES_PREFIX + ".oauth";

	/**
	 * Key
	 */
	private String key;

	/**
	 * Secret
	 */
	private String secret;

	/**
	 * 登录成功跳转地址
	 */
	private String callbackUrl;

	/**
	 * Bitbucket 配置
	 */
	@NestedConfigurationProperty
	private Bitbucket bitbucket = new Bitbucket();

	/**
	 * Twitter 配置
	 */
	@NestedConfigurationProperty
	private Twitter twitter = new Twitter();

	/**
	 * Yahoo 配置
	 */
	@NestedConfigurationProperty
	private Yahoo yahoo = new Yahoo();

	/**
	 * 通用 OAuth2 配置
	 */
	@NestedConfigurationProperty
	private Generic generic = new Generic();

	/**
	 * CAS 配置
	 */
	@NestedConfigurationProperty
	private Cas cas = new Cas();

	/**
	 * DropBox 配置
	 */
	@NestedConfigurationProperty
	private DropBox dropBox = new DropBox();

	/**
	 * Facebook 配置
	 */
	@NestedConfigurationProperty
	private Facebook facebook = new Facebook();

	/**
	 * FigShare 配置
	 */
	@NestedConfigurationProperty
	private FigShare figShare = new FigShare();

	/**
	 * Foursquare 配置
	 */
	@NestedConfigurationProperty
	private Foursquare foursquare = new Foursquare();

	/**
	 * GitHub 配置
	 */
	@NestedConfigurationProperty
	private GitHub gitHub = new GitHub();

	/**
	 * Google2 配置
	 */
	@NestedConfigurationProperty
	private Google2 google2 = new Google2();

	/**
	 * HiOrgServer 配置
	 */
	private HiOrgServer hiOrgServer = new HiOrgServer();

	/**
	 * LinkedIn2 配置
	 */
	@NestedConfigurationProperty
	private LinkedIn2 linkedIn2 = new LinkedIn2();

	/**
	 * Ok 配置
	 */
	@NestedConfigurationProperty
	private Ok ok = new Ok();

	/**
	 * PayPal 配置
	 */
	@NestedConfigurationProperty
	private PayPal payPal = new PayPal();

	/**
	 * QQ 配置
	 */
	@NestedConfigurationProperty
	private Qq qq = new Qq();

	/**
	 * Strava 配置
	 */
	@NestedConfigurationProperty
	private Strava strava = new Strava();

	/**
	 * Vk 配置
	 */
	@NestedConfigurationProperty
	private Vk vk = new Vk();

	/**
	 * 微博配置
	 */
	@NestedConfigurationProperty
	private Weibo weibo = new Weibo();

	/**
	 * 微信配置
	 */
	@NestedConfigurationProperty
	private Wechat wechat = new Wechat();

	/**
	 * Windows Live 配置
	 */
	@NestedConfigurationProperty
	private WindowsLive windowsLive = new WindowsLive();

	/**
	 * WordPress 配置
	 */
	@NestedConfigurationProperty
	private WordPress wordPress = new WordPress();

	/**
	 * 返回 Key
	 *
	 * @return Key
	 */
	public String getKey(){
		return key;
	}

	/**
	 * 设置 Key
	 *
	 * @param key
	 * 		Key
	 */
	public void setKey(String key){
		this.key = key;
	}

	/**
	 * 返回 Secret
	 *
	 * @return Secret
	 */
	public String getSecret(){
		return secret;
	}

	/**
	 * 设置 Secret
	 *
	 * @param secret
	 * 		Secret
	 */
	public void setSecret(String secret){
		this.secret = secret;
	}

	/**
	 * 返回登录成功跳转地址
	 *
	 * @return 登录成功跳转地址
	 */
	public String getCallbackUrl(){
		return callbackUrl;
	}

	/**
	 * 设置登录成功跳转地址
	 *
	 * @param callbackUrl
	 * 		登录成功跳转地址
	 */
	public void setCallbackUrl(String callbackUrl){
		this.callbackUrl = callbackUrl;
	}

	/**
	 * 返回 Bitbucket 配置
	 *
	 * @return Bitbucket 配置
	 */
	public Bitbucket getBitbucket(){
		return bitbucket;
	}

	/**
	 * 设置 Bitbucket 配置
	 *
	 * @param bitbucket
	 * 		Bitbucket 配置
	 */
	public void setBitbucket(Bitbucket bitbucket){
		this.bitbucket = bitbucket;
	}

	/**
	 * 返回 Twitter 配置
	 *
	 * @return Twitter 配置
	 */
	public Twitter getTwitter(){
		return twitter;
	}

	/**
	 * 设置 Twitter 配置
	 *
	 * @param twitter
	 * 		Twitter 配置
	 */
	public void setTwitter(Twitter twitter){
		this.twitter = twitter;
	}

	/**
	 * 返回 Yahoo 配置
	 *
	 * @return Yahoo 配置
	 */
	public Yahoo getYahoo(){
		return yahoo;
	}

	/**
	 * 设置 Yahoo 配置
	 *
	 * @param yahoo
	 * 		Yahoo 配置
	 */
	public void setYahoo(Yahoo yahoo){
		this.yahoo = yahoo;
	}

	/**
	 * 返回通用 OAuth2 配置
	 *
	 * @return 通用 OAuth2 配置
	 */
	public Generic getGeneric(){
		return generic;
	}

	/**
	 * 设置通用 OAuth2 配置
	 *
	 * @param generic
	 * 		通用 OAuth2 配置
	 */
	public void setGeneric(Generic generic){
		this.generic = generic;
	}

	/**
	 * 返回 CAS 配置
	 *
	 * @return CAS 配置
	 */
	public Cas getCas(){
		return cas;
	}

	/**
	 * 设置 CAS 配置
	 *
	 * @param cas
	 * 		CAS 配置
	 */
	public void setCas(Cas cas){
		this.cas = cas;
	}

	/**
	 * 返回 DropBox 配置
	 *
	 * @return DropBox 配置
	 */
	public DropBox getDropBox(){
		return dropBox;
	}

	/**
	 * 设置 DropBox 配置
	 *
	 * @param dropBox
	 * 		DropBox 配置
	 */
	public void setDropBox(DropBox dropBox){
		this.dropBox = dropBox;
	}

	/**
	 * 返回 Facebook 配置
	 *
	 * @return Facebook 配置
	 */
	public Facebook getFacebook(){
		return facebook;
	}

	/**
	 * 设置 Facebook 配置
	 *
	 * @param facebook
	 * 		Facebook 配置
	 */
	public void setFacebook(Facebook facebook){
		this.facebook = facebook;
	}

	/**
	 * 返回 FigShare 配置
	 *
	 * @return FigShare 配置
	 */
	public FigShare getFigShare(){
		return figShare;
	}

	/**
	 * 设置 FigShare 配置
	 *
	 * @param figShare
	 * 		FigShare 配置
	 */
	public void setFigShare(FigShare figShare){
		this.figShare = figShare;
	}

	/**
	 * 返回 Foursquare 配置
	 *
	 * @return Foursquare 配置
	 */
	public Foursquare getFoursquare(){
		return foursquare;
	}

	/**
	 * 设置 Foursquare 配置
	 *
	 * @param foursquare
	 * 		Foursquare 配置
	 */
	public void setFoursquare(Foursquare foursquare){
		this.foursquare = foursquare;
	}

	/**
	 * 返回 GitHub 配置
	 *
	 * @return GitHub 配置
	 */
	public GitHub getGitHub(){
		return gitHub;
	}

	/**
	 * 设置 GitHub 配置
	 *
	 * @param gitHub
	 * 		GitHub 配置
	 */
	public void setGitHub(GitHub gitHub){
		this.gitHub = gitHub;
	}

	/**
	 * 返回 Google2 配置
	 *
	 * @return Google2 配置
	 */
	public Google2 getGoogle2(){
		return google2;
	}

	/**
	 * 设置 Google2 配置
	 *
	 * @param google2
	 * 		Google2 配置
	 */
	public void setGoogle2(Google2 google2){
		this.google2 = google2;
	}

	/**
	 * 返回 HiOrgServer 配置
	 *
	 * @return HiOrgServer 配置
	 */
	public HiOrgServer getHiOrgServer(){
		return hiOrgServer;
	}

	/**
	 * 设置 HiOrgServer 配置
	 *
	 * @param hiOrgServer
	 * 		HiOrgServer 配置
	 */
	public void setHiOrgServer(HiOrgServer hiOrgServer){
		this.hiOrgServer = hiOrgServer;
	}

	/**
	 * 返回 LinkedIn2 配置
	 *
	 * @return LinkedIn2 配置
	 */
	public LinkedIn2 getLinkedIn2(){
		return linkedIn2;
	}

	/**
	 * 设置 LinkedIn2 配置
	 *
	 * @param linkedIn2
	 * 		LinkedIn2 配置
	 */
	public void setLinkedIn2(LinkedIn2 linkedIn2){
		this.linkedIn2 = linkedIn2;
	}

	/**
	 * 返回 Ok 配置
	 *
	 * @return Ok 配置
	 */
	public Ok getOk(){
		return ok;
	}

	/**
	 * 设置 Ok 配置
	 *
	 * @param ok
	 * 		Ok 配置
	 */
	public void setOk(Ok ok){
		this.ok = ok;
	}

	/**
	 * 返回 PayPal 配置
	 *
	 * @return PayPal 配置
	 */
	public PayPal getPayPal(){
		return payPal;
	}

	/**
	 * 设置 PayPal 配置
	 *
	 * @param payPal
	 * 		PayPal 配置
	 */
	public void setPayPal(PayPal payPal){
		this.payPal = payPal;
	}

	/**
	 * 返回 QQ 配置
	 *
	 * @return QQ 配置
	 */
	public Qq getQq(){
		return qq;
	}

	/**
	 * 设置 QQ 配置
	 *
	 * @param qq
	 * 		QQ 配置
	 */
	public void setQq(Qq qq){
		this.qq = qq;
	}

	/**
	 * 返回 Strava 配置
	 *
	 * @return Strava 配置
	 */
	public Strava getStrava(){
		return strava;
	}

	/**
	 * 设置 Strava 配置
	 *
	 * @param strava
	 * 		Strava 配置
	 */
	public void setStrava(Strava strava){
		this.strava = strava;
	}

	/**
	 * 返回 Vk 配置
	 *
	 * @return Vk 配置
	 */
	public Vk getVk(){
		return vk;
	}

	/**
	 * 设置 Vk 配置
	 *
	 * @param vk
	 * 		Vk 配置
	 */
	public void setVk(Vk vk){
		this.vk = vk;
	}

	/**
	 * 返回微博配置
	 *
	 * @return 微博配置
	 */
	public Weibo getWeibo(){
		return weibo;
	}

	/**
	 * 设置微博配置
	 *
	 * @param weibo
	 * 		微博配置
	 */
	public void setWeibo(Weibo weibo){
		this.weibo = weibo;
	}

	/**
	 * 返回微信配置
	 *
	 * @return 微信配置
	 */
	public Wechat getWechat(){
		return wechat;
	}

	/**
	 * 设置微信配置
	 *
	 * @param wechat
	 * 		微信配置
	 */
	public void setWechat(Wechat wechat){
		this.wechat = wechat;
	}

	/**
	 * 返回 Windows Live 配置
	 *
	 * @return Windows Live 配置
	 */
	public WindowsLive getWindowsLive(){
		return windowsLive;
	}

	/**
	 * 设置 Windows Live 配置
	 *
	 * @param windowsLive
	 * 		Windows Live 配置
	 */
	public void setWindowsLive(WindowsLive windowsLive){
		this.windowsLive = windowsLive;
	}

	/**
	 * 返回 WordPress 配置
	 *
	 * @return WordPress 配置
	 */
	public WordPress getWordPress(){
		return wordPress;
	}

	/**
	 * 设置 WordPress 配置
	 *
	 * @param wordPress
	 * 		WordPress 配置
	 */
	public void setWordPress(WordPress wordPress){
		this.wordPress = wordPress;
	}

	public abstract static class BaseOAuthConfig extends BaseClientConfig {

		protected boolean tokenAsHeader;

		/**
		 * 授权类型
		 */
		protected String responseType = OAuthConfiguration.RESPONSE_TYPE_CODE;

		/**
		 * 返回申请的权限范围
		 */
		protected String scope;

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseOAuthConfig(String name){
			super(name);
		}

		public boolean isTokenAsHeader(){
			return tokenAsHeader;
		}

		public void setTokenAsHeader(boolean tokenAsHeader){
			this.tokenAsHeader = tokenAsHeader;
		}

		/**
		 * 返回授权类型
		 *
		 * @return 授权类型
		 */
		public String getResponseType(){
			return responseType;
		}

		/**
		 * 设置授权类型
		 *
		 * @param responseType
		 * 		授权类型
		 */
		public void setResponseType(String responseType){
			this.responseType = responseType;
		}

		/**
		 * 返回申请的权限范围
		 *
		 * @return 申请的权限范围
		 */
		public String getScope(){
			return scope;
		}

		/**
		 * 设置申请的权限范围
		 *
		 * @param scope
		 * 		申请的权限范围
		 */
		public void setScope(String scope){
			this.scope = scope;
		}

	}

	public abstract static class BaseOAuth10Config extends BaseOAuthConfig {

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseOAuth10Config(String name){
			super(name);
		}

	}

	public abstract static class BaseOAuth20Config extends BaseOAuthConfig {

		private boolean withState;

		/**
		 * 客户自定义参数
		 */
		private Map<String, String> customParameters = new LinkedHashMap<>();

		/**
		 * 构造函数
		 *
		 * @param name
		 * 		Client 名称
		 */
		public BaseOAuth20Config(String name){
			super(name);
		}

		public boolean isWithState(){
			return withState;
		}

		public void setWithState(boolean withState){
			this.withState = withState;
		}

		/**
		 * 返回客户自定义参数
		 *
		 * @return 客户自定义参数
		 */
		public Map<String, String> getCustomParameters(){
			return customParameters;
		}

		/**
		 * 设置客户自定义参数
		 *
		 * @param customParameters
		 * 		客户自定义参数
		 */
		public void setCustomParameters(Map<String, String> customParameters){
			this.customParameters = customParameters;
		}

	}

	/**
	 * Bitbucket 配置
	 */
	public final static class Bitbucket extends BaseOAuth10Config {

		public Bitbucket(){
			super("bitbucket");
		}

	}

	/**
	 * Twitter 配置
	 */
	public final static class Twitter extends BaseOAuth10Config {

		/**
		 * 是否总是需要确认授权
		 */
		private boolean alwaysConfirmAuthorization = false;

		/**
		 * 是否包含 E-mail
		 */
		private boolean includeEmail = false;

		public Twitter(){
			super("twitter");
		}

		/**
		 * 返回是否总是需要确认授权
		 *
		 * @return 是否总是需要确认授权
		 */
		public boolean isAlwaysConfirmAuthorization(){
			return alwaysConfirmAuthorization;
		}

		/**
		 * 设置是否总是需要确认授权
		 *
		 * @param alwaysConfirmAuthorization
		 * 		是否总是需要确认授权
		 */
		public void setAlwaysConfirmAuthorization(boolean alwaysConfirmAuthorization){
			this.alwaysConfirmAuthorization = alwaysConfirmAuthorization;
		}

		/**
		 * 返回是否包含 E-mail
		 *
		 * @return 是否包含 E-mail
		 */
		public boolean isIncludeEmail(){
			return includeEmail;
		}

		/**
		 * 设置是否包含 E-mail
		 *
		 * @param includeEmail
		 * 		是否包含 E-mail
		 */
		public void setIncludeEmail(boolean includeEmail){
			this.includeEmail = includeEmail;
		}

	}

	/**
	 * Yahoo 配置
	 */
	public final static class Yahoo extends BaseOAuth10Config {

		public Yahoo(){
			super("yahoo");
		}

	}

	/**
	 * 通用 OAuth 2 配置
	 */
	public final static class Generic extends BaseOAuth20Config {

		private String authUrl;

		private String tokenUrl;

		private String profileUrl;

		private String profilePath;

		private String profileId;

		private String clientAuthenticationMethod;

		private Verb profileVerb;

		private Map<String, String> profileAttrs;

		public Generic(){
			super("oauth2");
		}

		public String getAuthUrl(){
			return authUrl;
		}

		public void setAuthUrl(String authUrl){
			this.authUrl = authUrl;
		}

		public String getTokenUrl(){
			return tokenUrl;
		}

		public void setTokenUrl(String tokenUrl){
			this.tokenUrl = tokenUrl;
		}

		public String getProfileUrl(){
			return profileUrl;
		}

		public void setProfileUrl(String profileUrl){
			this.profileUrl = profileUrl;
		}

		public String getProfilePath(){
			return profilePath;
		}

		public void setProfilePath(String profilePath){
			this.profilePath = profilePath;
		}

		public String getProfileId(){
			return profileId;
		}

		public void setProfileId(String profileId){
			this.profileId = profileId;
		}

		public String getClientAuthenticationMethod(){
			return clientAuthenticationMethod;
		}

		public void setClientAuthenticationMethod(String clientAuthenticationMethod){
			this.clientAuthenticationMethod = clientAuthenticationMethod;
		}

		public Verb getProfileVerb(){
			return profileVerb;
		}

		public void setProfileVerb(Verb profileVerb){
			this.profileVerb = profileVerb;
		}

		public Map<String, String> getProfileAttrs(){
			return profileAttrs;
		}

		public void setProfileAttrs(Map<String, String> profileAttrs){
			this.profileAttrs = profileAttrs;
		}

	}

	/**
	 * CAS 配置
	 */
	public final static class Cas extends BaseOAuth20Config {

		private String casOAuthUrl;

		private String casLogoutUrl;

		private boolean springSecurityCompliant = false;

		private boolean implicitFlow = false;

		public Cas(){
			super("cas");
		}

		public String getCasOAuthUrl(){
			return casOAuthUrl;
		}

		public void setCasOAuthUrl(String casOAuthUrl){
			this.casOAuthUrl = casOAuthUrl;
		}

		public String getCasLogoutUrl(){
			return casLogoutUrl;
		}

		public void setCasLogoutUrl(String casLogoutUrl){
			this.casLogoutUrl = casLogoutUrl;
		}

		public boolean isSpringSecurityCompliant(){
			return springSecurityCompliant;
		}

		public void setSpringSecurityCompliant(boolean springSecurityCompliant){
			this.springSecurityCompliant = springSecurityCompliant;
		}

		public boolean isImplicitFlow(){
			return implicitFlow;
		}

		public void setImplicitFlow(boolean implicitFlow){
			this.implicitFlow = implicitFlow;
		}

	}

	/**
	 * DropBox 配置
	 */
	public final static class DropBox extends BaseOAuth20Config {

		public DropBox(){
			super("dropbox");
		}

	}

	/**
	 * Facebook 配置
	 */
	public final static class Facebook extends BaseOAuth20Config {

		private String fields;

		private int limit = FacebookProfileDefinition.DEFAULT_LIMIT;

		public Facebook(){
			super("facebook");
		}

		public String getFields(){
			return fields;
		}

		public void setFields(String fields){
			this.fields = fields;
		}

		public int getLimit(){
			return limit;
		}

		public void setLimit(int limit){
			this.limit = limit;
		}
	}

	/**
	 * FigShare 配置
	 */
	public final static class FigShare extends BaseOAuth20Config {

		public FigShare(){
			super("FigShare");
		}

	}

	/**
	 * Foursquare 配置
	 */
	public final static class Foursquare extends BaseOAuth20Config {

		public Foursquare(){
			super("foursquare");
		}

	}

	/**
	 * GitHub 配置
	 */
	public final static class GitHub extends BaseOAuth20Config {

		public GitHub(){
			super("github");
		}

	}

	/**
	 * Google2 配置
	 */
	public final static class Google2 extends BaseOAuth20Config {

		public Google2(){
			super("google2");
		}

	}

	/**
	 * HiOrgServer 配置
	 */
	public final static class HiOrgServer extends BaseOAuth20Config {

		public HiOrgServer(){
			super("HiOrgServer");
		}

	}

	/**
	 * LinkedIn2 配置
	 */
	public final static class LinkedIn2 extends BaseOAuth20Config {

		public LinkedIn2(){
			super("linkedIn2");
		}

	}

	/**
	 * OK 配置
	 */
	public final static class Ok extends BaseOAuth20Config {

		private String publicKey;

		public Ok(){
			super("ok");
		}

		public String getPublicKey(){
			return publicKey;
		}

		public void setPublicKey(String publicKey){
			this.publicKey = publicKey;
		}

	}

	/**
	 * PayPal 配置
	 */
	public final static class PayPal extends BaseOAuth20Config {

		public PayPal(){
			super("paypal");
		}

	}

	/**
	 * QQ 配置
	 */
	public final static class Qq extends BaseOAuth20Config {

		private List<QQClient.QQScope> scopes;

		public Qq(){
			super("qq");
		}

		public List<QQClient.QQScope> getScopes(){
			return scopes;
		}

		public void setScopes(List<QQClient.QQScope> scopes){
			this.scopes = scopes;
		}

	}

	/**
	 * Strava 配置
	 */
	public final static class Strava extends BaseOAuth20Config {

		private String approvalPrompt = "auto";

		public Strava(){
			super("strava");
		}

		public String getApprovalPrompt(){
			return approvalPrompt;
		}

		public void setApprovalPrompt(String approvalPrompt){
			this.approvalPrompt = approvalPrompt;
		}

	}

	/**
	 * Vk 配置
	 */
	public final static class Vk extends BaseOAuth20Config {

		public Vk(){
			super("vk");
		}

	}

	/**
	 * 微博配置
	 */
	public final static class Weibo extends BaseOAuth20Config {

		public Weibo(){
			super("weibo");
		}

	}

	/**
	 * 微信配置
	 */
	public final static class Wechat extends BaseOAuth20Config {

		private List<WechatClient.WechatScope> scopes;

		public Wechat(){
			super("wechat");
		}

		public List<WechatClient.WechatScope> getScopes(){
			return scopes;
		}

		public void setScopes(List<WechatClient.WechatScope> scopes){
			this.scopes = scopes;
		}

	}

	/**
	 * Windows Live 配置
	 */
	public final static class WindowsLive extends BaseOAuth20Config {

		public WindowsLive(){
			super("WindowsLive");
		}

	}

	/**
	 * WordPress 配置
	 */
	public final static class WordPress extends BaseOAuth20Config {

		public WordPress(){
			super("WordPress");
		}

	}

}
