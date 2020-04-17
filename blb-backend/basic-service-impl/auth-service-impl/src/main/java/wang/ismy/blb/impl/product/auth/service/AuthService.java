package wang.ismy.blb.impl.product.auth.service;

import wang.ismy.blb.api.auth.User;

import javax.annotation.Nullable;

/**
 * @author MY
 * @date 2020/4/13 8:49
 */
public interface AuthService {

    /**
     * 用户认证，传入一个用户对象，将用户信息进行存储
     * @param user
     * @return 返回token
     */
    @Nullable
    String auth(User user);

    /**
     * 根据传入的token，获取相对应的user
     * @param token
     * @return token不存在返回null
     */
    @Nullable
    User valid(String token);
}
