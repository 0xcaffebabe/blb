package wang.ismy.blb.aggregation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.aggregation.annotations.NeedLogin;
import wang.ismy.blb.aggregation.client.*;
import wang.ismy.blb.aggregation.pojo.RiderOrderDTO;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;
import wang.ismy.blb.api.order.pojo.dto.OrderResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.OrderRiderDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.api.rider.pojo.dto.order.RiderHistoryOrderItemDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;

import java.util.List;

/**
 * @author MY
 * @date 2020/6/1 9:02
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RiderAggApi {
    private final RiderApiClient riderApiClient;
    private final RiderOrderApiClient riderOrderApiClient;
    private final OrderApiClient orderApiClient;
    private final ShopApiClient shopApiClient;
    private final AuthApiClient authApiClient;
    @PostMapping("register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO){
        return riderApiClient.register(registerDTO);
    }

    @PostMapping("login")
    public Result<LoginResultDTO> login(@RequestParam String username,@RequestParam String password){
        return riderApiClient.login(username,password);
    }

    @GetMapping("order/undelivery")
    public Result<RiderHistoryOrderItemDTO> getUndeliveryOrder(){
        return riderOrderApiClient.getRiderUnDeliveryOrder();
    }

    @GetMapping("order/delivery")
    @NeedLogin
    public Result<List<OrderResultDTO>> getDeliveryOrder(){
        return orderApiClient.getDeliveryOrder();
    }

    @PostMapping("order/grab/{orderId}")
    @NeedLogin
    public Result<String> grabOrder(@PathVariable Long orderId){
        return riderOrderApiClient.riderGrabOrder(orderId);
    }

    @GetMapping("order/detail/{orderId}")
    @NeedLogin
    public Result<RiderOrderDTO> getOrderDetail(@PathVariable Long orderId){
        var orderRes = orderApiClient.getOrder(orderId);
        if (!orderRes.getSuccess()){
            log.warn("调用 订单服务失败：{}",orderRes);
            throw new BlbException("调用订单服务失败");
        }
        var order = orderRes.getData();

        OrderRiderDTO rider = getRider(orderId);

        if (rider == null){
            log.warn("骑手订单{}不匹配",orderId);
            throw new BlbException("骑手订单不匹配");
        }

        var shopRes = shopApiClient.getShopInfo(order.getShopId());
        if (!shopRes.getSuccess()){
            log.warn("调用店铺服务失败:{}",shopRes);
            throw new BlbException("调用店铺服务失败");
        }
        var shop = shopRes.getData();

        var orderCodeRes = orderApiClient.getOrderValidCode(orderId);
        if (!orderCodeRes.getSuccess()){
            log.warn("调用订单服务失败:{}",orderCodeRes);
            throw new BlbException("调用订单服务失败");
        }
        var code = orderCodeRes.getData();

        RiderOrderDTO dto = new RiderOrderDTO();
        BeanUtils.copyProperties(order,dto);
        dto.setShopName(shop.getShopName());
        dto.setShopAddress(shop.getShopAddress());
        dto.setDinnerOutCode(code.getDiningOutCode());
        dto.setTakeMealCode(code.getTakeMealCode());

        return Result.success(dto);
    }

    private OrderRiderDTO getRider(@PathVariable Long orderId) {
        var riderRes = riderOrderApiClient.getRiderByOrder(orderId);
        if (!riderRes.getSuccess()){
            log.warn("调用 骑手服务失败:{}",riderRes);
            throw new BlbException("调用骑手服务失败");
        }
        return riderRes.getData();
    }

    @PostMapping("order/{orderId}/delivery")
    @NeedLogin
    public Result<Void> setOrderDelivery(@PathVariable Long orderId){
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("调用认证服务失败:{}",authRes);
            throw new BlbException("调用认证服务失败");
        }
        var user = authRes.getData();
        var rider = getRider(orderId);
        if (rider == null){
            log.warn("订单{}无法找到骑手",orderId);
            throw new BlbException("订单无法找到骑手");
        }
        if (user.getUserId().equals(rider.getRiderId())){
            return orderApiClient.updateOrderStatus(orderId, OrderStatusEnum.SHIPPING.getCode());
        }
        return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
    }

    @PostMapping("order/{orderId}/complete")
    @NeedLogin
    public Result<String> setOrderComplete(@PathVariable Long orderId,@RequestParam("code") String code){
        return riderOrderApiClient.riderCompleteOrder(orderId,code);
    }
}
