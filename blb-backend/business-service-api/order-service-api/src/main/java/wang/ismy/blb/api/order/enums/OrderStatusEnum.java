package wang.ismy.blb.api.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MY
 * @date 2020/4/9 9:11
 */
@AllArgsConstructor
@Getter
public enum OrderStatusEnum {

    /** 订单未处理状态（商家未处理） */
    UN_PROCESSED(0,"未处理"),
    /** 商家已完成，等待骑手取餐*/
    PROCESSED(1,"已处理"),
    /** 骑手配送中*/
    SHIPPING(2,"配送中"),
    /** 骑手已将餐品交给消费者 */
    DONE(3,"已完结"),
    /** 商家拒绝接单或者订单被取消 */
    INVALID(4,"已作废")
    ;
    private Integer code;
    private String msg;

    public static OrderStatusEnum valueOf(Integer code){
        for (OrderStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
