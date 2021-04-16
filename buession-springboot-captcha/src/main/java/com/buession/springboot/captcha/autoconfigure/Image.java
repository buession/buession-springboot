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

import com.buession.security.captcha.core.ImageType;
import com.buession.security.captcha.core.WordType;
import com.buession.security.captcha.font.RandomFontFactory;
import com.buession.security.captcha.handler.generator.AbstractGenerator;
import com.buession.security.captcha.text.AbstractTextFactory;
import com.buession.security.captcha.text.SimpleTextFactory;
import com.buession.security.captcha.text.TextFactory;

import java.awt.image.BufferedImageOp;
import java.util.List;

/**
 * 图片验证码配置
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
public class Image {

	/**
	 * 验证码图片宽度
	 */
	private int width = AbstractGenerator.DEFAULT_WIDTH;

	/**
	 * 验证码图片高度
	 */
	private int height = AbstractGenerator.DEFAULT_HEIGHT;

	/**
	 * 验证码图片类型
	 */
	private ImageType type = ImageType.PNG;

	/**
	 * 验证码背景
	 */
	private Background background = new Background();

	/**
	 * 验证码字体
	 */
	private Font font = new Font();

	/**
	 * 验证码单词
	 */
	private Word word = new Word();

	/**
	 * 文本
	 */
	private Text text = new Text();

	/**
	 * 过滤器配置
	 */
	private Filter filter = new Filter();

	public int getWidth(){
		return width;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getHeight(){
		return height;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public ImageType getType(){
		return type;
	}

	public void setType(ImageType type){
		this.type = type;
	}

	public Background getBackground(){
		return background;
	}

	public void setBackground(Background background){
		this.background = background;
	}

	public Font getFont(){
		return font;
	}

	public void setFont(Font font){
		this.font = font;
	}

	public Word getWord(){
		return word;
	}

	public void setWord(Word word){
		this.word = word;
	}

	public Text getText(){
		return text;
	}

	public void setText(Text text){
		this.text = text;
	}

	public Filter getFilter(){
		return filter;
	}

	public void setFilter(Filter filter){
		this.filter = filter;
	}

	public final static class Color {

		/**
		 * 最小颜色值
		 */
		private int minColor = 0;

		/**
		 * 最大颜色值
		 */
		private int maxColor = 255;

		public Color(){
		}

		public Color(int minColor, int maxColor){
			this.minColor = minColor;
			this.maxColor = maxColor;
		}

		public int getMinColor(){
			return minColor;
		}

		public void setMinColor(int minColor){
			this.minColor = minColor;
		}

		public int getMaxColor(){
			return maxColor;
		}

		public void setMaxColor(int maxColor){
			this.maxColor = maxColor;
		}

	}

	public final static class Background {

		/**
		 * 颜色
		 */
		private com.buession.springboot.captcha.Color color =
				new com.buession.springboot.captcha.Color(java.awt.Color.WHITE.getRed(),
						java.awt.Color.WHITE.getGreen(), java.awt.Color.WHITE.getBlue());

		/**
		 * 透明度
		 */
		private float alpha = 1F;

		public com.buession.springboot.captcha.Color getColor(){
			return color;
		}

		public void setColor(com.buession.springboot.captcha.Color color){
			this.color = color;
		}

		public float getAlpha(){
			return alpha;
		}

		public void setAlpha(float alpha){
			this.alpha = alpha;
		}

	}

	public final static class Font {

		/**
		 * 字体名称清单
		 */
		private List<String> families = RandomFontFactory.DEFAULT_FAMILIES;

		/**
		 * 最小字号
		 */
		private int minSize = 45;

		/**
		 * 最大字号
		 */
		private int maxSize = 56;

		/**
		 * 字体颜色
		 */
		private Color color = new Color(java.awt.Color.BLACK.getRGB(), java.awt.Color.BLACK.getRGB());

		/**
		 * 字体样式
		 */
		private int style = java.awt.Font.ITALIC | java.awt.Font.BOLD;

		public List<String> getFamilies(){
			return families;
		}

		public void setFamilies(List<String> families){
			this.families = families;
		}

		public int getMinSize(){
			return minSize;
		}

		public void setMinSize(int minSize){
			this.minSize = minSize;
		}

		public int getMaxSize(){
			return maxSize;
		}

		public void setMaxSize(int maxSize){
			this.maxSize = maxSize;
		}

		public Color getColor(){
			return color;
		}

		public void setColor(Color color){
			this.color = color;
		}

		public int getStyle(){
			return style;
		}

		public void setStyle(int style){
			this.style = style;
		}

	}

	public final static class Word {

		/**
		 * 单词类型
		 */
		private WordType wordType;

		/**
		 * 单词内容
		 */
		private String content;

		/**
		 * 验证码最小字符长度
		 */
		private int minLength = 4;

		/**
		 * 验证码最大字符长度
		 */
		private int maxLength = 4;

		public WordType getWordType(){
			return wordType;
		}

		public void setWordType(WordType wordType){
			this.wordType = wordType;
		}

		public String getContent(){
			return content;
		}

		public void setContent(String content){
			this.content = content;
		}

		public int getMinLength(){
			return minLength;
		}

		public void setMinLength(int minLength){
			this.minLength = minLength;
		}

		public int getMaxLength(){
			return maxLength;
		}

		public void setMaxLength(int maxLength){
			this.maxLength = maxLength;
		}

	}

	public final static class Text {

		/**
		 * 文本工厂类
		 */
		private Class<? extends TextFactory> textFactoryClass = SimpleTextFactory.class;

		/**
		 * 上边距
		 */
		private int topMargin = AbstractTextFactory.DEFAULT_MARGIN;

		/**
		 * 下边距
		 */
		private int bottomMargin = AbstractTextFactory.DEFAULT_MARGIN;

		/**
		 * 左边距
		 */
		private int leftMargin = AbstractTextFactory.DEFAULT_MARGIN;

		/**
		 * 右边距
		 */
		private int rightMargin = AbstractTextFactory.DEFAULT_MARGIN;

		public Class<? extends TextFactory> getTextFactoryClass(){
			return textFactoryClass;
		}

		public void setTextFactoryClass(Class<? extends TextFactory> textFactoryClass){
			this.textFactoryClass = textFactoryClass;
		}

		public int getTopMargin(){
			return topMargin;
		}

		public void setTopMargin(int topMargin){
			this.topMargin = topMargin;
		}

		public int getBottomMargin(){
			return bottomMargin;
		}

		public void setBottomMargin(int bottomMargin){
			this.bottomMargin = bottomMargin;
		}

		public int getLeftMargin(){
			return leftMargin;
		}

		public void setLeftMargin(int leftMargin){
			this.leftMargin = leftMargin;
		}

		public int getRightMargin(){
			return rightMargin;
		}

		public void setRightMargin(int rightMargin){
			this.rightMargin = rightMargin;
		}
	}

	public final static class Filter {

		private CurvesRipple curvesRipple = new CurvesRipple();

		private Oval oval = new Oval();

		private Configurable configurable = new Configurable();

		public CurvesRipple getCurvesRipple(){
			return curvesRipple;
		}

		public void setCurvesRipple(CurvesRipple curvesRipple){
			this.curvesRipple = curvesRipple;
		}

		public Oval getOval(){
			return oval;
		}

		public void setOval(Oval oval){
			this.oval = oval;
		}

		public Configurable getConfigurable(){
			return configurable;
		}

		public void setConfigurable(Configurable configurable){
			this.configurable = configurable;
		}

		public final static class CurvesRipple {

			private float strokeMin;

			private float strokeMax;

			private Color color = new Color();

			public float getStrokeMin(){
				return strokeMin;
			}

			public void setStrokeMin(float strokeMin){
				this.strokeMin = strokeMin;
			}

			public float getStrokeMax(){
				return strokeMax;
			}

			public void setStrokeMax(float strokeMax){
				this.strokeMax = strokeMax;
			}

			public Color getColor(){
				return color;
			}

			public void setColor(Color color){
				this.color = color;
			}
		}

		public final static class Oval {

			private int strokeMin;

			private int strokeMax;

			private Color color = new Color();

			public int getStrokeMin(){
				return strokeMin;
			}

			public void setStrokeMin(int strokeMin){
				this.strokeMin = strokeMin;
			}

			public int getStrokeMax(){
				return strokeMax;
			}

			public void setStrokeMax(int strokeMax){
				this.strokeMax = strokeMax;
			}

			public Color getColor(){
				return color;
			}

			public void setColor(Color color){
				this.color = color;
			}

		}

		public final static class Configurable {

			private List<Class<? extends BufferedImageOp>> filterClass;

			public List<Class<? extends BufferedImageOp>> getFilterClass(){
				return filterClass;
			}

			public void setFilterClass(List<Class<? extends BufferedImageOp>> filterClass){
				this.filterClass = filterClass;
			}

		}

	}

}
