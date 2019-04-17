package com.nixum.authentication.exception;

import com.nixum.common.exception.ExceptionResolver;
import com.nixum.common.response.Result;
import com.nixum.common.response.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class SecurityExceptionResolver extends ExceptionResolver {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public Result authException(final Throwable e) {
        log.error("身份验证异常 => {}", e.getMessage());
        return ResultGenerator.genUnauthorizedResult(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class, UsernameNotFoundException.class})
    public Result userException(final Throwable e) {
        log.error("用户异常 => {}", e.getMessage());
        return ResultGenerator.genFailedResult(e.getMessage());
    }
}
