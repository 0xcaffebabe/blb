package wang.ismy.blb.api.pay.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MY
 * @date 2020/4/9 11:13
 */
@Getter
@AllArgsConstructor
public enum PayTypeEnum {

    /** 支付宝支付 */
    ALI_PAY(0,"支付宝");
    private Integer code;
    private String type;

    public static PayTypeEnum valueOf(Integer code) {
        for (PayTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
