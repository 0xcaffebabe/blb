package wang.ismy.blb.aggregation.pojo;

import lombok.Data;

/**
 * @author MY
 * @date 2020/6/2 8:55
 */
@Data
public class RiderOrderDTO {
    private Long orderId;
    private String dinnerOutCode;
    private String takeMealCode;
    private String shopName;
    private String consumerName;
    private String consumerAddress;
    private String consumerPhone;
    private String shopAddress;
}
