package wang.ismy.blb.api.pay.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PayTypeEnumTest {

    @Test
    @DisplayName("保护支付类型枚举不被意外修改")
    void protect() {
        List<String> list = List.of("支付宝");
        for (int i = 0; i < list.size(); i++) {
            assertEquals(list.get(i),PayTypeEnum.valueOf(i).getType());
        }
    }
}