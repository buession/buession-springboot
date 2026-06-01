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
		return new BitbucketClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth10Client(this, config.getBitbucket());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "casOAuthWrapperClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "cas.enabled")
	public CasOAuthWrapperClient casOAuthWrapperClient(ObjectProvider<Customizer<CasOAuthWrapperClient>> customizers) {
		return new CasOAuthWrapperClient(config.getKey(), config.getSecret(), config.getCas().getCasOAuthUrl()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Cas cas = config.getCas();

				nonNullpropertyMapper.from(cas::getImplicitFlow).to(this::setImplicitFlow);
				hasTextpropertyMapper.from(cas.getCasLogoutUrl()).to(this::setCasLogoutUrl);
				nonNullpropertyMapper.from(cas::getAccessTokenVerb).to(this::setAccessTokenVerb);

				super.internalInit(forceReinit);
				initOAuth20Client(this, cas);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "dropboxClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "dropbox.enabled")
	public DropBoxClient dropboxClient(ObjectProvider<Customizer<DropBoxClient>> customizers) {
		return new DropBoxClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getDropBox());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "facebookClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "facebook.enabled")
	public FacebookClient facebookClient(ObjectProvider<Customizer<FacebookClient>> customizers) {
		return new FacebookClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Facebook facebook = config.getFacebook();

				nonNullpropertyMapper.from(facebook::getFields).to(this::setFields);
				nonNullpropertyMapper.from(facebook::getLimit).to(this::setLimit);

				super.internalInit(forceReinit);
				initOAuth20Client(this, facebook);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "figShareClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "fig-share.enabled")
	public FigShareClient figShareClient(ObjectProvider<Customizer<FigShareClient>> customizers) {
		return new FigShareClient() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				this.setKey(config.getKey());
				this.setSecret(config.getSecret());

				super.internalInit(forceReinit);

				initOAuth20Client(this, config.getFigShare());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "foursquareClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "foursquare.enabled")
	public FoursquareClient foursquareClient(ObjectProvider<Customizer<FoursquareClient>> customizers) {
		return new FoursquareClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getFoursquare());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "genericOAuth20Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "generic.enabled")
	public GenericOAuth20Client genericOAuth20Client(ObjectProvider<Customizer<GenericOAuth20Client>> customizers) {
		return new GenericOAuth20Client() {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Generic generic = config.getGeneric();

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

				super.internalInit(forceReinit);

				initOAuth20Client(this, generic);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "githubClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "github.enabled")
	public GitHubClient githubClient(ObjectProvider<Customizer<GitHubClient>> customizers) {
		return new GitHubClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getGitHub());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "google2Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "google2.enabled")
	public Google2Client google2Client(ObjectProvider<Customizer<Google2Client>> customizers) {
		return new Google2Client(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				OAuth.Google2 google2 = config.getGoogle2();

				nonNullpropertyMapper.from(google2::getScope)
						.as((v)->EnumUtils.getEnumIgnoreCase(Google2Client.Google2Scope.class, v)).to(this::setScope);

				super.internalInit(forceReinit);
				initOAuth20Client(this, google2);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "hiOrgServerClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "hi-org-server.enabled")
	public HiOrgServerClient hiOrgServerClient(ObjectProvider<Customizer<HiOrgServerClient>> customizers) {
		return new HiOrgServerClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getHiOrgServer());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "linkedin2Client")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "linkedin2.enabled")
	public LinkedIn2Client linkedin2Client(ObjectProvider<Customizer<LinkedIn2Client>> customizers) {
		return new LinkedIn2Client(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getLinkedIn2());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "okClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "ok.enabled")
	public OkClient okClient(ObjectProvider<Customizer<OkClient>> customizers) {
		final OkClient okClient = new OkClient(config.getKey(), config.getSecret(), config.getOk().getPublicKey());

		initOAuth20Client(okClient, config.getOk());
		customizer(okClient, customizers);

		return okClient;
	}

	@Bean(name = "paypalClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "paypal.enabled")
	public PayPalClient paypalClient(ObjectProvider<Customizer<PayPalClient>> customizers) {
		return new PayPalClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getPayPal());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "qqClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "qq.enabled")
	public QQClient qqClient(ObjectProvider<Customizer<QQClient>> customizers) {
		return new QQClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Qq qq = config.getQq();

				nonNullpropertyMapper.from(qq::getScopes).to(this::setScopes);

				super.internalInit(forceReinit);
				initOAuth20Client(this, qq);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "stravaClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "strava.enabled")
	public StravaClient stravaClient(ObjectProvider<Customizer<StravaClient>> customizers) {
		return new StravaClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Strava strava = config.getStrava();

				nonNullpropertyMapper.from(strava::getApprovalPrompt).to(this::setApprovalPrompt);

				super.internalInit(forceReinit);
				initOAuth20Client(this, strava);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "twitterClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "twitter.enabled")
	public TwitterClient twitterClient(ObjectProvider<Customizer<TwitterClient>> customizers) {
		return new TwitterClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Twitter twitter = config.getTwitter();

				nonNullpropertyMapper.from(twitter::getAlwaysConfirmAuthorization)
						.to(this::setAlwaysConfirmAuthorization);
				nonNullpropertyMapper.from(twitter::getIncludeEmail).to(this::setIncludeEmail);

				super.internalInit(forceReinit);
				initOAuth10Client(this, twitter);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "vkClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "vk.enabled")
	public VkClient vkClient(ObjectProvider<Customizer<VkClient>> customizers) {
		return new VkClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getVk());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "wechatClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "wechat.enabled")
	public WechatClient wechatClient(ObjectProvider<Customizer<WechatClient>> customizers) {
		return new WechatClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Wechat wechat = config.getWechat();

				nonNullpropertyMapper.from(wechat::getScopes).to(this::setScopes);

				super.internalInit(forceReinit);
				initOAuth20Client(this, wechat);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "weiboClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "weibo.enabled")
	public WeiboClient weiboClient(ObjectProvider<Customizer<WeiboClient>> customizers) {
		return new WeiboClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				final OAuth.Weibo weibo = config.getWeibo();

				nonNullpropertyMapper.from(weibo::getScope)
						.as((v)->EnumUtils.getEnumIgnoreCase(WeiboClient.WeiboScope.class, v)).to(this::setScope);

				super.internalInit(forceReinit);
				initOAuth20Client(this, weibo);
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "windowsLiveClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "windows-live.enabled")
	public WindowsLiveClient windowsLiveClient(ObjectProvider<Customizer<WindowsLiveClient>> customizers) {
		return new WindowsLiveClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getWindowsLive());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "wordpressClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "word-press.enabled")
	public WordPressClient wordPressClient(ObjectProvider<Customizer<WordPressClient>> customizers) {
		return new WordPressClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth20Client(this, config.getWordPress());
				customizer(this, customizers);
			}

		};
	}

	@Bean(name = "yahooClient")
	@ConditionalOnMissingBean
	@ConditionalOnBooleanProperty(prefix = OAuth.PREFIX, name = "yahoo.enabled")
	public YahooClient yahooClient(ObjectProvider<Customizer<YahooClient>> customizers) {
		return new YahooClient(config.getKey(), config.getSecret()) {

			@Override
			protected void internalInit(final boolean forceReinit) {
				super.internalInit(forceReinit);
				initOAuth10Client(this, config.getYahoo());
				customizer(this, customizers);
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
