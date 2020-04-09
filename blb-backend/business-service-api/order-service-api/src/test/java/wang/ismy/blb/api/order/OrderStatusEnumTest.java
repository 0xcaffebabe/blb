package wang.ismy.blb.api.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wang.ismy.blb.api.order.enums.OrderStatusEnum;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusEnumTest {

    /**
     * 防止枚举被意外修改
     */
    @Test
    @DisplayName("保证订单状态枚举行为")
    void protectEnum() {
        var list = List.of("未处理","已处理","配送中","已完结","已作废");
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i), OrderStatusEnum.valueOf(i).getMsg());
        }
    }
}