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
package com.buession.springboot.web.reactive;

import com.buession.web.http.ExceptionHandlerResolver;
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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
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
public abstract class AbstractErrorWebExceptionHandler extends org.springframework.boot.autoconfigure.web.reactive
        .error.AbstractErrorWebExceptionHandler implements ExceptionHandlerResolver {

    private final ErrorProperties errorProperties;

    private String exceptionAttribute = DEFAULT_EXCEPTION_ATTRIBUTE;

    private String cacheControl = CACHE_CONTROL;

    private Map<HttpStatus, String> errorViews;

    private Map<Exception, String> exceptionViews;

    private final static Logger logger = LoggerFactory.getLogger(AbstractErrorWebExceptionHandler.class);

    private final static Logger pageNotFoundLogger = LoggerFactory.getLogger(PAGE_NOT_FOUND_LOG_CATEGORY);

    public AbstractErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                            ErrorProperties errorProperties, ApplicationContext applicationContext){
        super(errorAttributes, resourceProperties, applicationContext);
        this.errorProperties = errorProperties;
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
        return RouterFunctions.route(acceptJson(), this::renderErrorJsonView).andRoute(RequestPredicates.all(),
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
            if(throwable instanceof HttpRequestMethodNotSupportedException){
                return handleHttpRequestMethodNotSupported(request, response,
                        (HttpRequestMethodNotSupportedException) throwable);
            }else if(throwable instanceof HttpMediaTypeNotSupportedException){
                return handleHttpMediaTypeNotSupported(request, response, (HttpMediaTypeNotSupportedException)
                        throwable);
            }else if(throwable instanceof HttpMediaTypeNotAcceptableException){
                return handleHttpMediaTypeNotAcceptable(request, response, (HttpMediaTypeNotAcceptableException)
                        throwable);
            }else if(throwable instanceof MissingPathVariableException){
                return handleMissingPathVariable(request, response, (MissingPathVariableException) throwable);
            }else if(throwable instanceof MissingServletRequestParameterException){
                return handleMissingServletRequestParameter(request, response,
                        (MissingServletRequestParameterException) throwable);
            }else if(throwable instanceof ServletRequestBindingException){
                return handleServletRequestBindingException(request, response, (ServletRequestBindingException)
                        throwable);
            }else if(throwable instanceof ConversionNotSupportedException){
                return handleConversionNotSupported(request, response, (ConversionNotSupportedException) throwable);
            }else if(throwable instanceof TypeMismatchException){
                return handleTypeMismatch(request, response, (TypeMismatchException) throwable);
            }else if(throwable instanceof HttpMessageNotReadableException){
                return handleHttpMessageNotReadable(request, response, (HttpMessageNotReadableException) throwable);
            }else if(throwable instanceof HttpMessageNotWritableException){
                return handleHttpMessageNotWritable(request, response, (HttpMessageNotWritableException) throwable);
            }else if(throwable instanceof MethodArgumentNotValidException){
                return handleMethodArgumentNotValidException(request, response, (MethodArgumentNotValidException)
                        throwable);
            }else if(throwable instanceof MissingServletRequestPartException){
                return handleMissingServletRequestPartException(request, response,
                        (MissingServletRequestPartException) throwable);
            }else if(throwable instanceof BindException){
                return handleBindException(request, response, (BindException) throwable);
            }else if(throwable instanceof NoHandlerFoundException){
                return handleNoHandlerFoundException(request, response, (NoHandlerFoundException) throwable);
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

    protected Map<String, Object> handleHttpRequestMethodNotSupported(final ServerRequest request, final
    ServerResponse response, final HttpRequestMethodNotSupportedException ex){
        //response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);

        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if(supportedMethods != null){
            response.headers().setAllow(supportedMethods);
        }

        return doResolve(request, ex);
    }

    protected Map<String, Object> handleHttpMediaTypeNotSupported(final ServerRequest request, final ServerResponse
            response, final HttpMediaTypeNotSupportedException ex){
        // response.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        List<MediaType> mediaTypes = ex.getSupportedMediaTypes();
        if(!CollectionUtils.isEmpty(mediaTypes)){
            response.headers().setAccept(mediaTypes);
        }

        return doResolve(request, ex);
    }

    protected Map<String, Object> handleHttpMediaTypeNotAcceptable(final ServerRequest request, final ServerResponse
            response, final HttpMediaTypeNotAcceptableException ex){
        // response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleMissingPathVariable(final ServerRequest request, final ServerResponse
            response, final MissingPathVariableException ex){
        //  response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleMissingServletRequestParameter(final ServerRequest request, final
    ServerResponse response, final MissingServletRequestParameterException ex){
        //  response.setStatusCode(HttpStatus.BAD_REQUEST);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleServletRequestBindingException(final ServerRequest request, final
    ServerResponse response, final ServletRequestBindingException ex){
        // response.setStatusCode(HttpStatus.BAD_REQUEST);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleConversionNotSupported(final ServerRequest request, final ServerResponse
            response, final ConversionNotSupportedException ex){
        //response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleTypeMismatch(final ServerRequest request, final ServerResponse response,
                                                     final TypeMismatchException ex){
        //response.setStatusCode(HttpStatus.BAD_REQUEST);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleHttpMessageNotReadable(final ServerRequest request, final ServerResponse
            response, final HttpMessageNotReadableException ex){
        //response.setStatusCode(HttpStatus.BAD_REQUEST);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleHttpMessageNotWritable(final ServerRequest request, final ServerResponse
            response, final HttpMessageNotWritableException ex){
        //response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleMethodArgumentNotValidException(final ServerRequest request, final
    ServerResponse response, final MethodArgumentNotValidException ex){
        //response.setStatusCode(HttpStatus.BAD_REQUEST);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleMissingServletRequestPartException(final ServerRequest request, final
    ServerResponse response, final MissingServletRequestPartException ex){
        //response.setStatusCode(HttpStatus.BAD_REQUEST);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleBindException(final ServerRequest request, final ServerResponse response,
                                                      final BindException ex){
        //response.setStatusCode(HttpStatus.BAD_REQUEST);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleNoHandlerFoundException(final ServerRequest request, final ServerResponse
            response, final NoHandlerFoundException ex){
        pageNotFoundLogger.warn(ex.getMessage());
        //response.setStatusCode(HttpStatus.NOT_FOUND);
        return doResolve(request, ex);
    }

    protected Map<String, Object> handleAsyncRequestTimeoutException(final ServerRequest request, final
    ServerResponse response, final AsyncRequestTimeoutException ex){
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
        HttpStatus statusCode = (HttpStatus) errorAttributes.get("status");

        return statusCode;
    }

    protected String[] determineViewName(final ServerRequest request, final Throwable throwable, final HttpStatus
            httpStatus){
        Set<String> views = new LinkedHashSet<>(4);
        String viewDir = "error/";
        String viewName = null;

        if(getExceptionViews() != null){
            viewName = getExceptionViews().get(throwable);
            if(viewName != null){
                views.add(viewDir + viewName);
            }
        }

        if(viewName == null && getErrorViews() != null){
            viewName = getErrorViews().get(httpStatus);
            if(viewName != null){
                views.add(viewDir + viewName);
            }
        }

        if(viewName == null){
            viewName = SERIES_VIEWS.get(httpStatus.series());
            if(viewName != null){
                views.add(viewDir + viewName);
            }
        }

        views.add(viewDir + DEFAULT_ERROR_VIEW);

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

        return Flux.just(views).flatMap((viewName)->renderErrorView(viewName, responseBody, errorAttributes))
                .switchIfEmpty(whitelabelEnabled ? renderDefaultErrorView(responseBody, errorAttributes) : Mono.error
                        (getError(request))).next();
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
        ServerResponse.BodyBuilder responseBody = responseBody(httpStatus, MediaType.APPLICATION_JSON_UTF8);

        return responseBody.body(BodyInserters.fromObject(errorAttributes));
    }

    protected final static ServerResponse.BodyBuilder responseBody(final HttpStatus httpStatus, final MediaType
            contentType){
        return ServerResponse.status(httpStatus).contentType(contentType);
    }

    private Throwable determineException(final ServerRequest request){
        return determineException(getError(request));
    }

    private Throwable determineException(final Throwable throwable){
        if(throwable instanceof ResponseStatusException){
            return (throwable.getCause() != null) ? throwable.getCause() : throwable;
        }else{
            return throwable;
        }
    }

}
