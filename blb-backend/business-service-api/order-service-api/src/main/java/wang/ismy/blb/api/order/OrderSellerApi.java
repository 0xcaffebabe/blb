package wang.ismy.blb.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.order.pojo.dto.NewOrderItemDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 10:43
 */
@Api(tags = "订单商家接口")
@RequestMapping(value = "v1/api/seller",produces = MediaType.APPLICATION_JSON_VALUE)
public interface OrderSellerApi {
    /**
     * 拉取商家订单列表
     * @param query 搜索条件
     * @param page
     * @param size
     * @return 分页订单列表结果
     */
    @ApiOperation("拉取商家订单列表")
    @GetMapping("")
    Result<Page<ConsumerOrderItemDTO>> getSellerOrderList(@RequestBody OrderQuery query,
                                                          @RequestParam("page") Long page,
                                                          @RequestParam("size")Long size);

    /**
     * 获取商家新订单
     * @return
     */
    @ApiOperation("获取商家新订单列表")
    @GetMapping("new")
    Result<List<NewOrderItemDTO>> getNewOrderList();

    /**
     * 拉取卖家订单详细信息
     * @param orderId
     * @return 分页订单列表结果
     */
    @ApiOperation("拉取订餐者订单详细信息")
    @GetMapping("detail/{orderId}")
    Result<ConsumerOrderDetailDTO> getSellerOrderDetail(@PathVariable("orderId") Long orderId);

    class Fallback implements OrderSellerApi{
        @Override
        public Result<Page<ConsumerOrderItemDTO>> getSellerOrderList(OrderQuery query, Long page, Long size) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 卖家订单服务 获取订单列表接口失败");
        }

        @Override
        public Result<List<NewOrderItemDTO>> getNewOrderList() {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 卖家订单服务 获取新订单列表接口失败");
        }

        @Override
        public Result<ConsumerOrderDetailDTO> getSellerOrderDetail(Long orderId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 卖家订单服务 获取订单详情接口失败");
        }
    }
}
