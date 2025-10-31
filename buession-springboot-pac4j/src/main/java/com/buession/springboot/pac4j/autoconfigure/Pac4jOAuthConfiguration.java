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
 * | Copyright @ 2013-2025 Buession.com Inc.														       |
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "bitbucket.enabled", havingValue = "true")
	public BitbucketClient bitbucketClient(ObjectProvider<Customizer<BitbucketClient>> customizers) {
		final BitbucketClient bitbucketClient = new BitbucketClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth10Client(bitbucketClient, config.getBitbucket());

		return bitbucketClient;
	}

	@Bean(name = "casOAuthWrapperClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "cas.enabled", havingValue = "true")
	public CasOAuthWrapperClient casOAuthWrapperClient(ObjectProvider<Customizer<CasOAuthWrapperClient>> customizers) {
		final CasOAuthWrapperClient casOAuthWrapperClient = new CasOAuthWrapperClient(config.getKey(),
				config.getSecret(), config.getCas().getCasOAuthUrl()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Cas cas = config.getCas();

		propertyMapper.from(cas::getImplicitFlow).to(casOAuthWrapperClient::setImplicitFlow);
		hasTextpropertyMapper.from(cas.getCasLogoutUrl()).to(casOAuthWrapperClient::setCasLogoutUrl);
		propertyMapper.from(cas::getAccessTokenVerb).to(casOAuthWrapperClient::setAccessTokenVerb);

		initOAuth20Client(casOAuthWrapperClient, cas);

		return casOAuthWrapperClient;
	}

	@Bean(name = "dropboxClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "dropbox.enabled", havingValue = "true")
	public DropBoxClient dropboxClient(ObjectProvider<Customizer<DropBoxClient>> customizers) {
		final DropBoxClient dropboxClient = new DropBoxClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(dropboxClient, config.getDropBox());

		return dropboxClient;
	}

	@Bean(name = "facebookClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "facebook.enabled", havingValue = "true")
	public FacebookClient facebookClient(ObjectProvider<Customizer<FacebookClient>> customizers) {
		final FacebookClient facebookClient = new FacebookClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Facebook facebook = config.getFacebook();

		propertyMapper.from(facebook::getFields).to(facebookClient::setFields);
		propertyMapper.from(facebook::getLimit).to(facebookClient::setLimit);

		initOAuth20Client(facebookClient, facebook);

		return facebookClient;
	}

	@Bean(name = "figShareClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "fig-share.enabled", havingValue = "true")
	public FigShareClient figShareClient(ObjectProvider<Customizer<FigShareClient>> customizers) {
		final FigShareClient figShareClient = new FigShareClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		figShareClient.setKey(config.getKey());
		figShareClient.setSecret(config.getSecret());

		initOAuth20Client(figShareClient, config.getFigShare());

		return figShareClient;
	}

	@Bean(name = "foursquareClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "foursquare.enabled", havingValue = "true")
	public FoursquareClient foursquareClient(ObjectProvider<Customizer<FoursquareClient>> customizers) {
		final FoursquareClient foursquareClient = new FoursquareClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(foursquareClient, config.getFoursquare());

		return foursquareClient;
	}

	@Bean(name = "genericOAuth20Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "generic.enabled", havingValue = "true")
	public GenericOAuth20Client genericOAuth20Client(ObjectProvider<Customizer<GenericOAuth20Client>> customizers) {
		final GenericOAuth20Client genericOAuth20Client = new GenericOAuth20Client() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Generic generic = config.getGeneric();

		genericOAuth20Client.setKey(config.getKey());
		genericOAuth20Client.setSecret(config.getSecret());

		hasTextpropertyMapper.from(generic.getAuthUrl()).to(genericOAuth20Client::setAuthUrl);
		hasTextpropertyMapper.from(generic.getTokenUrl()).to(genericOAuth20Client::setTokenUrl);
		hasTextpropertyMapper.from(generic.getProfileUrl()).to(genericOAuth20Client::setProfileUrl);
		hasTextpropertyMapper.from(generic.getProfilePath()).to(genericOAuth20Client::setProfileNodePath);
		hasTextpropertyMapper.from(generic.getProfileId()).to(genericOAuth20Client::setProfileId);
		hasTextpropertyMapper.from(generic.getClientAuthenticationMethod())
				.to(genericOAuth20Client::setClientAuthenticationMethod);
		hasTextpropertyMapper.from(generic.getProfileVerb()).to(genericOAuth20Client::setProfileVerb);
		hasTextpropertyMapper.from(generic.getProfileAttrs()).to(genericOAuth20Client::setProfileAttrs);

		initOAuth20Client(genericOAuth20Client, generic);

		return genericOAuth20Client;
	}

	@Bean(name = "githubClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "github.enabled", havingValue = "true")
	public GitHubClient githubClient(ObjectProvider<Customizer<GitHubClient>> customizers) {
		final GitHubClient gitHubClient = new GitHubClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(gitHubClient, config.getGitHub());

		return gitHubClient;
	}

	@Bean(name = "google2Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "google2.enabled", havingValue = "true")
	public Google2Client google2Client(ObjectProvider<Customizer<Google2Client>> customizers) {
		final Google2Client google2Client = new Google2Client(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		propertyMapper.from(config.getGoogle2()::getScope)
				.as((v)->EnumUtils.getEnumIgnoreCase(Google2Client.Google2Scope.class, v)).to(google2Client::setScope);

		initOAuth20Client(google2Client, config.getGoogle2());

		return google2Client;
	}

	@Bean(name = "hiOrgServerClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "hi-org-server.enabled", havingValue = "true")
	public HiOrgServerClient hiOrgServerClient(ObjectProvider<Customizer<HiOrgServerClient>> customizers) {
		final HiOrgServerClient hiOrgServerClient = new HiOrgServerClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(hiOrgServerClient, config.getHiOrgServer());

		return hiOrgServerClient;
	}

	@Bean(name = "linkedin2Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "linkedin2.enabled", havingValue = "true")
	public LinkedIn2Client linkedin2Client(ObjectProvider<Customizer<LinkedIn2Client>> customizers) {
		final LinkedIn2Client linkedIn2Client = new LinkedIn2Client(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(linkedIn2Client, config.getLinkedIn2());

		return linkedIn2Client;
	}

	@Bean(name = "okClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "ok.enabled", havingValue = "true")
	public OkClient okClient(ObjectProvider<Customizer<OkClient>> customizers) {
		final OkClient okClient = new OkClient(config.getKey(), config.getSecret(), config.getOk().getPublicKey());

		initOAuth20Client(okClient, config.getOk());

		return okClient;
	}

	@Bean(name = "paypalClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "paypal.enabled", havingValue = "true")
	public PayPalClient paypalClient(ObjectProvider<Customizer<PayPalClient>> customizers) {
		final PayPalClient payPalClient = new PayPalClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(payPalClient, config.getPayPal());

		return payPalClient;
	}

	@Bean(name = "qqClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "qq.enabled", havingValue = "true")
	public QQClient qqClient(ObjectProvider<Customizer<QQClient>> customizers) {
		final QQClient qqClient = new QQClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Qq qq = config.getQq();

		propertyMapper.from(qq::getScopes).to(qqClient::setScopes);

		initOAuth20Client(qqClient, qq);

		return qqClient;
	}

	@Bean(name = "stravaClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "strava.enabled", havingValue = "true")
	public StravaClient stravaClient(ObjectProvider<Customizer<StravaClient>> customizers) {
		final StravaClient stravaClient = new StravaClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Strava strava = config.getStrava();

		propertyMapper.from(strava::getApprovalPrompt).to(stravaClient::setApprovalPrompt);

		initOAuth20Client(stravaClient, strava);

		return stravaClient;
	}

	@Bean(name = "twitterClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "twitter.enabled", havingValue = "true")
	public TwitterClient twitterClient(ObjectProvider<Customizer<TwitterClient>> customizers) {
		final TwitterClient twitterClient = new TwitterClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Twitter twitter = config.getTwitter();

		propertyMapper.from(twitter::getAlwaysConfirmAuthorization).to(twitterClient::setAlwaysConfirmAuthorization);
		propertyMapper.from(twitter::getIncludeEmail).to(twitterClient::setIncludeEmail);

		initOAuth10Client(twitterClient, twitter);

		return twitterClient;
	}

	@Bean(name = "vkClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "vk.enabled", havingValue = "true")
	public VkClient vkClient(ObjectProvider<Customizer<VkClient>> customizers) {
		final VkClient vkClient = new VkClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(vkClient, config.getVk());

		return vkClient;
	}

	@Bean(name = "wechatClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "wechat.enabled", havingValue = "true")
	public WechatClient wechatClient(ObjectProvider<Customizer<WechatClient>> customizers) {
		final WechatClient wechatClient = new WechatClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Wechat wechat = config.getWechat();

		propertyMapper.from(wechat::getScopes).to(wechatClient::setScopes);

		initOAuth20Client(wechatClient, wechat);

		return wechatClient;
	}

	@Bean(name = "weiboClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "weibo.enabled", havingValue = "true")
	public WeiboClient weiboClient(ObjectProvider<Customizer<WeiboClient>> customizers) {
		final WeiboClient weiboClient = new WeiboClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};
		final OAuth.Weibo weibo = config.getWeibo();

		propertyMapper.from(weibo::getScope)
				.as((v)->EnumUtils.getEnumIgnoreCase(WeiboClient.WeiboScope.class, v)).to(weiboClient::setScope);

		initOAuth20Client(weiboClient, weibo);

		return weiboClient;
	}

	@Bean(name = "windowsLiveClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "windows-live.enabled", havingValue = "true")
	public WindowsLiveClient windowsLiveClient(ObjectProvider<Customizer<WindowsLiveClient>> customizers) {
		final WindowsLiveClient windowsLiveClient = new WindowsLiveClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(windowsLiveClient, config.getWindowsLive());

		return windowsLiveClient;
	}

	@Bean(name = "wordpressClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "word-press.enabled", havingValue = "true")
	public WordPressClient wordPressClient(ObjectProvider<Customizer<WordPressClient>> customizers) {
		final WordPressClient wordPressClient = new WordPressClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth20Client(wordPressClient, config.getWordPress());

		return wordPressClient;
	}

	@Bean(name = "yahooClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "yahoo.enabled", havingValue = "true")
	public YahooClient yahooClient(ObjectProvider<Customizer<YahooClient>> customizers) {
		final YahooClient yahooClient = new YahooClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
			}

		};

		initOAuth10Client(yahooClient, config.getYahoo());

		return yahooClient;
	}

	// ********************************************* //
	// *************** end oauth 2.0 *************** //
	// ********************************************* //

	protected <C extends OAuth10Client> void initOAuth10Client(final C client,
															   final OAuth.BaseOAuth10Config oAuth10Config) {
		final OAuth10Configuration configuration = client.getConfiguration();

		propertyMapper.from(oAuth10Config::getResponseType).to(configuration::setResponseType);
		propertyMapper.from(oAuth10Config::getScope).to(configuration::setScope);
		propertyMapper.from(oAuth10Config::getTokenAsHeader).to(configuration::setTokenAsHeader);

		afterIndirectClientInitialized(client, config, oAuth10Config);
	}

	protected <C extends OAuth20Client> void initOAuth20Client(final C client,
															   final OAuth.BaseOAuth20Config oAuth20Config) {
		final OAuth20Configuration configuration = client.getConfiguration();

		propertyMapper.from(oAuth20Config::getResponseType).to(configuration::setResponseType);
		propertyMapper.from(oAuth20Config::getScope).to(configuration::setScope);
		propertyMapper.from(oAuth20Config::getTokenAsHeader).to(configuration::setTokenAsHeader);
		propertyMapper.from(oAuth20Config::getCustomParameters).to(configuration::setCustomParams);
		propertyMapper.from(oAuth20Config::getWithState).to(configuration::setWithState);

		afterIndirectClientInitialized(client, config, oAuth20Config);
	}

}
