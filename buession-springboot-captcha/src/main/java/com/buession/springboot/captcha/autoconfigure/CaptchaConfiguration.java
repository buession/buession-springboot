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
 * | Copyright @ 2013-2021 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.captcha.autoconfigure;

import com.buession.security.captcha.filter.BlurRippleFilterFactory;
import com.buession.security.captcha.filter.ConfigurableFilterFactory;
import com.buession.security.captcha.filter.CurvesRippleFilterFactory;
import com.buession.security.captcha.filter.DiffuseRippleFilterFactory;
import com.buession.security.captcha.filter.DoubleRippleFilterFactory;
import com.buession.security.captcha.filter.FilterFactory;
import com.buession.security.captcha.filter.MarbleRippleFilterFactory;
import com.buession.security.captcha.filter.OvalFilterFactory;
import com.buession.security.captcha.filter.SoftenRippleFilterFactory;
import com.buession.security.captcha.filter.WobbleRippleFilterFactory;
import com.buession.security.captcha.filter.operation.CurvesImageOp;
import com.buession.security.captcha.filter.operation.OvalImageOp;
import com.buession.security.captcha.handler.DefaultHandler;
import com.buession.security.captcha.handler.Handler;
import com.buession.security.captcha.handler.generator.Generator;
import com.buession.security.captcha.servlet.CaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yong.Teng
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CaptchaProperties.class)
@ConditionalOnClass({Handler.class})
public class CaptchaConfiguration {

	@Autowired
	protected CaptchaProperties captchaProperties;

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "Configurable")
	public FilterFactory configurableFilterFactory() throws IllegalAccessException, InstantiationException{
		ConfigurableFilterFactory filterFactory = new ConfigurableFilterFactory();
		List<Class<? extends BufferedImageOp>> filterClass =
				captchaProperties.getImage().getFilter().getConfigurable().getFilterClass();

		if(filterClass != null){
			List<BufferedImageOp> filters = new ArrayList<>(filterClass.size());

			for(Class<? extends BufferedImageOp> clazz : filterClass){
				BufferedImageOp bufferedImageOp = clazz.newInstance();

				if(clazz.isAssignableFrom(CurvesImageOp.class)){
					CurvesImageOp curvesImageOp = ((CurvesImageOp) bufferedImageOp);
					Image.Filter.CurvesRipple curvesRipple =
							captchaProperties.getImage().getFilter().getCurvesRipple();

					curvesImageOp.setStrokeMin(curvesRipple.getStrokeMin());
					curvesImageOp.setStrokeMax(curvesRipple.getStrokeMax());
					curvesImageOp.setColorFactory(ColorUtils.createColorFactory(curvesRipple.getColor()));
				}else if(clazz.isAssignableFrom(OvalImageOp.class)){
					OvalImageOp ovalImageOp = ((OvalImageOp) bufferedImageOp);
					Image.Filter.Oval oval = captchaProperties.getImage().getFilter().getOval();

					ovalImageOp.setStrokeMin(oval.getStrokeMin());
					ovalImageOp.setStrokeMax(oval.getStrokeMax());
					ovalImageOp.setColorFactory(ColorUtils.createColorFactory(oval.getColor()));
				}

				filters.add(bufferedImageOp);
			}

			filterFactory.setFilters(filters);
		}

		return filterFactory;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "DiffuseRipple")
	public FilterFactory diffuseRippleFilterFactory(){
		return new DiffuseRippleFilterFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "BlurRipple")
	public FilterFactory blurRippleFilterFactory(){
		return new BlurRippleFilterFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "DoubleRipple")
	public FilterFactory doubleRippleFilterFactory(){
		return new DoubleRippleFilterFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "MarbleRipple")
	public FilterFactory marbleRippleFilterFactory(){
		return new MarbleRippleFilterFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "SoftenRipple")
	public FilterFactory softenRippleFilterFactory(){
		return new SoftenRippleFilterFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "WobbleRipple")
	public FilterFactory wobbleRippleFilterFactory(){
		return new WobbleRippleFilterFactory();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "CurvesRipple")
	public FilterFactory curvesRippleFilterFactory(){
		CurvesRippleFilterFactory filterFactory = new CurvesRippleFilterFactory();
		Image.Filter.CurvesRipple curvesRipple = captchaProperties.getImage().getFilter().getCurvesRipple();

		filterFactory.setStrokeMin(curvesRipple.getStrokeMin());
		filterFactory.setStrokeMax(curvesRipple.getStrokeMax());
		filterFactory.setColorFactory(ColorUtils.createColorFactory(curvesRipple.getColor()));

		return filterFactory;
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(prefix = "spring.captcha.image.filter", name = "mode", havingValue = "Oval",
			matchIfMissing = true)
	public FilterFactory ovalFilterFactory(){
		OvalFilterFactory filterFactory = new OvalFilterFactory();
		Image.Filter.Oval oval = captchaProperties.getImage().getFilter().getOval();

		filterFactory.setStrokeMin(oval.getStrokeMin());
		filterFactory.setStrokeMax(oval.getStrokeMax());
		filterFactory.setColorFactory(ColorUtils.createColorFactory(oval.getColor()));

		return filterFactory;
	}

	@Bean
	@ConditionalOnMissingBean
	public Handler handler(FilterFactory filterFactory) throws InstantiationException, IllegalAccessException{
		Image image = captchaProperties.getImage();
		Generator generator = GeneratorUtils.createGenerator(image.getType());

		generator.setWidth(image.getWidth());
		generator.setHeight(image.getHeight());

		ImageCaptchaFactoryBuilder imageCaptchaFactoryBuilder = ImageCaptchaFactoryBuilder.getInstance(image);
		imageCaptchaFactoryBuilder.background().font().word().text().build(generator);

		generator.setFilterFactory(filterFactory);

		return new DefaultHandler(generator);
	}

	@Configuration
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public static class ServletCaptchaConfiguration extends CaptchaConfiguration {

		@Bean
		public FilterRegistrationBean<CaptchaFilter> registerAuthFilter(Handler handler){
			FilterRegistrationBean<CaptchaFilter> registration = new FilterRegistrationBean<>();

			registration.setFilter(new CaptchaFilter(handler, captchaProperties.getSessionKey()));
			registration.addUrlPatterns(captchaProperties.getVerifyCodePath());
			registration.setName(CaptchaFilter.class.getName());
			registration.setOrder(1);

			return registration;
		}

	}

}
