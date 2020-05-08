package wang.ismy.blb.aggregation.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;

/**
 * @author MY
 * @date 2020/5/8 15:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeliveryShowDTO extends DeliveryDTO {
    private String realName;
    private String phone;
}
