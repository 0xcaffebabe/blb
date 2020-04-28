package wang.ismy.blb.impl.rider.service;

import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;

/**
 * @author MY
 * @date 2020/4/28 8:41
 */
public interface RiderService {
    /**
     * 骑手注册
     * @param registerDTO
     * @return
     */
    String register(RegisterDTO registerDTO);

    /**
     * 骑手登录
     * @param loginStr
     * @param password
     * @return
     */
    LoginResultDTO login(String loginStr, String password);
}
