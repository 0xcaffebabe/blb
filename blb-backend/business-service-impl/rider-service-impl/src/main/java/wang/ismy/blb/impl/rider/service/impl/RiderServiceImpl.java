package wang.ismy.blb.impl.rider.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.api.rider.pojo.entity.RiderDO;
import wang.ismy.blb.api.rider.pojo.entity.RiderInfoDO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.impl.rider.client.AuthApiClient;
import wang.ismy.blb.impl.rider.repository.RiderInfoRepository;
import wang.ismy.blb.impl.rider.repository.RiderRepository;
import wang.ismy.blb.impl.rider.service.RiderService;

/**
 * @author MY
 * @date 2020/4/28 9:01
 */
@Service
@AllArgsConstructor
@Slf4j
@Setter
public class RiderServiceImpl implements RiderService {
    private final RiderRepository riderRepository;
    private final RiderInfoRepository riderInfoRepository;
    private final SnowFlake snowFlake;
    private AuthApiClient authApiClient;
    @Override
    public String register(RegisterDTO registerDTO) {
        if (riderRepository.existsByUsername(registerDTO.getUsername())){
            log.warn("用户名{}已存在",registerDTO.getUsername());
            throw new BlbException("用户名已存在");
        }
        RiderDO riderDO = new RiderDO();
        BeanUtils.copyProperties(registerDTO,riderDO);
        riderDO.setEnable(true);
        riderDO.setRiderId(snowFlake.nextId());
        riderDO.setPassword(DigestUtils.md5Hex(registerDTO.getPassword()));
        riderRepository.save(riderDO);

        RiderInfoDO riderInfoDO = new RiderInfoDO();
        BeanUtils.copyProperties(registerDTO,riderInfoDO);
        riderInfoDO.setRiderId(riderDO.getRiderId());
        riderInfoRepository.save(riderInfoDO);
        return "注册成功，欢迎成为饱了吧第"+riderRepository.count()+"个骑手";
    }

    @Override
    public LoginResultDTO login(String loginStr, String password) {
        RiderDO rider = riderRepository.findByUsername(loginStr);
        if (rider == null){
            log.warn("用户{}不存在",loginStr);
            throw new BlbException("账号或者密码不正确");
        }
        if (!rider.getPassword().equals(DigestUtils.md5Hex(password))){
            log.warn("用户{}密码不正确",loginStr);
            throw new BlbException("账号或者密码不正确");
        }
        User user = new User();
        user.setUserType(UserTypeEnum.RIDER.getType());
        user.setUsername(rider.getUsername());
        user.setUserId(rider.getRiderId());
        var authRes = authApiClient.auth(user);
        if (!authRes.getSuccess()){
            log.warn("调用认证服务失败:{}",authRes);
            throw new BlbException(ResultCode.AUTH_ERROR);
        }
        LoginResultDTO result = new LoginResultDTO();
        result.setToken(authRes.getData());
        result.setGreeting("欢迎登录");
        return result;
    }
}
