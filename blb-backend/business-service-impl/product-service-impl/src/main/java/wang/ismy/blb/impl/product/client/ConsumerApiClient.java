package wang.ismy.blb.impl.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.consumer.ConsumerApi;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/19 13:17
 */
@FeignClient(value = ServiceName.CONSUMER_SERVICE,fallback = ConsumerApiClient.Fallback.class)
public interface ConsumerApiClient extends ConsumerApi {

    /** mock 等待消费者服务 todo*/
    @Component
    class Fallback implements ConsumerApiClient{
        @Override
        public Result<RegisterResultDTO> register(RegisterDTO registerDTO) {
            return null;
        }

        @Override
        public Result<LoginResultDTO> login(String loginStr, String password) {
            return null;
        }

        @Override
        public Result<ConsumerDTO> getInfo() {
            return null;
        }

        @Override
        public Result<ConsumerDTO> getInfo(Long consumerId) {
            return null;
        }

        @Override
        public Result<Map<Long, ConsumerDTO>> getInfo(List<Long> consumerIdList) {
            var map = consumerIdList.stream().collect(Collectors.toMap(l->l, l->MockUtils.create(ConsumerDTO.class)));
            return Result.success(map);
        }

        @Override
        public Result<Void> updateInfo(ConsumerUpdateDTO consumerUpdateDTO) {
            return null;
        }

        @Override
        public Result<Void> updatePassword(String oldPasword, String newPassword) {
            return null;
        }
    }

}
