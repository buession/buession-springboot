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
 * | Copyright @ 2013-2019 Buession.com Inc.														|
 * +------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.boot.banner;

import com.buession.core.Framework;
import com.buession.core.utils.StringUtils;
import com.github.lalyos.jfiglet.FigletFont;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
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

    private final static String TITLE = Framework.NAME.toUpperCase();

    private final static int SEPARATOR_REPEAT_COUNT = 60;

    private final static String LINE_SEPARATOR = String.join("", Collections.nCopies(SEPARATOR_REPEAT_COUNT, "-"));

    private final static Formatter formatter = new Formatter();

    private final static Properties properties = System.getProperties();

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out){
        printAsciiArt(out, getTitle(), collectEnvironmentInfo(environment, sourceClass));
    }

    protected String getTitle(){
        return "(" + TITLE + ")";
    }

    protected String getVersion(){
        return getVersion(AbstractBanner.class);
    }

    protected <T> String getVersion(Class<T> clazz){
        if(clazz == null){
            return null;
        }

        Package pkg = clazz.getPackage();
        return pkg != null ? pkg.getImplementationVersion() : null;
    }

    private String collectEnvironmentInfo(Environment environment, Class<?> sourceClass){
        Throwable throwable = null;
        String result;

        formatter.format("Project Version: %s%n", new Object[]{getVersion()});
        formatter.format("Buession Framework Version: %s%n", new Object[]{getVersion(Framework.class)});

        if(properties.containsKey("BANNER_SKIP")){
            try{
                result = formatter.toString();
            }catch(Throwable t){
                throwable = t;
                throw t;
            }finally{
                closeFormatter(throwable);
            }
        }else{
            try{
                formatter.format("Spring Boot Framework Version: %s%n", new Object[]{SpringBootVersion.getVersion()});
                formatter.format("%s%n", new Object[]{LINE_SEPARATOR});
                formatter.format("System Date/Time: %s%n", new Object[]{LocalDateTime.now()});
                formatter.format("System Temp Directory: %s%n", new Object[]{properties.get("java.io.tmpdir")});
                formatter.format("%s%n", new Object[]{LINE_SEPARATOR});
                formatter.format("Java Home: %s%n", new Object[]{properties.get("java.home")});
                formatter.format("Java Vendor: %s%n", new Object[]{properties.get("java.vendor")});
                formatter.format("Java Version: %s%n", new Object[]{properties.get("java.version")});
                formatter.format("JCE Installed: %s%n", new Object[]{BooleanUtils.toStringYesNo(isJceInstalled())});
                formatter.format("%s%n", new Object[]{LINE_SEPARATOR});
                formatter.format("OS Architecture: %s%n", new Object[]{properties.get("os.arch")});
                formatter.format("OS Name: %s%n", new Object[]{properties.get("os.name")});
                formatter.format("OS Version: %s%n", new Object[]{properties.get("os.version")});
                formatter.format("%s%n", new Object[]{LINE_SEPARATOR});

                injectEnvironmentInfoIntoBanner(formatter, environment, sourceClass);
                result = formatter.toString();
            }catch(Throwable t){
                throwable = t;
                throw t;
            }finally{
                closeFormatter(throwable);
            }
        }

        return result;
    }

    protected void injectEnvironmentInfoIntoBanner(Formatter formatter, Environment environment, Class<?> sourceClass){
    }

    private final static boolean isJceInstalled(){
        try{
            return Cipher.getMaxAllowedKeyLength("AES") == 2147483647;
        }catch(Exception e){
            return false;
        }
    }

    private final static void printAsciiArt(PrintStream out, String asciiArt, String additional){
        try{
            out.println("\u001b[36m");
            if(StringUtils.isNotBlank(additional) == true){
                out.println(FigletFont.convertOneLine(asciiArt));
                out.println(additional);
            }else{
                out.print(FigletFont.convertOneLine(asciiArt));
            }

            out.println("\u001b[0m");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private final static void closeFormatter(Throwable throwable){
        if(throwable != null){
            try{
                formatter.close();
            }catch(Throwable t){
                throwable.addSuppressed(t);
            }
        }else{
            formatter.close();
        }
    }

}
