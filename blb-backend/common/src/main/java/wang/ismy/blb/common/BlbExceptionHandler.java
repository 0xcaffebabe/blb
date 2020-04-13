package wang.ismy.blb.common;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/13 10:12
 */
@RestControllerAdvice
public class BlbExceptionHandler {

    @ExceptionHandler(BlbException.class)
    public Result<Object> handle(BlbException e){
        return Result.failure(e.getResultCode());
    }

    @ExceptionHandler(Throwable.class)
    public Result<Object> handle(RuntimeException e){
        return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handle(MethodArgumentNotValidException e){
        return Result.failure(ResultCode.PARAM_IS_INVALID.getCode(),e.getBindingResult().getFieldError().getDefaultMessage());
    }
}
