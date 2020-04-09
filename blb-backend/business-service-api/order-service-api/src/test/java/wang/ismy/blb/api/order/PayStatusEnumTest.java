package wang.ismy.blb.api.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wang.ismy.blb.api.order.enums.PayStatusEnum;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PayStatusEnumTest {

    /**
     * 防止枚举被意外修改
     */
    @Test
    @DisplayName("保证支付状态枚举行为")
    void protectEnum() {
        var list = List.of("等待支付", "已支付", "已取消");
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), PayStatusEnum.valueOf(i).getMsg());
        }
    }
}