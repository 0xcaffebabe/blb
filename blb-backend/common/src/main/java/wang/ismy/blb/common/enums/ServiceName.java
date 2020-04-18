package wang.ismy.blb.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MY
 * @date 2020/4/13 10:01
 */

public interface ServiceName {
    long DATA_CENTER_ID = 1L;
    String CACHE_SERVICE = "cache-service";
    String AUTH_SERVICE = "auth-service";

    String CART_SERVICE = "cart-service";
    String PRODUCT_SERVICE = "product-service";
    long PRODUCT_SERVICE_ID = 4L;
    String ORDER_SERVICE = "order-service";
    String SHOP_SERVICE = "shop-service";
}
