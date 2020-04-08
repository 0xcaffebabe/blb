package wang.ismy.blb.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTest {

    @Test
    @DisplayName("测试生成成功空结果")
    void testSuccess() {
        var result = Result.success();
        assertEquals(ResultCode.SUCCESS.getMsg(), result.getMsg());
        assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        assertTrue(result.getSuccess());
        assertNull(result.getData());
    }

    @Test
    @DisplayName("测试生成带数据的成功结果")
    void testSuccessWithData() {
        String data = "test";
        Result<String> result = Result.success(data);
        assertEquals(ResultCode.SUCCESS.getMsg(), result.getMsg());
        assertEquals(ResultCode.SUCCESS.getCode(), result.getCode());
        assertTrue(result.getSuccess());
        assertEquals(data, result.getData());
    }

    @Test
    @DisplayName("测试根据返回码枚举生成失败结果")
    void testFailureWithResultCode() {
        var result = Result.failure(ResultCode.PARAM_IS_BLANK);
        assertEquals(ResultCode.PARAM_IS_BLANK.getCode(),result.getCode());
        assertEquals(ResultCode.PARAM_IS_BLANK.getMsg(),result.getMsg());
    }

    @Test
    @DisplayName("测试根据自定义code和自定义msg生成失败结果")
    void testFailureWithCustomize() {
        int code = 9527;
        String msg = "自定义失败";
        var result = Result.failure(code, msg);
        assertEquals(code,result.getCode());
        assertEquals(msg,result.getMsg());
    }
}