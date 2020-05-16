package wang.ismy.blb.impl.consumer.service;

import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;

import java.util.List;

/**
 * 消费者收货服务提供的接口
 * @author MY
 * @date 2020/4/22 8:43
 */
public interface ConsumerDeliveryService {
    /**
     * 当前消费者增加收货信息
     * @param token
     * @param deliveryDTO
     */
    void addDelivery(String token, DeliveryDTO deliveryDTO);

    /**
     * 当前消费者更新收货信息
     * @param token
     * @param deliveryId
     * @param deliveryDTO
     */
    void updateDelivery(String token, Long deliveryId, DeliveryDTO deliveryDTO);

    /**
     * 获取当前消费者的所有收货信息
     * @param token
     * @return
     */
    List<DeliveryDTO> getDeliveryInfoList(String token);

    /**
     * 获取当前消费者的默认收货信息
     * @param token
     * @return
     */
    DeliveryDTO getDefaultDeliveryInfo(String token);

    /**
     * 获取某位消费者的默认收货信息
     * @param consumerId
     * @return
     */
    DeliveryDTO getDefaultDeliveryInfo(Long consumerId);

    /**
     * 根据收货信息ID获取收货信息
     * @param deliveryId
     * @return
     */
    DeliveryDTO getDeliveryInfo(Long deliveryId);

    /**
     * 消费者删除收货信息
     * @param token
     * @param deliveryId
     */
    void deleteDelivery(String token, Long deliveryId);
}
