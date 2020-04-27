package wang.ismy.blb.impl.pay.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/27 10:31
 */
@Data
public class PayResultDTO {
    private Long payId;
    private String thirdPartId;
    private Integer status;
    private BigDecimal amount;
    private String msg;
}
