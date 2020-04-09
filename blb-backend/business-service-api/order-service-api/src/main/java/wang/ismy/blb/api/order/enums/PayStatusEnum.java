package wang.ismy.blb.api.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MY
 * @date 2020/4/9 9:20
 */
@AllArgsConstructor
@Getter
public enum PayStatusEnum {
    /** 等待支付（未支付） */
    UN_PROCESSED(0,"等待支付"),
    /** 商家已完成，等待骑手取餐 */
    PROCESSED(1,"已支付"),
    /** 订餐者关闭或者超时关闭 */
    SHIPPING(2,"已取消"),
    ;
    private Integer code;
    private String msg;

    public static PayStatusEnum valueOf(Integer code){
        for (PayStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
