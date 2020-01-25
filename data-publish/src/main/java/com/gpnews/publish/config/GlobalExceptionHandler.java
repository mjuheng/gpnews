package com.gpnews.publish.config;

import com.gpnews.utils.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author HuangChongHeng
 * @date 2020/1/20
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleBindException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        log.warn("参数校验异常:{}({})", fieldError.getDefaultMessage(),fieldError.getField());
        return ResponseEntity.status(500).body(ResultUtil.errorSingleResult(fieldError.getDefaultMessage()));
    }

//    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResponseEntity handleAuthorizationException(AuthorizationException ex){
        return ResponseEntity.status(401).body(ResultUtil.errorSingleResult("权限不足"));
    }
}
