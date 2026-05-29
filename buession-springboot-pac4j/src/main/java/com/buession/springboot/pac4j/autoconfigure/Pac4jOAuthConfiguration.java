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
		return new BitbucketClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth10Client(this, config.getBitbucket());
			}

		};
	}

	@Bean(name = "casOAuthWrapperClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "cas.enabled", havingValue = "true")
	public CasOAuthWrapperClient casOAuthWrapperClient(ObjectProvider<Customizer<CasOAuthWrapperClient>> customizers) {
		return new CasOAuthWrapperClient(config.getKey(), config.getSecret(), config.getCas().getCasOAuthUrl()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Cas cas = config.getCas();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(cas::getImplicitFlow).to(this::setImplicitFlow);
				hasTextpropertyMapper.from(cas.getCasLogoutUrl()).to(this::setCasLogoutUrl);
				nonNullpropertyMapper.from(cas::getAccessTokenVerb).to(this::setAccessTokenVerb);

				initOAuth20Client(this, cas);
			}

		};
	}

	@Bean(name = "dropboxClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "dropbox.enabled", havingValue = "true")
	public DropBoxClient dropboxClient(ObjectProvider<Customizer<DropBoxClient>> customizers) {
		return new DropBoxClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getDropBox());
			}

		};
	}

	@Bean(name = "facebookClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "facebook.enabled", havingValue = "true")
	public FacebookClient facebookClient(ObjectProvider<Customizer<FacebookClient>> customizers) {
		return new FacebookClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Facebook facebook = config.getFacebook();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(facebook::getFields).to(this::setFields);
				nonNullpropertyMapper.from(facebook::getLimit).to(this::setLimit);

				initOAuth20Client(this, facebook);
			}

		};
	}

	@Bean(name = "figShareClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "fig-share.enabled", havingValue = "true")
	public FigShareClient figShareClient(ObjectProvider<Customizer<FigShareClient>> customizers) {
		return new FigShareClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);

				this.setKey(config.getKey());
				this.setSecret(config.getSecret());

				initOAuth20Client(this, config.getFigShare());
			}

		};
	}

	@Bean(name = "foursquareClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "foursquare.enabled", havingValue = "true")
	public FoursquareClient foursquareClient(ObjectProvider<Customizer<FoursquareClient>> customizers) {
		return new FoursquareClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getFoursquare());
			}

		};
	}

	@Bean(name = "genericOAuth20Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "generic.enabled", havingValue = "true")
	public GenericOAuth20Client genericOAuth20Client(ObjectProvider<Customizer<GenericOAuth20Client>> customizers) {
		return new GenericOAuth20Client() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Generic generic = config.getGeneric();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				this.setKey(config.getKey());
				this.setSecret(config.getSecret());

				hasTextpropertyMapper.from(generic.getAuthUrl()).to(this::setAuthUrl);
				hasTextpropertyMapper.from(generic.getTokenUrl()).to(this::setTokenUrl);
				hasTextpropertyMapper.from(generic.getProfileUrl()).to(this::setProfileUrl);
				hasTextpropertyMapper.from(generic.getProfilePath()).to(this::setProfilePath);
				hasTextpropertyMapper.from(generic.getProfileId()).to(this::setProfileId);
				hasTextpropertyMapper.from(generic.getClientAuthenticationMethod())
						.to(this::setClientAuthenticationMethod);
				hasTextpropertyMapper.from(generic.getProfileVerb()).to(this::setProfileVerb);
				hasTextpropertyMapper.from(generic.getProfileAttrs()).to(this::setProfileAttrs);

				initOAuth20Client(this, generic);
			}

		};
	}

	@Bean(name = "githubClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "github.enabled", havingValue = "true")
	public GitHubClient githubClient(ObjectProvider<Customizer<GitHubClient>> customizers) {
		return new GitHubClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getGitHub());
			}

		};
	}

	@Bean(name = "google2Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "google2.enabled", havingValue = "true")
	public Google2Client google2Client(ObjectProvider<Customizer<Google2Client>> customizers) {
		return new Google2Client(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				OAuth.Google2 google2 = config.getGoogle2();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(google2::getScope)
						.as((v)->EnumUtils.getEnumIgnoreCase(Google2Client.Google2Scope.class, v)).to(this::setScope);

				initOAuth20Client(this, google2);
			}

		};
	}

	@Bean(name = "hiOrgServerClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "hi-org-server.enabled", havingValue = "true")
	public HiOrgServerClient hiOrgServerClient(ObjectProvider<Customizer<HiOrgServerClient>> customizers) {
		return new HiOrgServerClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getHiOrgServer());
			}

		};
	}

	@Bean(name = "linkedin2Client")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "linkedin2.enabled", havingValue = "true")
	public LinkedIn2Client linkedin2Client(ObjectProvider<Customizer<LinkedIn2Client>> customizers) {
		return new LinkedIn2Client(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getLinkedIn2());
			}

		};
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
		return new PayPalClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getPayPal());
			}

		};
	}

	@Bean(name = "qqClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "qq.enabled", havingValue = "true")
	public QQClient qqClient(ObjectProvider<Customizer<QQClient>> customizers) {
		return new QQClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Qq qq = config.getQq();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(qq::getScopes).to(this::setScopes);

				initOAuth20Client(this, qq);
			}

		};
	}

	@Bean(name = "stravaClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "strava.enabled", havingValue = "true")
	public StravaClient stravaClient(ObjectProvider<Customizer<StravaClient>> customizers) {
		return new StravaClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Strava strava = config.getStrava();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(strava::getApprovalPrompt).to(this::setApprovalPrompt);

				initOAuth20Client(this, strava);
			}

		};
	}

	@Bean(name = "twitterClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "twitter.enabled", havingValue = "true")
	public TwitterClient twitterClient(ObjectProvider<Customizer<TwitterClient>> customizers) {
		return new TwitterClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Twitter twitter = config.getTwitter();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(twitter::getAlwaysConfirmAuthorization)
						.to(this::setAlwaysConfirmAuthorization);
				nonNullpropertyMapper.from(twitter::getIncludeEmail).to(this::setIncludeEmail);

				initOAuth10Client(this, twitter);
			}

		};
	}

	@Bean(name = "vkClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "vk.enabled", havingValue = "true")
	public VkClient vkClient(ObjectProvider<Customizer<VkClient>> customizers) {
		return new VkClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getVk());
			}

		};
	}

	@Bean(name = "wechatClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "wechat.enabled", havingValue = "true")
	public WechatClient wechatClient(ObjectProvider<Customizer<WechatClient>> customizers) {
		return new WechatClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Wechat wechat = config.getWechat();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(wechat::getScopes).to(this::setScopes);

				initOAuth20Client(this, wechat);
			}

		};
	}

	@Bean(name = "weiboClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "weibo.enabled", havingValue = "true")
	public WeiboClient weiboClient(ObjectProvider<Customizer<WeiboClient>> customizers) {
		return new WeiboClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Weibo weibo = config.getWeibo();

				super.internalInit(forceReinit);
				customizer(this, customizers);

				nonNullpropertyMapper.from(weibo::getScope)
						.as((v)->EnumUtils.getEnumIgnoreCase(WeiboClient.WeiboScope.class, v)).to(this::setScope);

				initOAuth20Client(this, weibo);
			}

		};
	}

	@Bean(name = "windowsLiveClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "windows-live.enabled", havingValue = "true")
	public WindowsLiveClient windowsLiveClient(ObjectProvider<Customizer<WindowsLiveClient>> customizers) {
		return new WindowsLiveClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getWindowsLive());
			}

		};
	}

	@Bean(name = "wordpressClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "word-press.enabled", havingValue = "true")
	public WordPressClient wordPressClient(ObjectProvider<Customizer<WordPressClient>> customizers) {
		return new WordPressClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth20Client(this, config.getWordPress());
			}

		};
	}

	@Bean(name = "yahooClient")
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = OAuth.PREFIX, name = "yahoo.enabled", havingValue = "true")
	public YahooClient yahooClient(ObjectProvider<Customizer<YahooClient>> customizers) {
		return new YahooClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				customizer(this, customizers);
				initOAuth10Client(this, config.getYahoo());
			}

		};
	}

	// ********************************************* //
	// *************** end oauth 2.0 *************** //
	// ********************************************* //

	protected <C extends OAuth10Client> void initOAuth10Client(final C client,
	                                                           final OAuth.BaseOAuth10Config oAuth10Config) {
		final OAuth10Configuration configuration = client.getConfiguration();

		nonNullpropertyMapper.from(oAuth10Config::getResponseType).to(configuration::setResponseType);
		nonNullpropertyMapper.from(oAuth10Config::getScope).to(configuration::setScope);
		nonNullpropertyMapper.from(oAuth10Config::getTokenAsHeader).to(configuration::setTokenAsHeader);

		afterIndirectClientInitialized(client, config, oAuth10Config);
	}

	protected <C extends OAuth20Client> void initOAuth20Client(final C client,
	                                                           final OAuth.BaseOAuth20Config oAuth20Config) {
		final OAuth20Configuration configuration = client.getConfiguration();

		nonNullpropertyMapper.from(oAuth20Config::getResponseType).to(configuration::setResponseType);
		nonNullpropertyMapper.from(oAuth20Config::getScope).to(configuration::setScope);
		nonNullpropertyMapper.from(oAuth20Config::getTokenAsHeader).to(configuration::setTokenAsHeader);
		nonNullpropertyMapper.from(oAuth20Config::getCustomParameters).to(configuration::setCustomParams);
		nonNullpropertyMapper.from(oAuth20Config::getWithState).to(configuration::setWithState);

		afterIndirectClientInitialized(client, config, oAuth20Config);
	}

}
