package wang.ismy.blb.api.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MY
 * @date 2020/4/11 15:13
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    /** 订餐者角色*/
    CONSUMER(0,"订餐者"),
    /** 商家角色 */
    SELLER(1,"商家"),
    /** 骑手角色 */
    RIDER(2,"骑手"),
    /** 管理员角色 */
    ADMIN(3,"管理员");
    private Integer code;
    private String type;
}
