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
package com.buession.springboot.pac4j.autoconfigure;

import com.buession.core.Customizer;
import com.buession.core.utils.EnumUtils;
import com.buession.springboot.pac4j.config.OAuth;
import org.pac4j.oauth.client.*;
import org.pac4j.oauth.config.OAuth10Configuration;
import org.pac4j.oauth.config.OAuth20Configuration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Pac4j HTTP 自动配置类
 *
 * @author Yong.Teng
 * @since 2.0.0
 */
@AutoConfiguration(before = {Pac4jConfiguration.class})
@EnableConfigurationProperties(Pac4jProperties.class)
@ConditionalOnClass({OAuth10Client.class, OAuth20Client.class})
public class Pac4jOAuthConfiguration extends AbstractPac4jClientConfiguration<OAuth> {

	public Pac4jOAuthConfiguration(Pac4jProperties properties) {
		super(properties, properties.getClient().getOAuth());
	}

	@Bean(name = "bitbucketClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "bitbucket.enabled")
	public BitbucketClient bitbucketClient(ObjectProvider<Customizer<BitbucketClient>> customizers) {
		final OAuth.Bitbucket bitbucket = config.getBitbucket();
		final BitbucketClient bitbucketClient = new BitbucketClient(bitbucket.getKey(), bitbucket.getSecret());

		initOAuth10Client(bitbucketClient, bitbucket, customizers);

		return bitbucketClient;
	}

	@Bean(name = "casOAuthWrapperClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "cas.enabled")
	public CasOAuthWrapperClient casOAuthWrapperClient(ObjectProvider<Customizer<CasOAuthWrapperClient>> customizers) {
		final OAuth.Cas cas = config.getCas();
		final CasOAuthWrapperClient casOAuthWrapperClient = new CasOAuthWrapperClient(cas.getKey(), cas.getSecret(),
				cas.getCasOAuthUrl());

		casOAuthWrapperClient.setName(cas.getCasOAuthUrl());

		nonNullpropertyMapper.from(cas::getImplicitFlow).to(casOAuthWrapperClient::setImplicitFlow);
		hasTextpropertyMapper.from(cas.getCasLogoutUrl()).to(casOAuthWrapperClient::setCasLogoutUrl);
		nonNullpropertyMapper.from(cas::getAccessTokenVerb).to(casOAuthWrapperClient::setAccessTokenVerb);

		initOAuth20Client(casOAuthWrapperClient, cas, customizers);

		return casOAuthWrapperClient;
	}

	@Bean(name = "dropboxClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "dropbox.enabled")
	public DropBoxClient dropboxClient(ObjectProvider<Customizer<DropBoxClient>> customizers) {
		final OAuth.DropBox dropBox = config.getDropBox();
		final DropBoxClient dropBoxClient = new DropBoxClient(dropBox.getKey(), dropBox.getSecret());

		initOAuth20Client(dropBoxClient, dropBox, customizers);

		return dropBoxClient;
	}

	@Bean(name = "facebookClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "facebook.enabled")
	public FacebookClient facebookClient(ObjectProvider<Customizer<FacebookClient>> customizers) {
		final OAuth.Facebook facebook = config.getFacebook();
		final FacebookClient facebookClient = new FacebookClient(facebook.getKey(), facebook.getSecret());

		nonNullpropertyMapper.from(facebook::getFields).to(facebookClient::setFields);
		nonNullpropertyMapper.from(facebook::getLimit).to(facebookClient::setLimit);
		initOAuth20Client(facebookClient, facebook, customizers);

		return facebookClient;
	}

	@Bean(name = "figShareClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "fig-share.enabled")
	public FigShareClient figShareClient(ObjectProvider<Customizer<FigShareClient>> customizers) {
		final OAuth.FigShare figShare = config.getFigShare();
		final FigShareClient figShareClient = new FigShareClient();

		figShareClient.setKey(figShare.getKey());
		figShareClient.setSecret(figShare.getSecret());
		initOAuth20Client(figShareClient, figShare, customizers);

		return figShareClient;
	}

	@Bean(name = "foursquareClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "foursquare.enabled")
	public FoursquareClient foursquareClient(ObjectProvider<Customizer<FoursquareClient>> customizers) {
		final OAuth.Foursquare foursquare = config.getFoursquare();
		final FoursquareClient foursquareClient = new FoursquareClient(foursquare.getKey(), foursquare.getSecret());

		initOAuth20Client(foursquareClient, foursquare, customizers);

		return foursquareClient;
	}

	@Bean(name = "genericOAuth20Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "generic.enabled")
	public GenericOAuth20Client genericOAuth20Client(ObjectProvider<Customizer<GenericOAuth20Client>> customizers) {
		final OAuth.Generic generic = config.getGeneric();
		final GenericOAuth20Client genericOAuth20Client = new GenericOAuth20Client();

		genericOAuth20Client.setKey(generic.getKey());
		genericOAuth20Client.setSecret(generic.getSecret());
		hasTextpropertyMapper.from(generic.getAuthUrl()).to(genericOAuth20Client::setAuthUrl);
		hasTextpropertyMapper.from(generic.getTokenUrl()).to(genericOAuth20Client::setTokenUrl);
		hasTextpropertyMapper.from(generic.getProfileUrl()).to(genericOAuth20Client::setProfileUrl);
		hasTextpropertyMapper.from(generic.getProfilePath()).to(genericOAuth20Client::setProfilePath);
		hasTextpropertyMapper.from(generic.getProfileId()).to(genericOAuth20Client::setProfileId);
		hasTextpropertyMapper.from(generic.getClientAuthenticationMethod())
				.to(genericOAuth20Client::setClientAuthenticationMethod);
		hasTextpropertyMapper.from(generic.getProfileVerb()).to(genericOAuth20Client::setProfileVerb);
		hasTextpropertyMapper.from(generic.getProfileAttrs()).to(genericOAuth20Client::setProfileAttrs);
		initOAuth20Client(genericOAuth20Client, generic, customizers);

		return genericOAuth20Client;
	}

	@Bean(name = "githubClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "github.enabled")
	public GitHubClient githubClient(ObjectProvider<Customizer<GitHubClient>> customizers) {
		final OAuth.GitHub github = config.getGitHub();
		final GitHubClient gitHubClient = new GitHubClient(github.getKey(), github.getSecret());

		initOAuth20Client(gitHubClient, github, customizers);

		return gitHubClient;
	}

	@Bean(name = "google2Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "google2.enabled")
	public Google2Client google2Client(ObjectProvider<Customizer<Google2Client>> customizers) {
		final OAuth.Google2 google2 = config.getGoogle2();
		final Google2Client google2Client = new Google2Client(google2.getKey(), google2.getSecret());

		nonNullpropertyMapper.from(google2::getScope)
				.as((v)->EnumUtils.getEnumIgnoreCase(Google2Client.Google2Scope.class, v)).to(google2Client::setScope);
		initOAuth20Client(google2Client, google2, customizers);

		return google2Client;
	}

	@Bean(name = "hiOrgServerClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "hi-org-server.enabled")
	public HiOrgServerClient hiOrgServerClient(ObjectProvider<Customizer<HiOrgServerClient>> customizers) {
		final OAuth.HiOrgServer hiOrgServer = config.getHiOrgServer();
		final HiOrgServerClient hiOrgServerClient = new HiOrgServerClient(hiOrgServer.getKey(),
				hiOrgServer.getSecret());

		initOAuth20Client(hiOrgServerClient, hiOrgServer, customizers);

		return hiOrgServerClient;
	}

	@Bean(name = "linkedin2Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "linkedin2.enabled")
	public LinkedIn2Client linkedin2Client(ObjectProvider<Customizer<LinkedIn2Client>> customizers) {
		final OAuth.LinkedIn2 linkedIn2 = config.getLinkedIn2();
		final LinkedIn2Client linkedIn2Client = new LinkedIn2Client(linkedIn2.getKey(), linkedIn2.getSecret());

		initOAuth20Client(linkedIn2Client, linkedIn2, customizers);

		return linkedIn2Client;
	}

	@Bean(name = "okClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "ok.enabled")
	public OkClient okClient(ObjectProvider<Customizer<OkClient>> customizers) {
		final OAuth.Ok ok = config.getOk();
		final OkClient okClient = new OkClient(ok.getKey(), ok.getSecret(), ok.getPublicKey());

		initOAuth20Client(okClient, ok, customizers);

		return okClient;
	}

	@Bean(name = "paypalClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "paypal.enabled")
	public PayPalClient paypalClient(ObjectProvider<Customizer<PayPalClient>> customizers) {
		final OAuth.PayPal payPal = config.getPayPal();
		final PayPalClient payPalClient = new PayPalClient(payPal.getKey(), payPal.getSecret());

		initOAuth20Client(payPalClient, payPal, customizers);

		return payPalClient;
	}

	@Bean(name = "qqClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "qq.enabled")
	public QQClient qqClient(ObjectProvider<Customizer<QQClient>> customizers) {
		final OAuth.Qq qq = config.getQq();
		final QQClient qqClient = new QQClient(qq.getKey(), qq.getSecret());

		nonNullpropertyMapper.from(qq::getScopes).to(qqClient::setScopes);
		initOAuth20Client(qqClient, qq, customizers);

		return qqClient;
	}

	@Bean(name = "stravaClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "strava.enabled")
	public StravaClient stravaClient(ObjectProvider<Customizer<StravaClient>> customizers) {
		final OAuth.Strava strava = config.getStrava();
		final StravaClient stravaClient = new StravaClient(strava.getKey(), strava.getSecret());

		nonNullpropertyMapper.from(strava::getApprovalPrompt).to(stravaClient::setApprovalPrompt);
		initOAuth20Client(stravaClient, strava, customizers);

		return stravaClient;
	}

	@Bean(name = "twitterClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "twitter.enabled")
	public TwitterClient twitterClient(ObjectProvider<Customizer<TwitterClient>> customizers) {
		final OAuth.Twitter twitter = config.getTwitter();
		final TwitterClient twitterClient = new TwitterClient(twitter.getKey(), twitter.getSecret());

		nonNullpropertyMapper.from(twitter::getAlwaysConfirmAuthorization)
				.to(twitterClient::setAlwaysConfirmAuthorization);
		nonNullpropertyMapper.from(twitter::getIncludeEmail).to(twitterClient::setIncludeEmail);
		initOAuth10Client(twitterClient, twitter, customizers);

		return twitterClient;
	}

	@Bean(name = "vkClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "vk.enabled")
	public VkClient vkClient(ObjectProvider<Customizer<VkClient>> customizers) {
		final OAuth.Vk vk = config.getVk();
		final VkClient vkClient = new VkClient(vk.getKey(), vk.getSecret());

		initOAuth20Client(vkClient, vk, customizers);

		return vkClient;
	}

	@Bean(name = "wechatClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "wechat.enabled")
	public WechatClient wechatClient(ObjectProvider<Customizer<WechatClient>> customizers) {
		final OAuth.Wechat wechat = config.getWechat();
		final WechatClient wechatClient = new WechatClient(wechat.getKey(), wechat.getSecret());

		nonNullpropertyMapper.from(wechat::getScopes).to(wechatClient::setScopes);
		initOAuth20Client(wechatClient, wechat, customizers);

		return wechatClient;
	}

	@Bean(name = "weiboClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "weibo.enabled")
	public WeiboClient weiboClient(ObjectProvider<Customizer<WeiboClient>> customizers) {
		final OAuth.Weibo weibo = config.getWeibo();
		final WeiboClient weiboClient = new WeiboClient(weibo.getKey(), weibo.getSecret());

		nonNullpropertyMapper.from(weibo::getScope)
				.as((v)->EnumUtils.getEnumIgnoreCase(WeiboClient.WeiboScope.class, v)).to(weiboClient::setScope);
		initOAuth20Client(weiboClient, weibo, customizers);

		return weiboClient;
	}

	@Bean(name = "windowsLiveClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "windows-live.enabled")
	public WindowsLiveClient windowsLiveClient(ObjectProvider<Customizer<WindowsLiveClient>> customizers) {
		final OAuth.WindowsLive windowsLive = config.getWindowsLive();
		final WindowsLiveClient windowsLiveClient = new WindowsLiveClient(windowsLive.getKey(),
				windowsLive.getSecret());

		initOAuth20Client(windowsLiveClient, windowsLive, customizers);

		return windowsLiveClient;
	}

	@Bean(name = "wordpressClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "word-press.enabled")
	public WordPressClient wordPressClient(ObjectProvider<Customizer<WordPressClient>> customizers) {
		final OAuth.WordPress wordPress = config.getWordPress();
		final WordPressClient wordPressClient = new WordPressClient(wordPress.getKey(), wordPress.getSecret());

		initOAuth20Client(wordPressClient, wordPress, customizers);

		return wordPressClient;
	}

	@Bean(name = "yahooClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "yahoo.enabled")
	public YahooClient yahooClient(ObjectProvider<Customizer<YahooClient>> customizers) {
		final OAuth.Yahoo yahoo = config.getYahoo();
		final YahooClient yahooClient = new YahooClient(yahoo.getKey(), yahoo.getSecret());

		initOAuth10Client(yahooClient, yahoo, customizers);

		return yahooClient;
	}

	// ********************************************* //
	// *************** end oauth 2.0 *************** //
	// ********************************************* //

	protected <C extends OAuth10Client> void initOAuth10Client(final C client,
	                                                           final OAuth.BaseOAuth10Config oAuth10Config,
	                                                           final ObjectProvider<Customizer<C>> customizers) {
		final OAuth10Configuration configuration = client.getConfiguration();

		nonNullpropertyMapper.from(oAuth10Config::getResponseType).to(configuration::setResponseType);
		nonNullpropertyMapper.from(oAuth10Config::getScope).to(configuration::setScope);
		nonNullpropertyMapper.from(oAuth10Config::getTokenAsHeader).to(configuration::setTokenAsHeader);

		afterIndirectClientInitialized(client, config, oAuth10Config, customizers);
	}

	protected <C extends OAuth20Client> void initOAuth20Client(final C client,
	                                                           final OAuth.BaseOAuth20Config oAuth20Config,
	                                                           final ObjectProvider<Customizer<C>> customizers) {
		final OAuth20Configuration configuration = client.getConfiguration();

		nonNullpropertyMapper.from(oAuth20Config::getResponseType).to(configuration::setResponseType);
		nonNullpropertyMapper.from(oAuth20Config::getScope).to(configuration::setScope);
		nonNullpropertyMapper.from(oAuth20Config::getTokenAsHeader).to(configuration::setTokenAsHeader);
		nonNullpropertyMapper.from(oAuth20Config::getCustomParameters).to(configuration::setCustomParams);
		nonNullpropertyMapper.from(oAuth20Config::getWithState).to(configuration::setWithState);

		afterIndirectClientInitialized(client, config, oAuth20Config, customizers);
	}

}
