package wang.ismy.blb.impl.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.order.OrderApi;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderDetailItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/18 9:16
 */
@FeignClient(value = ServiceName.ORDER_SERVICE,fallback = OrderApiClient.Fallback.class)
public interface OrderApiClient extends OrderApi {
    @Component
    class Fallback extends OrderApi.Fallback implements OrderApiClient{ }
}
