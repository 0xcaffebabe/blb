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

    // TODO 等待订单服务开发完成
    /** mock 实现*/
    @Component
    class Fallback implements OrderApiClient{

        @Override
        public Result<OrderResultDTO> getOrder(Long orderId) {
            OrderResultDTO order = new OrderResultDTO();
            order.setOrderStatus(OrderStatusEnum.DONE.getCode());
            order.setConsumerId(1L);
            order.setShopId(1L);
            OrderDetailItemDTO detail1 = new OrderDetailItemDTO();
            detail1.setProductId(1L);
            detail1.setProductSpec(1L);
            OrderDetailItemDTO detail2 = new OrderDetailItemDTO();
            detail2.setProductId(1L);
            detail2.setProductSpec(2L);
            OrderDetailItemDTO detail3 = new OrderDetailItemDTO();
            detail3.setProductId(2L);
            detail3.setProductSpec(3L);
            order.setOrderDetailList(List.of(detail1,detail2,detail3));
            return Result.success(order);
        }

        @Override
        public Result<Long> addOrder(OrderCreateDTO orderCreateDTO) {
            return null;
        }

        @Override
        public Result<Void> updateOrderStatus(Long orderId, Integer status) {
            return null;
        }

        @Override
        public Result<Void> updatePayStatus(Long orderId, Integer status) {
            return null;
        }

        @Override
        public Result<OrderValidCode> getOrderValidCode(Long orderId) {
            return null;
        }

        @Override
        public Result<Void> updateOrderAmount(Long orderId, BigDecimal amount) {
            return null;
        }

        @Override
        public Result<Map<Long,Long>> getProductSales(List<Long> productIdList) {
            Map<Long,Long> map = new HashMap<>();
            for (Long aLong : productIdList) {
                map.put(aLong,100L);
            }
            return Result.success(map);
        }
    }
}
