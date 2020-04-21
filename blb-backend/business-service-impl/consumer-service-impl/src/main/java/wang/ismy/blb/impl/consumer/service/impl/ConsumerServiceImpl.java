package wang.ismy.blb.impl.consumer.service.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.consumer.pojo.ConsumerDO;
import wang.ismy.blb.api.consumer.pojo.ConsumerInfoDO;
import wang.ismy.blb.api.consumer.pojo.dto.*;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.impl.consumer.client.AuthApiClient;
import wang.ismy.blb.impl.consumer.repository.ConsumerInfoRepository;
import wang.ismy.blb.impl.consumer.repository.ConsumerRepository;
import wang.ismy.blb.impl.consumer.service.ConsumerService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/21 9:39
 */
@AllArgsConstructor
@Service
@Setter
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {
    private  ConsumerRepository consumerRepository;
    private  ConsumerInfoRepository consumerInfoRepository;
    private  AuthApiClient authApiClient;
    private  SnowFlake snowFlake;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResultDTO register(RegisterDTO registerDTO) {
        if (consumerRepository.findByUsername(registerDTO.getUsername())!=null){
            throw new BlbException(ResultCode.USER_HAS_EXISTED);
        }
        ConsumerDO consumerDO = new ConsumerDO();
        consumerDO.setUsername(registerDTO.getUsername());
        consumerDO.setPhone(registerDTO.getPhone());
        consumerDO.setPassword(DigestUtils.md5Hex(registerDTO.getPassword()));
        consumerDO.setEmail(registerDTO.getEmail());
        consumerDO.setEnable(true);
        consumerDO.setUserId(snowFlake.nextId());
        consumerDO.setCreateTime(LocalDateTime.now());
        consumerDO.setUpdateTime(LocalDateTime.now());

        consumerRepository.save(consumerDO);

        ConsumerInfoDO consumerInfoDO = new ConsumerInfoDO();
        consumerInfoDO.setAvatar(registerDTO.getAvatar());
        consumerInfoDO.setGender(registerDTO.getGender());
        consumerInfoDO.setRealName(registerDTO.getRealName());
        consumerInfoDO.setUserId(consumerDO.getUserId());
        consumerInfoRepository.save(consumerInfoDO);

        long number = consumerRepository.count();
        RegisterResultDTO result = new RegisterResultDTO();
        result.setGreeting("注册成功，欢迎加入饱了吧");
        result.setUserNumber(number);
        return result;
    }

    @Override
    public LoginResultDTO login(String loginStr, String password) {
        ConsumerDO consumer = consumerRepository.findByUsername(loginStr);
        if (consumer == null){
            throw new BlbException(ResultCode.USER_LOGIN_ERROR);
        }
        if (!consumer.getPassword().equals(DigestUtils.md5Hex(password))){
            throw new BlbException(ResultCode.USER_LOGIN_ERROR);
        }
        User user = new User();
        user.setUserType(UserTypeEnum.CONSUMER.getType());
        user.setUsername(consumer.getUsername());
        user.setUserId(consumer.getUserId());
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

    @Override
    public ConsumerDTO getInfo(String token) {
        User consumer = getConsumer(token);
        if (!consumer.getUserType().equals(UserTypeEnum.CONSUMER.getType())){
            log.warn("当前登录用户不是消费者");
            throw new BlbException(ResultCode.AUTH_ERROR);
        }
        var consumerInfo = consumerInfoRepository.findById(consumer.getUserId()).orElse(null);
        if (consumerInfo == null){
            throw new BlbException(ResultCode.USER_NOT_EXIST);
        }
        ConsumerDTO dto = new ConsumerDTO();
        BeanUtils.copyProperties(consumerInfo,dto);
        dto.setUsername(consumer.getUsername());
        return dto;
    }

    private User getConsumer(String token) {
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("调用认证服务失败:{}",authRes);
            throw new BlbException(ResultCode.AUTH_ERROR);
        }
        return authRes.getData();
    }

    @Override
    public ConsumerDTO getInfo(Long consumerId) {
        var consumer = consumerRepository.findById(consumerId).orElse(null);
        var consumerInfo = consumerInfoRepository.findById(consumerId).orElse(null);
        if (consumerInfo == null || consumer == null){
            throw new BlbException(ResultCode.USER_NOT_EXIST);
        }
        ConsumerDTO dto = new ConsumerDTO();
        BeanUtils.copyProperties(consumerInfo,dto);
        dto.setUsername(consumer.getUsername());
        return dto;
    }

    @Override
    public Map<Long, ConsumerDTO> getInfo(List<Long> consumerIdList) {
        var consumerMap = consumerRepository.findAllById(consumerIdList)
                .stream()
                .collect(Collectors.toMap(ConsumerDO::getUserId, c->c));
        var consumerInfoMap = consumerInfoRepository.findAllById(consumerIdList)
                .stream()
                .collect(Collectors.toMap(ConsumerInfoDO::getUserId, c->c));
        return consumerIdList.stream()
                .collect(Collectors.toMap(l->l,l->{
                    ConsumerDTO dto = new ConsumerDTO();
                    BeanUtils.copyProperties(consumerInfoMap.get(l),dto);
                    dto.setUsername(consumerMap.get(l).getUsername());
                    return dto;
                }));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateInfo(String token, ConsumerUpdateDTO consumerUpdateDTO) {
        var consumer = getConsumer(token);
        var consumerDO = consumerRepository.findById(consumer.getUserId()).orElse(null);
        var consumerInfoDO = consumerInfoRepository.findById(consumer.getUserId()).orElse(null);
        if (consumerDO == null || consumerInfoDO == null){
            log.warn("用户不存在:{}",consumer.getUserId());
            return;
        }
        if (consumerRepository.findByUsername(consumerUpdateDTO.getUsername()) != null){
            throw new BlbException(ResultCode.USER_HAS_EXISTED);
        }
        consumerDO.setUsername(consumerUpdateDTO.getUsername());
        consumerDO.setPhone(consumerUpdateDTO.getPhone());
        consumerRepository.save(consumerDO);

        consumerInfoDO.setAvatar(consumerUpdateDTO.getAvatar());
        consumerInfoRepository.save(consumerInfoDO);
    }

    @Override
    public void updatePassword(String token, String oldPassword, String newPassword) {
        var consumer = getConsumer(token);
        var consumerDO = consumerRepository.findById(consumer.getUserId()).orElse(null);
        if (consumerDO == null ){
            log.warn("用户不存在:{}",consumer.getUserId());
            return;
        }

        if (!consumerDO.getPassword().equals(DigestUtils.md5Hex(oldPassword))){
            log.warn("修改密码 密码不一致");
            return;
        }
        consumerDO.setPassword(DigestUtils.md5Hex(newPassword));
        consumerRepository.save(consumerDO);
    }
}
