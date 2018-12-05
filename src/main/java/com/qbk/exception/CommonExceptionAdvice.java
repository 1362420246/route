package com.qbk.exception;

import com.qbk.util.BaseResultGenerator;
import com.qbk.util.BaseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.xml.bind.ValidationException;
import java.util.Set;

/**
 * 异常处理类
 */
@Log4j2
@ControllerAdvice
@ResponseBody
public class CommonExceptionAdvice {


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseResult<String> handleMissingServletRequestParServiceExceptionameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数", e);
        return BaseResultGenerator.error("缺少请求参数");
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseResult<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("参数解析失败", e);
        return BaseResultGenerator.error("参数解析失败");

    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数验证失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String code = error.getDefaultMessage();
        String message = String.format("%s", code);
        return BaseResultGenerator.error(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public BaseResult handleBindException(BindException e) {
        log.error("参数绑定失败", e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        return BaseResultGenerator.error(message);
    }


    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResult handleServiceException(ConstraintViolationException e) {
        log.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        return BaseResultGenerator.error(message);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationException.class)
    public BaseResult handleValidationException(ValidationException e) {
        log.error("参数验证失败", e);
        return BaseResultGenerator.error("参数验证失败");
    }

    /**
     * 404 - Not Found
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResult noHandlerFoundException(NoHandlerFoundException e) {
        log.error("Not Found", e);
        return BaseResultGenerator.error("Not Found=" + e);
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return BaseResultGenerator.error("不支持当前请求方法");
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持当前媒体类型", e);
        return BaseResultGenerator.error("不支持当前媒体类型");
    }

    /**
     * 业务层需要自己声明异常的情况
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServiceException.class)
    public BaseResult handleServiceException(ServiceException e) {
        log.error("业务逻辑异常", e);
        return BaseResultGenerator.error(e.getMessage());
    }


    /**
     * 获取其它异常。包括500
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public BaseResult defaultErrorHandler(Exception e) {
        log.error("Exception", e);
        return BaseResultGenerator.error("系统异常");
    }


}
