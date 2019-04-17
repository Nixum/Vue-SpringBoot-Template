package com.nixum.common.exception;

import com.nixum.common.response.Result;
import com.nixum.common.response.ResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 * https://blog.csdn.net/u013194072/article/details/79044286
 * 当子模块有自己的全局异常通知时，需要把该注解注掉，使用子模块的异常通知去继承，并加注解
 * 如果两个模块都带注解的话，这个异常通知的Exception异常会覆盖子模块的异常通知
 */
//@RestControllerAdvice
public class ExceptionResolver {
    private final static Logger log = LoggerFactory.getLogger(ExceptionResolver.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result validatorException(final ConstraintViolationException e) {
        log.error("验证实体异常 => {}", e.getMessage());
        final String msg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return ResultGenerator.genFailedResult(msg);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ServiceException.class, ServletException.class})
    public Result serviceException(final Throwable e) {
        log.error("服务异常 => {}", e.getMessage());
        return ResultGenerator.genFailedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public Result databaseException(final Throwable e) {
        log.error("数据库异常 => {}", e.getMessage());
        return ResultGenerator.genFailedResult("database error");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result apiNotFound(final Throwable e, final HttpServletRequest request) {
        log.error("API 不存在 => {}", e.getMessage());
        return ResultGenerator.genFailedResult("API [" + request.getRequestURI() + "] not existed");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result methodNotSupport(final Throwable e) {
        log.error("方法异常 => {}", e.getMessage());
        return ResultGenerator.genMethodErrorResult();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result globalException(final HttpServletRequest request, final Throwable e) {
        log.error("全局异常 => {}", e.getMessage());
        return ResultGenerator.genInternalServerErrorResult(String.format("%s => %s", request.getRequestURI(), e.getMessage()));
    }
}
