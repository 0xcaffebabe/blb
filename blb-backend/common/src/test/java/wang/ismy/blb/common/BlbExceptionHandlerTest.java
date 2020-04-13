package wang.ismy.blb.common;

import org.junit.jupiter.api.Test;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

import static org.junit.jupiter.api.Assertions.*;

class BlbExceptionHandlerTest {

    @Test
    void testBusinessException(){
        BlbException blbException = new BlbException(ResultCode.AUTH_ERROR);
        BlbExceptionHandler handler = new BlbExceptionHandler();
        var result = handler.handle(blbException);
        assertEquals(Result.failure(ResultCode.AUTH_ERROR),result);
    }

    @Test
    void testAllException(){
        RuntimeException runtimeException = new RuntimeException("test runtime");
        BlbExceptionHandler handler = new BlbExceptionHandler();
        var result = handler.handle(runtimeException);
        assertEquals("test runtime",result.getMsg());
        assertEquals(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),result.getCode());
    }
}