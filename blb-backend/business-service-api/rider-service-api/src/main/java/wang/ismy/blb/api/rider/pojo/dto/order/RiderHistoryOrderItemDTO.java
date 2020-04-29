package wang.ismy.blb.api.rider.pojo.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createTime;
}
