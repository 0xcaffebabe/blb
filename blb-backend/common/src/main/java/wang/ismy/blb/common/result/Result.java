package wang.ismy.blb.common.result;

import lombok.Data;
import wang.ismy.blb.common.enums.ResultCode;

/**
 * 所有接口统一返回结果
 * @author MY
 * @date 2020/4/8 8:39
 */
@Data
public class Result<T> {

    private Integer code;

    private Boolean success;

    private String msg;

    private T data;

    /**
     * 生成一个成功的空结果
     * @return 空结果
     */
    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        return result;
    }

    /**
     * 生成一个成功结果
     * @param data 返回数据
     * @return 统一成功结果
     */
    public static <T> Result<T> success(T data){
        Result<T> result = success();
        result.setData(data);
        return result;
    }

    /**
     * 生成一个空的失败结果
     * @param resultCode
     * @return 空失败结果
     */
    public static <T> Result<T> failure(ResultCode resultCode){
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMsg());
        return result;
    }

    /**
     * 自定义code与msg生成空失败结果
     * @param code
     * @param msg
     * @return 空失败结果
     */
    public static <T> Result<T> failure(Integer code,String msg){
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
