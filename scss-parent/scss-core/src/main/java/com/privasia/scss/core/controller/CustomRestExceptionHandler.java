package com.privasia.scss.core.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.privasia.scss.common.dto.ApiError;
import com.privasia.scss.common.dto.ApiResponseObject;
import com.privasia.scss.common.dto.CustomResponseEntity;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.exception.InvalidJwtTokenException;
import com.privasia.scss.core.exception.JwtExpiredTokenException;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.RequiredTypeException;

@RestControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

  protected CustomResponseEntity<ApiResponseObject> handleExceptionInternal(Exception exception, HttpStatus httpStatus,
      WebRequest request, String error) {
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
    }
    final ApiError apiError = new ApiError(httpStatus, exception.getLocalizedMessage(), error);
    System.out.println("error "+ error);
    ApiResponseObject<ApiError> body = new ApiResponseObject<ApiError>(httpStatus, apiError);
    return new CustomResponseEntity<ApiResponseObject>(body, httpStatus);
  }

  protected CustomResponseEntity<ApiResponseObject> handleExceptionInternal(Exception exception, HttpStatus httpStatus,
      WebRequest request, List<String> errors) {
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(httpStatus)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
    }
    final ApiError apiError = new ApiError(httpStatus, exception.getLocalizedMessage(), errors);
    System.out.println("error "+ errors);
    ApiResponseObject<ApiError> body = new ApiResponseObject<ApiError>(httpStatus, apiError);
    return new CustomResponseEntity<ApiResponseObject>(body, httpStatus);
  }

  // 400
  @Override
  protected ResponseEntity handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
      final HttpHeaders headers, final HttpStatus status, final WebRequest webRequest) {

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    logger.info(exception.getClass().getName());
    //
    final List<String> errors = new ArrayList<String>();
    for (final FieldError error : exception.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : exception.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    return handleExceptionInternal(exception, httpStatus, webRequest, errors);
  }

  @Override
  protected ResponseEntity handleBindException(final BindException exception, final HttpHeaders headers,
      final HttpStatus status, final WebRequest webRequest) {

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    logger.info(exception.getClass().getName());
    //
    final List<String> errors = new ArrayList<String>();
    for (final FieldError error : exception.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : exception.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    return handleExceptionInternal(exception, httpStatus, webRequest, errors);
  }

  @Override
  protected ResponseEntity handleTypeMismatch(final TypeMismatchException exception, final HttpHeaders headers,
      final HttpStatus status, final WebRequest webRequest) {

    logger.info(exception.getClass().getName());
    //
    final String error = exception.getValue() + " value for " + exception.getPropertyName() + " should be of type "
        + exception.getRequiredType();

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    return handleExceptionInternal(exception, httpStatus, webRequest, error);
  }

  @Override
  protected ResponseEntity handleMissingServletRequestPart(final MissingServletRequestPartException exception,
      final HttpHeaders headers, final HttpStatus status, final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    //
    final String error = exception.getRequestPartName() + " part is missing";
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(exception, httpStatus, webRequest, error);
  }

  @Override
  protected ResponseEntity handleMissingServletRequestParameter(final MissingServletRequestParameterException exception,
      final HttpHeaders headers, final HttpStatus status, final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    //
    final String error = exception.getParameterName() + " parameter is missing";
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(exception, httpStatus, webRequest, error);
  }

  //

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public CustomResponseEntity<ApiResponseObject> handleMethodArgumentTypeMismatch(
      final MethodArgumentTypeMismatchException exception, final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    //
    final String error = exception.getName() + " should be of type " + exception.getRequiredType().getName();

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(exception, httpStatus, webRequest, error);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  public CustomResponseEntity<ApiResponseObject> handleConstraintViolation(final ConstraintViolationException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    //
    final List<String> errors = new ArrayList<String>();
    for (final ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      errors.add(
          violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
    }

    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(exception, httpStatus, webRequest, errors);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  public CustomResponseEntity<ApiResponseObject> handleConflictException(
      final DataIntegrityViolationException exception, final WebRequest webRequest) {
    exception.printStackTrace();
    logger.info(exception.getClass().getName());
    String error = "Duplicate entry for a column marked as unique";
    HttpStatus httpStatus = HttpStatus.CONFLICT;
    return handleExceptionInternal(exception, httpStatus, webRequest, error);
  }


  // 404

  @Override
  protected ResponseEntity handleNoHandlerFoundException(final NoHandlerFoundException exception,
      final HttpHeaders headers, final HttpStatus status, final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    //
    final String error = "No handler found for " + exception.getHttpMethod() + " " + exception.getRequestURL();

    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    return handleExceptionInternal(exception, httpStatus, webRequest, error);
  }

  // 405

  @Override
  protected ResponseEntity handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException exception,
      final HttpHeaders headers, final HttpStatus status, final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    //
    final StringBuilder builder = new StringBuilder();
    builder.append(exception.getMethod());
    builder.append(" method is not supported for this webRequest. Supported methods are ");
    exception.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

    HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
    return handleExceptionInternal(exception, httpStatus, webRequest, builder.toString());
  }

  // 415

  @Override
  protected ResponseEntity handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception,
      final HttpHeaders headers, final HttpStatus status, final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    //
    final StringBuilder builder = new StringBuilder();
    builder.append(exception.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    exception.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

    HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
    return handleExceptionInternal(exception, httpStatus, webRequest, builder.substring(0, builder.length() - 2));
  }

  // 403
  @ExceptionHandler({AccessDeniedException.class})
  public CustomResponseEntity<ApiResponseObject> handleAccessDenied(final Exception exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error", exception);
    //
    HttpStatus httpStatus = HttpStatus.FORBIDDEN;
    return handleExceptionInternal(exception, httpStatus, webRequest, "Forbidden");
  }

  // 500
  @ExceptionHandler({Exception.class})
  public CustomResponseEntity<ApiResponseObject> handleAll(final Exception exception, final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error", exception);
    System.out.println("error "+ exception);
    //
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    return handleExceptionInternal(exception, httpStatus, webRequest, "internal error occurred");
  }

  // custom
  // @ResponseStatus(value = HttpStatus.OK, reason = "No Search Results Found!")
  @ExceptionHandler({ResultsNotFoundException.class})
  public CustomResponseEntity<ApiResponseObject> handleResultsNotFound(final ResultsNotFoundException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error", exception);
    //
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    return handleExceptionInternal(exception, httpStatus, webRequest, "No Search Results Found!");
  }

  // custom
  @ExceptionHandler({InvalidJwtTokenException.class})
  public CustomResponseEntity<ApiResponseObject> handleInvalidJwtToken(final InvalidJwtTokenException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error", exception);
    //
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    return handleExceptionInternal(exception, httpStatus, webRequest, "Invalid Jwt Token!");
  }

  @ExceptionHandler({BadCredentialsException.class})
  public CustomResponseEntity<ApiResponseObject> handleBadCredentials(final BadCredentialsException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error", exception);
    //
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    return handleExceptionInternal(exception, httpStatus, webRequest, "Invalid Credentials!");
  }

  @ExceptionHandler({JwtExpiredTokenException.class})
  public CustomResponseEntity<ApiResponseObject> handleJwtExpiredToken(final JwtExpiredTokenException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error", exception);
    //
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    return handleExceptionInternal(exception, httpStatus, webRequest, "Expired Jwt Token!");
  }

  @ExceptionHandler({RequiredTypeException.class})
  public CustomResponseEntity<ApiResponseObject> handleRequiredTypeException(final RequiredTypeException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error", exception);
    //
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    return handleExceptionInternal(exception, httpStatus, webRequest, "Jwt Reuqired Not Type!");
  }

  @ExceptionHandler({MalformedJwtException.class})
  public CustomResponseEntity<ApiResponseObject> handleMalformedJwtException(final MalformedJwtException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error %%%%%%%%%%%", exception);
    System.out.println("%%%%%%%%%%%%%%");
    //
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    return handleExceptionInternal(exception, httpStatus, webRequest, "Invalid token, Malformed!");
  }

  @ExceptionHandler({JsonParseException.class})
  public CustomResponseEntity<ApiResponseObject> handleJsonParseException(final JsonParseException exception,
      final WebRequest webRequest) {
    logger.info(exception.getClass().getName());
    logger.error("error %%%%%%%%%%%", exception);
    System.out.println("%%%%%%%%%%%%%%");
    //
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    return handleExceptionInternal(exception, httpStatus, webRequest, "Invalid json, Malformed!");
  }


  @ExceptionHandler(BusinessException.class)
  public CustomResponseEntity<ApiResponseObject> businessExceptionHandler(Exception ex, final WebRequest webRequest) {

    logger.info(ex.getClass().getName());
    logger.error("error", ex);
    //
    HttpStatus httpStatus = HttpStatus.PRECONDITION_FAILED;
    return handleExceptionInternal(ex, httpStatus, webRequest, "Business Exception Occured!");
  }



}
