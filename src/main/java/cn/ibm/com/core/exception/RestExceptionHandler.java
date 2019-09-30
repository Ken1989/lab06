package cn.ibm.com.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.ibm.com.core.constant.ErrorCode;
import cn.ibm.com.core.rest.RestResult;
import cn.ibm.com.core.rest.RestResultGenerator;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> RestResult<T> runtimeExceptionHandler(Exception e) {
        log.error("---------> huge error!", e);
        return RestResultGenerator.genErrorResult(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private <T> RestResult<T> illegalParamsExceptionHandler(MethodArgumentNotValidException e) {
        log.error("---------> invalid request!", e);
        return RestResultGenerator.genErrorResult(ErrorCode.ILLEGAL_PARAMS);
    }
    
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    private <T> RestResult<T> businessException(MethodArgumentNotValidException e) {
        log.error("---------> invalid request!", e);
        return RestResultGenerator.genErrorResult(ErrorCode.ILLEGAL_PARAMS);
    }

}
