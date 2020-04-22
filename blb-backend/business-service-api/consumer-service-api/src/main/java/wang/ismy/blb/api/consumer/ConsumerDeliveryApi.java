package wang.ismy.blb.api.consumer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/9 20:57
 */
@Api(tags = "消费者收货信息服务")
@RequestMapping(value = "v1/api/delivery",produces = MediaType.APPLICATION_JSON_VALUE)
public interface ConsumerDeliveryApi {

    /**
     * 消费者增加收货信息接口
     * @param deliveryDTO
     * @return 操作结果写入result
     */
    @ApiOperation("消费者增加收货信息接口")
    @PostMapping("")
    Result<Void> addDelivery(@RequestBody DeliveryDTO deliveryDTO);

    /**
     * 消费者更新收货信息
     * @param deliveryId
     * @param deliveryDTO
     * @return
     */
    @ApiOperation("消费者更新收货信息")
    @ApiImplicitParam(paramType = "path", name = "deliveryId", dataType = "Long", required = true, value = "收货信息ID")
    @PutMapping("{deliveryId}")
    Result<Void> updateDelivery(@PathVariable("deliveryId") Long deliveryId,
                                @RequestBody DeliveryDTO deliveryDTO);

    /**
     * 消费者获取自己的收货信息列表
     * @return 收货信息列表
     */
    @ApiOperation("消费者获取自己的收货信息列表")
    @GetMapping("list")
    Result<List<DeliveryDTO>> getDeliveryInfoList();

    /**
     * 消费者获取自己的默认收货信息
     * @return 没有默认收货信息返回null
     */
    @ApiOperation("消费者获取自己的默认收货信息")
    @GetMapping("default")
    Result<DeliveryDTO> getDefaultDeliveryInfo();

    /**
     * 根据消费者ID获取默认收货信息
     * @param consumerId
     * @return 没有默认收货信息返回null
     */
    @ApiOperation("根据消费者ID获取默认收货信息")
    @ApiImplicitParam(paramType = "path", name = "consumerId", dataType = "Long", required = true, value = "消费者ID")
    @GetMapping("default/{consumerId}")
    Result<DeliveryDTO> getDefaultDeliveryInfo(@PathVariable Long consumerId);

    /**
     * 根据收货信息ID获取收货信息
     * @param deliveryId
     * @return 不存在返回null
     */
    @ApiOperation("根据收货信息ID获取收货信息")
    @ApiImplicitParam(paramType = "path", name = "deliveryId", dataType = "Long", required = true, value = "收货信息ID")
    @GetMapping("{deliveryId}")
    Result<DeliveryDTO> getDeliveryInfo(@PathVariable Long deliveryId);
}
