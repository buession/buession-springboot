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
 * | Copyright @ 2013-2024 Buession.com Inc.														       |
 * +-------------------------------------------------------------------------------------------------------+
 */
package com.buession.springboot.web.reactive;

import com.buession.web.reactive.ErrorWebExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.MediaTypeNotSupportedStatusException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yong.Teng
 */
public class DefaultErrorWebExceptionHandler
		extends org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler
		implements ErrorWebExceptionHandler {

	protected final static MediaType TEXT_HTML_UTF8 = new MediaType("text", "html", StandardCharsets.UTF_8);

	private final ErrorProperties errorProperties;

	private String cacheControl = CACHE_CONTROL;

	private String exceptionAttribute = DEFAULT_EXCEPTION_ATTRIBUTE;

	protected Map<Exception, String> exceptionViews;

	private final static Logger logger = LoggerFactory.getLogger(DefaultErrorWebExceptionHandler.class);

	private final static Logger pageNotFoundLogger = LoggerFactory.getLogger(PAGE_NOT_FOUND_LOG_CATEGORY);

	/**
	 * 构造函数
	 *
	 * @param errorAttributes
	 * 		The error attributes
	 * @param resources
	 * 		The resources configuration properties
	 * @param errorProperties
	 * 		the error configuration properties
	 * @param applicationContext
	 * 		The application context
	 *
	 * @since 3.0.0
	 */
	public DefaultErrorWebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
										   ErrorProperties errorProperties, ApplicationContext applicationContext) {
		super(errorAttributes, resources, errorProperties, applicationContext);
		this.errorProperties = errorProperties;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public String getExceptionAttribute() {
		return exceptionAttribute;
	}

	public void setExceptionAttribute(String exceptionAttribute) {
		this.exceptionAttribute = exceptionAttribute;
	}

	public Map<Exception, String> getExceptionViews() {
		return exceptionViews;
	}

	public void setExceptionViews(Map<Exception, String> exceptionViews) {
		this.exceptionViews = exceptionViews;
	}

	protected Exception getException(Map<String, Object> errorAttributes) {
		return errorAttributes.containsKey("exception") ? (Exception) errorAttributes.get("exception") : null;
	}

	@Override
	protected Mono<ServerResponse> renderErrorView(ServerRequest request) {
		Map<String, Object> error = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML));
		int errorStatus = getHttpStatus(error);
		Throwable throwable = getError(request);

		ServerResponse.BodyBuilder responseBody = ServerResponse.status(errorStatus).contentType(TEXT_HTML_UTF8);

		return Flux.just(getViews(errorStatus, throwable).toArray(new String[]{}))
				.flatMap((viewName)->renderErrorView(viewName, responseBody, error))
				.switchIfEmpty(errorProperties.getWhitelabel().isEnabled()
						? renderDefaultErrorView(responseBody, error) : Mono.error(throwable))
				.next();
	}

	@Override
	protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		Map<String, Object> error = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
		Throwable throwable = getError(request);

		try{
			final ServerResponse response = null;

			if(throwable instanceof MethodArgumentNotValidException){
				handleMethodArgumentNotValidException(request, response, error,
						(MethodArgumentNotValidException) throwable);
			}else if(throwable instanceof BindException){
				handleBindException(request, response, error, (BindException) throwable);
			}else if(throwable instanceof WebExchangeBindException){
				handleWebExchangeBindException(request, response, error, (WebExchangeBindException) throwable);
			}else if(throwable instanceof ServerWebInputException){
				handleServerWebInputException(request, response, error, (ServerWebInputException) throwable);
			}else if(throwable instanceof TypeMismatchException){
				handleTypeMismatchException(request, response, error, (TypeMismatchException) throwable);
			}else if(throwable instanceof HttpMessageNotReadableException){
				handleHttpMessageNotReadableException(request, response, error,
						(HttpMessageNotReadableException) throwable);
			}else if(throwable instanceof MethodNotAllowedException){
				handleMethodNotAllowedException(request, response, error, (MethodNotAllowedException) throwable);
			}else if(throwable instanceof NotAcceptableStatusException){
				handleNotAcceptableException(request, response, error, (NotAcceptableStatusException) throwable);
			}else if(throwable instanceof MediaTypeNotSupportedStatusException){
				handleMediaTypeNotSupportedException(request, response, error,
						(MediaTypeNotSupportedStatusException) throwable);
			}else if(throwable instanceof UnsupportedMediaTypeStatusException){
				handleUnsupportedMediaTypeException(request, response, error,
						(UnsupportedMediaTypeStatusException) throwable);
				//}else if(throwable instanceof MissingPathVariableException){
				//handleMissingPathVariable(request, response, error, (MissingPathVariableException) throwable);
			}else if(throwable instanceof ConversionNotSupportedException){
				handleConversionNotSupportedException(request, response, error,
						(ConversionNotSupportedException) throwable);
			}else if(throwable instanceof HttpMessageNotWritableException){
				handleHttpMessageNotWritableException(request, response, error,
						(HttpMessageNotWritableException) throwable);
			}else if(throwable instanceof ResponseStatusException){
				handleResponseStatusException(request, response, error, (ResponseStatusException) throwable);
			}else if(throwable instanceof AsyncRequestTimeoutException){
				handleAsyncRequestTimeoutException(request, response, error, (AsyncRequestTimeoutException) throwable);
			}
		}catch(Exception handlerEx){
			if(logger.isWarnEnabled()){
				logger.warn("Failure while trying to resolve exception [{}]", throwable.getClass().getName(),
						handlerEx);
			}
		}

		error.put("code", getHttpStatus(error));

		return error;
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link MethodArgumentNotValidException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleMethodArgumentNotValidException(final ServerRequest request,
																		final ServerResponse response,
																		final Map<String, Object> errorAttributes,
																		final MethodArgumentNotValidException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link BindException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleBindException(final ServerRequest request, final ServerResponse response,
													  final Map<String, Object> errorAttributes,
													  final BindException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link WebExchangeBindException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleWebExchangeBindException(final ServerRequest request,
																 final ServerResponse response,
																 final Map<String, Object> errorAttributes,
																 final WebExchangeBindException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link ServerWebInputException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleServerWebInputException(final ServerRequest request,
																final ServerResponse response,
																final Map<String, Object> errorAttributes,
																final ServerWebInputException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 400
	 */
	/*protected Map<String, Object> handleServletRequestBindingException(final ServerRequest request, final
	ServerResponse response, final ServletRequestBindingException ex){
		// response.setStatusCode(HttpStatus.BAD_REQUEST);
		return doResolve(request, ex);
	}*/

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link TypeMismatchException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleTypeMismatchException(final ServerRequest request,
															  final ServerResponse response,
															  final Map<String, Object> errorAttributes,
															  final TypeMismatchException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link HttpMessageNotReadableException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleHttpMessageNotReadableException(final ServerRequest request,
																		final ServerResponse response,
																		final Map<String, Object> errorAttributes,
																		final HttpMessageNotReadableException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 405
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link MethodNotAllowedException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleMethodNotAllowedException(final ServerRequest request,
																  final ServerResponse response,
																  final Map<String, Object> errorAttributes,
																  final MethodNotAllowedException ex) {
		Set<HttpMethod> supportedMethods = ex.getSupportedMethods();
		if(supportedMethods != null && response.headers() != null){
			response.headers().setAllow(supportedMethods);
		}

		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 406
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link NotAcceptableStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleNotAcceptableException(final ServerRequest request,
															   final ServerResponse response,
															   final Map<String, Object> errorAttributes,
															   final NotAcceptableStatusException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 415
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link MediaTypeNotSupportedStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleMediaTypeNotSupportedException(final ServerRequest request,
																	   final ServerResponse response,
																	   final Map<String, Object> errorAttributes,
																	   final MediaTypeNotSupportedStatusException ex) {
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if(mediaTypes != null && response.headers() != null){
			response.headers().setAccept(mediaTypes);
		}

		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 415
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link UnsupportedMediaTypeStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleUnsupportedMediaTypeException(final ServerRequest request,
																	  final ServerResponse response,
																	  final Map<String, Object> errorAttributes,
																	  final UnsupportedMediaTypeStatusException ex) {
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if(mediaTypes != null && response.headers() != null){
			response.headers().setAccept(mediaTypes);
		}

		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 500
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link MissingPathVariableException}
	 *
	 * @return 返回数据
	 */
	//protected Map<String, Object> handleMissingPathVariable(final ServerRequest request, final ServerResponse
	// response
	//	, final MissingPathVariableException ex){
	//	return doResolve(request, ex);
	//}

	/**
	 * Status code: 500
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link ConversionNotSupportedException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleConversionNotSupportedException(final ServerRequest request,
																		final ServerResponse response,
																		final Map<String, Object> errorAttributes,
																		final ConversionNotSupportedException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 500
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link HttpMessageNotWritableException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleHttpMessageNotWritableException(final ServerRequest request,
																		final ServerResponse response,
																		final Map<String, Object> errorAttributes,
																		final HttpMessageNotWritableException ex) {
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 错误码
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link ResponseStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleResponseStatusException(final ServerRequest request,
																final ServerResponse response,
																final Map<String, Object> errorAttributes,
																final ResponseStatusException ex) {
		if(ex.getStatus() == HttpStatus.NOT_FOUND){
			pageNotFoundLogger.warn(ex.getMessage());
		}
		return doResolve(request, errorAttributes, ex);
	}

	/**
	 * Status code: 503
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param errorAttributes
	 * 		错误属性
	 * @param ex
	 *        {@link AsyncRequestTimeoutException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleAsyncRequestTimeoutException(final ServerRequest request,
																	 final ServerResponse response,
																	 final Map<String, Object> errorAttributes,
																	 final AsyncRequestTimeoutException ex) {
		if(request.exchange().getResponse().isCommitted() == false){
			//response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
		}else{
			logger.warn("Async request timed out");
		}

		return doResolve(request, errorAttributes, ex);
	}

	protected Map<String, Object> doResolve(final ServerRequest request, final Map<String, Object> errorAttributes,
											final Throwable throwable) {
		//HttpStatus httpStatus = getHttpStatus(request);

		errorAttributes.put("state", false);

		//result.put("message", httpStatus.getReasonPhrase());
		//result.put("status", httpStatus);
		//result.put("timestamp", new Date());
		errorAttributes.put(exceptionAttribute, throwable);

		if(getCacheControl() != null){
			request.exchange().getResponse().getHeaders().setCacheControl(getCacheControl());
		}

		return errorAttributes;
	}

	private List<String> getViews(int errorStatus, Throwable throwable) {
		final List<String> views = new ArrayList<>();

		views.add("error/" + errorStatus);

		HttpStatus.Series series = HttpStatus.Series.resolve(errorStatus);
		if(series != null){
			views.add("error/" + SERIES_VIEWS.get(series));
		}

		if(throwable != null && getExceptionViews() != null){
			String exceptionView = getExceptionViews().get(throwable);
			if(exceptionView != null){
				views.add("error/" + exceptionView);
			}
		}

		views.add("error/error");

		return views;
	}

}
