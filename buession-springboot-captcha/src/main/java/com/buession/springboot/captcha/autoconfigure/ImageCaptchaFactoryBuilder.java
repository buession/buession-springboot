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

import com.buession.core.utils.RandomUtils;
import com.buession.core.validator.Validate;
import com.buession.security.captcha.background.BackgroundFactory;
import com.buession.security.captcha.background.SingleColorBackgroundFactory;
import com.buession.security.captcha.color.ColorFactory;
import com.buession.security.captcha.font.FontFactory;
import com.buession.security.captcha.font.RandomFontFactory;
import com.buession.security.captcha.font.SingleFontFactory;
import com.buession.security.captcha.handler.generator.Generator;
import com.buession.security.captcha.text.TextFactory;
import com.buession.security.captcha.word.RandomWordFactory;
import com.buession.security.captcha.word.WordFactory;

import java.awt.*;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
class ImageCaptchaFactoryBuilder {

	private Image config;

	private BackgroundFactory backgroundFactory;

	private FontFactory fontFactory;

	private ColorFactory colorFactory;

	private WordFactory wordFactory;

	private TextFactory textFactory;

	private ImageCaptchaFactoryBuilder(final Image config){
		this.config = config;
	}

	public final static ImageCaptchaFactoryBuilder getInstance(final Image config){
		return new ImageCaptchaFactoryBuilder(config);
	}

	public ImageCaptchaFactoryBuilder background(){
		Image.Background background = config.getBackground();

		backgroundFactory = new SingleColorBackgroundFactory(new Color(background.getColor().getR(),
				background.getColor().getG(), background.getColor().getB()), background.getAlpha());

		return this;
	}

	public ImageCaptchaFactoryBuilder font(){
		Image.Font font = config.getFont();

		if(Validate.isEmpty(font.getFamilies()) || font.getFamilies().size() == 1){
			String fontName = SingleFontFactory.DEFAULT_FONT.getName();
			int fontSize = RandomUtils.nextInt(font.getMinSize(), font.getMinSize());

			fontFactory = new SingleFontFactory(fontName, fontSize, font.getStyle());
		}else{
			fontFactory = new RandomFontFactory(font.getFamilies(), font.getMinSize(), font.getMaxSize(),
					font.getStyle());
		}

		Image.Color color = config.getFont().getColor();
		colorFactory = ColorUtils.createColorFactory(color.getMinColor(), color.getMaxColor());

		return this;
	}

	public ImageCaptchaFactoryBuilder word(){
		Image.Word word = config.getWord();

		if(Validate.hasText(word.getContent())){
			wordFactory = new RandomWordFactory(word.getMinLength(), word.getMaxLength(), word.getContent());
		}else if(word.getWordType() != null){
			wordFactory = new RandomWordFactory(word.getMinLength(), word.getMaxLength(), word.getWordType());
		}

		return this;
	}

	public ImageCaptchaFactoryBuilder text() throws IllegalAccessException, InstantiationException{
		Image.Text text = config.getText();
		textFactory = text.getTextFactoryClass().newInstance();

		textFactory.setTopMargin(text.getTopMargin());
		textFactory.setBottomMargin(text.getBottomMargin());
		textFactory.setLeftMargin(textFactory.getLeftMargin());
		textFactory.setRightMargin(textFactory.getRightMargin());

		return this;
	}

	public void build(Generator generator){
		if(backgroundFactory != null){
			generator.setBackgroundFactory(backgroundFactory);
		}

		if(wordFactory != null){
			generator.setWordFactory(wordFactory);
		}

		if(textFactory != null){
			if(fontFactory != null){
				textFactory.setFontFactory(fontFactory);
			}

			if(colorFactory != null){
				textFactory.setColorFactory(colorFactory);
			}

			generator.setTextFactory(textFactory);
		}
	}

}
