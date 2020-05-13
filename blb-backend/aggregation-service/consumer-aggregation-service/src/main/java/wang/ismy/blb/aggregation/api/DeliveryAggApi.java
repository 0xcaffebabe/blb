package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.aggregation.client.consumer.ConsumerApiClient;
import wang.ismy.blb.aggregation.client.consumer.ConsumerDeliveryApiClient;
import wang.ismy.blb.aggregation.pojo.DeliveryShowDTO;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.common.result.Result;

import java.util.List;

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
        if (!deliveryRes.getSuccess() || deliveryRes.getData() == null){
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

    @ApiOperation("拉取收货信息列表")
    @GetMapping("")
    public Result<List<DeliveryDTO>> getDeliveryInfoList(){
        return deliveryApiClient.getDeliveryInfoList();
    }

    @ApiOperation("更新收货信息")
    @PutMapping("{deliveryId}")
    public Result<Void> updateDelivery(@PathVariable Long deliveryId,
                                       @RequestBody DeliveryDTO deliveryDTO){
        return deliveryApiClient.updateDelivery(deliveryId, deliveryDTO);
    }

    @ApiOperation("新增收货信息")
    @PostMapping()
    public Result<Void> addDelivery(@RequestBody DeliveryDTO deliveryDTO){
        return deliveryApiClient.addDelivery(deliveryDTO);
    }
}
