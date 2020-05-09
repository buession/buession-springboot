/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * =================================================================================================
 *
 * This software consists of voluntary contributions made by many individuals on behalf of the
 * Apache Software Foundation. For more information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * +------------------------------------------------------------------------------------------------+
 * | License: http://www.apache.org/licenses/LICENSE-2.0.txt 										|
 * | Author: Yong.Teng <webmaster@buession.com> 													|
 * | Copyright @ 2013-2020 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.banner;

import com.buession.core.Framework;
import com.buession.core.utils.StringUtils;
import com.buession.core.utils.VersionUtils;
import com.github.lalyos.jfiglet.FigletFont;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import javax.crypto.Cipher;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Formatter;
import java.util.Properties;

/**
 * @author Yong.Teng
 */
public abstract class AbstractBanner implements Banner {

	private final static int SEPARATOR_REPEAT_COUNT = 60;

	private final static String LINE_SEPARATOR = String.join(StringUtils.EMPTY, Collections.nCopies
			(SEPARATOR_REPEAT_COUNT, "-"));

	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out){
		String additional = collectEnvironmentInfo(environment, sourceClass);

		try{
			out.println("\u001b[36m");
			out.println(FigletFont.convertOneLine(getTitle()));

			if(StringUtils.isNotBlank(additional) == true){
				out.println(additional);
			}

			out.println("\u001b[0m");
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	protected String getTitle(){
		return "(" + Framework.NAME.toUpperCase() + ")";
	}

	protected String getVersion(){
		return getVersion(getClass());
	}

	protected <T> String getVersion(Class<T> clazz){
		return clazz == null ? null : VersionUtils.determineClassVersion(clazz);
	}

	private String collectEnvironmentInfo(final Environment environment, final Class<?> sourceClass){
		Properties properties = System.getProperties();
		Throwable throwable = null;

		Formatter formatter = new Formatter();

		formatter.format("%n");
		formatter.format("Project Version: %s%n", getVersion());
		formatter.format("%s%n", LINE_SEPARATOR);

		try{
			if(properties.containsKey("BANNER_SKIP") == false){
				formatter.format("Buession Framework Version: %s%n", Framework.VERSION);
				formatter.format("Buession Spring Boot Version: %s%n", getVersion(AbstractBanner.class));
				formatter.format("%s%n", LINE_SEPARATOR);

				formatter.format("Spring Framework Version: %s%n", SpringVersion.getVersion());
				formatter.format("Spring Boot Framework Version: %s%n", SpringBootVersion.getVersion());
				formatter.format("%s%n", LINE_SEPARATOR);

				formatter.format("OS Architecture: %s%n", properties.get("os.arch"));
				formatter.format("OS Name: %s%n", properties.get("os.name"));
				formatter.format("OS Version: %s%n", properties.get("os.version"));
				formatter.format("System Date Time: %s%n", LocalDateTime.now());
				formatter.format("System Temp Directory: %s%n", FileUtils.getTempDirectoryPath());
				formatter.format("User Home: %s%n", FileUtils.getUserDirectoryPath());
				formatter.format("%s%n", LINE_SEPARATOR);

				Runtime runtime = Runtime.getRuntime();

				formatter.format("Java Home: %s%n", properties.get("java.home"));
				formatter.format("Java Vendor: %s%n", properties.get("java.vendor"));
				formatter.format("Java Version: %s%n", properties.get("java.version"));
				formatter.format("JVM Total Memory: %s%n", FileUtils.byteCountToDisplaySize(runtime.totalMemory()));
				formatter.format("JVM Maximum Memory: %s%n", FileUtils.byteCountToDisplaySize(runtime.maxMemory()));
				formatter.format("JVM Free Memory: %s%n", FileUtils.byteCountToDisplaySize(runtime.freeMemory()));
				formatter.format("JCE Installed: %s%n", isJceInstalled() ? "Yes" : "No");
				formatter.format("%s%n", LINE_SEPARATOR);

				injectEnvironmentInfoIntoBanner(formatter, environment, sourceClass);
			}

			return formatter.toString();
		}catch(Throwable t){
			throwable = t;
			throw t;
		}finally{
			closeFormatter(formatter, throwable);
		}
	}

	protected void injectEnvironmentInfoIntoBanner(final Formatter formatter, final Environment environment, final
	Class<?> sourceClass){
	}

	private final static boolean isJceInstalled(){
		try{
			return Cipher.getMaxAllowedKeyLength("AES") == 2147483647;
		}catch(Exception e){
			return false;
		}
	}

	private final static void closeFormatter(Formatter formatter, Throwable throwable){
		try{
			formatter.close();
		}catch(Throwable t){
			if(throwable != null){
				throwable.addSuppressed(t);
			}
		}
	}

}
