package wang.ismy.blb.impl.consumer.service;

import wang.ismy.blb.api.consumer.pojo.dto.*;

import java.util.List;
import java.util.Map;

/**
 * 消费者服务应该提供的接口
 * @author MY
 * @date 2020/4/21 8:59
 */
public interface ConsumerService {
    /**
     * 订餐者注册
     * @param registerDTO
     * @return
     */
    RegisterResultDTO register(RegisterDTO registerDTO);

    /**
     * 订餐者登录
     * @param loginStr
     * @param password
     * @return
     */
    LoginResultDTO login(String loginStr, String password);

    /**
     * 当前登录订餐者获取信息
     * @param token
     * @return
     */
    ConsumerDTO getInfo(String token);

    /**
     * 根据订餐者ID获取订餐者信息
     * @param consumerId
     * @return
     */
    ConsumerDTO getInfo(Long consumerId);

    /**
     * 根据订餐者ID批量获取订餐者信息
     * @param consumerIdList
     * @return
     */
    Map<Long,ConsumerDTO> getInfo(List<Long> consumerIdList);

    /**
     * 更新当前登录订餐者信息
     * @param token
     * @param consumerUpdateDTO
     */
    void updateInfo(String token, ConsumerUpdateDTO consumerUpdateDTO);

    /**
     * 当前登录订餐者修改密码
     * @param token
     * @param oldPassword
     * @param newPassword
     */
    void updatePassword(String token, String oldPassword, String newPassword);
}
