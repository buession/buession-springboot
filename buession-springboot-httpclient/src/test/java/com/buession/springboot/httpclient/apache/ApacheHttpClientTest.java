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
 * | Copyright @ 2013-2023 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.httpclient.apache;

import com.buession.httpclient.ApacheHttpAsyncClient;
import com.buession.httpclient.HttpClient;
import com.buession.httpclient.core.Header;
import com.buession.httpclient.core.Response;
import com.buession.httpclient.core.concurrent.Callback;
import com.buession.httpclient.exception.RequestException;
import com.buession.springboot.httpclient.SpringBootTestApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yong.Teng
 * @since 1.3.1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootTestApplication.class)
public class ApacheHttpClientTest {

	@Autowired
	private HttpClient apacheHttpClient;

	@Autowired
	private ApacheHttpAsyncClient apacheHttpAsyncClient;

	private final static Logger logger = LoggerFactory.getLogger(ApacheHttpClientTest.class);

	@Test
	public void get(){
		Response response = null;
		try{
			List<Header> headers = new ArrayList<>(2);

			headers.add(new Header("Referer", "https://www.163.com"));
			headers.add(new Header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) " +
					"Gecko/20100101 Firefox/88.0"));

			response = apacheHttpClient.get(
					"https://gimg3.baidu.com/rel/src=https%3A%2F%2Fgips3.baidu.com%2Fit%2Fu%3D1371558006%2C3234928627%26fm%3D3028%26app%3D3028%26f%3DPNG%26fmt%3Dauto%26q%3D100%26size%3Df1138_640&refer=http%3A%2F%2Fwww.baidu.com&app=2010&size=w560&n=0&g=0n&q=100&fmt=auto?sec=1680800400&t=f73a553d17bf64428b1491c9d66df5e1",
					headers);
		}catch(RequestException e){
			logger.error("请求异常：{}", e.getMessage());
		}catch(IOException e){
			logger.error("图片保存异常：{}", e.getMessage());
		}finally{
			if(response != null && response.getInputStream() != null){
				try{
					response.getInputStream().close();
				}catch(IOException e){
				}
			}
		}
	}

	@Test
	public void asyncGet(){
		try{
			List<Header> headers = new ArrayList<>(2);

			headers.add(new Header("Referer", "https://www.163.com"));
			headers.add(new Header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) " +
					"Gecko/20100101 Firefox/88.0"));

			apacheHttpAsyncClient.get(
					"https://gimg3.baidu.com/rel/src=https%3A%2F%2Fgips3.baidu.com%2Fit%2Fu%3D1371558006%2C3234928627%26fm%3D3028%26app%3D3028%26f%3DPNG%26fmt%3Dauto%26q%3D100%26size%3Df1138_640&refer=http%3A%2F%2Fwww.baidu.com&app=2010&size=w560&n=0&g=0n&q=100&fmt=auto?sec=1680800400&t=f73a553d17bf64428b1491c9d66df5e1",
					headers,
					new Callback() {

						@Override
						public void completed(Response response){
							System.out.println(response.getContentLength());
							if(response != null && response.getInputStream() != null){
								try{
									response.getInputStream().close();
								}catch(IOException e){
								}
							}
						}

						@Override
						public void failed(Exception ex){

						}

						@Override
						public void cancelled(){

						}
					});
		}catch(RequestException e){
			logger.error("请求异常：{}", e.getMessage());
		}catch(IOException e){
			logger.error("图片保存异常：{}", e.getMessage());
		}finally{
		}
	}

}
