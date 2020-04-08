package wang.ismy.blb.api.rider.pojo.dto.order;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MY
 * @date 2020/4/8 14:15
 */
@Data
@ApiModel("骑手历史订单项")
public class RiderHistoryOrderItemDTO {
   private Long orderId;
   private Long riderId;
   private String shopLogo;
   private BigDecimal amount;
   private LocalDateTime createTime;
}
