package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.aggregation.client.consumer.ConsumerApiClient;
import wang.ismy.blb.aggregation.client.consumer.ConsumerDeliveryApiClient;
import wang.ismy.blb.aggregation.pojo.DeliveryShowDTO;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/5/8 15:32
 */
@RestController
@RequestMapping(value = "delivery",produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "收货信息接口")
@AllArgsConstructor
public class DeliveryAggApi {
    private final ConsumerDeliveryApiClient deliveryApiClient;
    private final ConsumerApiClient consumerApiClient;
    @ApiOperation("获取默认收货地址")
    @GetMapping("default")
    public Result getDefaultDelivery(){
        var deliveryRes = deliveryApiClient.getDefaultDeliveryInfo();
        if (!deliveryRes.getSuccess()){
            return deliveryRes;
        }
        var consumerRes = consumerApiClient.getInfo();
        if (!consumerRes.getSuccess()){
            return deliveryRes;
        }
        DeliveryShowDTO showDTO = new DeliveryShowDTO();
        BeanUtils.copyProperties(deliveryRes.getData(),showDTO);
        showDTO.setRealName(consumerRes.getData().getRealName());
        showDTO.setPhone(consumerRes.getData().getPhone());

        return Result.success(showDTO);
    }
}
