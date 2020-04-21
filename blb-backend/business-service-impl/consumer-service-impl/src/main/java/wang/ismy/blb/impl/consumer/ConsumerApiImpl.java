package wang.ismy.blb.impl.consumer;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.consumer.ConsumerApi;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.consumer.service.ConsumerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/21 8:57
 */
@RestController
@AllArgsConstructor
public class ConsumerApiImpl implements ConsumerApi {
    private final ConsumerService consumerService;
    @Override
    public Result<RegisterResultDTO> register(@Valid RegisterDTO registerDTO) {
        return Result.success(consumerService.register(registerDTO));
    }

    @Override
    public Result<LoginResultDTO> login(String loginStr, String password) {
        return Result.success(consumerService.login(loginStr,password));
    }

    @Override
    public Result<ConsumerDTO> getInfo() {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(consumerService.getInfo(token));
    }

    @Override
    public Result<ConsumerDTO> getInfo(Long consumerId) {
        return Result.success(consumerService.getInfo(consumerId));
    }

    @Override
    public Result<Map<Long, ConsumerDTO>> getInfo(List<Long> consumerIdList) {
        return Result.success(consumerService.getInfo(consumerIdList));
    }

    @Override
    public Result<Void> updateInfo(ConsumerUpdateDTO consumerUpdateDTO) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        consumerService.updateInfo(token,consumerUpdateDTO);
        return Result.success();
    }

    @Override
    public Result<Void> updatePassword(String oldPassword, String newPassword) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        consumerService.updatePassword(token,oldPassword,newPassword);
        return Result.success();
    }
}
