package wang.ismy.blb.impl.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.consumer.ConsumerApi;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;

import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/19 13:17
 */
@FeignClient(value = ServiceName.CONSUMER_SERVICE,fallback = ConsumerApiClient.Fallback.class)
public interface ConsumerApiClient extends ConsumerApi {

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
        public Result<ConsumerDTO> getInfo(String consumerId) {
            return null;
        }

        @Override
        public Result<Map<Long, ConsumerDTO>> getInfo(List<Long> consumerIdList) {
            return null;
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
