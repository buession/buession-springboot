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
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.utils.EnumUtils;
import com.buession.core.validator.Validate;
import com.buession.springboot.pac4j.config.OAuth;
import org.pac4j.core.client.Clients;
import org.pac4j.http.client.direct.ParameterClient;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.pac4j.oauth.client.*;
import org.pac4j.oauth.config.OAuth10Configuration;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Pac4j HTTP 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({JwtAuthenticator.class, ParameterClient.class})
@ConditionalOnProperty(name = OAuth.PREFIX)
@Import({Pac4jConfiguration.class})
public class Pac4jOAuthConfiguration extends AbstractPac4jConfiguration<OAuth> {

	public Pac4jOAuthConfiguration(Pac4jProperties properties, ObjectProvider<Clients> clients){
		super(properties, clients.getIfAvailable());
		this.config = properties.getOAuth();
	}

	// *********************************************** //
	// *************** start oauth 1.0 *************** //
	// *********************************************** //

	@Bean(name = "bitbucketClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "bitbucket.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public BitbucketClient bitbucketClient(){
		final BitbucketClient bitbucketClient = new BitbucketClient(config.getKey(), config.getSecret());

		initOAuth10Client(bitbucketClient, config.getBitbucket());

		return bitbucketClient;
	}

	@Bean(name = "twitterClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "twitter.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public TwitterClient twitterClient(){
		final TwitterClient twitterClient = new TwitterClient(config.getKey(), config.getSecret());

		twitterClient.setAlwaysConfirmAuthorization(config.getTwitter().isAlwaysConfirmAuthorization());
		twitterClient.setIncludeEmail(config.getTwitter().isIncludeEmail());

		initOAuth10Client(twitterClient, config.getTwitter());

		return twitterClient;
	}

	@Bean(name = "yahooClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "yahoo.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public YahooClient yahooClient(){
		final YahooClient yahooClient = new YahooClient(config.getKey(), config.getSecret());

		initOAuth10Client(yahooClient, config.getYahoo());

		return yahooClient;
	}

	// ********************************************* //
	// *************** end oauth 1.0 *************** //
	// ********************************************* //


	// *********************************************** //
	// *************** start oauth 2.0 *************** //
	// *********************************************** //

	@Bean(name = "genericOAuth20Client")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "generic.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public GenericOAuth20Client genericOAuth20Client(){
		final GenericOAuth20Client genericOAuth20Client = new GenericOAuth20Client();

		genericOAuth20Client.setKey(config.getKey());
		genericOAuth20Client.setSecret(config.getSecret());

		PropertyMapper propertyMapper = PropertyMapper.get().alwaysApplyingWhenNonNull();

		propertyMapper.from(config.getGeneric().getAuthUrl()).to(genericOAuth20Client::setAuthUrl);
		propertyMapper.from(config.getGeneric().getTokenUrl()).to(genericOAuth20Client::setTokenUrl);
		propertyMapper.from(config.getGeneric().getProfileUrl()).to(genericOAuth20Client::setProfileUrl);
		propertyMapper.from(config.getGeneric().getProfilePath()).to(genericOAuth20Client::setProfileNodePath);
		propertyMapper.from(config.getGeneric().getProfileId()).to(genericOAuth20Client::setProfileId);
		propertyMapper.from(config.getGeneric().getClientAuthenticationMethod())
				.to(genericOAuth20Client::setClientAuthenticationMethod);
		propertyMapper.from(config.getGeneric().getProfileVerb()).to(genericOAuth20Client::setProfileVerb);
		propertyMapper.from(config.getGeneric().getProfileAttrs()).to(genericOAuth20Client::setProfileAttrs);

		initOAuth20Client(genericOAuth20Client, config.getGeneric());

		return genericOAuth20Client;
	}

	@Bean(name = "casOAuthWrapperClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "cas.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public CasOAuthWrapperClient casOAuthWrapperClient(){
		final CasOAuthWrapperClient casOAuthWrapperClient = new CasOAuthWrapperClient(config.getKey(),
				config.getSecret(), config.getCas().getCasOAuthUrl());

		if(Validate.hasText(config.getCas().getCasLogoutUrl())){
			casOAuthWrapperClient.setCasLogoutUrl(config.getCas().getCasLogoutUrl());
		}

		casOAuthWrapperClient.setSpringSecurityCompliant(config.getCas().isSpringSecurityCompliant());
		casOAuthWrapperClient.setImplicitFlow(config.getCas().isImplicitFlow());

		initOAuth20Client(casOAuthWrapperClient, config.getCas());

		return casOAuthWrapperClient;
	}

	@Bean(name = "dropboxClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "dropbox.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public DropBoxClient dropboxClient(){
		final DropBoxClient dropboxClient = new DropBoxClient(config.getKey(), config.getSecret());

		initOAuth20Client(dropboxClient, config.getDropBox());

		return dropboxClient;
	}

	@Bean(name = "facebookClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "facebook.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public FacebookClient facebookClient(){
		final FacebookClient facebookClient = new FacebookClient(config.getKey(), config.getSecret());

		facebookClient.setFields(config.getFacebook().getFields());
		facebookClient.setLimit(config.getFacebook().getLimit());

		initOAuth20Client(facebookClient, config.getFacebook());

		return facebookClient;
	}

	@Bean(name = "figShareClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = {"figShare.enabled", "fig-share.enabled"}, havingValue = "on")
	@ConditionalOnMissingBean
	public FigShareClient figShareClient(){
		final FigShareClient figShareClient = new FigShareClient();

		figShareClient.setKey(config.getKey());
		figShareClient.setSecret(config.getSecret());

		initOAuth20Client(figShareClient, config.getFigShare());

		return figShareClient;
	}

	@Bean(name = "foursquareClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "foursquare.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public FoursquareClient foursquareClient(){
		final FoursquareClient foursquareClient = new FoursquareClient(config.getKey(), config.getSecret());

		initOAuth20Client(foursquareClient, config.getFoursquare());

		return foursquareClient;
	}

	@Bean(name = "githubClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "github.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public GitHubClient githubClient(){
		final GitHubClient gitHubClient = new GitHubClient(config.getKey(), config.getSecret());

		initOAuth20Client(gitHubClient, config.getGitHub());

		return gitHubClient;
	}

	@Bean(name = "google2Client")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "google2.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public Google2Client google2Client(){
		final Google2Client google2Client = new Google2Client(config.getKey(), config.getSecret());

		initOAuth20Client(google2Client, config.getGoogle2());

		if(Validate.hasText(config.getGoogle2().getScope())){
			Google2Client.Google2Scope scope = EnumUtils.getEnumIgnoreCase(Google2Client.Google2Scope.class,
					config.getGoogle2().getScope());

			if(scope != null){
				google2Client.setScope(scope);
			}
		}

		return google2Client;
	}

	@Bean(name = "google2Client")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "google2.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public HiOrgServerClient hiOrgServerClient(){
		final HiOrgServerClient hiOrgServerClient = new HiOrgServerClient(config.getKey(), config.getSecret());

		initOAuth20Client(hiOrgServerClient, config.getHiOrgServer());

		return hiOrgServerClient;
	}

	@Bean(name = "linkedin2Client")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "linkedin2.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public LinkedIn2Client linkedin2Client(){
		final LinkedIn2Client linkedIn2Client = new LinkedIn2Client(config.getKey(), config.getSecret());

		initOAuth20Client(linkedIn2Client, config.getLinkedIn2());

		return linkedIn2Client;
	}

	@Bean(name = "okClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "ok.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public OkClient okClient(){
		final OkClient okClient = new OkClient(config.getKey(), config.getSecret(), config.getOk().getPublicKey());

		initOAuth20Client(okClient, config.getOk());

		return okClient;
	}

	@Bean(name = "paypalClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "paypal.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public PayPalClient paypalClient(){
		final PayPalClient payPalClient = new PayPalClient(config.getKey(), config.getSecret());

		initOAuth20Client(payPalClient, config.getPayPal());

		return payPalClient;
	}

	@Bean(name = "qqClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "qq.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public QQClient qqClient(){
		final QQClient qqClient = new QQClient(config.getKey(), config.getSecret());

		initOAuth20Client(qqClient, config.getQq());

		if(Validate.isNotEmpty(config.getQq().getScopes())){
			qqClient.setScopes(config.getQq().getScopes());
		}

		return qqClient;
	}

	@Bean(name = "stravaClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "strava.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public StravaClient stravaClient(){
		final StravaClient stravaClient = new StravaClient(config.getKey(), config.getSecret());

		initOAuth20Client(stravaClient, config.getStrava());

		if(Validate.hasText(config.getStrava().getApprovalPrompt())){
			stravaClient.setApprovalPrompt(config.getStrava().getApprovalPrompt());
		}

		return stravaClient;
	}

	@Bean(name = "vkClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "vk.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public VkClient vkClient(){
		final VkClient vkClient = new VkClient(config.getKey(), config.getSecret());

		initOAuth20Client(vkClient, config.getVk());

		return vkClient;
	}

	@Bean(name = "weiboClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "weibo.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public WeiboClient weiboClient(){
		final WeiboClient weiboClient = new WeiboClient(config.getKey(), config.getSecret());

		initOAuth20Client(weiboClient, config.getWeibo());

		if(Validate.hasText(config.getWeibo().getScope())){
			WeiboClient.WeiboScope scope = EnumUtils.getEnumIgnoreCase(WeiboClient.WeiboScope.class,
					config.getWeibo().getScope());

			if(scope != null){
				weiboClient.setScope(scope);
			}
		}

		return weiboClient;
	}

	@Bean(name = "wechatClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "wechat.enabled", havingValue = "on")
	@ConditionalOnMissingBean
	public WechatClient wechatClient(){
		final WechatClient wechatClient = new WechatClient(config.getKey(), config.getSecret());

		initOAuth20Client(wechatClient, config.getWechat());

		if(Validate.isNotEmpty(config.getWechat().getScopes())){
			wechatClient.setScopes(config.getWechat().getScopes());
		}

		return wechatClient;
	}

	@Bean(name = "windowsLiveClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = {"windowsLive.enabled",
			"windows-live.enabled"}, havingValue = "on")
	@ConditionalOnMissingBean
	public WindowsLiveClient windowsLiveClient(){
		final WindowsLiveClient windowsLiveClient = new WindowsLiveClient(config.getKey(), config.getSecret());

		initOAuth20Client(windowsLiveClient, config.getWindowsLive());

		return windowsLiveClient;
	}

	@Bean(name = "wordpressClient")
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = {"wordPress.enabled",
			"word-press.enabled"}, havingValue = "on")
	@ConditionalOnMissingBean
	public WordPressClient wordPressClient(){
		final WordPressClient wordPressClient = new WordPressClient(config.getKey(), config.getSecret());

		initOAuth20Client(wordPressClient, config.getWordPress());

		return wordPressClient;
	}

	// ********************************************* //
	// *************** end oauth 2.0 *************** //
	// ********************************************* //

	protected void initOAuth10Client(final OAuth10Client client, final OAuth.BaseOAuth10Config config){
		final OAuth10Configuration configuration = client.getConfiguration();

		if(Validate.hasText(this.config.getCallbackUrl())){
			client.setCallbackUrl(this.config.getCallbackUrl());
		}

		if(Validate.hasText(config.getResponseType())){
			configuration.setResponseType(config.getResponseType());
		}

		if(Validate.hasText(config.getScope())){
			configuration.setScope(config.getScope());
		}

		configuration.setTokenAsHeader(config.isTokenAsHeader());

		afterClientInitialized(client, config);
	}

	protected void initOAuth20Client(final OAuth20Client client, final OAuth.BaseOAuth20Config config){
		final OAuth20Configuration configuration = client.getConfiguration();

		if(Validate.hasText(this.config.getCallbackUrl())){
			client.setCallbackUrl(this.config.getCallbackUrl());
		}

		if(Validate.hasText(config.getResponseType())){
			configuration.setResponseType(config.getResponseType());
		}

		if(Validate.hasText(config.getScope())){
			configuration.setScope(config.getScope());
		}

		configuration.setWithState(config.isWithState());
		configuration.setTokenAsHeader(config.isTokenAsHeader());
		configuration.setTokenAsHeader(config.isTokenAsHeader());

		if(config.getCustomParameters() != null){
			configuration.setCustomParams(config.getCustomParameters());
		}

		afterClientInitialized(client, config);
	}

}
