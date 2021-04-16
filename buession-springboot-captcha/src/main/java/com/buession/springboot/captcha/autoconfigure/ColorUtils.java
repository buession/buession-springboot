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
import com.buession.security.captcha.color.ColorFactory;
import com.buession.security.captcha.color.RandomColorFactory;
import com.buession.security.captcha.color.SingleColorFactory;

import java.awt.*;

/**
 * @author Yong.Teng
 * @since 1.0.0
 */
class ColorUtils {

	private ColorUtils(){

	}

	public final static ColorFactory createColorFactory(final int min, final int max){
		if(min >= 0 && max <= 255 && max >= 0 && max <= 255){
			return new RandomColorFactory(min, max);
		}else{
			int colorRgb = RandomUtils.nextInt(min, max);
			return new SingleColorFactory(new Color(colorRgb));
		}
	}

	public final static ColorFactory createColorFactory(final Image.Color color){
		return createColorFactory(color.getMinColor(), color.getMaxColor());
	}

}
