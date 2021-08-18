/*
 *  Copyright 1999-2019 Evision Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.feng.hotel.advice;

import com.feng.hotel.base.Constants;
import com.feng.hotel.base.entity.response.Result;
import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.BizInfo;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常处理器，对所有对异常进行处理
     *
     * @param e 异常
     * @return 处理后对统一返回
     * @see BizException                       业务异常处理
     * @see ConstraintViolationException       校验异常
     * @see MethodArgumentNotValidException    校验异常
     * @see BindException                      校验异常
     */
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Result<?> handleException(Throwable e, HttpServletRequest request) {
        String requestUri = Objects.isNull(request) ? StringUtils.EMPTY : request.getRequestURI();
        boolean debug = Boolean.parseBoolean(request.getHeader(Constants.ENABLE_DEBUG_HEADER));

        String cause = ExceptionUtils.getStackTrace(e);

        LOGGER.error("handle exception. request path: [{}]. cause: [{}]", requestUri, cause);

        if (e instanceof BizException) {
            return Result.error((BizException) e, debug ? cause : null);
        }
        if (e instanceof MultipartException) {
            return Result.error(BizInfo.FILE_UPLOAD_ERROR);

        }
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException ce = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> constraintViolations = ce.getConstraintViolations();
            if (CollectionUtils.isEmpty(constraintViolations)) {
                return Result.error(BizInfo.PARAM_VALIDATE_ERROR, debug ? cause : null);
            }

            ConstraintViolation<?> violation = constraintViolations
                .stream()
                .findFirst()
                .orElse(null);

            if (violation == null) {
                return Result.error(BizInfo.PARAM_VALIDATE_ERROR, debug ? cause : null);
            }

            String message = violation.getMessage();
            return Result.error(BizInfo.PARAM_VALIDATE_ERROR.code(), message, debug ? cause : null);
        }

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mve = (MethodArgumentNotValidException) e;
            BindingResult result = mve.getBindingResult();
            if (!result.hasErrors()) {
                return Result.error(BizInfo.PARAM_VALIDATE_ERROR, debug ? cause : null);
            }

            if (result.hasFieldErrors()) {
                Result<?> bingResult = dealBindingError(debug, cause, result);
                if (bingResult != null) {
                    return bingResult;
                }
            }
        }

        if (e instanceof BindException) {
            BindException be = (BindException) e;
            BindingResult result = be.getBindingResult();
            if (!result.hasErrors()) {
                return Result.error(BizInfo.PARAM_VALIDATE_ERROR, debug ? cause : null);
            }

            Result<?> bingResult = dealBindingError(debug, cause, result);
            if (bingResult != null) {
                return bingResult;
            }
        }

        return Result.error(BizInfo.SYS_ERROR, debug ? cause : null);
    }

    /**
     * 处理参数绑定错误
     *
     * @param debug  是否开启了debug
     * @param cause  异常信息
     * @param result 参数绑定的结果
     * @return 对参数绑定异常的处理结果
     */
    private Result<?> dealBindingError(boolean debug, String cause, BindingResult result) {
        List<ObjectError> allErrors = result.getAllErrors();
        if (!CollectionUtils.isEmpty(allErrors)) {
            ObjectError error = allErrors.get(0);
            String defaultMessage = error.getDefaultMessage();
            return Result.error(BizInfo.PARAM_VALIDATE_ERROR.code(), defaultMessage,
                debug ? cause : null);
        }
        return null;
    }


}
