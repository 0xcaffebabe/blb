package wang.ismy.blb.impl.consumer.service.impl;

import com.fasterxml.jackson.databind.util.ArrayIterator;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.consumer.pojo.DeliveryInfoDO;
import wang.ismy.blb.api.consumer.pojo.dto.DeliveryDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.impl.consumer.client.AuthApiClient;
import wang.ismy.blb.impl.consumer.entity.ConsumerDeliveryDO;
import wang.ismy.blb.impl.consumer.repository.ConsumerDeliveryRepository;
import wang.ismy.blb.impl.consumer.repository.DeliveryRepository;
import wang.ismy.blb.impl.consumer.service.ConsumerDeliveryService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/22 9:00
 */
@Service
@AllArgsConstructor
@Slf4j
@Setter
public class ConsumerDeliveryServiceImpl implements ConsumerDeliveryService {
    private DeliveryRepository deliveryRepository;
    private ConsumerDeliveryRepository consumerDeliveryRepository;
    private AuthApiClient authApiClient;
    private SnowFlake snowFlake;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void addDelivery(String token, DeliveryDTO deliveryDTO) {
        User consumer = getConsumer(token);
        if (consumer == null) {
            return;
        }
        DeliveryInfoDO deliveryInfoDO = new DeliveryInfoDO();
        deliveryInfoDO.setBuilding(deliveryDTO.getBuilding());
        deliveryInfoDO.setDetail(deliveryDTO.getDetail());
        deliveryInfoDO.setDeliveryInfoId(snowFlake.nextId());
        deliveryInfoDO.initTime();

        deliveryRepository.save(deliveryInfoDO);

        // 如果新增的是默认地址，则将其之前的默认地址设置为false
        if (deliveryDTO.getDefaultDelivery() != null && deliveryDTO.getDefaultDelivery()) {
            consumerDeliveryRepository.resetDefaultDelivery(consumer.getUserId());
        }

        ConsumerDeliveryDO consumerDeliveryDO = new ConsumerDeliveryDO();
        consumerDeliveryDO.setDefaultDelivery(deliveryDTO.getDefaultDelivery());
        consumerDeliveryDO.setDeliveryInfoId(deliveryInfoDO.getDeliveryInfoId());
        consumerDeliveryDO.setUserId(consumer.getUserId());
        consumerDeliveryDO.initTime();
        consumerDeliveryRepository.save(consumerDeliveryDO);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateDelivery(String token, Long deliveryId, DeliveryDTO deliveryDTO) {
        User consumer = getConsumer(token);
        if (consumer == null) {
            return;
        }
        DeliveryInfoDO deliveryInfoDO = deliveryRepository.findById(deliveryId).orElse(null);
        if (deliveryInfoDO == null) {
            log.warn("更新收货信息 找不到指定的收货信息:{}", deliveryId);
            return;
        }
        ConsumerDeliveryDO consumerDeliveryDO =
                consumerDeliveryRepository.findById(deliveryInfoDO.getDeliveryInfoId()).orElse(null);
        deliveryInfoDO.setBuilding(deliveryDTO.getBuilding());
        deliveryInfoDO.setDetail(deliveryDTO.getDetail());
        deliveryInfoDO.setUpdateTime(LocalDateTime.now());
        deliveryRepository.save(deliveryInfoDO);
        if (!consumerDeliveryDO.getUserId().equals(consumer.getUserId())) {
            log.warn("收货信息{}不属于用户{}", deliveryInfoDO.getDeliveryInfoId(), consumer.getUserId());
            throw new BlbException("收货信息不属于当前用户");
        }
        // 如果更新了默认地址，则将其之前的默认地址设置为false
        if (deliveryDTO.getDefaultDelivery() != null && deliveryDTO.getDefaultDelivery()) {

            consumerDeliveryRepository.findAllByUserIdAndDefaultDelivery(consumer.getUserId(),true)
                    .forEach(s->{
                        s.setDefaultDelivery(false);
                        consumerDeliveryRepository.save(s);
                    });
        }
        consumerDeliveryDO.setDefaultDelivery(deliveryDTO.getDefaultDelivery());
        consumerDeliveryRepository.save(consumerDeliveryDO);

    }

    @Override
    public List<DeliveryDTO> getDeliveryInfoList(String token) {
        User consumer = getConsumer(token);
        if (consumer == null) {
            return List.of();
        }
        var consumerDeliveryMap = consumerDeliveryRepository.findAllByUserId(consumer.getUserId())
                .stream()
                .collect(Collectors.toMap(d -> d.getDeliveryInfoId(), d -> d));
        var deliveryList = deliveryRepository.findAllById(new ArrayList<>(consumerDeliveryMap.keySet()));
        List<DeliveryDTO> result = new ArrayList<>();
        for (DeliveryInfoDO deliveryInfoDO : deliveryList) {
            DeliveryDTO dto = new DeliveryDTO();
            dto.setDetail(deliveryInfoDO.getDetail());
            dto.setBuilding(deliveryInfoDO.getBuilding());
            dto.setDefaultDelivery(consumerDeliveryMap.get(deliveryInfoDO.getDeliveryInfoId()).getDefaultDelivery());
            dto.setDeliveryId(deliveryInfoDO.getDeliveryInfoId());
            result.add(dto);
        }
        return result;
    }

    @Override
    public DeliveryDTO getDefaultDeliveryInfo(String token) {
        User consumer = getConsumer(token);
        if (consumer == null) {
            return null;
        }
        return getDeliveryDTO(consumer.getUserId());
    }

    @Override
    public DeliveryDTO getDefaultDeliveryInfo(Long consumerId) {
        return getDeliveryDTO(consumerId);
    }

    @Override
    public DeliveryDTO getDeliveryInfo(Long deliveryId) {
        var consumerDelivery = consumerDeliveryRepository.findById(deliveryId).orElse(null);
        if (consumerDelivery == null) {
            log.warn("找不到该收货信息:{}", deliveryId);
            return null;
        }
        return getDeliveryDTO(consumerDelivery);
    }

    private DeliveryDTO getDeliveryDTO(ConsumerDeliveryDO consumerDelivery) {
        var delivery = deliveryRepository.findById(consumerDelivery.getDeliveryInfoId()).orElse(null);

        DeliveryDTO dto = new DeliveryDTO();
        dto.setDetail(delivery.getDetail());
        dto.setBuilding(delivery.getBuilding());
        dto.setDefaultDelivery(consumerDelivery.getDefaultDelivery());
        dto.setDeliveryId(delivery.getDeliveryInfoId());

        return dto;
    }

    private DeliveryDTO getDeliveryDTO(Long consumerId) {
        var deliveryList = consumerDeliveryRepository.findAllByUserIdAndDefaultDelivery(consumerId, true);
        if (CollectionUtils.isEmpty(deliveryList)) {
            log.warn("用户{}没有默认收货信息", consumerId);
            return null;
        }
        var consumerDelivery = deliveryList.get(0);
        return getDeliveryDTO(consumerDelivery);
    }

    private User getConsumer(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()) {
            log.warn("收货服务 调用认证服务失败，{}", authRes);
            return null;
        }
        var consumer = authRes.getData();
        if (consumer == null) {
            log.warn("收货服务 获取不到用户信息：{}", token);
            return null;
        }
        return consumer;
    }
}
