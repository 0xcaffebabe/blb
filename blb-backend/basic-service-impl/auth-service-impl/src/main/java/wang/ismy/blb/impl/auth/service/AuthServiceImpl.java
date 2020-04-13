package wang.ismy.blb.impl.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.cache.CacheService;
import wang.ismy.blb.impl.auth.config.AuthConfigProperties;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * @author MY
 * @date 2020/4/13 9:57
 */
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private CacheService cacheService;
    private AuthConfigProperties configProperties;

    @Nullable
    @Override
    public String auth(User user) {
        String token = getKey(user);
        if (cacheService.put(token, user, configProperties.getTokenTtl())){
            return token;
        }
        return null;
    }

    @Nullable
    @Override
    public User valid(String token) {
        return cacheService.get(token,User.class);
    }

    private String getKey(User user){
        return "user-token" + "-" + UUID.randomUUID().toString();
    }
}
