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
package com.buession.springboot.boot.utils;

import java.math.BigInteger;

/**
 * @author Yong.Teng
 * @since 1.2.1
 */
public class FileUtils {

	public final static BigInteger ONE_KB_BI = BigInteger.valueOf(1024);

	public final static BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);

	public final static BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);

	public final static BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);

	public final static BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);

	public final static BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);

	private FileUtils(){

	}

	public static String byteCountToDisplaySize(final long size){
		return byteCountToDisplaySize(BigInteger.valueOf(size));
	}

	public static String byteCountToDisplaySize(final BigInteger size){
		if(size.divide(ONE_EB_BI).compareTo(BigInteger.ZERO) > 0){
			return size.divide(ONE_EB_BI) + " EB";
		}else if(size.divide(ONE_PB_BI).compareTo(BigInteger.ZERO) > 0){
			return size.divide(ONE_PB_BI) + " PB";
		}else if(size.divide(ONE_TB_BI).compareTo(BigInteger.ZERO) > 0){
			return size.divide(ONE_TB_BI) + " TB";
		}else if(size.divide(ONE_GB_BI).compareTo(BigInteger.ZERO) > 0){
			return size.divide(ONE_GB_BI) + " GB";
		}else if(size.divide(ONE_MB_BI).compareTo(BigInteger.ZERO) > 0){
			return size.divide(ONE_MB_BI) + " MB";
		}else if(size.divide(ONE_KB_BI).compareTo(BigInteger.ZERO) > 0){
			return size.divide(ONE_KB_BI) + " KB";
		}else{
			return size + " bytes";
		}
	}

}
