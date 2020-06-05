package wang.ismy.blb.aggregation.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author MY
 * @date 2020/6/2 8:55
 */
@Data
public class RiderOrderDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;
    private String dinnerOutCode;
    private String takeMealCode;
    private String shopName;
    private String consumerName;
    private String consumerAddress;
    private String consumerPhone;
    private String shopAddress;
}
