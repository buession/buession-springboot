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
 * | Copyright @ 2013-2020 Buession.com Inc.														       |
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
import com.buession.springboot.captcha.Color;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 图片验证码配置
 *
 * @author Yong.Teng
 * @since 1.2.0
 */
@ConfigurationProperties(prefix = "spring.captcha.image")
public class ImageProperties {

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
	private ImageType imageType = ImageType.PNG;

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
	 * 验证码 URL Path
	 */
	private String verifyCodePath = "/verifyCode/verifyCode";

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

	public ImageType getImageType(){
		return imageType;
	}

	public void setImageType(ImageType imageType){
		this.imageType = imageType;
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

	public String getVerifyCodePath(){
		return verifyCodePath;
	}

	public void setVerifyCodePath(String verifyCodePath){
		this.verifyCodePath = verifyCodePath;
	}

	public final static class Background {

		/**
		 * 背景类型
		 */
		private Type type = Type.SINGLE;

		/**
		 * 简单背景
		 */
		private Single single = new Single();

		/**
		 * 圆圈背景
		 */
		private Oval oval = new Oval();

		public Type getType(){
			return type;
		}

		public void setType(Type type){
			this.type = type;
		}

		public Single getSingle(){
			return single;
		}

		public void setSingle(Single single){
			this.single = single;
		}

		public Oval getOval(){
			return oval;
		}

		public void setOval(Oval oval){
			this.oval = oval;
		}

		public enum Type {

			SINGLE,

			OVAL

		}

		public final static class Single {

			/**
			 * 颜色
			 */
			private Color color = new Color(java.awt.Color.WHITE.getRed(), java.awt.Color.WHITE.getGreen(),
					java.awt.Color.WHITE.getBlue());

			/**
			 * 透明度
			 */
			private float alpha = 1F;

			public Color getColor(){
				return color;
			}

			public void setColor(Color color){
				this.color = color;
			}

			public float getAlpha(){
				return alpha;
			}

			public void setAlpha(float alpha){
				this.alpha = alpha;
			}

		}

		public final static class Oval {

			/**
			 * 最小颜色值
			 */
			private int minColor = 150;

			/**
			 * 最大颜色值
			 */
			private int maxColor = 250;

			/**
			 * 圆圈数量
			 */
			//private int ovalCount = OvalBackgroundFactory.DEFAULT_OVAL_COUNT;

			/**
			 * 线条宽度
			 */
			//private float strokeWidth = OvalBackgroundFactory.DEFAULT_STROKE_WIDTH;

			/**
			 * 透明度
			 */
			private float alpha = 1F;

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

			/*public int getOvalCount(){
				return ovalCount;
			}

			public void setOvalCount(int ovalCount){
				this.ovalCount = ovalCount;
			}

			public float getStrokeWidth(){
				return strokeWidth;
			}

			public void setStrokeWidth(float strokeWidth){
				this.strokeWidth = strokeWidth;
			}*/

			public float getAlpha(){
				return alpha;
			}

			public void setAlpha(float alpha){
				this.alpha = alpha;
			}

		}
	}

	public final static class Font {

		/**
		 * 字体类型
		 */
		private Type type = Type.SINGLE;

		/**
		 * 简单字体
		 */
		private Single single = new Single();

		/**
		 * 随机字体
		 */
		private Random random = new Random();

		/**
		 * 字体颜色
		 */
		private Color color = new Color();

		public Type getType(){
			return type;
		}

		public void setType(Type type){
			this.type = type;
		}

		public Single getSingle(){
			return single;
		}

		public void setSingle(Single single){
			this.single = single;
		}

		public Random getRandom(){
			return random;
		}

		public void setRandom(Random random){
			this.random = random;
		}

		public Color getColor(){
			return color;
		}

		public void setColor(Color color){
			this.color = color;
		}

		public enum Type {

			SINGLE,

			RANDOM

		}

		public final static class Single {

			/**
			 * 字体名称
			 */
			private String name = "Arial";

			/**
			 * 字体大小
			 */
			private int size = 56;

			/**
			 * 字体样式
			 */
			private int style = java.awt.Font.ITALIC | java.awt.Font.BOLD;

			public String getName(){
				return name;
			}

			public void setName(String name){
				this.name = name;
			}

			public int getSize(){
				return size;
			}

			public void setSize(int size){
				this.size = size;
			}

			public int getStyle(){
				return style;
			}

			public void setStyle(int style){
				this.style = style;
			}

		}

		public final static class Random {

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

			public int getStyle(){
				return style;
			}

			public void setStyle(int style){
				this.style = style;
			}

		}

		public final static class Color {

			/**
			 * 字体颜色类型
			 */
			private Type type = Type.SINGLE;

			/**
			 * 单一字体颜色
			 */
			private Single single = new Single();

			/**
			 * 随机字体颜色
			 */
			private Random random = new Random();

			public Type getType(){
				return type;
			}

			public void setType(Type type){
				this.type = type;
			}

			public Single getSingle(){
				return single;
			}

			public void setSingle(Single single){
				this.single = single;
			}

			public Random getRandom(){
				return random;
			}

			public void setRandom(Random random){
				this.random = random;
			}

			public enum Type {

				SINGLE,

				RANDOM

			}

			public final static class Single {

				/**
				 * 颜色值
				 */
				private java.awt.Color color = java.awt.Color.BLACK;

				public java.awt.Color getColor(){
					return color;
				}

				public void setColor(java.awt.Color color){
					this.color = color;
				}

			}

			public final static class Random {

				/**
				 * 最小颜色值
				 */
				private int minColor = 0;

				/**
				 * 最大颜色值
				 */
				private int maxColor = 255;

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

}
