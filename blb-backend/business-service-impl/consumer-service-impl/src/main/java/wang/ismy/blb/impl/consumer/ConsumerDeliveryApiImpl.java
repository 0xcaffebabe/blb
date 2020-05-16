package wang.ismy.blb.impl.consumer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.consumer.ConsumerDeliveryApi;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.consumer.service.ConsumerDeliveryService;

import java.util.List;

/**
 * @author MY
 * @date 2020/4/22 8:43
 */
@RestController
@AllArgsConstructor
public class ConsumerDeliveryApiImpl implements ConsumerDeliveryApi {
    private final ConsumerDeliveryService deliveryService;
    @Override
    public Result<Void> addDelivery(DeliveryDTO deliveryDTO) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        deliveryService.addDelivery(token,deliveryDTO);
        return Result.success();
    }

    @Override
    public Result<Void> updateDelivery(Long deliveryId, DeliveryDTO deliveryDTO) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        deliveryService.updateDelivery(token,deliveryId,deliveryDTO);
        return Result.success();
    }

    @Override
    public Result<List<DeliveryDTO>> getDeliveryInfoList() {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(deliveryService.getDeliveryInfoList(token));
    }

    @Override
    public Result<DeliveryDTO> getDefaultDeliveryInfo() {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(deliveryService.getDefaultDeliveryInfo(token));
    }

    @Override
    public Result<DeliveryDTO> getDefaultDeliveryInfo(Long consumerId) {
        return Result.success(deliveryService.getDefaultDeliveryInfo(consumerId));
    }

    @Override
    public Result<Void> deleteDelivery(Long deliveryId) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        deliveryService.deleteDelivery(token,deliveryId);
        return Result.success();
    }

    @Override
    public Result<DeliveryDTO> getDeliveryInfo(Long deliveryId) {
        return Result.success(deliveryService.getDeliveryInfo(deliveryId));
    }
}
