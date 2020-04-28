package wang.ismy.blb.impl.rider.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.rider.RiderApi;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.rider.service.RiderService;

import javax.validation.Valid;

/**
 * @author MY
 * @date 2020/4/28 8:39
 */
@RestController
@AllArgsConstructor
public class RiderApiImpl implements RiderApi {
    private final RiderService riderService;
    @Override
    public Result<String> register(@Valid RegisterDTO registerDTO) {
        return Result.success(riderService.register(registerDTO));
    }

    @Override
    public Result<LoginResultDTO> login(String loginStr, String password) {
        return Result.success(riderService.login(loginStr,password));
    }
}
