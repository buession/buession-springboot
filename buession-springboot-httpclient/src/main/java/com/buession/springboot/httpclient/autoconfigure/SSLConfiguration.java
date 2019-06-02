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
 * | Copyright @ 2013-2019 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.httpclient.autoconfigure;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author Yong.Teng
 */
@Configuration
@Order(1)
public class SSLConfiguration {

    private final static String[] SUPPORTED_PROTOCOLS = new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2",
            "TLSv1.3"};

    @Autowired
    private SSLContext sslContext;

    @Bean(name = "sslContext")
    //@ConditionalOnMissingBean
    public SSLContext sslContext() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException{
        return SSLContextBuilder.create().loadTrustMaterial(null, new TrustStrategy() {

            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException{
                return true;
            }

        }).build();
    }

    @Bean(name = "sslConnectionSocketFactory")
    //@ConditionalOnMissingBean
    public SSLConnectionSocketFactory sslConnectionSocketFactory(){
        return new SSLConnectionSocketFactory(sslContext, SUPPORTED_PROTOCOLS, null, NoopHostnameVerifier.INSTANCE);
    }

}
