package wang.ismy.blb.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/9 10:26
 */
@Api(tags = "订单消费者接口")
@RequestMapping("consumer")
public interface OrderConsumerApi {

    /**
     * 拉取订餐者订单列表
     * @param query 搜索条件
     * @param pageable
     * @return 分页订单列表结果
     */
    @ApiOperation("拉取订餐者订单列表")
    @GetMapping("")
    Result<Page<ConsumerOrderItemDTO>> getConsumerOrderList(OrderQuery query, Pageable pageable);

    /**
     * 拉取订餐者订单详细信息
     *
     * @param orderId
     * @return 分页订单列表结果
     */
    @ApiOperation("拉取订餐者订单详细信息")
    @GetMapping("detail/{orderId}")
    Result<ConsumerOrderDetailDTO> getConsumerOrderDetail(@PathVariable("orderId") Long orderId);
}
