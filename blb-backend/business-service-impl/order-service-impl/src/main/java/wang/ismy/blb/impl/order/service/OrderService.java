package wang.ismy.blb.impl.order.service;

import wang.ismy.blb.api.order.pojo.dto.OrderCreateDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderValidCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品服务应该提供的接口
 * @author MY
 * @date 2020/4/24 8:47
 */
public interface OrderService {
    /**
     * 根据ID获取订单信息
     * @param orderId
     * @return
     */
    OrderResultDTO getOrder(Long orderId);

    /**
     * 新增一条订单
     * @param token
     * @param orderCreateDTO
     * @return
     */
    Long addOrder(String token,OrderCreateDTO orderCreateDTO);

    /**
     * 更新订单状态
     * @param orderId
     * @param status
     */
    void updateOrderStatus(Long orderId, Integer status);

    /**
     * 更新支付状态
     * @param orderId
     * @param status
     */
    void updatePayStatus(Long orderId, Integer status);

    /**
     * 获取订单验证码
     * @param orderId
     * @return
     */
    OrderValidCode getOrderValidCode(Long orderId);

    /**
     * 商家更新订单金额
     * @param token
     * @param orderId
     * @param amount
     */
    void updateOrderAmount(String token, Long orderId, BigDecimal amount);

    /**
     * 批量获取商品销量
     * @param productIdList
     * @return
     */
    Map<Long, Long> getProductSales(List<Long> productIdList);
}
