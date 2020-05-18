package wang.ismy.blb.api.order.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;

import java.util.List;

/**
 * @author MY
 * @date 2020/5/18 20:18
 */
@Data
public class NewOrderItemDTO extends ConsumerOrderDetailDTO {
    private String diningOutCode;
}
