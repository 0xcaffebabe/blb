package wang.ismy.blb.common;

import wang.ismy.blb.common.enums.ResultCode;

/**
 * @author MY
 * @date 2020/4/13 10:11
 */
public class BlbException extends RuntimeException {

    private ResultCode resultCode;
    private String msg;

    public BlbException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public BlbException(String msg){
        this.msg = msg;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public String getMsg() {
        return msg;
    }
}
