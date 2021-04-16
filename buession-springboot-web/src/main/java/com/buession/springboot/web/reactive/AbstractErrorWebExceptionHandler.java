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
package com.buession.springboot.web.reactive;

import com.buession.core.validator.Validate;
import com.buession.web.reactive.ErrorWebExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
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

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yong.Teng
 */
public abstract class AbstractErrorWebExceptionHandler extends org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler implements ErrorWebExceptionHandler {

	protected final ErrorProperties errorProperties;

	protected final ErrorAttributes errorAttributes;

	protected String exceptionAttribute = DEFAULT_EXCEPTION_ATTRIBUTE;

	protected String cacheControl = CACHE_CONTROL;

	protected Map<HttpStatus, String> errorViews;

	protected Map<Exception, String> exceptionViews;

	private final static Logger logger = LoggerFactory.getLogger(AbstractErrorWebExceptionHandler.class);

	private final static Logger pageNotFoundLogger = LoggerFactory.getLogger(PAGE_NOT_FOUND_LOG_CATEGORY);

	public AbstractErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
											ErrorProperties errorProperties, ApplicationContext applicationContext){
		super(errorAttributes, resourceProperties, applicationContext);
		this.errorProperties = errorProperties;
		this.errorAttributes = errorAttributes;
	}

	public String getCacheControl(){
		return cacheControl;
	}

	public void setCacheControl(String cacheControl){
		this.cacheControl = cacheControl;
	}

	public Map<HttpStatus, String> getErrorViews(){
		return errorViews;
	}

	public void setErrorViews(Map<HttpStatus, String> errorViews){
		this.errorViews = errorViews;
	}

	public Map<Exception, String> getExceptionViews(){
		return exceptionViews;
	}

	public void setExceptionViews(Map<Exception, String> exceptionViews){
		this.exceptionViews = exceptionViews;
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes){
		return RouterFunctions.route(this.acceptJson(), this::renderErrorJsonView).andRoute(RequestPredicates.all(),
				this::renderErrorHtmlView);
	}

	protected Map<String, Object> doSpecialResolveException(final ServerRequest request, final Throwable throwable){
		return null;
	}

	protected Map<String, Object> doDefaultResolveException(final ServerRequest request, final Throwable throwable){
		return null;
	}

	protected Map<String, Object> doResolveException(final ServerRequest request, final ServerResponse response,
													 final Throwable throwable){
		try{
			if(throwable instanceof MethodArgumentNotValidException){
				return handleMethodArgumentNotValidException(request, response,
						(MethodArgumentNotValidException) throwable);
			}else if(throwable instanceof BindException){
				return handleBindException(request, response, (BindException) throwable);
			}else if(throwable instanceof WebExchangeBindException){
				return handleWebExchangeBindException(request, response, (WebExchangeBindException) throwable);
			}else if(throwable instanceof ServerWebInputException){
				return handleServerWebInputException(request, response, (ServerWebInputException) throwable);
			}else if(throwable instanceof TypeMismatchException){
				return handleTypeMismatchException(request, response, (TypeMismatchException) throwable);
			}else if(throwable instanceof HttpMessageNotReadableException){
				return handleHttpMessageNotReadableException(request, response,
						(HttpMessageNotReadableException) throwable);
			}else if(throwable instanceof MethodNotAllowedException){
				return handleMethodNotAllowedException(request, response, (MethodNotAllowedException) throwable);
			}else if(throwable instanceof NotAcceptableStatusException){
				return handleNotAcceptableException(request, response, (NotAcceptableStatusException) throwable);
			}else if(throwable instanceof MediaTypeNotSupportedStatusException){
				return handleMediaTypeNotSupportedException(request, response,
						(MediaTypeNotSupportedStatusException) throwable);
			}else if(throwable instanceof UnsupportedMediaTypeStatusException){
				return handleUnsupportedMediaTypeException(request, response,
						(UnsupportedMediaTypeStatusException) throwable);
				//}else if(throwable instanceof MissingPathVariableException){
				//return handleMissingPathVariable(request, response, (MissingPathVariableException) throwable);
			}else if(throwable instanceof ConversionNotSupportedException){
				return handleConversionNotSupportedException(request, response,
						(ConversionNotSupportedException) throwable);
			}else if(throwable instanceof HttpMessageNotWritableException){
				return handleHttpMessageNotWritableException(request, response,
						(HttpMessageNotWritableException) throwable);
			}else if(throwable instanceof ResponseStatusException){
				return handleResponseStatusException(request, response, (ResponseStatusException) throwable);
			}else if(throwable instanceof AsyncRequestTimeoutException){
				return handleAsyncRequestTimeoutException(request, response, (AsyncRequestTimeoutException) throwable);
			}
		}catch(Exception handlerEx){
			if(logger.isWarnEnabled()){
				logger.warn("Failure while trying to resolve exception [{}]", throwable.getClass().getName(),
						handlerEx);
			}
		}

		return doDefaultResolveException(request, throwable);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link MethodArgumentNotValidException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleMethodArgumentNotValidException(final ServerRequest request,
																		final ServerResponse response,
																		final MethodArgumentNotValidException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link BindException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleBindException(final ServerRequest request, final ServerResponse response,
													  final BindException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link WebExchangeBindException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleWebExchangeBindException(final ServerRequest request,
																 final ServerResponse response,
																 final WebExchangeBindException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link ServerWebInputException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleServerWebInputException(final ServerRequest request,
																final ServerResponse response,
																final ServerWebInputException ex){
		return doResolve(request, ex);
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
	 * @param ex
	 *        {@link TypeMismatchException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleTypeMismatchException(final ServerRequest request,
															  final ServerResponse response,
															  final TypeMismatchException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 400
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link HttpMessageNotReadableException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleHttpMessageNotReadableException(final ServerRequest request,
																		final ServerResponse response,
																		final HttpMessageNotReadableException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 405
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link MethodNotAllowedException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleMethodNotAllowedException(final ServerRequest request,
																  final ServerResponse response,
																  final MethodNotAllowedException ex){
		Set<HttpMethod> supportedMethods = ex.getSupportedMethods();
		if(supportedMethods != null && response.headers() != null){
			response.headers().setAllow(supportedMethods);
		}

		return doResolve(request, ex);
	}

	/**
	 * Status code: 406
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link NotAcceptableStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleNotAcceptableException(final ServerRequest request,
															   final ServerResponse response,
															   final NotAcceptableStatusException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 415
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link MediaTypeNotSupportedStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleMediaTypeNotSupportedException(final ServerRequest request,
																	   final ServerResponse response,
																	   final MediaTypeNotSupportedStatusException ex){
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if(Validate.isNotEmpty(mediaTypes) && response.headers() != null){
			response.headers().setAccept(mediaTypes);
		}

		return doResolve(request, ex);
	}

	/**
	 * Status code: 415
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link UnsupportedMediaTypeStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleUnsupportedMediaTypeException(final ServerRequest request,
																	  final ServerResponse response,
																	  final UnsupportedMediaTypeStatusException ex){
		List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
		if(Validate.isNotEmpty(mediaTypes) && response.headers() != null){
			response.headers().setAccept(mediaTypes);
		}

		return doResolve(request, ex);
	}

	/**
	 * Status code: 500
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
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
	 * @param ex
	 *        {@link ConversionNotSupportedException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleConversionNotSupportedException(final ServerRequest request,
																		final ServerResponse response,
																		final ConversionNotSupportedException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 500
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link HttpMessageNotWritableException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleHttpMessageNotWritableException(final ServerRequest request,
																		final ServerResponse response,
																		final HttpMessageNotWritableException ex){
		return doResolve(request, ex);
	}

	/**
	 * Status code: 错误码
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link ResponseStatusException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleResponseStatusException(final ServerRequest request,
																final ServerResponse response,
																final ResponseStatusException ex){
		if(ex.getStatus() == HttpStatus.NOT_FOUND){
			pageNotFoundLogger.warn(ex.getMessage());
		}
		return doResolve(request, ex);
	}

	/**
	 * Status code: 503
	 *
	 * @param request
	 *        {@link ServerRequest}
	 * @param response
	 *        {@link ServerResponse}
	 * @param ex
	 *        {@link AsyncRequestTimeoutException}
	 *
	 * @return 返回数据
	 */
	protected Map<String, Object> handleAsyncRequestTimeoutException(final ServerRequest request,
																	 final ServerResponse response,
																	 final AsyncRequestTimeoutException ex){
		if(request.exchange().getResponse().isCommitted() == false){
			//response.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
		}else{
			logger.warn("Async request timed out");
		}

		return doResolve(request, ex);
	}

	protected RequestPredicate acceptTextHtml(){
		return (serverRequest)->{
			try{
				List<MediaType> acceptedMediaTypes = serverRequest.headers().accept();

				acceptedMediaTypes.remove(MediaType.ALL);
				MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);

				return acceptedMediaTypes.stream().anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
			}catch(InvalidMediaTypeException ex){
				return false;
			}
		};
	}

	protected RequestPredicate acceptJson(){
		return (serverRequest)->{
			try{
				List<MediaType> acceptedMediaTypes = serverRequest.headers().accept();

				acceptedMediaTypes.remove(MediaType.ALL);
				MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);

				return acceptedMediaTypes.stream().anyMatch(MediaType.APPLICATION_JSON::isCompatibleWith);
			}catch(InvalidMediaTypeException ex){
				return false;
			}
		};
	}

	protected ServerHttpResponse getServerHttpResponse(final ServerRequest request){
		return request.exchange().getResponse();
	}

	protected boolean isIncludeStackTrace(final ServerRequest request, final MediaType produces){
		ErrorProperties.IncludeStacktrace include = errorProperties.getIncludeStacktrace();
		if(include == ErrorProperties.IncludeStacktrace.ALWAYS){
			return true;
		}

		if(include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM){
			return isTraceEnabled(request);
		}

		return false;
	}

	protected HttpStatus getHttpStatus(final ServerRequest request){
		boolean includeStackTrace = isIncludeStackTrace(request, MediaType.TEXT_HTML);
		Map<String, Object> errorAttributes = getErrorAttributes(request, includeStackTrace);
		Object status = errorAttributes.get("status");

		if(status instanceof HttpStatus){
			return (HttpStatus) status;
		}else if(status instanceof Integer){
			return HttpStatus.resolve((Integer) status);
		}

		return null;
	}

	protected String[] determineViewName(final ServerRequest request, final Throwable throwable,
										 final HttpStatus httpStatus){
		Set<String> views = new LinkedHashSet<>(4);
		String viewName = null;

		if(getExceptionViews() != null){
			viewName = getExceptionViews().get(throwable);
			if(viewName != null){
				views.add(buildView(viewName));
			}
		}

		if(viewName == null && getErrorViews() != null){
			viewName = getErrorViews().get(httpStatus);
			if(viewName != null){
				views.add(buildView(viewName));
			}
		}

		if(viewName == null){
			viewName = SERIES_VIEWS.get(httpStatus.series());
			if(viewName != null){
				views.add(buildView(viewName));
			}
		}

		views.add(buildView(DEFAULT_ERROR_VIEW));

		return views.toArray(new String[]{});
	}

	protected Map<String, Object> doResolve(final ServerRequest request, final Throwable throwable){
		HttpStatus httpStatus = getHttpStatus(request);

		Map<String, Object> result = new HashMap<>(6);

		result.put("state", false);
		result.put("code", httpStatus.value());
		result.put("message", httpStatus.getReasonPhrase());
		result.put("status", httpStatus);
		result.put("timestamp", new Date());
		result.put(exceptionAttribute, throwable);

		if(getCacheControl() != null){
			request.exchange().getResponse().getHeaders().setCacheControl(getCacheControl());
		}

		return result;
	}

	protected Mono<ServerResponse> renderErrorHtmlView(final ServerRequest request){
		boolean whitelabelEnabled = errorProperties.getWhitelabel().isEnabled();
		Throwable throwable = determineException(request);
		Map<String, Object> exceptionAttributes = doSpecialResolveException(request, throwable);

		if(exceptionAttributes == null){
			exceptionAttributes = doResolveException(request, null, throwable);
		}

		Map<String, Object> errorAttributes = exceptionAttributes != null ? exceptionAttributes : doResolve(request,
				throwable);
		HttpStatus httpStatus = getHttpStatus(request);
		String[] views = determineViewName(request, throwable, httpStatus);
		ServerResponse.BodyBuilder responseBody = responseBody(httpStatus, MediaType.TEXT_HTML);

		return Flux.just(views).flatMap((viewName)->renderErrorView(viewName, responseBody, errorAttributes)).switchIfEmpty(whitelabelEnabled ? renderDefaultErrorView(responseBody, errorAttributes) : Mono.error(getError(request))).next();
	}

	protected Mono<ServerResponse> renderErrorJsonView(final ServerRequest request){
		Throwable throwable = determineException(request);
		Map<String, Object> exceptionAttributes = doSpecialResolveException(request, throwable);

		if(exceptionAttributes == null){
			exceptionAttributes = doResolveException(request, null, throwable);
		}

		HttpStatus httpStatus = getHttpStatus(request);
		Map<String, Object> errorAttributes = exceptionAttributes != null ? exceptionAttributes : doResolve(request,
				throwable);
		ServerResponse.BodyBuilder responseBody = responseBody(httpStatus, MediaType.APPLICATION_JSON);

		return responseBody.body(BodyInserters.fromValue(errorAttributes));
	}

	protected static ServerResponse.BodyBuilder responseBody(final HttpStatus httpStatus, final MediaType contentType){
		return ServerResponse.status(httpStatus).contentType(contentType);
	}

	protected Throwable determineException(final ServerRequest request){
		return determineException(getError(request));
	}

	protected Throwable determineException(final Throwable throwable){
		if(throwable instanceof ResponseStatusException){
			return (throwable.getCause() != null) ? throwable.getCause() : throwable;
		}else{
			return throwable;
		}
	}

	protected static String buildView(final String viewName){
		StringBuilder sb = new StringBuilder(viewName.length() + 6);

		sb.append("error/").append(viewName);

		return sb.toString();
	}

}
